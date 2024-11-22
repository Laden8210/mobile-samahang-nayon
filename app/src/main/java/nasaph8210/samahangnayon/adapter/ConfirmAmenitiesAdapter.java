package nasaph8210.samahangnayon.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.model.AmenitiesAdded;

public class ConfirmAmenitiesAdapter extends RecyclerView.Adapter<ConfirmAmenitiesAdapter.AmenityViewHolder> {
    private final List<AmenitiesAdded> amenities;

    public ConfirmAmenitiesAdapter(List<AmenitiesAdded> amenities) {
        this.amenities = amenities;
    }

    @NonNull
    @Override
    public AmenityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_amenity, parent, false);
        return new AmenityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AmenityViewHolder holder, int position) {
        AmenitiesAdded amenity = amenities.get(position);
        holder.tvAmenityName.setText(amenity.getName());
        holder.tvAmenityPrice.setText("₱" + amenity.getPrice());
        holder.tvAmenityQuantity.setText("Quantity: " + amenity.getQuantity());
    }

    @Override
    public int getItemCount() {
        return amenities.size();
    }

    static class AmenityViewHolder extends RecyclerView.ViewHolder {
        TextView tvAmenityName, tvAmenityPrice, tvAmenityQuantity;

        public AmenityViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAmenityName = itemView.findViewById(R.id.tv_amenity_name);
            tvAmenityPrice = itemView.findViewById(R.id.tv_amenity_price);
            tvAmenityQuantity = itemView.findViewById(R.id.tv_amenity_quantity);
        }
    }
}
