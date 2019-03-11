package simulation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Random;

@Data
@NoArgsConstructor
public class Subscription implements Serializable {
    private int price;
    private SubscriptionType type;

    public Subscription(SubscriptionType type, int price){
        this.price = price;
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public SubscriptionType getType() {
        return type;
    }
}

