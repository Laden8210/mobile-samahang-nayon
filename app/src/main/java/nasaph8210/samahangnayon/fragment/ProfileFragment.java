package nasaph8210.samahangnayon.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import org.json.JSONObject;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.api.PostCallback;
import nasaph8210.samahangnayon.api.PostTask;
import nasaph8210.samahangnayon.model.Guest;
import nasaph8210.samahangnayon.util.Messenger;
import nasaph8210.samahangnayon.util.SessionManager;
import nasaph8210.samahangnayon.view.LoginActivity;


public class ProfileFragment extends Fragment implements PostCallback {


    private Button btnEditProfile;
    private Button btnLogout;

    private TextView editName, editEmail, editContact, editPassword;

    private TextView fullname, email, contact;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        btnLogout = view.findViewById(R.id.btnLogout);
        fullname = view.findViewById(R.id.fullname);
        email = view.findViewById(R.id.email);
        contact = view.findViewById(R.id.contactnumber);
        editContact = view.findViewById(R.id.editContactNumber);
        editEmail = view.findViewById(R.id.editEmail);
        editName = view.findViewById(R.id.editName);
        editPassword = view.findViewById(R.id.editPassword);

        editContact.setOnClickListener(this::editContactAction);
        editEmail.setOnClickListener(this::editEmailAction);
        editName.setOnClickListener(this::editNameAction);
        editPassword.setOnClickListener(this::editPasswordAction);


