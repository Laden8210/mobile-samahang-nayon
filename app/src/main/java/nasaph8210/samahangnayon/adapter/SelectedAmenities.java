package nasaph8210.samahangnayon.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.callback.RemoveAmenities;
import nasaph8210.samahangnayon.model.Amenities;
import nasaph8210.samahangnayon.model.AmenitiesAdded;
import nasaph8210.samahangnayon.util.Messenger;
import nasaph8210.samahangnayon.view.CreateReservationActivity;

public class SelectedAmenities extends RecyclerView.Adapter<SelectedAmenities.MyViewHolder> {
    private List<AmenitiesAdded> amenitiesList;
    private Context context;

    private RemoveAmenities removeAmenities;

    public SelectedAmenities(List<AmenitiesAdded> amenitiesList, Context context, RemoveAmenities removeAmenities) {
        this.amenitiesList = amenitiesList;
        this.context = context;
        this.removeAmenities = removeAmenities;
    }

    @NonNull
    @Override
    public SelectedAmenities.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_amenities, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedAmenities.MyViewHolder holder, int position) {
        AmenitiesAdded amenities = amenitiesList.get(position);
        holder.tvAmenityName.setText(amenities.getName());
        holder.tvQuantity.setText(String.valueOf(amenities.getQuantity()));

        int pos = position;

        holder.cardAmenities.setOnClickListener(v -> {
            Messenger.showAlertDialog(context, "Remove Amenity", "Are you sure you want to remove this amenity?", "Yes", "No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    removeAmenities.removeAmenity(pos);
                    notifyDataSetChanged();
                }
            }, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            }).show();
        });
    }

    @Override
    public int getItemCount() {
        return amenitiesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvAmenityName;
        private TextView tvQuantity;
        private CardView cardAmenities;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAmenityName = itemView.findViewById(R.id.tvAmenitiesName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            cardAmenities = itemView.findViewById(R.id.cardAmenities);

        }
    }
}
