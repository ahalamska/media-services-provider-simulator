package simulation;

import java.io.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Simulation implements Serializable, Runnable {
    private static Simulation INSTANCE;
    public static Simulation getInstance(){
        if(INSTANCE == null){
            return INSTANCE = new Simulation();
        }
        return INSTANCE;
    }
    private transient ExecutorService userExecutor = Executors.newCachedThreadPool();
    private transient ExecutorService distributorExecutor = Executors.newCachedThreadPool();
    private  ExecutorService simulationExecutor = Executors.newSingleThreadExecutor();
    private volatile boolean run = false;
    private int paused;

    public int isPaused() {
        return paused;
    }

    public void setPaused(int paused) {
        this.paused = paused;
    }

    public Simulation() {
        this.paused = 0;
    }

    public void tabulaRasa(){
        simulationExecutor = Executors.newSingleThreadExecutor();
        userExecutor = Executors.newCachedThreadPool();
        distributorExecutor = Executors.newCachedThreadPool();
        UserManager.getInstance().getUserList().forEach(user -> userExecutor.submit(user));
        DistributorManager.getInstance().getDistributorList().forEach(distributor -> distributorExecutor.submit(distributor));
        userExecutor.submit(this);



    }

    public void serialize(){
        UserManager.getInstance().killAll();
        UserManager.getInstance().serialize();
        Time.getInstance().serialize();
        DistributorManager.getInstance().serialize();
        ProductMenager.getInstance().serialize();
        SaleManager.getInstance().serialize();
        Wallet.getINSTANCE().serialize();


        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("simulation.data"));
            outputStream.writeObject(INSTANCE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserialize(){
        UserManager.getInstance().deserialize();
        DistributorManager.getInstance().deserialize();
        ProductMenager.getInstance().deserialize();
        SaleManager.getInstance().deserialize();
        Wallet.getINSTANCE().deserialize();
        Time.getInstance().deserialize();
        try {
            ObjectInputStream  inputStream = new ObjectInputStream(new FileInputStream("simulation.data"));
            INSTANCE = (Simulation) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        tabulaRasa();
        UserManager.getInstance().startAll();
    }

    public synchronized void setRun(boolean var){
        run = var;
    }

    public synchronized boolean isRun(){
        return run;
    }

        public void stop(){
            simulationExecutor.shutdown();
            userExecutor.shutdown();
            distributorExecutor.shutdown();
            try {
                simulationExecutor.awaitTermination(3000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!simulationExecutor.isShutdown()){
                simulationExecutor.shutdownNow();
            }
            try {
                userExecutor.awaitTermination(3000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!userExecutor.isShutdown()){
                userExecutor.shutdownNow();
            }
            try {
                distributorExecutor.awaitTermination(3000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!distributorExecutor.isShutdown()){
                distributorExecutor.shutdownNow();
            }
            simulationExecutor = Executors.newSingleThreadExecutor();
            userExecutor = Executors.newCachedThreadPool();
            distributorExecutor = Executors.newCachedThreadPool();
        }

    public void pause(){
        setRun(false);
        serialize();
        System.out.println("Requesting pause...");
        stop();

        System.out.println("Pausing complete");
        System.out.println("DAY : " + Time.getInstance().getDayNumber());
        this.paused += 1;

    }
    public void resume (){
        this.paused += 1;
        System.out.println("DAY : " + Time.getInstance().getDayNumber());
        deserialize();
        simulationExecutor = Executors.newSingleThreadExecutor();
       // Simulation.getInstance().start();


    }
    public void end(){
        setRun(false);
        System.out.println("Requesting shutdown...");
        stop();
        System.out.println("Shutdown complete");
    }
    public void addDistributor(){
        Distributor newDistributor = new Distributor();
        DistributorManager.getInstance().getDistributorList().add(newDistributor);
        distributorExecutor.submit(newDistributor);
    }
    public void addUser(){
        User newUser = new User();
        UserManager.getInstance().getUserList().add(newUser);
        userExecutor.submit(newUser);
    }

    @Override
    public void run() {
        setRun(true);
        Runnable task = () -> {
            System.out.println("Starting...");
            while (isRun()) {

                System.out.println("/////////////////////  DAY : " + Time.getInstance().getDayNumber() + "///////////////////////////");

                /*if ((new Random().nextInt(100) < 20 && DistributorManager.getInstance().getDistributorList().size() < 15) || Time.getInstance().getDayNumber() ==1) {
                    Distributor newDistributor = new Distributor();
                    DistributorManager.getInstance().getDistributorList().add(newDistributor);
                    distributorExecutor.submit(newDistributor);

                }
                */
                if (new Random().nextInt(100) < 10 * DistributorManager.getInstance().getDistributorList().size() && UserManager.getInstance().getUserList().size() < 300) {
                    User newUser = new User();
                    UserManager.getInstance().addUser(newUser);
                    userExecutor.submit(newUser);
                }

                if (new Random().nextInt(100) < 10) {
                    SaleManager.getInstance().addSale();
                }


                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Time.getInstance().getDayNumber() % 30 == 0 && Time.getInstance().getDayNumber() != 0) {
                    System.out.println("PAYMENT TIME\n");
                    Wallet.getINSTANCE().addSum(UserManager.getInstance().getPayment());
                    Wallet.getINSTANCE().addSum(-DistributorManager.getInstance().countPayment());

                }
                System.out.println(Wallet.getINSTANCE().getSum());
                SaleManager.getInstance().checkIfUpdated();
                Time.getInstance().nextDay();

            }

        };
        simulationExecutor.submit(task);
    }
}
