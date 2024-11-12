package nasaph8210.samahangnayon.model;

import com.google.gson.annotations.SerializedName;

public class OtpResponse {

    @SerializedName("otp")
    private int otp;

    @SerializedName("token")
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getOtp() {
        return otp;
    }

    public void setOtp(int otp) {
        this.otp = otp;
    }
}
