package nasaph8210.samahangnayon.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.api.PostCallback;
import nasaph8210.samahangnayon.api.PostTask;
import nasaph8210.samahangnayon.model.OtpResponse;
import nasaph8210.samahangnayon.util.Loader;

public class ForgotPasswordActivity extends AppCompatActivity implements PostCallback {

    private TextInputEditText etEmail;
    private TextInputEditText etOtp;
    private TextInputLayout tilEmail;
    private TextInputLayout tilOtp;
    private Button btnSendOtp;
    private Button btnVerifyOtp;

    private String token;


    private Loader loader;
    private OtpResponse otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Initialize TextInputLayout and TextInputEditText
        tilEmail = findViewById(R.id.til_email);
        etEmail = findViewById(R.id.et_email);
        tilOtp = findViewById(R.id.til_otp);
        etOtp = findViewById(R.id.et_otp);

        btnSendOtp = findViewById(R.id.button);
        btnVerifyOtp = findViewById(R.id.button_verify);

        loader = new Loader();

        btnSendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOtp();
            }
        });

        btnVerifyOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOtp();
            }
        });
    }

    private void sendOtp() {
        String phoneNumber = etEmail.getText().toString().trim();
        if (phoneNumber.isEmpty()) {
            tilEmail.setError("Please enter your phone number");
            return;
        }


        loader.showLoader(this);
        try {
            JSONObject json = new JSONObject();
            json.put("contactnumber", phoneNumber);

            new PostTask(this, this, "error", "requestOtp").execute(json);
        }catch (Exception e){
            tilEmail.setError("Please enter a valid phone number");
            return;
        }

        tilEmail.setError(null); // Clear error

    }

    private void verifyOtp() {
        int enterOTP =Integer.valueOf( etOtp.getText().toString().trim());
        if (otp == null) {
            tilOtp.setError("Please enter the OTP");
            return;
        }
        if (enterOTP != otp.getOtp()) {
            tilOtp.setError("Invalid OTP");
            return;
        }
        tilOtp.setError(null);
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        intent.putExtra("token", otp.getToken());
        startActivity(intent);
    }

    @Override
    public void onPostSuccess(String responseData) {
        Gson gson = new Gson();
       otp = gson.fromJson(responseData, OtpResponse.class);

       loader.dismissLoader();

    }

    @Override
    public void onPostError(String errorMessage) {
        // Handle error response
        Toast.makeText(this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
    }
}
