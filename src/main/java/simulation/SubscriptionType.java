package simulation;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum SubscriptionType implements Serializable {
    BASIC(1,"720p"),
    FAMILY(3, "1080p"),
    PREMIUM(4, "4K"),
    NULL(1,"0" );


    SubscriptionType(int maxNumberOfUsers, String resolution) {
        this.maxNumberOfUsers = maxNumberOfUsers;
        this.resolution = resolution;
    }
    private int maxNumberOfUsers;
    private String resolution;

}


