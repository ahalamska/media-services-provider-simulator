package simulation;

import java.io.Serializable;
import java.util.Date;

public class Series extends Product implements Serializable {
    private String genre;
    private String[] cast;
    private Season season;

    public Series(String name, String description, String releaseDate, int length, String country, Double rating, Double prise, String licenseNR, String genre, String[] cast) {
        super(name, description, releaseDate, length,  country, rating, prise, licenseNR);
        this.genre = genre;
        this.cast = cast;
        this.season = new Season();
    }

    private int runtimecounter() {
        return 0;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String[] getCast() {
        return cast;
    }

    public void setCast(String[] cast) {
        this.cast = cast;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

}
