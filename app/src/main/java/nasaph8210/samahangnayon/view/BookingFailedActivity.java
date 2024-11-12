package nasaph8210.samahangnayon.view;

import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.api.PostCallback;
import nasaph8210.samahangnayon.api.PostTask;
import nasaph8210.samahangnayon.model.Payment;
import nasaph8210.samahangnayon.model.Reservation;

public class BookingFailedActivity extends AppCompatActivity implements PostCallback {

    private ImageView imageView;
    private TextView transactionId, amount, date, time, status, paymentMethod, referenceNumber, remarks;
    private Button viewReservationButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_booking_failed);


        imageView = findViewById(R.id.imageView);
        transactionId = findViewById(R.id.transactionId); // Assuming you give IDs to TextViews
        amount = findViewById(R.id.amount);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        status = findViewById(R.id.status);
        paymentMethod = findViewById(R.id.paymentMethod);
        remarks = findViewById(R.id.remarks);
        viewReservationButton = findViewById(R.id.button3);
        imageView = findViewById(R.id.imageView);

        Uri data = getIntent().getData();
        if (data != null) {
            String paymentId = data.getQueryParameter("payment_id");

            Log.d("BookingSuccessActivity", "Payment ID: " + paymentId);

            try {

                JSONObject jsonObject = new JSONObject();

                jsonObject.put("reference_number", paymentId);

                new PostTask(this, this, "error", "getPaymentInformation").execute(jsonObject);

            }catch (Exception e){
                e.printStackTrace();
            }

            Glide.with(this)
                    .load(R.drawable.failed)
                    .into(imageView);
        }
    }

    @Override
    public void onPostSuccess(String responseData) {
        Gson gson = new Gson();
        Reservation transaction = gson.fromJson(responseData, Reservation.class);
        Payment payment = transaction.getPayments().get(0);

        transactionId.setText(String.valueOf(payment.getReferenceNumber()));
        amount.setText(payment.getAmountPaid());

        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputDateFormat = new SimpleDateFormat("MMMM dd, yyyy");

        // Set the output format timezone to PHT (Asia/Manila)
        outputDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));

        try {
            // Parse the input date string
            Date date = inputDateFormat.parse(payment.getDateCreated().toString());

            // Format the date into the desired output format
            String formattedDate = outputDateFormat.format(date);

            // Set the formatted date to the TextView
            this.date.setText(formattedDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat inputTimeFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat outputTimeFormat = new SimpleDateFormat("hh:mm a");

        // Set the output format timezone to PHT (Asia/Manila)
        outputTimeFormat.setTimeZone(TimeZone.getTimeZone("Asia/Manila"));

        try {
            // Parse the input time string
            Date time = inputTimeFormat.parse(payment.getTimeCreated().toString());

            // Format the time into the desired output format
            String formattedTime = outputTimeFormat.format(time);

            // Set the formatted time to the TextView
            this.time.setText(formattedTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        status.setText(payment.getStatus());
        paymentMethod.setText(payment.getPaymentType());
        remarks.setText(payment.getPurpose());


        viewReservationButton.setOnClickListener(v -> {
            finish();
        });

    }

    @Override
    public void onPostError(String errorMessage) {

    }
}