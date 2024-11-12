package nasaph8210.samahangnayon.fragment.bottomsheet;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.adapter.AmenitiesSelection;

import nasaph8210.samahangnayon.api.PostCallback;
import nasaph8210.samahangnayon.api.PostTask;
import nasaph8210.samahangnayon.callback.AddAmenities;
import nasaph8210.samahangnayon.callback.ConfirmAmenities;
import nasaph8210.samahangnayon.model.Amenities;



public class AmenitiesFragment extends BottomSheetDialogFragment implements PostCallback {

    private RecyclerView rvAmenities;
    private List<Amenities> amenitiesList;
    private AmenitiesSelection amenitiesSelection;
    private TextInputLayout tilQuantity;
    private ConfirmAmenities confirmAmenities;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bs_amenities, container, false);
        rvAmenities = view.findViewById(R.id.rv_amenities);
        JSONObject object = new JSONObject();
        new PostTask(getContext(), this, "Error", "amenities").execute(object);
        return view;

    }

    public void setListener(ConfirmAmenities confirmAmenities) {
        this.confirmAmenities = confirmAmenities;
    }

    @Override
    public void onPostSuccess(String responseData) {
        Gson gson = new Gson();
        Type amenities = new TypeToken<List<Amenities>>() {}.getType();
        amenitiesList = gson.fromJson(responseData, amenities);
        amenitiesSelection = new AmenitiesSelection(amenitiesList, getContext(), new AddAmenities() {
            @Override
            public void addAmenities(Amenities amenities) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_select_amenities, null);
                tilQuantity = view.findViewById(R.id.til_quantity);
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(view)
                        .setPositiveButton("OK", (dialog, which) -> {
                            if (tilQuantity.getEditText().getText().toString().isEmpty()) {
                                tilQuantity.setError("Quantity is required");
                                return;
                            }
                            int quantity = Integer.parseInt(tilQuantity.getEditText().getText().toString());
                            if (quantity > 0) {
                                dialog.dismiss();
                                confirmAmenities.confirmAmenities(amenities, quantity);
                            } else {
                                tilQuantity.setError("Invalid quantity");
                            }
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .show();
            }
        });
        rvAmenities.setAdapter(amenitiesSelection);
        rvAmenities.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onPostError(String errorMessage) {

    }
}
