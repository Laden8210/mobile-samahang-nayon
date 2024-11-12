package nasaph8210.samahangnayon.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.api.PostCallback;
import nasaph8210.samahangnayon.api.PostTask;
import nasaph8210.samahangnayon.model.Room;
import nasaph8210.samahangnayon.util.ImageUtil;
import nasaph8210.samahangnayon.util.Messenger;
import nasaph8210.samahangnayon.util.SessionManager;

public class RoomDetails extends AppCompatActivity {
    private TextView tvType;
    private ImageView ivRoom;
    private TextView tvPrice;
    private Button btnReservation;
    private TextView tvDescription;
    private TextView tvCapacity;
    private TextView tvNumber;

    private TextView tvCheckIn;
    private TextView tvCheckOut;

    private TextView tvRoomNumber;
    private TextView tvRoomPrice;
    private TextView tvDiscount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        tvType = findViewById(R.id.tv_type);
        ivRoom = findViewById(R.id.iv_room);
        tvPrice = findViewById(R.id.tv_price);
        btnReservation = findViewById(R.id.btn_reservation);
        btnReservation.setOnClickListener(this::reservationAction);
        tvDescription = findViewById(R.id.tv_description);
        tvCapacity = findViewById(R.id.tv_capacity);
        tvCheckIn = findViewById(R.id.tv_check_in);
        tvCheckOut = findViewById(R.id.tv_check_out);
        tvRoomNumber = findViewById(R.id.tv_room_number);
        tvRoomPrice = findViewById(R.id.tv_price_old);
        tvDiscount = findViewById(R.id.tv_discount);
        initializeData();
    }

    private String formatDate(String dateString) {
        // Define the input date format matching the input string
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.ENGLISH);
        // Define the output date format to only show year, month, and day
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            // Parse the date string into a Date object
            Date date = inputFormat.parse(dateString);
            // Format the Date object into the desired string format
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString; // Return the original string in case of error
        }
    }
    private void initializeData() {


        if (getIntent().hasExtra("checkIn")) {
            String checkInDateStr = getIntent().getStringExtra("checkIn");
            String formattedCheckInDate = formatDate(checkInDateStr);
            tvCheckIn.setText(formattedCheckInDate);
        }

        // Format check-out date
        if (getIntent().hasExtra("checkOut")) {
            String checkOutDateStr = getIntent().getStringExtra("checkOut");
            String formattedCheckOutDate = formatDate(checkOutDateStr);
            tvCheckOut.setText(formattedCheckOutDate);
        }

        if (getIntent().hasExtra(Room.INTENT_NAME)){
            Room room =  getIntent().getParcelableExtra(Room.INTENT_NAME);
            tvType.setText(room.getRoomType());

            tvRoomNumber.setText(String.valueOf(room.getRoomNumber()));

            tvDescription.setText(room.getDescription());
            tvCapacity.setText(String.valueOf(room.getCapacity()));

            if (room.getDiscount() != null){
                double totalDiscount =  room.getRoomPrice() -  (room.getRoomPrice() * ((float) room.getDiscount() / 100));

                tvDiscount.setText("₱"+String.format("%.2f", totalDiscount));
                tvRoomPrice.setText("₱"+String.format("%.2f", room.getRoomPrice()));
            }else{
                tvDiscount.setText("0%");
                tvRoomPrice.setText("₱"+room.getRoomPrice().toString());

            }


            if (room.getPromotion() == null){
                tvPrice.setText("₱"+String.format("%.2f/night", room.getRoomPrice()));

            }else{
                double totalDiscount =  room.getRoomPrice() -  (room.getRoomPrice() * ((float) room.getDiscount() / 100));
                tvPrice.setText("₱"+String.format("%.2f", totalDiscount)+"/night");
            }

            try {
                JSONObject object = new JSONObject();
                object.put("room_id", room.getRoomId());
                new PostTask(RoomDetails.this, new PostCallback() {
                    @Override
                    public void onPostSuccess(String responseData) {
                        try {
                            String base64Image = new JSONObject(responseData).getString("image");
                            ImageUtil.createImage(RoomDetails.this, base64Image, ivRoom);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    @Override
                    public void onPostError(String errorMessage) {

                    }
                }, "Error", "rooms/image").execute(object);
            }catch (Exception e){

            }
        }
    }


    private void reservationAction(View view) {

        if(SessionManager.getInstance(this).getToken() != null) {
            Messenger.showAlertDialog(this, "Reservation", "Do you want to make a reservation?",
                    "Confirm", "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(RoomDetails.this, CreateReservationActivity.class);
                            intent.putExtra(Room.INTENT_NAME, (Room)getIntent().getParcelableExtra(Room.INTENT_NAME));
                            intent.putExtra("checkIn", getIntent().getSerializableExtra("checkIn"));
                            intent.putExtra("checkOut", getIntent().getSerializableExtra("checkOut"));
                            intent.putExtra("children", getIntent().getStringExtra("children" ));
                            intent.putExtra("adult", getIntent().getStringExtra("adult"));
                            Log.d("RoomDetails", "CheckIn: " + getIntent().getSerializableExtra("checkIn"));
                            Log.d("RoomDetails", "CheckOut: " + getIntent().getSerializableExtra("checkOut"));
                            Log.d("RoomDetails", "Children: " + getIntent().getStringExtra("children"));
                            Log.d("RoomDetails", "Adult: " + getIntent().getStringExtra("adult"));


                            startActivity(intent);
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    }).show();

            return;
        }
        Messenger.showAlertDialog(this, "Sign In Required", "Please log in or register first before making a reservation.",
                "Confirm", "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(RoomDetails.this, LoginActivity.class));
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();

    }
}