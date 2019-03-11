package simulation;

import java.io.Serializable;
import java.util.Date;

public class Movie extends Product implements Serializable {
    private String genre;
    private String[] cast;
    private String trailer;
    private int timeToWatch;

    public Movie(String name, String description, String releaseDate, int length, String country, Double rating, Double prise, String licenseNR, String genre, String[] cast, String trailer, int timeToWatch) {
        super(name, description, releaseDate, length, country, rating, prise, licenseNR);
        this.genre = genre;
        this.cast = cast;
        this.trailer = trailer;
        this.timeToWatch = timeToWatch;

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
        cast = cast;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public int getTimeToWatch() {
        return timeToWatch;
    }

    public void setTimeToWatch(int timeToWatch) {
        this.timeToWatch = timeToWatch;
    }


}
