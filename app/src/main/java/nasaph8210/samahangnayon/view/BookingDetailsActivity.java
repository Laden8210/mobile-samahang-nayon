package nasaph8210.samahangnayon.view;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.adapter.AmenitiesAdapter;
import nasaph8210.samahangnayon.adapter.ReservationSubGuestAdapter;
import nasaph8210.samahangnayon.adapter.SelectedAmenities;
import nasaph8210.samahangnayon.adapter.SpinnerAdapter;
import nasaph8210.samahangnayon.adapter.SubGuestAdapter;
import nasaph8210.samahangnayon.api.PostCallback;
import nasaph8210.samahangnayon.api.PostTask;
import nasaph8210.samahangnayon.callback.ConfirmAmenities;
import nasaph8210.samahangnayon.callback.RemoveAmenities;
import nasaph8210.samahangnayon.fragment.bottomsheet.AmenitiesFragment;
import nasaph8210.samahangnayon.model.Amenities;
import nasaph8210.samahangnayon.model.AmenitiesAdded;
import nasaph8210.samahangnayon.model.Amenity;
import nasaph8210.samahangnayon.model.Reservation;
import nasaph8210.samahangnayon.model.ReservationAmenity;
import nasaph8210.samahangnayon.model.SubGuest;
import nasaph8210.samahangnayon.model.Transaction;
import nasaph8210.samahangnayon.util.Loader;
import nasaph8210.samahangnayon.util.Messenger;

public class BookingDetailsActivity extends AppCompatActivity {


    // TODO: change button depend on the type of the transaction
    private Transaction transaction;
    private TextView tvRoomRate, tvDiscount, tvTotalAmount;
    private RecyclerView  recyclerViewGuests;
    private Button btnCancelReservation;

    private TableLayout tableLayout;
    private FloatingActionButton fabAdGuest;
    private TextView tvRoomTotalCost;
    private TextView tvRoomPrice, tvRoomType, tvCheckIn, tvCheckout, tvGuest, tvLengthOfStay;

    private Button btnAddAmenties;
    private AmenitiesFragment amenitiesFragment;

