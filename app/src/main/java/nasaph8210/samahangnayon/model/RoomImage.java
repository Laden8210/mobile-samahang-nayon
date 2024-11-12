package nasaph8210.samahangnayon.model;

import com.google.gson.annotations.SerializedName;

public class RoomImage {

    @SerializedName("image")
    public String image;

    @SerializedName("mime_type")
    public String mimeType;

    public RoomImage() {
    }

    public RoomImage(String image, String mimeType) {
        this.image = image;
        this.mimeType = mimeType;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }
}
