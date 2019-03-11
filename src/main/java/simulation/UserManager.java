package simulation;

import lombok.Getter;
import lombok.Setter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
public class UserManager implements Serializable {
    private List<User> userList;
    
    private static UserManager INSTANCE;
    
    public static UserManager getInstance(){
        if(INSTANCE == null){
            return INSTANCE = new UserManager();
        }
        return INSTANCE;
    }

    public void startAll(){
        userList.forEach(User::start);
    }

    public void killAll(){
        userList.forEach(User::kill);
    }

    public UserManager() {
        this.userList = new ArrayList<User>();
    }
    public int getPayment(){
        int payment = 0;
        for (User user:
             userList) {
            payment += user.getPayment();
        }
        return payment;
    }
    public void addUser(User user){
        this.userList.add(user);
    }

    public List<User> getUserList() {
        return userList;
    }
    public void serialize(){
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("users.data"));
            outputStream.writeObject(INSTANCE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void deserialize(){
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("users.data"));
            INSTANCE = (UserManager) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
