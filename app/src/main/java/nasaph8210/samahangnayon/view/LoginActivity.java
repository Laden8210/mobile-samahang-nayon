    package nasaph8210.samahangnayon.view;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.TextView;

    import androidx.activity.EdgeToEdge;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.core.graphics.Insets;
    import androidx.core.view.ViewCompat;
    import androidx.core.view.WindowInsetsCompat;

    import com.google.android.material.textfield.TextInputLayout;
    import com.google.gson.Gson;

    import org.json.JSONObject;

    import java.util.Map;

    import nasaph8210.samahangnayon.MainActivity;
    import nasaph8210.samahangnayon.R;
    import nasaph8210.samahangnayon.api.PostCallback;
    import nasaph8210.samahangnayon.api.PostTask;
    import nasaph8210.samahangnayon.util.Messenger;
    import nasaph8210.samahangnayon.util.SessionManager;

    public class LoginActivity extends AppCompatActivity implements PostCallback {

        private TextInputLayout tfEmail;
        private TextInputLayout tfPassword;
        private TextView tvRegister;
        private TextView tvForgotPassword;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_login);
            Button btnLogin = findViewById(R.id.btnLogin);
            tvForgotPassword = findViewById(R.id.tvForgetPassword);

            tvForgotPassword.setOnClickListener(e ->{
                startActivity(new Intent(this, ForgotPasswordActivity.class));
            });

            if (SessionManager.getInstance(this).getToken() != null) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }

            TextView tvRegister = findViewById(R.id.tvRegister);
            tvRegister.setOnClickListener(e ->{
                startActivity(new Intent(this, RegisterActivity.class));
            });

            tfEmail = findViewById(R.id.tf_email);
            tfPassword = findViewById(R.id.tf_password);
            btnLogin.setOnClickListener(this::loginAction);

            tvRegister = findViewById(R.id.tvRegister);

            tvRegister.setOnClickListener(this::registerAction);

        }

        private void registerAction(View view) {
            startActivity(new Intent(this, RegisterActivity.class));
        }

        private void loginAction(View view) {
            JSONObject postData = new JSONObject();
            try {
                postData.put("emailaddress", tfEmail.getEditText().getText().toString());
                postData.put("password", tfPassword.getEditText().getText().toString());
                new PostTask(this, this, "Invalid email or password", "guest/login").execute(postData);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onPostSuccess(String responseData) {
            Gson gson = new Gson();
            Map<String, String> responseMap = gson.fromJson(responseData, Map.class);

            SessionManager.getInstance(this).setToken(responseMap.get("token"));
            SessionManager.getInstance(this).setVerified(false);
            String message = responseMap.get("message");

            Intent intent = new Intent(this, VerifyLoginActivity.class);
            startActivity(intent);

        }

        @Override
        public void onPostError(String errorMessage) {
            Messenger.showAlertDialog(this, "Error", errorMessage, "OK").show();
        }
    }