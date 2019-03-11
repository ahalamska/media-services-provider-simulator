package simulation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DistributorManager implements Serializable {
    private static DistributorManager INSTANCE;

    public static DistributorManager getInstance(){
        if(INSTANCE == null){
            return INSTANCE = new DistributorManager();
        }
        return INSTANCE;
    }
    private List<Distributor> distributorList = new ArrayList<>();

    public DistributorManager() {}

    public List<Distributor> getDistributorList() {
        return distributorList;
    }

    public void setDistributorList(List<Distributor> distributorList) {
        this.distributorList = distributorList;
    }

    public double countPayment(){
        double payment = 0;
        for (Distributor d :
            this.distributorList) {
            payment+= d.getPayment();
        }
        return payment;
    }
    public void serialize(){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("distributors.data"));
            outputStream.writeObject(INSTANCE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deserialize(){
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("distributors.data"));
            INSTANCE = (DistributorManager) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}

