package nasaph8210.samahangnayon.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.adapter.AmenitiesAdapter;
import nasaph8210.samahangnayon.adapter.ConfirmAmenitiesAdapter;
import nasaph8210.samahangnayon.api.PostCallback;
import nasaph8210.samahangnayon.api.PostTask;
import nasaph8210.samahangnayon.model.AmenitiesAdded;
import nasaph8210.samahangnayon.util.DateFormatter;
import nasaph8210.samahangnayon.util.Loader;
import nasaph8210.samahangnayon.util.Messenger;
public class ConfirmPayment extends AppCompatActivity  implements PostCallback {

    private Button confirmPaymentButton;
    private Loader loader;

    private String paymentMethod;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_payment);

        confirmPaymentButton = findViewById(R.id.btn_confirm_payment);

        loader = new Loader();


        try {

            String jsonData = getIntent().getStringExtra("object");
            Log.d("ConfirmPayment", "Reservation data: " + jsonData);
            JSONObject reservationData = new JSONObject(jsonData);

            confirmPaymentButton.setOnClickListener(e ->{
                loader.showLoader(this);

                new PostTask(this, this, "error", "create-reservation").execute(reservationData);
            });


            populateReservationDetails(reservationData);

        } catch (JSONException e) {
            Log.e("ConfirmPayment", "Error parsing reservation data", e);
            Messenger.showAlertDialog(this, "Error", "Failed to load reservation details.", "OK").show();
        }
    }

    private void populateReservationDetails(JSONObject reservationData) throws JSONException {
        TextView tvRoomInfo = findViewById(R.id.tv_room_info);
        tvRoomInfo.setText("Room: " + reservationData.getString("room_id") +
                " (Room Number: " + reservationData.getString("room_number_id") + ")");

        TextView tvDates = findViewById(R.id.tv_dates);
        tvDates.setText("Check-in: " + reservationData.getString("check_in") +
                " - Check-out: " + reservationData.getString("check_out"));

        TextView tvTotalPayment = findViewById(R.id.tv_total_payment);
        tvTotalPayment.setText("Total Payment: " + getIntent().getStringExtra("total_cost"));

        TextView tvPaymentOption = findViewById(R.id.tv_payment_option);
        tvPaymentOption.setText("Payment Option: " + reservationData.getString("payment_option"));

        paymentMethod = reservationData.getString("payment_option");
        TextView tvRoomRate = findViewById(R.id.tv_room_rate);
        tvRoomRate.setText("Room Rate: ₱" + getIntent().getStringExtra("room_rate") + " per night");


        // Populate amenities
        JSONArray amenitiesArray = reservationData.getJSONArray("amenities");
        RecyclerView rvAmenities = findViewById(R.id.rv_amenities);
        List<AmenitiesAdded> amenitiesList = parseAmenities(amenitiesArray);
        ConfirmAmenitiesAdapter adapter = new ConfirmAmenitiesAdapter(amenitiesList);
        rvAmenities.setLayoutManager(new LinearLayoutManager(this));
        rvAmenities.setAdapter(adapter);
    }

    private List<AmenitiesAdded> parseAmenities(JSONArray amenitiesArray) throws JSONException {
        List<AmenitiesAdded> amenitiesList = new ArrayList<>();
        for (int i = 0; i < amenitiesArray.length(); i++) {
            JSONObject amenity = amenitiesArray.getJSONObject(i);
            amenitiesList.add(new AmenitiesAdded(
                    Long.parseLong(amenity.getString("id")),
                    amenity.getString("name"),
                    amenity.getDouble("price"),
                    amenity.getInt("quantity")
            ));
        }
        return amenitiesList;
    }

    private void confirmPayment() {
        // Implement confirmation logic here
        Toast.makeText(this, "Payment confirmed successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostSuccess(String responseData) {
        loader.dismissLoader();
        try {



            if(paymentMethod.equalsIgnoreCase("pay_later")) {
                Messenger.showAlertDialog(this, "Success", "Reservation created successfully", "OK", "Back",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(ConfirmPayment.this, HeroActivity.class));
                            }
                        }, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(ConfirmPayment.this, HeroActivity.class));
                            }
                        }
                ).show();
                return;
            }


            JSONObject jsonObject = new JSONObject(responseData);
            JSONObject dataObject = jsonObject.getJSONObject("data");
            JSONObject attributesObject = dataObject.getJSONObject("attributes");

            String checkoutUrl = attributesObject.getString("checkout_url");
            Intent intent = new Intent(this, PaymentActivity.class);
            intent.putExtra("checkout_url", checkoutUrl);
            startActivity(intent);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        Messenger.showAlertDialog(this, "Success", "Reservation created successfully", "OK").show();

    }

    @Override
    public void onPostError(String errorMessage) {
        loader.dismissLoader();
        Messenger.showAlertDialog(this, "Error", errorMessage, "OK").show();
    }
}