        if (SessionManager.getInstance(getContext()).getToken() == null) {
            Messenger.showAlertDialog(getContext(), "Session Expired", "Your session has expired. Please login again.", "Ok", null,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                            getActivity().finish();
                        }
                    }, null).show();
        }

        btnLogout.setOnClickListener(this::logoutAction);
        try{
            JSONObject jsonObject = new JSONObject();
            new PostTask(getContext(), this, "Error", "user/getCurrentUser").execute(jsonObject);

        }catch (Exception e){

        }
        return view;
    }

    private void editNameAction(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit Name");

        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.dialog_change_name, (ViewGroup) getView(), false);


        TextInputEditText firstNameEditText = viewInflated.findViewById(R.id.firstNameEditText);
        TextInputEditText lastNameEditText = viewInflated.findViewById(R.id.lastNameEditText);
        TextInputEditText middleNameEditText = viewInflated.findViewById(R.id.middleNameEditText);
        TextInputEditText passwordEditText = viewInflated.findViewById(R.id.passwordEditText);

        builder.setView(viewInflated);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String firstName = firstNameEditText.getText().toString();
                String lastName = lastNameEditText.getText().toString();
                String middleName = middleNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                try {
                 JSONObject jsonObject = new JSONObject();
                    jsonObject.put("firstName", firstName);
                    jsonObject.put("lastName", lastName);
                    jsonObject.put("middleName", middleName);
                    jsonObject.put("password", password);

                    new PostTask(getContext(), new PostCallback() {
                        @Override
                        public void onPostSuccess(String responseData) {
                            Messenger.showAlertDialog(getContext(), "Success", "Name updated successfully", "Ok", null, null, null).show();
                        }

                        @Override
                        public void onPostError(String errorMessage) {
                            Messenger.showAlertDialog(getContext(), "Error", errorMessage, "Ok", null, null, null).show();
                        }
                    }, "Error", "user/update").execute(jsonObject);

                }catch (Exception e){
                    e.printStackTrace();
                }

                dialogInterface.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }


    private void editContactAction(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit Contact Information");

        // Inflate the custom layout for the dialog
        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.dialog_change_phone, (ViewGroup) getView(), false);

        // Initialize the EditText fields
        TextInputEditText phoneNumberEditText = viewInflated.findViewById(R.id.phoneNumberEditText);
        TextInputEditText confirmPasswordEditText = viewInflated.findViewById(R.id.confirmPasswordEditText);

        // Set existing values if applicable
        // phoneNumberEditText.setText(currentPhoneNumber);
        // confirmPasswordEditText.setText(currentPassword); // if you want to pre-fill

        builder.setView(viewInflated);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String phoneNumber = phoneNumberEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                try {
                 JSONObject jsonObject = new JSONObject();
                    jsonObject.put("contactNumber", phoneNumber);
                    jsonObject.put("password", confirmPassword);

                    new PostTask(getContext(), new PostCallback() {
                        @Override
                        public void onPostSuccess(String responseData) {
                            Messenger.showAlertDialog(getContext(), "Success", "Contact updated successfully", "Ok", null, null, null).show();
                        }

                        @Override
                        public void onPostError(String errorMessage) {
                            Messenger.showAlertDialog(getContext(), "Error", errorMessage, "Ok", null, null, null).show();
                        }
                    }, "Error", "user/updateNumber").execute(jsonObject);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        builder.show();
    }


    private void editEmailAction(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit Email");

        // Inflate the custom layout for the dialog
        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.dialog_change_email, (ViewGroup) getView(), false);

        // Initialize the EditText fields
        TextInputEditText emailEditText = viewInflated.findViewById(R.id.emailEditText);
        TextInputEditText confirmPasswordEditText = viewInflated.findViewById(R.id.confirmPasswordEditText);

        builder.setView(viewInflated);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Get the input from the EditText fields
                String email = emailEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                try {
                 JSONObject jsonObject = new JSONObject();
                 jsonObject.put("email", email);
                 jsonObject.put("password", confirmPassword);

                    new PostTask(getContext(), new PostCallback() {
                        @Override
                        public void onPostSuccess(String responseData) {
                            Messenger.showAlertDialog(getContext(), "Success", "Email updated successfully", "Ok", null, null, null).show();
                        }

                        @Override
                        public void onPostError(String errorMessage) {
                            Messenger.showAlertDialog(getContext(), "Error", errorMessage, "Ok", null, null, null).show();
                        }
                    }, "Error", "user/updateEmail").execute(jsonObject);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss(); // Close the dialog
            }
        });

        builder.show();
    }

    // Method to validate input (you can customize the logic as needed)
    private boolean isInputValid(String email, String confirmPassword) {
        // Check if the email is valid (basic example)
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() &&
                confirmPassword != null && !confirmPassword.isEmpty();
    }


    private void editPasswordAction(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Edit Password");

        // Inflate the custom layout for the dialog
        View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.dialog_change_password, (ViewGroup) getView(), false);

        // Initialize the EditText fields
        TextInputEditText newPasswordEditText = viewInflated.findViewById(R.id.newPasswordEditText);
        TextInputEditText confirmPasswordEditText = viewInflated.findViewById(R.id.confirmPasswordEditText);
        TextInputEditText oldPasswordEditText = viewInflated.findViewById(R.id.oldPasswordEditText);

        builder.setView(viewInflated);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Get the input from the EditText fields
                String newPassword = newPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                String oldPassword = oldPasswordEditText.getText().toString();

                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("newPassword", newPassword);
                    jsonObject.put("oldPassword", oldPassword);
                    jsonObject.put("confirmPassword", confirmPassword);


                    new PostTask(getContext(), new PostCallback() {
                        @Override
                        public void onPostSuccess(String responseData) {
                            Messenger.showAlertDialog(getContext(), "Success", "Password updated successfully", "Ok", null, null, null).show();
                        }

                        @Override
                        public void onPostError(String errorMessage) {
                            Messenger.showAlertDialog(getContext(), "Error", errorMessage, "Ok", null, null, null).show();
                        }
                    }, "Error", "user/updatePassword").execute(jsonObject);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss(); // Close the dialog
            }
        });

        builder.show();
    }

    private void logoutAction(View view) {
        Messenger.showAlertDialog(getContext(), "Logout", "Are you sure you want to logout?", "Yes", "No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SessionManager.getInstance(getContext()).clear();
                        getContext().startActivity(new Intent(getContext(), LoginActivity.class));
                        dialogInterface.dismiss();
                        getActivity().finish();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    @Override
    public void onPostSuccess(String responseData) {
        Gson gson = new Gson();

        Guest guest = gson.fromJson(responseData, Guest.class);

        fullname.setText(guest.getFirstName() + " " + guest.getLastName());
        email.setText(guest.getEmailAddress());
        contact.setText(guest.getContactNumber());

    }

    @Override
    public void onPostError(String errorMessage) {

    }
}