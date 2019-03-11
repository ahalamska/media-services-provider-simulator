package simulation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ProductMenager implements Serializable {
    private static ProductMenager INSTANCE = new ProductMenager();

    public synchronized static ProductMenager getInstance(){
        if(INSTANCE == null){
            return INSTANCE = new ProductMenager();
        }
        return INSTANCE;
    }
    private List<Product> productList = new ArrayList<>();
    private List<LiveStream> liveStreamList = new ArrayList<>();

    public ProductMenager() {
    }
    public Product getRandomProduct(){
        return this.productList.get(new Random().nextInt(this.productList.size()));
    }

    public void addProduct(Product product) {
        productList.add(product);
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Product search (String id){
        Product r = new Product();
        for (Product p : this.productList){
            if(p.getLicenseNR() == id ){
                r = p;

            }
        }
        return r;
    }

    public List<LiveStream> getLiveStreamList() {
        return liveStreamList;
    }

    public synchronized void serialize(){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("products.data"));
            outputStream.writeObject(INSTANCE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deserialize(){
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("products.data"));
            INSTANCE = (ProductMenager) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


