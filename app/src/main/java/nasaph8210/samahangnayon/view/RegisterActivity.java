package nasaph8210.samahangnayon.view;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import android.widget.AdapterView;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.adapter.SpinnerAdapter;
import nasaph8210.samahangnayon.api.PostCallback;
import nasaph8210.samahangnayon.api.PostTask;

import nasaph8210.samahangnayon.api.RetrofitClient;
import nasaph8210.samahangnayon.callback.ApiService;
import nasaph8210.samahangnayon.model.Region;
import nasaph8210.samahangnayon.model.City;
import nasaph8210.samahangnayon.model.Barangay;
import nasaph8210.samahangnayon.util.Loader;
import nasaph8210.samahangnayon.util.Messenger;
import nasaph8210.samahangnayon.util.Miner;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements PostCallback {

    private TextInputLayout tfFirstName, tfMiddleName, tfLastName, tfBirthdate, tfEmail, tfContactNumber, tfPassword, tfCpassword, tfStreet;
    private Button btnDatePicker, btnRegister;
    private Spinner spGender;

    private Spinner spRegion, spCity, spBarangay;
    private List<Region> regions;
    private List<City> cities;
    private List<Barangay> barangays;

    private Loader loader;


    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loader = new Loader();


        // Initialize TextInputLayouts
        tfFirstName = findViewById(R.id.tf_first_name);
        tfMiddleName = findViewById(R.id.tf_middle_name);
        tfLastName = findViewById(R.id.tf_last_name);
        tfBirthdate = findViewById(R.id.tf_birthdate);
        tfEmail = findViewById(R.id.tf_email);
        tfContactNumber = findViewById(R.id.tf_contact_number);
        tfPassword = findViewById(R.id.tf_password);
        spGender = findViewById(R.id.sp_gender);
        spRegion = findViewById(R.id.sp_region);
        spCity = findViewById(R.id.sp_city);
        spBarangay = findViewById(R.id.sp_barangay);
        tfStreet = findViewById(R.id.tf_street);
        tfCpassword = findViewById(R.id.tf_cpassword);

        btnDatePicker = findViewById(R.id.button_date_picker);
        btnRegister = findViewById(R.id.button_register);

        apiService = RetrofitClient.getApiService();
        initializeSpinner();
        fetchRegions();

        spRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Region selectedRegion = regions.get(position);
                fetchCities(selectedRegion.getCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                City selectedCity = cities.get(position);
                fetchBarangays(selectedCity.getCode());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void initializeSpinner() {
        ArrayAdapter<String> genderAdapter = new SpinnerAdapter<String>().GetArrayAdapter(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                Arrays.asList(getResources().getStringArray(R.array.gender))
        );
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(genderAdapter);


    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                RegisterActivity.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String birthdate = selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay;
                    tfBirthdate.getEditText().setText(birthdate);
                },
                year, month, day);

        datePickerDialog.show();
    }

    private void registerUser() {

        String firstName = Miner.getText(tfFirstName);
        String middleName = Miner.getText(tfMiddleName);
        String lastName = Miner.getText(tfLastName);

        String email = Miner.getText(tfEmail);
        String street = Miner.getText(tfStreet);

        String contactNumber = Miner.getText(tfContactNumber);
        String password = Miner.getText(tfPassword);
        String gender = spGender.getSelectedItem().toString();

        String cpassword = Miner.getText(tfCpassword);


        if (firstName.isEmpty()) {

            showDialogError("First name is required");
            return;
        } else {
            tfFirstName.setError(null); // Clear error if not empty
        }

        if (lastName.isEmpty()) {
            showDialogError("Last name is required");
            return;
        } else {
            tfLastName.setError(null); // Clear error if not empty
        }

        if (tfBirthdate.getEditText().getText().toString().isEmpty()) {
            showDialogError("Birthdate is required");
            return;
        } else {
            tfBirthdate.setError(null); // Clear error if not empty
        }

        String birthdate = Miner.getText(tfBirthdate);

        if (birthdate.isEmpty()) {
            showDialogError("Birthdate is required");
            return;
        } else {
            tfBirthdate.setError(null); // Clear error if not empty
        }

        if (email.isEmpty()) {
            showDialogError("Email is required");
            return;
        } else {
            tfEmail.setError(null); // Clear error if not empty
        }

        if (street.isEmpty()) {
            showDialogError("Street is required");
            return;
        } else {
            tfStreet.setError(null); // Clear error if not empty
        }

        if (contactNumber.isEmpty()) {
            showDialogError("Contact number is required");
            return;
        } else {
            tfContactNumber.setError(null); // Clear error if not empty
        }

        if (password.isEmpty()) {
            showDialogError("Password is required");
            return;
        } else {
            tfPassword.setError(null); // Clear error if not empty
        }

        if (spRegion.getSelectedItem() == null) {
            showDialogError("Please select a region");
            return;
        }

        if (spCity.getSelectedItem() == null) {
            showDialogError("Please select a city");
            return;
        }

        if (spBarangay.getSelectedItem() == null) {
            showDialogError("Please select a barangay");
            return;
        }

        if (cpassword.isEmpty()) {
            showDialogError("Confirm Password is required");
            return;
        } else {
            tfCpassword.setError(null); // Clear error if not empty
        }

        if (!password.equals(cpassword)) {
            showDialogError("Password does not match");
            return;
        }




        String city = spCity.getSelectedItem().toString();
        String province = spRegion.getSelectedItem().toString();

        String brgy = spBarangay.getSelectedItem().toString();


        // TODO: add validation for the name and phone number

        tfFirstName.setError(null);
        tfLastName.setError(null);
        tfBirthdate.setError(null);
        tfEmail.setError(null);
        tfContactNumber.setError(null);
        tfPassword.setError(null);

        try {
            JSONObject object = new JSONObject();
            object.put("firstname", firstName);
            object.put("lastname", lastName);
            object.put("middlename", middleName);
            object.put("street", street);
            object.put("city", city);
            object.put("brgy", brgy);
            object.put("province", province);
            object.put("birthdate", birthdate);
            object.put("gender", gender);
            object.put("contactnumber", contactNumber);
            object.put("emailaddress", email);
            object.put("password", password);

            new PostTask(this, this, "Error", "user/createUser").execute(object);
            loader.showLoader(this);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showDialogError(String message) {
        Messenger.showAlertDialog(this, "Error", message, "OK", null, null, null).show();
    }

    @Override
    public void onPostSuccess(String responseData) {
        loader.dismissLoader();
        Gson gson = new Gson();
        Map<String, String> responseMap = gson.fromJson(responseData, Map.class);
        clearField();

        String message = responseMap.get("message");

        Messenger.showAlertDialog(this, "Create Account", message, "Login Now", "Back", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).show();
    }

    public void clearField() {
        tfFirstName.getEditText().setText("");
        tfMiddleName.getEditText().setText("");
        tfLastName.getEditText().setText("");
        tfBirthdate.getEditText().setText("");
        tfEmail.getEditText().setText("");
        tfContactNumber.getEditText().setText("");
        tfPassword.getEditText().setText("");
    }

    @Override
    public void onPostError(String errorMessage) {
        loader.dismissLoader();
        // Handle the error case here
        showDialogError(errorMessage);
    }

    private void fetchRegions() {
        apiService.fetchRegions().enqueue(new Callback<List<Region>>() {
            @Override
            public void onResponse(Call<List<Region>> call, Response<List<Region>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    regions = response.body();

                    Collections.sort(regions, new Comparator<Region>() {
                        @Override
                        public int compare(Region r1, Region r2) {
                            return r1.getName().compareToIgnoreCase(r2.getName());
                        }
                    });

                    ArrayAdapter<Region> adapter = new ArrayAdapter<>(
                            RegisterActivity.this,
                            android.R.layout.simple_spinner_item,
                            regions
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spRegion.setAdapter(adapter);
                } else {
                    Log.e("RegisterActivity", "Error fetching regions");
                }
            }

            @Override
            public void onFailure(Call<List<Region>> call, Throwable t) {
                Log.e("RegisterActivity", "Network failure: " + t.getMessage());
                Toast.makeText(RegisterActivity.this, "Failed to fetch regions", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchCities(String provinceCode) {
        apiService.fetchCities(provinceCode).enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cities = response.body();
                    Collections.sort(cities, new Comparator<City>() {
                        @Override
                        public int compare(City r1, City r2) {
                            return r1.getName().compareToIgnoreCase(r2.getName());
                        }
                    });

                    ArrayAdapter<City> adapter = new ArrayAdapter<>(
                            RegisterActivity.this,
                            android.R.layout.simple_spinner_item,
                            cities
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spCity.setAdapter(adapter);
                } else {
                    Log.e("RegisterActivity", "Error fetching cities");
                }
            }

            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                Log.e("RegisterActivity", "Network failure: " + t.getMessage());
                Toast.makeText(RegisterActivity.this, "Failed to fetch cities", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchBarangays(String cityCode) {
        apiService.fetchBarangays(cityCode).enqueue(new Callback<List<Barangay>>() {
            @Override
            public void onResponse(Call<List<Barangay>> call, Response<List<Barangay>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    barangays = response.body();
                    Collections.sort(barangays, new Comparator<Barangay>() {
                        @Override
                        public int compare(Barangay r1, Barangay r2) {
                            return r1.getName().compareToIgnoreCase(r2.getName());
                        }
                    });
                    ArrayAdapter<Barangay> adapter = new ArrayAdapter<>(
                            RegisterActivity.this,
                            android.R.layout.simple_spinner_item,
                            barangays
                    );
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spBarangay.setAdapter(adapter);
                } else {
                    Log.e("RegisterActivity", "Error fetching barangays");
                }
            }

            @Override
            public void onFailure(Call<List<Barangay>> call, Throwable t) {
                Log.e("RegisterActivity", "Network failure: " + t.getMessage());
                Toast.makeText(RegisterActivity.this, "Failed to fetch barangays", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
