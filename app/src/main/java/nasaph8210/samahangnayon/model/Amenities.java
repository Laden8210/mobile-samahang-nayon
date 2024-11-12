package nasaph8210.samahangnayon.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Amenities implements Parcelable {

    @SerializedName("AmenitiesId")
    private Long amenitiesId;
    @SerializedName("Name")
    private String name;
    @SerializedName("Price")
    private Double price;

    public Amenities() {
    }
    public Amenities(Long amenitiesId, String name, Double price) {
        this.amenitiesId = amenitiesId;
        this.name = name;
        this.price = price;
    }

    protected Amenities(Parcel in) {
        if (in.readByte() == 0) {
            amenitiesId = null;
        } else {
            amenitiesId = in.readLong();
        }
        name = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
    }

    public static final Creator<Amenities> CREATOR = new Creator<Amenities>() {
        @Override
        public Amenities createFromParcel(Parcel in) {
            return new Amenities(in);
        }

        @Override
        public Amenities[] newArray(int size) {
            return new Amenities[size];
        }
    };

    public Long getAmenitiesId() {
        return amenitiesId;
    }

    public void setAmenitiesId(Long amenitiesId) {
        this.amenitiesId = amenitiesId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        if (amenitiesId == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(amenitiesId);
        }
        parcel.writeString(name);
        if (price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(price);
        }
    }
}
