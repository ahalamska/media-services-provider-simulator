package simulation;

import java.io.*;

public class Time implements Serializable {
    private static Time INSTANCE = new Time();

    public synchronized static Time getInstance(){
        if(INSTANCE == null){
            return INSTANCE = new Time();
        }
        return INSTANCE;
    }
    private int dayNumber;

    public Time() {
        this.dayNumber = 0;
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void nextDay() {
        this.dayNumber +=1 ;
    }
    public void serialize(){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("time.data"));
            outputStream.writeObject(INSTANCE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deserialize(){
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("time.data"));
            INSTANCE = (Time) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
