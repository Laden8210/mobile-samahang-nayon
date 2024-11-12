package nasaph8210.samahangnayon.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Transaction implements Parcelable {

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

    @SerializedName("room_number")
    private ReservationRoom room;
    public Transaction() {
    }

    protected Transaction(Parcel in) {
        reservationId = in.readInt();
        guestId = in.readInt();
        roomId = in.readInt();
        dateCreated = in.readString();
        timeCreated = in.readString();
        dateCheckIn = in.readString();
        dateCheckOut = in.readString();
        totalCost = in.readString();
        status = in.readString();
        totalAdult = in.readInt();
        totalChildren = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(reservationId);
        dest.writeInt(guestId);
        dest.writeInt(roomId);
        dest.writeString(dateCreated);
        dest.writeString(timeCreated);
        dest.writeString(dateCheckIn);
        dest.writeString(dateCheckOut);
        dest.writeString(totalCost);
        dest.writeString(status);
        dest.writeInt(totalAdult);
        dest.writeInt(totalChildren);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

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

    public ReservationRoom getRoom() {
        return room;
    }

    public void setRoom(ReservationRoom room) {
        this.room = room;
    }
}
