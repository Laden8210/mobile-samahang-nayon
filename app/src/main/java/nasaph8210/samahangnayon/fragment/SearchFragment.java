package nasaph8210.samahangnayon.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.graphics.Insets;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;
import org.json.JSONException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.adapter.RoomAdapter;
import nasaph8210.samahangnayon.api.PostCallback;
import nasaph8210.samahangnayon.api.PostTask;
import nasaph8210.samahangnayon.callback.SelectRoom;
import nasaph8210.samahangnayon.fragment.bottomsheet.FilterFragment;
import nasaph8210.samahangnayon.fragment.bottomsheet.FilterFragmentGuest;
import nasaph8210.samahangnayon.model.Room;
import nasaph8210.samahangnayon.util.Loader;
import nasaph8210.samahangnayon.util.Messenger;
import nasaph8210.samahangnayon.view.RoomDetails;

public class SearchFragment extends Fragment implements PostCallback, SelectRoom {

    private TextInputLayout tfSearch;
    private FilterFragment filterFragment;
    private FilterFragmentGuest filterFragmentGuest;
    private List<Room> roomList;
    private RecyclerView rvRoom;
    private Date checkInDate;
    private Date checkOutDate;
    private String children;
    private String adult;
    private TextInputLayout tfGuest;
    private Loader loader;

    private Button btnSearch;

    private FloatingActionButton fabDate;
    
    private FloatingActionButton fabGuest;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        btnSearch = view.findViewById(R.id.btn_search);
        rvRoom = view.findViewById(R.id.rv_room);
        tfSearch = view.findViewById(R.id.tf_search);
        filterFragment = new FilterFragment();
        filterFragmentGuest = new FilterFragmentGuest();
        tfGuest = view.findViewById(R.id.tf_guest);


        fabDate = view.findViewById(R.id.fab_date);
        fabGuest = view.findViewById(R.id.fab_guest);
        fabDate.setOnClickListener(this::filterAction);
        
        fabGuest.setOnClickListener(this::filterGuestAction);

        tfSearch.getEditText().setOnClickListener(v -> filterAction(tfSearch));
        tfGuest.getEditText().setOnClickListener(v -> filterGuestAction(tfGuest));

        this.adult = "1";
        this.children = "0";

        tfGuest.getEditText().setText(adult + " Adult - " + children + " Children");

        initRoom();
        initSearch();
        loader = new Loader();
        loader.showLoader(getContext());

