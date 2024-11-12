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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.dialog.MaterialDialogs;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.adapter.SelectedAmenities;
import nasaph8210.samahangnayon.adapter.SpinnerAdapter;
import nasaph8210.samahangnayon.adapter.SubGuestAdapter;
import nasaph8210.samahangnayon.api.PostCallback;
import nasaph8210.samahangnayon.api.PostTask;
import nasaph8210.samahangnayon.callback.ConfirmAmenities;
import nasaph8210.samahangnayon.callback.RemoveAmenities;
import nasaph8210.samahangnayon.callback.RemoveSubGuest;
import nasaph8210.samahangnayon.fragment.bottomsheet.AmenitiesFragment;
import nasaph8210.samahangnayon.model.Amenities;
import nasaph8210.samahangnayon.model.AmenitiesAdded;
import nasaph8210.samahangnayon.model.Room;
import nasaph8210.samahangnayon.model.SubGuest;
import nasaph8210.samahangnayon.util.DateFormatter;
import nasaph8210.samahangnayon.util.ImageUtil;
import nasaph8210.samahangnayon.util.Loader;
import nasaph8210.samahangnayon.util.Messenger;

public class CreateReservationActivity extends AppCompatActivity implements PostCallback, RemoveSubGuest {

    private Date checkInDate;
    private Date checkOutDate;
    private Room room;
    private ImageView ivRoom;

    private TextView tvType;
    private TextView tvPrice;
    private TextView tvCheckIn;
    private TextView tvCheckOut;
    private TextView tvLengthOfStay;
    private TextView tvRoomTotalCost;
    private TextView tvTotalCost;
    private TextView tvGuests;
    private CardView cvAmenities;
    private List<AmenitiesAdded> amenitiesAddedList;
    private RecyclerView rvAmenities;

    private RadioButton rbFullPayment;
    private RadioButton rbPartialPayment;
    private TextInputLayout tilIdNumber;


    private AmenitiesFragment amenitiesFragment;

    private Button btnCreateReservation;

    private CardView cvGuest;

    private List<SubGuest> subGuests;

    private RecyclerView rvSubGuests;
    private RadioGroup paymentOptionsGroup ;

    private TextView tvDiscountLabel, tvDiscountPrice;
    private TextView tvDownpaymentLabel, tvDownpaymentPrice;
    private TextView tvTotalLabel;

    private TableLayout tilAmenities;
    private Loader loader;


    private RadioGroup discountOption;

