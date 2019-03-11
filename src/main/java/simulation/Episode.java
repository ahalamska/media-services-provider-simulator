package simulation;

import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class Episode implements Serializable {
    private String releaseDate;
    private int runtime;
    private int price;

    public Episode() {
        this.releaseDate = "25.02.1998";
        this.runtime = new Random().nextInt(30)+30;
        this.price = 5;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public int getPrice() {
        return price;
    }
}
