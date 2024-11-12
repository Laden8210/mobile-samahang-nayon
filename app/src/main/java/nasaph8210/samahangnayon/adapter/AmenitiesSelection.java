package nasaph8210.samahangnayon.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.callback.AddAmenities;
import nasaph8210.samahangnayon.model.Amenities;

public class AmenitiesSelection extends RecyclerView.Adapter<AmenitiesSelection.MyViewHolder> {

    private List<Amenities> amenitiesList;
    private Context context;
    private AddAmenities addAmenities;

    private TextInputLayout tilQuantity;

    public AmenitiesSelection(List<Amenities> amenitiesList, Context context, AddAmenities addAmenities) {
        this.amenitiesList = amenitiesList;
        this.context = context;
        this.addAmenities = addAmenities;
    }

    @NonNull
    @Override
    public AmenitiesSelection.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_amenities_selection, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AmenitiesSelection.MyViewHolder holder, int position) {
        Amenities amenities = amenitiesList.get(position);
        holder.amenityName.setText(amenities.getName());
        holder.price.setText("₱" + amenities.getPrice());
        holder.cardView.setOnClickListener(v -> {
            addAmenities.addAmenities(amenities);

        });
    }

    @Override
    public int getItemCount() {
        return amenitiesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private TextView amenityName;
        private TextView price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_amenities);
            amenityName = itemView.findViewById(R.id.tv_amenities);
            price = itemView.findViewById(R.id.tv_price);
        }
    }
}
