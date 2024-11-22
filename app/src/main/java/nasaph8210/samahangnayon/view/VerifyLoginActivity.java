package nasaph8210.samahangnayon.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONObject;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.api.PostCallback;
import nasaph8210.samahangnayon.api.PostTask;
import nasaph8210.samahangnayon.model.VerifyOTP;
import nasaph8210.samahangnayon.util.Loader;
import nasaph8210.samahangnayon.util.Messenger;
import nasaph8210.samahangnayon.util.SessionManager;

public class VerifyLoginActivity extends AppCompatActivity implements PostCallback {

    private Button btnVerify, btnRequestSMS, btnRequestEmail;
    private TextInputEditText etOTP;

    private VerifyOTP verifyOTP;
    private Loader loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_verify_login);

        btnRequestSMS = findViewById(R.id.change_verify_sms);
        btnRequestEmail= findViewById(R.id.change_verify_email);
        btnVerify = findViewById(R.id.button_verify);

        etOTP = findViewById(R.id.et_otp);

        loader = new Loader();

        btnRequestEmail.setOnClickListener(this::sendEmailOTP);
        btnRequestSMS.setOnClickListener(this::sendSMSOTP);
        btnVerify.setOnClickListener(this::verifyLoginAction);

    }

    private void verifyLoginAction(View view) {

        if (verifyOTP != null) {
            try {
                // Parse the input OTP
                int otp = Integer.parseInt(etOTP.getText().toString());

                // Check if the input OTP matches the server-provided OTP
                if (verifyOTP.getOtp() == otp) {

                    Messenger.showAlertDialog(this, "Verification Successful", "Your OTP has been verified successfully.", "Ok").show();
                    SessionManager.getInstance(this).setVerified(true);
                    startActivity(new Intent(this, HeroActivity.class));
                    return;
                } else {

                    Messenger.showAlertDialog(this, "Verification Error", "The OTP you entered is incorrect. Please try again.", "Ok").show();
                    return;
                }
            } catch (NumberFormatException e) {

                Messenger.showAlertDialog(this, "Verification Error", "Invalid OTP format. Please enter a numeric OTP.", "Ok").show();
                return;
            }
        }

        // Handle the case where verifyOTP is null
        Messenger.showAlertDialog(this, "Verification Error", "Please request an OTP before confirming.", "Ok").show();
    }


    private void sendSMSOTP(View view) {
        loader.showLoader(this);
        try {
            JSONObject object = new JSONObject();
            object.put("type", "SMS");
            new PostTask(this, this, "error", "verify-login").execute(object);
        }catch (Exception e){

        }
    }

    private void sendEmailOTP(View view) {
        loader.showLoader(this);
        try {
            JSONObject object = new JSONObject();
            object.put("type", "EMAIL");
            new PostTask(this, this, "error", "verify-login").execute(object);
        }catch (Exception e){

        }
    }

    @Override
    public void onPostSuccess(String responseData) {
        Gson gson = new Gson();
        verifyOTP = gson.fromJson(responseData, VerifyOTP.class);
        loader.dismissLoader();
    }

    @Override
    public void onPostError(String errorMessage) {
        loader.dismissLoader();
    }
}