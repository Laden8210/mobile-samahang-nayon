package nasaph8210.samahangnayon.view;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import nasaph8210.samahangnayon.R;

public class PaymentActivity extends AppCompatActivity {


    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        String checkoutUrl = getIntent().getStringExtra("checkout_url");
        webView.loadUrl(checkoutUrl);

    }
}