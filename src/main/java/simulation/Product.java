package simulation;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

public class Product extends RecursiveTreeObject<Product> implements Serializable {
    private String name;
    private String Photo;
    private String description;
    private String releaseDate;
    private int runtime;
    private String country;
    private Double rating;
    private Double prise;
    private int watched;
    private String licenseNR;

    public Product() {}



    public Product(String name, String description, String releaseDate, int length, String country, Double rating, Double prise, String licenseNR) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.runtime = length;

        this.country = country;
        this.rating = rating;
        this.prise = prise;
        this.licenseNR = licenseNR;
        this.watched = 0;
    }


    public int getWatched() {
        return watched;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductionDate() {
        return releaseDate;
    }

    public void setProductionDate(String productionDate) {
        this.releaseDate = productionDate;
    }

    public int getLength() {
        return runtime;
    }

    public void setLength(int length) {
        this.runtime = length;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Double getPrise() {
        return prise;
    }

    public void setPrise(Double prise) {
        this.prise = prise;
    }

    public String getLicenseNR() {
        return licenseNR;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
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

    public void setLicenseNR(String licenseNR) {
        this.licenseNR = licenseNR;
    }

    public void watch(){
        this.watched++;
    }
}
