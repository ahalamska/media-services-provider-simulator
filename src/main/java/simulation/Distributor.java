package simulation;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import java.io.Serializable;
import java.util.Random;

public class Distributor extends RecursiveTreeObject<Distributor> implements Runnable, Serializable {

    private int number;
    private Licence licence;
    private String bankAccount;
    private double bid;
    private double payedwatches;

    /**
     * This method does nothing
     */
    public Distributor() {
        this.number = new Random().nextInt(899999999)+100000000;
        this.bankAccount = "";
        for(int i = 0; i<4; i++) {
            int bank = new Random().nextInt(899999) + 100000;
            this.bankAccount += bank;
        }
        this.licence = new Licence();
        this.bid = new Random().nextDouble();
        this.payedwatches = 0;

    }

    public double getBid() {
        return bid;
    }

    public synchronized void addProduct(){
       Product p = Cache.getInstance().addToListOfProducts();
        System.out.println(this.number + "Added " + p.getName());
        this.licence.addProduct(p);

    }
    public void addLiveStream(){
        LiveStream liveStream = Cache.getInstance().addLiveToListOfProducts();
        System.out.println(this.number + "Added " + liveStream.getName());
        this.licence.addProduct(liveStream);
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Licence getLicence() {
        return licence;
    }

    public void setLicence(Licence licence) {
        this.licence = licence;
    }

    public double getPayment() {
        double watches = this.licence.countWatches() - this.payedwatches;
        this.payedwatches += watches;
        return (watches*this.bid);
    }
    public void negotiateBid(){
        this.bid = new Random().nextDouble();
        System.out.println(this.number+ "negotiateBid");
    }


    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    @Override
    public void run() {
        System.out.println(this.number + "Hi, I'm new Distributor");
        while(Simulation.getInstance().isRun()){
            if(new Random().nextInt(100)<19){
                addProduct();
                System.out.println(this.number + "Added Product");
            }
            if(new Random().nextInt(100)<5){
                addLiveStream();
                System.out.println(this.number + "Added LiveStream");
            }
            if(new Random().nextInt(100)<4){
                negotiateBid();
            }
            try {
                Thread.sleep(new Random().nextInt(600));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.licence.countWatches();
        }
    }
}