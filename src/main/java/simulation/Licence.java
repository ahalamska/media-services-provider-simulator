//copyright Alicja Halamska 136715
package simulation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Licence implements Serializable {
    private int LicenceId;
    private List<Product> productList = new ArrayList<>();

    public int getLicenceId() {
        return LicenceId;
    }


    public Licence(){
            this.LicenceId = new Random().nextInt(89999)+10000;
     }

    /**
     * Method that adds product to list
     * @param product the product to be added
     */
    public void addProduct(Product product){
        this.productList.add(product);
    }

    /**
     * This method counts payment or smth
     * @return payment sum
     */
    public double countWatches(){
        double watches = 0;
        for (Product product:this.productList) {
            watches+=product.getWatched();
        }
        return  watches;
    }
}
