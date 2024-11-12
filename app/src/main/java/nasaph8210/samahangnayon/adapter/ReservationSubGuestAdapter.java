package nasaph8210.samahangnayon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.model.ReservationSubGuest;
import nasaph8210.samahangnayon.model.SubGuest;
import nasaph8210.samahangnayon.view.CreateReservationActivity;

public class ReservationSubGuestAdapter extends RecyclerView.Adapter<ReservationSubGuestAdapter.MyViewHolder> {



    private List<ReservationSubGuest> subGuestList;

    public ReservationSubGuestAdapter(List<ReservationSubGuest> subGuestList) {

        this.subGuestList = subGuestList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_sub_guest_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ReservationSubGuest subGuest = subGuestList.get(position);
        holder.tvSubGuestName.setText(subGuest.getFirstName() + " " + subGuest.getMiddleName() + " " + subGuest.getLastName());
    }

    @Override
    public int getItemCount() {
        return subGuestList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSubGuestName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubGuestName = itemView.findViewById(R.id.tvSubGuestName);
        }
    }
}
