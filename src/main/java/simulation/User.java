package simulation;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class User extends RecursiveTreeObject<User> implements Runnable, Serializable {
    private int id;
    private String password;
    private String email;
    private String bankAccount;
    private Subscription subscription;
    private String birthDate = "25.02.1998";
    private List<Product> boughtProductList;
    private List<Episode> boughtEpisodeList;
    private  List<LiveStream> boughtLivesList;
    private int payment ;
    private boolean isRunning = true;

    public void buySubscription(){
        this.subscription = SubscriptionManager.getRandomSubscription();
        this.payment += this.subscription.getPrice();
    }
    public void buyLiveStream(){
        ProductMenager productMenager = ProductMenager.getInstance();

        if(productMenager.getLiveStreamList().size() == 0){return;}
        LiveStream liveStream = productMenager.getLiveStreamList().get(new Random().nextInt(productMenager.getLiveStreamList().size()));
        if (Integer.parseInt(liveStream.getReleaseDate()) >= Time.getInstance().getDayNumber()){ //if it's not too late buy it
            this.boughtLivesList.add(liveStream);
            Wallet.getINSTANCE().addSum(liveStream.getPrise());
            System.out.println(this.id + "Buying" + liveStream.getName());
        }
    }
    public void buyProduct(ProductMenager productMenager) {
        if(productMenager.getProductList().size() == 0){return;}
        buyLiveStream();

        Product product = productMenager.getProductList().get(new Random().nextInt(productMenager.getProductList().size()));
        if (product instanceof Series) {
            boughtEpisodeList.add(((Series) product).getSeason().getEpisode());
            System.out.println(this.id + "Buying Episode" + product.getName());
            Wallet.getINSTANCE().addSum(this.boughtEpisodeList.get(this.boughtEpisodeList.size()-1).getPrice());
        } else {
            System.out.println(this.id + "Buying" + product.getName());
            this.boughtProductList.add(product);
            Wallet.getINSTANCE().addSum(product.getPrise());
        }
    }

    public int getId() {
        return id;
    }

    public User() {
        this.id = new Random().nextInt(100000);
        System.out.println(this.id + "Hi, I'm new User\n");
        this.password = "password";
        this.email = "email";
        this.bankAccount = "";
        for(int i = 0; i<4; i++) {
            int bank = new Random().nextInt(899999) + 100000;
            this.bankAccount += bank;
        }
        this.boughtProductList = new ArrayList<>();
        this.boughtLivesList = new ArrayList<>();
        buySubscription();
        System.out.println(this.id + "My sub : " + this.subscription.getType().toString());

    }
    public void watchLiveStream(){
        for (LiveStream liveStream: this.boughtLivesList
             ) {
            if(Integer.parseInt(liveStream.getReleaseDate()) == Time.getInstance().getDayNumber()){
                liveStream.watch();
                boughtLivesList.remove(liveStream);
            }
        }
    }

    public void watch(ProductMenager productMenager) {
        //System.out.println("xxxxxxxxxxxxxxxxxx  Trying To Watch  xxxxxxxxxxxxx");
        if (this.subscription.getType() == SubscriptionType.NULL) {
            if (this.boughtProductList.size() == 0) {
                return;
            }
            this.boughtProductList.get(new Random().nextInt(this.boughtProductList.size())).watch();
            return;
        }

            if(ProductMenager.getInstance().getProductList().size() > 0){
                System.out.println(ProductMenager.getInstance().getProductList().size());
                Product product = productMenager.getProductList().get(new Random().nextInt(productMenager.getProductList().size()));
                product.watch();
                System.out.println(this.id + "Watched : " + product.getName());

            }

    }
    public int getPayment(){
       return this.payment;
    }

    public void kill(){
        isRunning = false;
    }
    public void start(){
        isRunning = true;
    }

    @Override
    public void run() {
        while(Simulation.getInstance().isRun() && isRunning){
            watchLiveStream();

            if(new Random().nextInt(100)<99){
                watch(ProductMenager.getInstance());

            }
            if (new Random().nextInt(100) < 20) {
                buyLiveStream();
            }


            if (this.subscription.getType() == SubscriptionType.NULL) {
                if (new Random().nextInt(100) < 90) {
                    buyProduct(ProductMenager.getInstance());
                }
            }

            try {
                Thread.sleep(new Random().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
