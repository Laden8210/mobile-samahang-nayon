package nasaph8210.samahangnayon.model;

import com.google.gson.annotations.SerializedName;

public class ReservationAmenity {

    @SerializedName("ReservationAmenitiesId")
    private int reservationAmenitiesId;

    @SerializedName("ReservationId")
    private int reservationId;

    @SerializedName("AmenitiesId")
    private int amenitiesId;

    @SerializedName("Quantity")
    private int quantity;

    @SerializedName("TotalCost")
    private String totalCost;

    public ReservationAmenity() {
    }

    public int getReservationAmenitiesId() {
        return reservationAmenitiesId;
    }

    public void setReservationAmenitiesId(int reservationAmenitiesId) {
        this.reservationAmenitiesId = reservationAmenitiesId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getAmenitiesId() {
        return amenitiesId;
    }

    public void setAmenitiesId(int amenitiesId) {
        this.amenitiesId = amenitiesId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    private Amenity amenity;

    public Amenity getAmenity() {
        return amenity;
    }

    public void setAmenity(Amenity amenity) {
        this.amenity = amenity;
    }

}