        btnSearch.setOnClickListener(v -> {
            loader.showLoader(getContext());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());


            roomList = new ArrayList<>();
            JSONObject object = new JSONObject();

            try {


                object.put("checkIn", sdf.format(checkInDate));
                object.put("checkOut", sdf.format(checkOutDate));
                object.put("adult", adult);
                object.put("children", children);


            } catch (JSONException e) {
                Log.e("SearchFragment", "Error creating JSON object: " + e.getMessage());
            }

            new PostTask(getContext(), this, "Error", "rooms").execute(object);
        });
        return view;
    }

    private void filterGuestAction(View view) {
        filterFragmentGuest.show(getParentFragmentManager(), "TAG");
        filterFragmentGuest.setOnFilterDismissListener((adultStr, childrenStr) -> {

            if (adultStr.isEmpty() ) {
                Toast.makeText(getContext(), "Please enter the number of guests.", Toast.LENGTH_SHORT).show();
                return; // Prevent updating the guest count
            }


            int adult = Integer.parseInt(adultStr);
            int children = Integer.parseInt(childrenStr);

            // Correctly calculate the total number of guests
            int totalGuests = adult + children;


            // Validate the total number of guests
            if (totalGuests > 10) {
                Messenger.showAlertDialog(getContext(), "Error", "The total number of guests cannot exceed 10.", "OK").show();
                return; // Prevent updating the guest count
            }


            this.adult = adultStr;
            this.children = childrenStr;

            tfGuest.getEditText().setText(adult + " Adult - " + children + " Children");
        });
    }


    private void filterGuestAction(TextInputLayout tfGuest) {
        filterFragmentGuest.show(getParentFragmentManager(), "TAG");
        filterFragmentGuest.setOnFilterDismissListener((adultStr, childrenStr) -> {

            if (adultStr.isEmpty() ) {
                Toast.makeText(getContext(), "Please enter the number of guests.", Toast.LENGTH_SHORT).show();
                return; // Prevent updating the guest count
            }


            int adult = Integer.parseInt(adultStr);
            int children = Integer.parseInt(childrenStr);

            // Correctly calculate the total number of guests
            int totalGuests = adult + children;

            if (totalGuests == 0) {
                Toast.makeText(getContext(), "Please enter the number of guests.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Validate the total number of guests
            if (totalGuests > 10) {
                Toast.makeText(getContext(), "The maximum number of guests is 10." + totalGuests, Toast.LENGTH_SHORT).show();
                return; // Prevent updating the guest count
            }


            this.adult = adultStr;
            this.children = childrenStr;

            tfGuest.getEditText().setText(adult + " Adult - " + children + " Children");
        });
    }


    private void initRoom() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        checkInDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        checkOutDate = calendar.getTime();
        roomList = new ArrayList<>();
        JSONObject object = new JSONObject();


    }

    private void initSearch() {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        checkInDate = calendar.getTime();

        calendar.add(Calendar.DAY_OF_MONTH, 1);
        checkOutDate = calendar.getTime();

        tfSearch.getEditText().setText(sdf.format(checkInDate) + " - " + sdf.format(checkOutDate));

        roomList = new ArrayList<>();
        JSONObject object = new JSONObject();

        try {
            object.put("checkIn", sdf.format(checkInDate));
            object.put("checkOut", sdf.format(checkOutDate));


        } catch (JSONException e) {
            Log.e("SearchFragment", "Error creating JSON object: " + e.getMessage());
        }

        new PostTask(getContext(), this, "Error", "rooms").execute(object);
    }

    private void filterAction(View view) {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select Date");

        long todayMillis = System.currentTimeMillis();
        builder.setSelection(new Pair<>(todayMillis, todayMillis)); // Default to today

        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();

        materialDatePicker.show(getParentFragmentManager(), "DATE_PICKER");

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            long startMillis = selection.first;
            long endMillis = selection.second;


            if (startMillis <= todayMillis) {
                Messenger.showAlertDialog(getContext(), "Error", "Selected date cannot be in the past.", "OK").show();
                return; // Prevent further processing
            }

            long differenceInDays = (endMillis - startMillis) / (1000 * 60 * 60 * 24); // Convert milliseconds to days
            if (differenceInDays > 10) {
                Messenger.showAlertDialog(getContext(), "Error", "The maximum number of days is 10.", "OK").show();
                return; // Prevent further processing
            }


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String startDate = sdf.format(new Date(startMillis));
            String endDate = sdf.format(new Date(endMillis));


            this.checkInDate = new Date(startMillis);
            this.checkOutDate = new Date(endMillis);

            tfSearch.getEditText().setText(
                    startDate + " - " + endDate
            );

        });
    }


    private void filterAction(TextInputLayout tfSearch) {
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select Date");


        long todayMillis = System.currentTimeMillis();
        builder.setSelection(new Pair<>(todayMillis, todayMillis)); // Default to today

        MaterialDatePicker<Pair<Long, Long>> materialDatePicker = builder.build();

        materialDatePicker.show(getParentFragmentManager(), "DATE_PICKER");

        materialDatePicker.addOnPositiveButtonClickListener(selection -> {

            long startMillis = selection.first;
            long endMillis = selection.second;


            if (startMillis <= todayMillis) {
                Toast.makeText(getContext(), "Selected date cannot be in the past.", Toast.LENGTH_SHORT).show();
                return; // Prevent further processing
            }


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
            String startDate = sdf.format(new Date(startMillis));
            String endDate = sdf.format(new Date(endMillis));


            this.checkInDate = new Date(startMillis);
            this.checkOutDate = new Date(endMillis);

            tfSearch.getEditText().setText(
                    startDate + " - " + endDate
            );

        });
    }


    @Override
    public void onPostSuccess(String responseData) {
        rvRoom.setLayoutManager(new LinearLayoutManager(getContext()));
        roomList.clear();

        Gson gson = new Gson();
        Type roomTypeList = new TypeToken<List<Room>>() {}.getType();
        roomList = gson.fromJson(responseData, roomTypeList);

        RoomAdapter roomAdapter = new RoomAdapter(getContext(), roomList, this);
        rvRoom.setAdapter(roomAdapter);
        loader.dismissLoader();
    }

    @Override
    public void onPostError(String errorMessage) {
        Log.e("SearchFragment", "Post request failed: " + errorMessage);
        loader.dismissLoader();
    }
    @Override
    public void onRoomSelected(Room room) {
        // Check if any required information is missing
        if (checkInDate == null || checkOutDate == null || adult == null || children == null) {



            new MaterialAlertDialogBuilder(getContext())
                    .setTitle("Missing Information")
                    .setMessage("Please select a date and number of guests.")
                    .setPositiveButton("OK", (dialog, which) -> {
                        // Optional: Add any action on dialog dismiss
                        dialog.dismiss();
                    })
                    .setCancelable(true) // Allow the dialog to be canceled
                    .show();
            return;
        }

        // Proceed with the intent if all information is available

        if (adult.equals("0") && children.equals("0")) {
            Messenger.showAlertDialog(getContext(), "Error", "Please enter the number of guests.", "OK").show();
            return;
        }

        Intent intent = new Intent(getContext(), RoomDetails.class);
        intent.putExtra(Room.INTENT_NAME, room);
        intent.putExtra("checkIn", this.checkInDate.toString());
        intent.putExtra("checkOut", this.checkOutDate.toString());
        intent.putExtra("adult", adult);
        intent.putExtra("children", children);

        Log.d("SearchFragment", "CheckIn: " + this.checkInDate.toString());
        Log.d("SearchFragment", "CheckOut: " + this.checkOutDate.toString());
        Log.d("SearchFragment", "Adult: " + adult);
        Log.d("SearchFragment", "Children: " + children);
        startActivity(intent);
    }

}
