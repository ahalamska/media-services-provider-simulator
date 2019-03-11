package simulation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Sale implements Serializable {
    private int beginning;
    private int ending;
    private int reduction;
    private List<Product> prodactsOnSale = new ArrayList<>();

    public Sale(){
        this.reduction = new Random().nextInt(45) + 5;
        this.beginning = Time.getInstance().getDayNumber();
        this.ending = new Random().nextInt(7);
        beginSale();
    }

    public void beginSale() {
        System.out.println(" NEW SALE  -" + reduction + "%");

        for (int i = 0; i < ProductMenager.getInstance().getProductList().size() / 25; i++) {
            Product prodactOnSale = ProductMenager.getInstance().getProductList().get(new Random().nextInt(ProductMenager.getInstance().getProductList().size()));

            if (prodactOnSale instanceof Movie) {
                this.prodactsOnSale.add(ProductMenager.getInstance()
                        .getProductList()
                        .get(new Random().nextInt(ProductMenager.getInstance().getProductList().size())));
                double newPrise = (prodactOnSale.getPrise() * reduction) / 100;
                prodactOnSale.setPrise(newPrise);

            }
        }
        for (int i = 0; i < ProductMenager.getInstance().getProductList().size() / 50; i++) {
            LiveStream liveStreamOnSale = ProductMenager.getInstance().getLiveStreamList().get(new Random().nextInt(ProductMenager.getInstance().getLiveStreamList().size()));
            {
                this.prodactsOnSale.add(ProductMenager.getInstance()
                        .getProductList()
                        .get(new Random().nextInt(ProductMenager.getInstance().getProductList().size())));
                double newPrise = (liveStreamOnSale.getPrise() * reduction) / 100;
                liveStreamOnSale.setPrise(newPrise);

            }
        }
    }

    public void endSale(){
        System.out.println("Ended Sale");
        for (Product p : this.prodactsOnSale
             ) {
            double oldprise = p.getPrise()/this.reduction *100;
            p.setPrise(oldprise);
        }
    }

    public int getEnding() {
        return ending;
    }
}



