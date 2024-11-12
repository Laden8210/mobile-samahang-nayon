package nasaph8210.samahangnayon.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.callback.RemoveSubGuest;
import nasaph8210.samahangnayon.model.SubGuest;
import nasaph8210.samahangnayon.util.Messenger;
import nasaph8210.samahangnayon.view.CreateReservationActivity;

public class SubGuestAdapter extends RecyclerView.Adapter<SubGuestAdapter.MyViewHolder> {


    private Context context;
    private List<SubGuest> subGuestList;
    private RemoveSubGuest removeSubGuest;

    public SubGuestAdapter(Context context, List<SubGuest> subGuestList, RemoveSubGuest removeSubGuest) {
        this.context = context;
        this.subGuestList = subGuestList;
        this.removeSubGuest = removeSubGuest;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = ((CreateReservationActivity) context).getLayoutInflater().inflate(R.layout.card_sub_guest_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SubGuest subGuest = subGuestList.get(position);
        holder.tvSubGuestName.setText(subGuest.getFirstName() + " " + subGuest.getMiddleName() + " " + subGuest.getLastName());

        holder.cvSubGuest.setOnClickListener(view -> {
            Messenger.showAlertDialog(context, "Remove Sub Guest", "Are you sure you want to remove this sub guest?", "Yes", "No",
                    (dialogInterface, i) -> {
                removeSubGuest.removeSubGuest(position);
                subGuestList.remove(position);
                notifyItemRemoved(position);
                dialogInterface.dismiss();
            }, (dialogInterface, i) -> dialogInterface.dismiss()).show();
        });
    }

    @Override
    public int getItemCount() {
        return subGuestList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvSubGuestName;
        private CardView cvSubGuest;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubGuestName = itemView.findViewById(R.id.tvSubGuestName);
            cvSubGuest = itemView.findViewById(R.id.cardView);
        }
    }
}
