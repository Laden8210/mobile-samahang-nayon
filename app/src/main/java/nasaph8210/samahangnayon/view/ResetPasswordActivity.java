package nasaph8210.samahangnayon.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.api.PostCallback;
import nasaph8210.samahangnayon.api.PostTask;
import nasaph8210.samahangnayon.util.Loader;
import nasaph8210.samahangnayon.util.Messenger;

public class ResetPasswordActivity extends AppCompatActivity implements PostCallback {


    private TextInputLayout tilPassword, tilcPassword;
    private Button btnResetPassword;

    private String token;


    private Loader loader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if(getIntent().hasExtra("token")){
            token = getIntent().getStringExtra("token");
        }
        loader = new Loader();


        setContentView(R.layout.activity_reset_password);

        tilPassword = findViewById(R.id.til_password);
        tilcPassword = findViewById(R.id.til_cpassword);

        btnResetPassword = findViewById(R.id.btnConfirm);

        btnResetPassword.setOnClickListener(this::confirmPassword);
    }

    private void confirmPassword(View view) {
        String newPassword = tilPassword.getEditText().getText().toString().trim();
        String confirmPassword = tilcPassword.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(newPassword)) {
            tilPassword.setError("Password is required");
            return;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            tilcPassword.setError("Confirm Password is required");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            tilcPassword.setError("Passwords do not match");
            return;
        }

        try {
            loader.showLoader(this);
            JSONObject object = new JSONObject();
            object.put("password", newPassword);
            object.put("token", token);

            new PostTask(this, this, "error", "changePassword").execute(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostSuccess(String responseData) {
        loader.dismissLoader();
        Messenger.showAlertDialog(this, "Password change", "Password successfully change", "Ok").show();
        startActivity(new Intent(this, LoginActivity.class));
    }

    @Override
    public void onPostError(String errorMessage) {
        loader.dismissLoader();
        Messenger.showAlertDialog(this, "Password change", errorMessage, "Ok").show();

    }
}