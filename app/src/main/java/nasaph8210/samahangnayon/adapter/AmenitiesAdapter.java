package nasaph8210.samahangnayon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.callback.AddAmenities;
import nasaph8210.samahangnayon.model.Amenities;
import nasaph8210.samahangnayon.model.ReservationAmenity;

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.MyViewHolder> {

    private List<ReservationAmenity> amenitiesList;


    private TextInputLayout tilQuantity;

    public AmenitiesAdapter(List<ReservationAmenity> amenitiesList){
        this.amenitiesList = amenitiesList;
    }

    @NonNull
    @Override
    public AmenitiesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_amenities_selection, parent, false);
        return new AmenitiesAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AmenitiesAdapter.MyViewHolder holder, int position) {
        ReservationAmenity amenities = amenitiesList.get(position);
        holder.amenityName.setText(amenities.getAmenity().getName() +" x " + amenities.getQuantity());
        holder.price.setText("₱" + amenities.getTotalCost());

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
