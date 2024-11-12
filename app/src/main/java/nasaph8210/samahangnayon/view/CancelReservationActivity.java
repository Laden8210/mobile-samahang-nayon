package nasaph8210.samahangnayon.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import nasaph8210.samahangnayon.R;

public class CancelReservationActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cancel_reservation2);
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.backToHome);

        button.setOnClickListener(v -> {
            startActivity(getIntent().setClass(this, HeroActivity.class));
            finish();
        });
        Glide.with(this).load(R.drawable.cancel).into(imageView);

    }
}