    // TODO: add format for the pwd id and senior

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_reservation);
        cvAmenities = findViewById(R.id.cv_amenities);

        tilIdNumber = findViewById(R.id.til_id_number);

        tilIdNumber.setVisibility(View.GONE);

        rvAmenities = findViewById(R.id.rv_amenities);
        rvAmenities.setLayoutManager(new LinearLayoutManager(this));
        tvGuests = findViewById(R.id.tv_guests);
        tvTotalLabel = findViewById(R.id.tv_total);

        tilAmenities = findViewById(R.id.tl_payment_summary);

        tvDiscountLabel = findViewById(R.id.tv_discount_label);
        tvDiscountPrice = findViewById(R.id.tv_discount_price);
        tvDownpaymentLabel = findViewById(R.id.tv_downpayment_label);
        tvDownpaymentPrice = findViewById(R.id.tv_downpayment_price);

        discountOption = findViewById(R.id.rg_discount_option);


        ivRoom = findViewById(R.id.iv_room);
        paymentOptionsGroup = findViewById(R.id.rg_payment_option);
        btnCreateReservation = findViewById(R.id.btn_reservation);
        room = getIntent().getParcelableExtra(Room.INTENT_NAME);

        Log.d("CheckIn", getIntent().getStringExtra("checkIn"));
        Log.d("CheckOut", getIntent().getStringExtra("checkOut"));
        checkInDate = stringToDate(getIntent().getStringExtra("checkIn"));
        checkOutDate = stringToDate(getIntent().getStringExtra("checkOut"));
        amenitiesAddedList = new ArrayList<>();

        rvSubGuests = findViewById(R.id.rv_guests);

        cvGuest = findViewById(R.id.cv_guests);

        rbFullPayment = findViewById(R.id.rb_full_payment);
        rbPartialPayment = findViewById(R.id.rb_partial_payment);

        rbFullPayment.setChecked(true);

        cvGuest.setOnClickListener(this::addSubGuests);


        loader = new Loader();

        tvGuests.setText(getIntent().getStringExtra("adult") + " Adult(s) " + getIntent().getStringExtra("children") + " Child(ren)");
        initPaymentListener();
        amenitiesFragment = new AmenitiesFragment();
        amenitiesFragment.setListener(new ConfirmAmenities() {
            @Override
            public void confirmAmenities(Amenities amenities, int quantity) {
                // Maximum quantity of amenities is 10

                if (quantity > 10) {
                    Messenger.showAlertDialog(CreateReservationActivity.this, "Error", "You can only add up to 10 of the same amenity", "OK").show();
                    return;
                }

                AmenitiesAdded amenitiesAdded = new AmenitiesAdded();
                amenitiesAdded.setAmenitiesId(amenities.getAmenitiesId());
                amenitiesAdded.setQuantity(quantity);
                amenitiesAdded.setName(amenities.getName());
                amenitiesAdded.setPrice(amenities.getPrice());

                amenitiesAddedList.add(amenitiesAdded);

                TableRow row = new TableRow(CreateReservationActivity.this);

                TextView tvAmenityName = new TextView(CreateReservationActivity.this);
                tvAmenityName.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.0f
                ));
                tvAmenityName.setText(amenities.getName());
                tvAmenityName.setTextSize(16);
                tvAmenityName.setTypeface(null, Typeface.BOLD);

                TextView tvAmenityPrice = new TextView(CreateReservationActivity.this);
                tvAmenityPrice.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.WRAP_CONTENT,
                        TableRow.LayoutParams.WRAP_CONTENT,
                        1.0f
                ));

                tvAmenityPrice.setText("P " + (amenities.getPrice() * quantity));
                tvAmenityPrice.setTextSize(16);

                row.addView(tvAmenityName);
                row.addView(tvAmenityPrice);
                tilAmenities.addView(row);
                SelectedAmenities selectedAmenities = new SelectedAmenities(amenitiesAddedList, CreateReservationActivity.this, new RemoveAmenities() {
                    @Override
                    public void removeAmenity(int position) {
                        amenitiesAddedList.remove(position);
                        tilAmenities.removeViewAt(position);
                        computeTotalCost();
                    }
                });
                computeTotalCost();
                rvAmenities.setLayoutManager(new LinearLayoutManager(CreateReservationActivity.this));
                rvAmenities.setAdapter(selectedAmenities);
            }
        });

        subGuests = new ArrayList<>();
        rvSubGuests.setLayoutManager(new LinearLayoutManager(this));
        initializeImage();
        initData();

        btnCreateReservation.setOnClickListener(this::createReservationAction);
        cvAmenities.setOnClickListener(this::showAmenities);

        computeTotalCost();
    }


    private Date stringToDate(String dateString) {
        // Define the input date format that matches the input string
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'Z yyyy", Locale.ENGLISH);

        try {
            // Parse the date string into a Date object
            return inputFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Return null in case of error
        }
    }
    private void addSubGuests(View view) {

        int totalGuest = Integer.parseInt(getIntent().getStringExtra("adult")) + Integer.parseInt(getIntent().getStringExtra("children"));
        if (subGuests.size() >= totalGuest) {
            Messenger.showAlertDialog(this, "Error", "You can only add up to "+totalGuest   +" sub guests", "OK").show();
            return;
        }

        View guestView = getLayoutInflater().inflate(R.layout.dialog_add_subguest, null);
        TextInputLayout firstNameInput = guestView.findViewById(R.id.tf_first_name);
        TextInputLayout lastNameInput = guestView.findViewById(R.id.tf_last_name);
        TextInputLayout middleNameInput = guestView.findViewById(R.id.tf_middle_name);
        TextInputEditText birthdateInput = guestView.findViewById(R.id.tf_birthdate);
        TextInputLayout contactNumberInput = guestView.findViewById(R.id.tf_contact_number);

        Spinner genderInput = guestView.findViewById(R.id.sp_gender);
        birthdateInput.setOnClickListener(v -> showDatePicker(birthdateInput));
        ArrayAdapter<String> genderAdapter = new SpinnerAdapter<String>().GetArrayAdapter(
                getApplicationContext(),
                com.google.android.material.R.layout.support_simple_spinner_dropdown_item,
                Arrays.asList(getResources().getStringArray(R.array.gender))
        );
        genderInput.setAdapter(genderAdapter);

        MaterialAlertDialogBuilder dialogBuilder = new MaterialAlertDialogBuilder(view.getContext());

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

            String gender =  genderInput.getSelectedItem().toString();

            Log.d("SubGuest", firstName + " " + lastName + " " + middleName + " " + birthdate + " " + contactNumber + " " + gender);

            if (firstName.isEmpty() || lastName.isEmpty() || birthdate.isEmpty() || contactNumber.isEmpty() ) {
                Messenger.showAlertDialog(this, "Error", "Please fill up all required fields", "OK").show();
            } else {

                SubGuest subGuest = new SubGuest(firstName, lastName, middleName, birthdate, contactNumber, gender);
                subGuests.add(subGuest);

                this.rvSubGuests.setAdapter(new SubGuestAdapter(CreateReservationActivity.this, subGuests, CreateReservationActivity.this));

                dialog.dismiss();
            }
        });
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

    private void showAmenities(View view) {
        amenitiesFragment.show(getSupportFragmentManager(), "amenities");
    }

    private void createReservationAction(View view) {

        try {
            JSONObject object = new JSONObject();
            object.put("room_id", room.getRoomId());
            object.put("room_number_id", room.getRoom_number_id());
            object.put("check_in", DateFormatter.formatDate(checkInDate));
            object.put("check_out", DateFormatter.formatDate(checkOutDate));
            object.put("amenities", amenitiesAddedList);

            if (discountOption.getCheckedRadioButtonId() == R.id.rg_senion) {
                String seniorIdNumber = tilIdNumber.getEditText().getText().toString();

                // Check if Senior Citizen ID is empty
                if (seniorIdNumber.isEmpty()) {
                    Messenger.showAlertDialog(this, "Error", "Samahang Nayon Hotel: Please enter your Senior Citizen ID number", "OK").show();
                    return;
                }

                // Validate Senior Citizen ID format (Format: XXX-OSCA-XXXX-XXXXX)
                if (!seniorIdNumber.matches("^[A-Z]{3}-OSCA-\\d{4}-\\d{5}$")) {
                    Messenger.showAlertDialog(this, "Error", "Samahang Nayon Hotel: Invalid Senior Citizen ID format. Use \"XXX-OSCA-XXXX-XXXXX\"", "OK").show();
                    return;
                }

                object.put("id_number", seniorIdNumber);
            }

            if (discountOption.getCheckedRadioButtonId() == R.id.rg_pwd) {
                String idNumber = tilIdNumber.getEditText().getText().toString();

                // Check if PWD ID is empty
                if (idNumber.isEmpty()) {
                    Messenger.showAlertDialog(this, "Error", "Samahang Nayon Hotel: Please enter your PWD ID number", "OK").show();
                    return;
                }

                // Validate PWD ID format (Format: XXX-XX-XXXX-XXXXXX)
                if (!idNumber.matches("^[A-Z]{2,3}-\\d{2}-\\d{4}-\\d{6}$")) {
                    Messenger.showAlertDialog(this, "Error", "Samahang Nayon Hotel: Invalid PWD ID format. Use \"XXX-XX-XXXX-XXXXXX\"", "OK").show();
                    return;
                }

                object.put("id_number", idNumber);
            }



            int discountSelectedId = discountOption.getCheckedRadioButtonId();

            if (discountSelectedId == R.id.rg_pwd){
                object.put("discountType", "PWD");

            }else if (discountSelectedId == R.id.rg_senion){
                object.put("discountType", "Senior Citizen");
            }else{
                object.put("discountType", "");
            }


            if (getIntent().hasExtra("children") && getIntent().getStringExtra("children") != null) {
                object.put("total_children", getIntent().getStringExtra("children").isEmpty() ? 0 : getIntent().getStringExtra("children"));
            }else{
                object.put("total_children", 0);

            }

            if (getIntent().hasExtra("adult") && getIntent().getStringExtra("adult") != null) {
                object.put("total_adult", getIntent().getStringExtra("adult").isEmpty() ? 0 : getIntent().getStringExtra("adult"));

            }else{
                object.put("total_adult", 0);
                Log.d("Adult", "0");
            }

            JSONArray amenitiesArray = new JSONArray();
            for (AmenitiesAdded amenity : amenitiesAddedList) {
                JSONObject amenityObject = new JSONObject();
                amenityObject.put("id", amenity.getAmenitiesId());
                amenityObject.put("name", amenity.getName());
                amenityObject.put("price", amenity.getPrice());
                amenityObject.put("quantity", amenity.getQuantity());
                amenitiesArray.put(amenityObject);
            }
            object.put("amenities", amenitiesArray);
            JSONArray subGuestObjectArray = new JSONArray();
            for (SubGuest subGuest : subGuests) {
                JSONObject subGuestObject = new JSONObject();
                subGuestObject.put("first_name", subGuest.getFirstName());
                subGuestObject.put("last_name", subGuest.getLastName());
                subGuestObject.put("middle_name", subGuest.getMiddleName());
                subGuestObject.put("birthdate", subGuest.getBirthdate());
                subGuestObject.put("contact_number", subGuest.getContactNumber());

                subGuestObject.put("gender", subGuest.getGender());
                subGuestObjectArray.put(subGuestObject);
            }

            object.put("sub_guests",subGuestObjectArray) ;



            int selectedId = paymentOptionsGroup.getCheckedRadioButtonId();
            if(selectedId == -1){
                Messenger.showAlertDialog(this, "Error", "Please select payment option", "OK").show();
                return;
            }

            object.put("payment_option", selectedId == R.id.rb_full_payment ? "full" : "partial");
            loader.showLoader(this);
            Log.d("Object", object.toString());
            new PostTask(CreateReservationActivity.this, this, "Error", "create-reservation").execute(object);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    private void initPaymentListener() {
        paymentOptionsGroup.setOnCheckedChangeListener((group, checkedId) -> {
            computeTotalCost();
        });

        discountOption.setOnCheckedChangeListener((group, checkedId) -> {
            computeTotalCost();

            if (checkedId == R.id.rg_senion){
                tilIdNumber.setVisibility(View.VISIBLE);
            }else if (checkedId == R.id.rg_pwd){
                tilIdNumber.setVisibility(View.VISIBLE);
            }
            else{
                tilIdNumber.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onPostSuccess(String responseData) {
        loader.dismissLoader();
        try {

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

    private void  initData(){
        tvTotalCost = findViewById(R.id.tv_total_cost);
        tvType = findViewById(R.id.tv_room_type);
        tvPrice = findViewById(R.id.tv_price);
        tvCheckIn = findViewById(R.id.tv_check_in);
        tvCheckOut = findViewById(R.id.tv_check_out);
        tvLengthOfStay = findViewById(R.id.tv_length_of_stay);
        tvRoomTotalCost = findViewById(R.id.tv_room_total_cost);

        tvType.setText(room.getRoomType()+ "-" + room.getRoomNumber());
        tvPrice.setText(String.format("P %.2f/night", room.getRoomPrice()));

        tvCheckIn.setText(DateFormatter.formatDate(checkInDate));
        tvCheckOut.setText(DateFormatter.formatDate(checkOutDate));
        long diffInMillies = checkOutDate.getTime() - checkInDate.getTime();
        long totalNights = diffInMillies / (1000 * 60 * 60 * 24);

        tvLengthOfStay.setText(String.valueOf(totalNights) + " night(s)");

        computeTotalCost();
    }

    private void initializeImage(){
        try {
            JSONObject object = new JSONObject();
            object.put("room_id", room.getRoomId());
            new PostTask(CreateReservationActivity.this, new PostCallback() {
                @Override
                public void onPostSuccess(String responseData) {
                    try {
                        String base64Image = new JSONObject(responseData).getString("image");
                        ImageUtil.createImage(CreateReservationActivity.this, base64Image, ivRoom);
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

    private void computeTotalCost(){
        long diffInMillies = checkOutDate.getTime() - checkInDate.getTime();
        long totalNights = diffInMillies / (1000 * 60 * 60 * 24);
        double roomTotalCost = room.getRoomPrice() * totalNights;
        Log.d("RoomTotalCost", String.valueOf(roomTotalCost));

        tvRoomTotalCost.setText(String.format("P %.2f", roomTotalCost ));

        int discountSelectedId = discountOption.getCheckedRadioButtonId();

        if (discountSelectedId == R.id.rg_none){
            if (room.getDiscount() != null) {
                double discount = roomTotalCost * ((float) room.getDiscount() / 100);
                Log.d("Computation", "Original Room Total: " + roomTotalCost);
                Log.d("Computation", "Discount Percentage: " + room.getDiscount());
                Log.d("Computation", "Discount Amount: " + discount);
                roomTotalCost -= discount; // or roomTotalCost = roomTotalCost - discount;
                Log.d("Computation", "Room Total After Discount: " + roomTotalCost);

                tvDiscountPrice.setText(String.format("P %.2f",discount));
                tvDiscountLabel.setVisibility(View.VISIBLE);
                tvDiscountPrice.setVisibility(View.VISIBLE);
                tvDiscountLabel.setText("Less Discount (" + room.getDiscount() + "%)");
            } else {
                tvDiscountLabel.setVisibility(View.GONE);
                tvDiscountPrice.setVisibility(View.GONE);
            }

        }else{
            double discount = roomTotalCost * ((float) 10/ 100);

            tvDiscountPrice.setText(String.format("P %.2f",discount));
            tvDiscountLabel.setVisibility(View.VISIBLE);
            tvDiscountPrice.setVisibility(View.VISIBLE);
            paymentOptionsGroup.check(R.id.rb_partial_payment);
            if (discountSelectedId == R.id.rg_pwd){
                tvDiscountLabel.setText("PWD Discount (10%)");
            }else if (discountSelectedId == R.id.rg_senion){
                tvDiscountLabel.setText("Senior Citizen Discount (10%)");
            }

        }


        double amenitiesTotalCost = 0;
        if (amenitiesAddedList.size() > 0){
            for (AmenitiesAdded amenity : amenitiesAddedList){

                amenitiesTotalCost += amenity.getPrice() * amenity.getQuantity();
            }
        }

        int selectedId = paymentOptionsGroup.getCheckedRadioButtonId();
        if (selectedId == R.id.rb_full_payment) {
            btnCreateReservation.setText("Book Now");
            tvDownpaymentPrice.setVisibility(View.GONE); // Hide downpayment
            tvDownpaymentLabel.setVisibility(View.GONE); // Hide downpayment label
            tvTotalLabel.setText("Total");

            // Full payment without any adjustments
            tvTotalCost.setText(String.format("P %.2f", roomTotalCost + amenitiesTotalCost));

        } else if (selectedId == R.id.rb_partial_payment) {
            btnCreateReservation.setText("Reserve Now");
            tvDownpaymentPrice.setVisibility(View.VISIBLE); // Show downpayment
            tvDownpaymentLabel.setVisibility(View.VISIBLE); // Show downpayment label
            tvTotalLabel.setText("Balance");

            if (discountSelectedId == R.id.rg_none) {
                // Partial payment: 30% of room total cost without discount
                double downpayment = (roomTotalCost * 0.30) + amenitiesTotalCost;
                double remainingBalance = (roomTotalCost - downpayment) ;

                tvDownpaymentPrice.setText(String.format("P %.2f", downpayment)); // Show 30% downpayment
                tvTotalCost.setText(String.format("P %.2f", remainingBalance)); // Show remaining balance

            } else {
                // Applying 10% discount, then partial payment (30% of discounted total)
                double discountedRoomTotal = roomTotalCost * 0.90; // 10% discount
                double downpayment = (discountedRoomTotal * 0.30) + amenitiesTotalCost;
                double remainingBalance = (discountedRoomTotal - downpayment);

                tvDownpaymentPrice.setText(String.format("P %.2f", downpayment)); // Show 30% downpayment
                tvTotalCost.setText(String.format("P %.2f", remainingBalance)); // Show remaining balance
            }
        }

// Update radio button text for full/partial payment based on discount
        if (discountSelectedId == R.id.rg_none) {
            rbFullPayment.setEnabled(true);
            rbFullPayment.setText("Full Payment: " + String.format("P %.2f", roomTotalCost + amenitiesTotalCost));
            rbPartialPayment.setText("Partial Payment: P " + String.format("%.2f", (roomTotalCost * 0.30) + amenitiesTotalCost));

        } else {
            double discountedRoomTotal = roomTotalCost * 0.90; // Apply 10% discount
            rbFullPayment.setEnabled(false); // Disable full payment when a discount is applied
            rbFullPayment.setText("Full Payment: " + String.format("P %.2f", discountedRoomTotal + amenitiesTotalCost));
            rbPartialPayment.setText("Partial Payment: P " + String.format("%.2f", (discountedRoomTotal * 0.30) + amenitiesTotalCost));
        }


    }


    @Override
    public void removeSubGuest(int position) {

    }
}