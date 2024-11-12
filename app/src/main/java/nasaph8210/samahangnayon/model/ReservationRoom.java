package nasaph8210.samahangnayon.model;

import com.google.gson.annotations.SerializedName;

public class ReservationRoom {

    @SerializedName("RoomId")
    private int roomId;

    @SerializedName("RoomType")
    private String roomType;

    @SerializedName("room_number")
    private int roomNumber;

    @SerializedName("Capacity")
    private int capacity;

    @SerializedName("RoomPrice")
    private String roomPrice;

    @SerializedName("Status")
    private String status;

    @SerializedName("Description")
    private String description;

    @SerializedName("room")
    private Room room;

    public ReservationRoom() {
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(String roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}