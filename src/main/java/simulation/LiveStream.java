package simulation;

import java.io.Serializable;
import java.util.Date;

public class LiveStream extends Product implements Serializable {

    public LiveStream(String name, String description, String releaseDate, int length,  String country, Double rating, Double prise, String licenseNR) {
        super(name, description, releaseDate, length,  country, rating, prise, licenseNR);
    }

}
