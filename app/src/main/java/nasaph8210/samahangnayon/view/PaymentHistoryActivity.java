package nasaph8210.samahangnayon.view;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.List;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.adapter.PaymentHistoryAdapter;
import nasaph8210.samahangnayon.api.ApiService;
import nasaph8210.samahangnayon.api.PostCallback;
import nasaph8210.samahangnayon.api.PostTask;
import nasaph8210.samahangnayon.api.ProofOfPaymentAPI;
import nasaph8210.samahangnayon.api.RetrofitClientUpload;

import nasaph8210.samahangnayon.model.Payment;
import nasaph8210.samahangnayon.model.ProofPaymentRequest;
import nasaph8210.samahangnayon.util.FileUtil;
import nasaph8210.samahangnayon.util.Loader;
import nasaph8210.samahangnayon.util.Messenger;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentHistoryActivity extends AppCompatActivity implements PostCallback {

    private RecyclerView recyclerView;
    private PaymentHistoryAdapter adapter;
    private int selectedReservationId = -1;
    private static final int REQUEST_PERMISSION_CODE = 1001;

    private Button btnAddPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        btnAddPayment = findViewById(R.id.button2);
        btnAddPayment.setOnClickListener(this::addPaymentAction);





        if (getIntent().hasExtra("reservation_id")) {
            int reservationId = getIntent().getIntExtra("reservation_id", 0);
            fetchPaymentHistory(reservationId);
        }
    }


    private void addPaymentAction(View view) {
        int reservationId = getIntent().getIntExtra("reservation_id", 0);

        JSONObject jsonObject = new JSONObject();


        new MaterialAlertDialogBuilder(this)
                .setTitle("Payment Options")
                .setMessage("Please choose your payment option:")
                .setPositiveButton("Full Payment", (dialog, which) -> {
                    try {
                        jsonObject.put("payment_option", "full");
                        jsonObject.put("reservation_id", reservationId);
                        new PostTask(this, new PostCallback() {
                            @Override
                            public void onPostSuccess(String responseData) {
                                              try{
                                    JSONObject jsonObject = new JSONObject(responseData);
                                    JSONObject dataObject = jsonObject.getJSONObject("data");
                                    JSONObject attributesObject = dataObject.getJSONObject("attributes");

                                    String checkoutUrl = attributesObject.getString("checkout_url");
                                    Intent intent = new Intent(PaymentHistoryActivity.this, PaymentActivity.class);
                                    intent.putExtra("checkout_url", checkoutUrl);
                                    startActivity(intent);
                                }catch (Exception e){
                                    Log.e("PaymentHistoryActivity", "Error: " + e.getMessage());
                                }


                            }

                            @Override
                            public void onPostError(String errorMessage) {
                                Messenger.showAlertDialog(PaymentHistoryActivity.this,  "Error","Failed to add payment!", "OK").show();
                            }
                        }, "Adding payment...", "addPayment").execute(jsonObject);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                })
                .setNegativeButton("Down Payment", (dialog, which) -> {
                    try {
                        jsonObject.put("reservation_id", reservationId);
                        jsonObject.put("payment_option", "down");
                        new PostTask(this, new PostCallback() {
                            @Override
                            public void onPostSuccess(String responseData) {
                                try{
                                    JSONObject jsonObject = new JSONObject(responseData);
                                    JSONObject dataObject = jsonObject.getJSONObject("data");
                                    JSONObject attributesObject = dataObject.getJSONObject("attributes");

                                    String checkoutUrl = attributesObject.getString("checkout_url");
                                    Intent intent = new Intent(PaymentHistoryActivity.this, PaymentActivity.class);
                                    intent.putExtra("checkout_url", checkoutUrl);
                                    startActivity(intent);
                                }catch (Exception e){
                                    Log.e("PaymentHistoryActivity", "Error: " + e.getMessage());
                                }

                            }

                            @Override
                            public void onPostError(String errorMessage) {
                                Messenger.showAlertDialog(PaymentHistoryActivity.this,  "Error","Failed to add payment!", "OK").show();
                            }
                        }, "Adding payment...", "addPayment").execute(jsonObject);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                })
                .setNeutralButton("Cancel", (dialog, which) -> {

                    dialog.dismiss();
                })
                .show();
    }



    private void fetchPaymentHistory(int reservationId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ReservationId", String.valueOf(reservationId));
        } catch (JSONException e) {
            Log.e("PaymentHistoryActivity", "Failed to create JSON object: " + e.getMessage());
        }

        new PostTask(this, this, "Fetching payment history...", "paymentHistory").execute(jsonObject);
    }

    @Override
    public void onPostSuccess(String responseData) {
        Gson gson = new Gson();
        Type paymentListType = new TypeToken<List<Payment>>() {}.getType();
        List<Payment> paymentList = gson.fromJson(responseData, paymentListType);

        adapter = new PaymentHistoryAdapter(paymentList, reservationId -> {
            selectedReservationId = reservationId;
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PaymentHistoryAdapter.UPLOAD_PROOF_REQUEST_CODE);
        });

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onPostError(String errorMessage) {
        Log.e("PaymentHistoryActivity", "Error: " + errorMessage);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PaymentHistoryAdapter.UPLOAD_PROOF_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            if (selectedImage != null && selectedReservationId > 0) {
                File imageFile = getFileFromUri(selectedImage);
                if (imageFile != null) {
                    showImageConfirmationDialog(imageFile, selectedReservationId);
                } else {
                    Toast.makeText(this, "Failed to process the image.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Invalid image or reservation ID.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showImageConfirmationDialog(File imageFile, int reservationId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_image_confirmation, null);
        builder.setView(dialogView);

        ImageView imgPreview = dialogView.findViewById(R.id.imgPreview);
        imgPreview.setImageURI(Uri.fromFile(imageFile));

        builder.setTitle("Confirm Upload")
                .setMessage("Do you want to upload this proof of payment?")
                .setPositiveButton("Upload", (dialog, which) -> {
                    Loader loader = new Loader();
                    loader.showLoader(this);
                    uploadProofToServer(imageFile, reservationId, loader);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void uploadProofToServer(File imageFile, int reservationId, Loader loader) {
        Log.d("UploadProof", "Starting upload process...");

        // Log the image file and reservation ID
        Log.d("UploadProof", "Reservation ID: " + reservationId);
        Log.d("UploadProof", "Image file: " + imageFile.getAbsolutePath());

        ApiService apiService = RetrofitClientUpload.getInstance().create(ApiService.class);

        // Prepare the request body for the reservation ID and image file
        RequestBody paymentIdBody = RequestBody.create(
                MediaType.parse("text/plain"),
                String.valueOf(reservationId)
        );
        Log.d("UploadProof", "Prepared payment ID body: " + String.valueOf(reservationId));

        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), imageFile);
        MultipartBody.Part proofImagePart = MultipartBody.Part.createFormData("proof_image", imageFile.getName(), requestFile);
        Log.d("UploadProof", "Prepared proof image part: " + imageFile.getName());

        // Create the call for the API request
        Call<ResponseBody> call = apiService.uploadProofPayment(paymentIdBody, proofImagePart);

        // Log the call being made
        Log.d("UploadProof", "Calling API endpoint to upload proof of payment...");
        String url = call.request().url().toString();
        Log.d("UploadProof", "API URL: " + url);
        // Enqueue the request
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("UploadProof", "Received response from API.");

                loader.dismissLoader();

                // Log the response code and body
                Log.d("UploadProof", "Response code: " + response.code());
                if (response.isSuccessful()) {
                    try {
                        String responseBody = response.body() != null ? response.body().string() : "No response body";
                        Log.d("UploadProof", "Successful response: " + responseBody);
                    } catch (IOException e) {
                        Log.e("UploadProof", "Error reading response body", e);
                    }

                    Toast.makeText(PaymentHistoryActivity.this, "Proof of payment uploaded successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    handleUploadError(response);
                    Log.e("UploadProof", "Upload failed. Response: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("UploadProof", "API request failed.");

                loader.dismissLoader();
                Toast.makeText(PaymentHistoryActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();

                // Log the error
                Log.e("UploadProof", "Network error", t);
            }
        });

        Log.d("UploadProof", "Upload request enqueued.");
    }


    private void handleUploadError(Response<ResponseBody> response) {
        try {
            String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
            Log.e("PaymentHistoryActivity", "Upload failed: " + errorBody);
            Toast.makeText(this, "Upload failed: " + errorBody, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e("PaymentHistoryActivity", "Error parsing error response", e);
            Toast.makeText(this, "An error occurred.", Toast.LENGTH_SHORT).show();
        }
    }

    private File getFileFromUri(Uri uri) {
        try {
            // Use the ContentResolver to open an InputStream and save it to a temporary file
            InputStream inputStream = getContentResolver().openInputStream(uri);
            File tempFile = new File(getCacheDir(), "temp_image_" + System.currentTimeMillis() + ".jpg");
            OutputStream outputStream = new FileOutputStream(tempFile);

            // Copy InputStream to File
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            inputStream.close();
            outputStream.close();

            return tempFile; // Return the temporary file
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PaymentHistoryActivity", "Error processing file from URI: " + e.getMessage());
            return null;
        }
    }

}
