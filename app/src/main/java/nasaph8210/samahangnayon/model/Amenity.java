package nasaph8210.samahangnayon.model;

import com.google.gson.annotations.SerializedName;

public class Amenity {
    @SerializedName("AmenitiesId")
    private int amenitiesId;
    @SerializedName("Name")
    private String name;
    @SerializedName("Price")
    private int price;

    public Amenity() {
    }

    public int getAmenitiesId() {
        return amenitiesId;
    }

    public void setAmenitiesId(int amenitiesId) {
        this.amenitiesId = amenitiesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
