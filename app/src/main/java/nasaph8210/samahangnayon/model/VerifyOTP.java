package nasaph8210.samahangnayon.model;

import com.google.gson.annotations.SerializedName;

public class VerifyOTP {

    @SerializedName("message")
    private String message;
    @SerializedName("otp")
    private int otp;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
}