    private TextView tvRoomNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_details);


        tvRoomRate = findViewById(R.id.tv_room_rate);
        tvTotalAmount = findViewById(R.id.tv_total_amount);
        tableLayout = findViewById(R.id.table_layout_payment);
        tvRoomNumber = findViewById(R.id.tv_room_number);

        tvRoomPrice = findViewById(R.id.tv_room_price);
        tvRoomType = findViewById(R.id.tv_room_type);
        tvCheckIn = findViewById(R.id.tv_check_in);
        tvCheckout = findViewById(R.id.tv_check_out);
        tvGuest = findViewById(R.id.tv_guest);
        tvLengthOfStay = findViewById(R.id.tv_length_of_stay);
        fabAdGuest = findViewById(R.id.fab_add_guest);
        recyclerViewGuests = findViewById(R.id.recycler_view_guests);
        btnCancelReservation = findViewById(R.id.btn_cancel_booking);
        btnAddAmenties = findViewById(R.id.btn_add_amenities);
        fabAdGuest.setOnClickListener(this::addGuestAction);

        btnAddAmenties.setOnClickListener(this::addAmenities);

        if (getIntent().hasExtra("transaction")) {
            transaction = getIntent().getParcelableExtra("transaction");
            displayTransactionDetails();
        }

        amenitiesFragment = new AmenitiesFragment();

        amenitiesFragment.setListener(new ConfirmAmenities() {
            @Override
            public void confirmAmenities(Amenities amenities, int quantity) {

                if (quantity == 0) {
                    Messenger.showAlertDialog(BookingDetailsActivity.this, "Error", "Please enter a valid quantity", "OK").show();

                }
                // Maximum quantity of amenities is 10
                if (quantity > 10) {
                    Messenger.showAlertDialog(BookingDetailsActivity.this, "Error", "Maximum quantity of amenities is 10", "OK").show();
                    return;
                }




                AmenitiesAdded amenitiesAdded = new AmenitiesAdded();
                amenitiesAdded.setAmenitiesId(amenities.getAmenitiesId());
                amenitiesAdded.setQuantity(quantity);
                amenitiesAdded.setName(amenities.getName());
                amenitiesAdded.setPrice(amenities.getPrice());

              try {
                  JSONObject jsonObject = new JSONObject();

                    jsonObject.put("reservation_id", transaction.getReservationId());
                    jsonObject.put("amenities_id", amenitiesAdded.getAmenitiesId());
                    jsonObject.put("quantity", amenitiesAdded.getQuantity());
                    jsonObject.put("total_cost", amenitiesAdded.getPrice());
                    Loader loader = new Loader();
                    loader.showLoader(BookingDetailsActivity.this);
                    new PostTask(BookingDetailsActivity.this, new PostCallback() {
                        @Override
                        public void onPostSuccess(String responseData) {
                            displayTransactionDetails();
                            loader.dismissLoader();
                        }

                        @Override
                        public void onPostError(String errorMessage) {
                            Messenger.showAlertDialog(BookingDetailsActivity.this, "Error", errorMessage, "OK").show();
                            loader.dismissLoader();
                        }
                    }, "error", "reservation/addAmenities").execute(jsonObject);

              }catch (Exception ex){
                  ex.printStackTrace();
              }
            }
        });


        setUpRecyclerViews();

        btnCancelReservation.setOnClickListener(this::cancelReservation);
    }

    private void showDatePicker(TextInputEditText editText) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, month1, dayOfMonth) -> {
                    calendar.set(year1, month1, dayOfMonth);
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                    editText.setText(sdf.format(calendar.getTime()));
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    private void addAmenities(View view){
        amenitiesFragment.show(getSupportFragmentManager(), "amenities");
    }

    private void addGuestAction(View view) {
        View guestView = getLayoutInflater().inflate(R.layout.dialog_add_subguest, null);
        TextInputLayout firstNameInput = guestView.findViewById(R.id.tf_first_name);
        TextInputLayout lastNameInput = guestView.findViewById(R.id.tf_last_name);
        TextInputLayout middleNameInput = guestView.findViewById(R.id.tf_middle_name);
        TextInputEditText birthdateInput = guestView.findViewById(R.id.tf_birthdate);
        TextInputLayout contactNumberInput = guestView.findViewById(R.id.tf_contact_number);

        Spinner genderInput = guestView.findViewById(R.id.sp_gender);
        birthdateInput.setOnClickListener(v -> showDatePicker(birthdateInput));
        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(view.getContext());


        ArrayAdapter<String> genderAdapter = new SpinnerAdapter<String>().GetArrayAdapter(
                getApplicationContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                Arrays.asList(getResources().getStringArray(R.array.gender))
        );
        genderInput.setAdapter(genderAdapter);

        dialogBuilder.setTitle("Add Sub Guests");
        dialogBuilder.setView(guestView);
        dialogBuilder.setNegativeButton("Cancel", (dialogInterface, which) -> dialogInterface.dismiss());

        dialogBuilder.setPositiveButton("Add", null);

        AlertDialog dialog = dialogBuilder.create();
        dialog.show();


        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            String firstName = firstNameInput.getEditText().getText().toString();
            String lastName = lastNameInput.getEditText().getText().toString();
            String middleName = middleNameInput.getEditText().getText().toString();
            String birthdate = birthdateInput.getText().toString();
            String contactNumber = contactNumberInput.getEditText().getText().toString();

            String gender = genderInput.getSelectedItem().toString();

            Log.d("SubGuest", firstName + " " + lastName + " " + middleName + " " + birthdate + " " + contactNumber + " " + gender);

            if (firstName.isEmpty() || lastName.isEmpty() || birthdate.isEmpty() || contactNumber.isEmpty() ) {
                Messenger.showAlertDialog(this, "Error", "Please fill up all required fields", "OK").show();
            } else {
                try {
                    SubGuest subGuest = new SubGuest(firstName, lastName, middleName, birthdate, contactNumber, gender);
                    JSONObject subGuestObject = new JSONObject();
                    subGuestObject.put("reservation_id", transaction.getReservationId());
                    subGuestObject.put("first_name", subGuest.getFirstName());
                    subGuestObject.put("last_name", subGuest.getLastName());
                    subGuestObject.put("middle_name", subGuest.getMiddleName());
                    subGuestObject.put("birthdate", subGuest.getBirthdate());
                    subGuestObject.put("contact_number", subGuest.getContactNumber());

                    subGuestObject.put("gender", subGuest.getGender());


                    new PostTask(this, new PostCallback() {
                        @Override
                        public void onPostSuccess(String responseData) {
                            dialog.dismiss();
                            displayTransactionDetails();
                        }

                        @Override
                        public void onPostError(String errorMessage) {
                            Messenger.showAlertDialog(BookingDetailsActivity.this, "Error", errorMessage, "OK").show();
                        }
                    }, "error", "reservation/addSubGuest").execute(subGuestObject);

                }catch (Exception ex){
                    ex.printStackTrace();
                }



                dialog.dismiss();
            }
        });
    }

    private void displayTransactionDetails() {


        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("reservation_id", transaction.getReservationId());

            new PostTask(this, new PostCallback() {
                @Override
                public void onPostSuccess(String responseData) {
                    tableLayout.removeAllViews();
                    Gson gson = new Gson();
                    Reservation deserializedReservation = gson.fromJson(responseData, Reservation.class);
                    tvRoomRate.setText("P" + deserializedReservation.getTotalCost());
                    tvRoomNumber.setText("Room " + deserializedReservation.getRoom().getRoomNumber());

                    tvRoomPrice.setText("P" + deserializedReservation.getTotalCost());
                    tvRoomType.setText(deserializedReservation.getRoom().getRoomType());
                    tvCheckIn.setText(deserializedReservation.getDateCheckIn());
                    tvCheckout.setText(deserializedReservation.getDateCheckOut());
                    tvGuest.setText(deserializedReservation.getTotalAdult() + " adult(s), " + deserializedReservation.getTotalChildren() + " child(ren)");

                    Date checkIn = null;
                    try {
                        checkIn = new SimpleDateFormat("yyyy-MM-dd").parse(deserializedReservation.getDateCheckIn());
                        Date checkOut = new SimpleDateFormat("yyyy-MM-dd").parse(deserializedReservation.getDateCheckOut());
                        long diff = checkOut.getTime() - checkIn.getTime();
                        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                        tvLengthOfStay.setText(days + " day(s)");
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    recyclerViewGuests.setAdapter(new ReservationSubGuestAdapter(deserializedReservation.getSubGuests()));

                    if (deserializedReservation.getStatus().equals("Booked") || deserializedReservation.getStatus().equals("Reserved")) {
                        btnCancelReservation.setVisibility(View.VISIBLE);

                        if(deserializedReservation.getStatus().equals("Booked")) {
                            btnCancelReservation.setText("Cancel Booking");
                        }else if (deserializedReservation.getStatus().equals("Reserved")){
                            btnCancelReservation.setText("Cancel Reservation");
                        }
                    }else{
                        btnCancelReservation.setVisibility(View.GONE);

                    }

                    if (deserializedReservation.getStatus().equals("Checked In") || deserializedReservation.getStatus().equals("Booked") || deserializedReservation.getStatus().equals("Reserved")) {
                        btnAddAmenties.setVisibility(View.VISIBLE);
                        fabAdGuest.setVisibility(View.VISIBLE);
                    }else{
                        fabAdGuest.setVisibility(View.GONE);
                        btnAddAmenties.setVisibility(View.GONE);
                    }
                    double totalAmenities = 0;

                    for (ReservationAmenity amenity: deserializedReservation.getReservationAmenities()) {
                        TableRow row = new TableRow(BookingDetailsActivity.this);

                        TextView tvAmenityName = new TextView(BookingDetailsActivity.this);
                        tvAmenityName.setLayoutParams(new TableRow.LayoutParams(
                                TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                1.0f
                        ));
                        tvAmenityName.setText(amenity.getAmenity().getName() +" - "+amenity.getQuantity());
                        tvAmenityName.setTextSize(16);
                        tvAmenityName.setTypeface(null, Typeface.NORMAL);

                        TextView tvAmenityPrice = new TextView(BookingDetailsActivity.this);
                        tvAmenityPrice.setLayoutParams(new TableRow.LayoutParams(
                                TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                1.0f
                        ));
                        tvAmenityPrice.setText("P " + amenity.getTotalCost());
                        tvAmenityPrice.setTextSize(16);

                        totalAmenities += Double.valueOf(amenity.getTotalCost());
                        row.addView(tvAmenityName);
                        row.addView(tvAmenityPrice);
                        tableLayout.addView(row);
                    }
                    double totalCost = Double.valueOf(deserializedReservation.getTotalCost()); // Make sure this is a double or parsed to double
                    double totalAmenitiesCost = totalAmenities; // This should be a double as well


                    double totalAmount = totalCost + totalAmenitiesCost;

                    String formattedTotalAmount = String.format("P%.2f", totalAmount);

                    tvTotalAmount.setText(formattedTotalAmount);
                }

                @Override
                public void onPostError(String errorMessage) {
                    Messenger.showAlertDialog(BookingDetailsActivity.this, "Error", errorMessage, "OK").show();
                }
            }, "error", "reservation/getReservationDetails").execute(jsonObject);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private void setUpRecyclerViews() {

        recyclerViewGuests.setLayoutManager(new LinearLayoutManager(this));
    }



    private void cancelReservation(View view) {
        Messenger.showAlertDialog(this, "Cancel Reservation", "Are you sure you want to cancel this reservation?", "Yes", "No",
                (dialogInterface, i) -> {
                    try {
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("reservation_id", transaction.getReservationId());

                        Loader loader = new Loader();
                        loader.showLoader(this);

                        new PostTask(this, new PostCallback() {
                            @Override
                            public void onPostSuccess(String responseData) {
                                loader.dismissLoader();
                                Intent intent = new Intent(BookingDetailsActivity.this, CancelReservationActivity.class);
                                startActivity(intent);
                                finish();
                            }

                            @Override
                            public void onPostError(String errorMessage) {
                                Messenger.showAlertDialog(BookingDetailsActivity.this, "Error", errorMessage, "OK").show();
                                loader.dismissLoader();
                            }
                        }, "error", "reservation/cancelReservation").execute(jsonObject);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                },
                (dialogInterface, i) -> {
                    // Cancel action
                }
        ).show();
    }
}
