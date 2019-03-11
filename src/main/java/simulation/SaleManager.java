package simulation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SaleManager implements Serializable {
    private static SaleManager INSTANCE = new SaleManager();

    public synchronized static SaleManager getInstance(){
        if(INSTANCE == null){
            return INSTANCE = new SaleManager();
        }
        return INSTANCE;
    }

    private List<Sale> saleList = new ArrayList<>();

    public SaleManager() {}

    public void addSale(){
        this.saleList.add(new Sale());
    }
    public void checkIfUpdated(){
        for (Sale sale:saleList
             ) {
            if(sale.getEnding() == Time.getInstance().getDayNumber() ){
                sale.endSale();
            }
        }
    }
    public void serialize(){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("sales.data"));
            outputStream.writeObject(INSTANCE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deserialize(){
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("sales.data"));
            INSTANCE = (SaleManager) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

