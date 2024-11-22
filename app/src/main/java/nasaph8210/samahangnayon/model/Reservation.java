package nasaph8210.samahangnayon.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Reservation {

    @SerializedName("ReservationId")
    private int reservationId;

    @SerializedName("GuestId")
    private int guestId;

    @SerializedName("RoomId")
    private int roomId;

    @SerializedName("DateCreated")
    private String dateCreated;

    @SerializedName("TimeCreated")
    private String timeCreated;

    @SerializedName("DateCheckIn")
    private String dateCheckIn;

    @SerializedName("DateCheckOut")
    private String dateCheckOut;

    @SerializedName("TotalCost")
    private String totalCost;

    @SerializedName("Status")
    private String status;

    @SerializedName("TotalAdult")
    private int totalAdult;

    @SerializedName("TotalChildren")
    private int totalChildren;

    @SerializedName("payments")
    private List<Payment> payments;

    @SerializedName("room_number")
    private ReservationRoom room;

    @SerializedName("reservation_amenities")
    private List<ReservationAmenity> reservationAmenities;

    @SerializedName("sub_guests")
    private List<ReservationSubGuest> subGuests;

    public Reservation() {
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }

    public String getDateCheckIn() {
        return dateCheckIn;
    }

    public void setDateCheckIn(String dateCheckIn) {
        this.dateCheckIn = dateCheckIn;
    }

    public String getDateCheckOut() {
        return dateCheckOut;
    }

    public void setDateCheckOut(String dateCheckOut) {
        this.dateCheckOut = dateCheckOut;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalAdult() {
        return totalAdult;
    }

    public void setTotalAdult(int totalAdult) {
        this.totalAdult = totalAdult;
    }

    public int getTotalChildren() {
        return totalChildren;
    }

    public void setTotalChildren(int totalChildren) {
        this.totalChildren = totalChildren;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public ReservationRoom getRoom() {
        return room;
    }

    public void setRoom(ReservationRoom room) {
        this.room = room;
    }

    public List<ReservationAmenity> getReservationAmenities() {
        return reservationAmenities;
    }

    public void setReservationAmenities(List<ReservationAmenity> reservationAmenities) {
        this.reservationAmenities = reservationAmenities;
    }

    public List<ReservationSubGuest> getSubGuests() {
        return subGuests;
    }

    public void setSubGuests(List<ReservationSubGuest> subGuests) {
        this.subGuests = subGuests;
    }
}
