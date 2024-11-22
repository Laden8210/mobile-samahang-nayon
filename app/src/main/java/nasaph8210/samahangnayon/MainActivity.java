package nasaph8210.samahangnayon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import nasaph8210.samahangnayon.util.SessionManager;
import nasaph8210.samahangnayon.view.HeroActivity;
import nasaph8210.samahangnayon.view.LoginActivity;
import nasaph8210.samahangnayon.view.VerifyLoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button btnStarted = findViewById(R.id.btnStart);
        btnStarted.setOnClickListener(e ->{
            startActivity(new Intent(this, HeroActivity.class));
        });


        Intent intent = new Intent(this, HeroActivity.class);
        startActivity(intent);




    }
}