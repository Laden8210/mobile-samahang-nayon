package nasaph8210.samahangnayon.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Room implements Parcelable {

    public static String INTENT_NAME = "ROOM";

    @SerializedName("room_number_id")
    public long room_number_id;

    @SerializedName("RoomId")
    private Long roomId;

    @SerializedName("RoomType")
    private String roomType;

    @SerializedName("room_number")
    private Integer roomNumber;

    @SerializedName("Capacity")
    private Integer capacity;

    @SerializedName("RoomPrice")
    private Double roomPrice;

    @SerializedName("Status")
    private String status;

    @SerializedName("Description")
    private String description;

    @SerializedName("PromotionId")
    private Long promotionId;

    @SerializedName("Promotion")
    private String promotion;

    @SerializedName("discount")
    private Integer discount;

    @SerializedName("StartDate")
    private String startDate;

    @SerializedName("EndDate")
    private String endDate;

    @SerializedName("DateCreated")
    private String dateCreated;

    @SerializedName("StartingDate")
    private String startingDate;

    @SerializedName("EndingDate")
    private String endingDate;

    public Room() {
    }

    public Room(long room_number_id, Long roomId, String roomType, Integer roomNumber, Integer capacity, Double roomPrice, String status, String description, Long promotionId, String promotion, Integer discount, String startDate, String endDate, String dateCreated, String startingDate, String endingDate) {
        this.room_number_id = room_number_id;
        this.roomId = roomId;
        this.roomType = roomType;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.roomPrice = roomPrice;
        this.status = status;
        this.description = description;
        this.promotionId = promotionId;
        this.promotion = promotion;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dateCreated = dateCreated;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
    }

    protected Room(Parcel in) {
        room_number_id = in.readLong();
        if (in.readByte() == 0) {
            roomId = null;
        } else {
            roomId = in.readLong();
        }
        roomType = in.readString();
        if (in.readByte() == 0) {
            roomNumber = null;
        } else {
            roomNumber = in.readInt();
        }
        if (in.readByte() == 0) {
            capacity = null;
        } else {
            capacity = in.readInt();
        }
        if (in.readByte() == 0) {
            roomPrice = null;
        } else {
            roomPrice = in.readDouble();
        }
        status = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            promotionId = null;
        } else {
            promotionId = in.readLong();
        }
        promotion = in.readString();
        if (in.readByte() == 0) {
            discount = null;
        } else {
            discount = in.readInt();
        }
        startDate = in.readString();
        endDate = in.readString();
        dateCreated = in.readString();
        startingDate = in.readString();
        endingDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(room_number_id);
        if (roomId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(roomId);
        }
        dest.writeString(roomType);
        if (roomNumber == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(roomNumber);
        }
        if (capacity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(capacity);
        }
        if (roomPrice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(roomPrice);
        }
        dest.writeString(status);
        dest.writeString(description);
        if (promotionId == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(promotionId);
        }
        dest.writeString(promotion);
        if (discount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(discount);
        }
        dest.writeString(startDate);
        dest.writeString(endDate);
        dest.writeString(dateCreated);
        dest.writeString(startingDate);
        dest.writeString(endingDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Room> CREATOR = new Creator<Room>() {
        @Override
        public Room createFromParcel(Parcel in) {
            return new Room(in);
        }

        @Override
        public Room[] newArray(int size) {
            return new Room[size];
        }
    };

    public static String getIntentName() {
        return INTENT_NAME;
    }

    public static void setIntentName(String intentName) {
        INTENT_NAME = intentName;
    }

    public long getRoom_number_id() {
        return room_number_id;
    }

    public void setRoom_number_id(long room_number_id) {
        this.room_number_id = room_number_id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(Double roomPrice) {
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

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotion() {
        return promotion;
    }

    public void setPromotion(String promotion) {
        this.promotion = promotion;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(String startingDate) {
        this.startingDate = startingDate;
    }

    public String getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(String endingDate) {
        this.endingDate = endingDate;
    }
}
