package simulation;

import lombok.Getter;

import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Getter
public class SubscriptionManager implements Serializable {
    private static Subscription BASIC = new Subscription(SubscriptionType.BASIC, 10);
    private static Subscription FAMILY = new Subscription(SubscriptionType.FAMILY, 15);
    private static Subscription PREMIUM = new Subscription(SubscriptionType.PREMIUM, 20);
    private static Subscription NULL = new Subscription(SubscriptionType.NULL, 0);
    private static List<Subscription> subscriptionList = Arrays.asList(BASIC, FAMILY, PREMIUM, NULL);

    public static Subscription getRandomSubscription(){
        return subscriptionList.get(new Random().nextInt(4));
    }

    public static Subscription getBASIC(){
        return BASIC;
    }
    public static Subscription getFAMILY(){
        return FAMILY;
    }
    public static Subscription getPREMIUM(){
        return PREMIUM;
    }
    public static Subscription getNULL(){
        return NULL;
    }
}
