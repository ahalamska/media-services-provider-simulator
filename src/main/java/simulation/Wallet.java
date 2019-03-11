package simulation;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Wallet implements Serializable {

    private static Wallet INSTANCE;
    public static Wallet getINSTANCE(){
        if(INSTANCE == null){
        return INSTANCE = new  Wallet();
        }
        return INSTANCE;
    }
    private Double sum;
    private List<Double> transactionsHistory = new ArrayList<>();

    public Double getSum() {
        return sum;
    }
    public void addSum(double payment){
        this.sum += payment;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public List<Double> getTransactionsHistory() {
        return transactionsHistory;
    }

    public void setTransactionsHistory(List<Double> transactionsHistory) {
        this.transactionsHistory = transactionsHistory;
    }

    public Wallet() {
        this.sum = 0.0;

    }
    public void serialize(){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("wallet.data"));
            outputStream.writeObject(INSTANCE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deserialize(){
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("wallet.data"));
            INSTANCE = (Wallet) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
