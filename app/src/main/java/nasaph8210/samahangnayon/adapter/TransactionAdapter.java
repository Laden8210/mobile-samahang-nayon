package nasaph8210.samahangnayon.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;

import java.util.List;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.api.PostCallback;
import nasaph8210.samahangnayon.api.PostTask;
import nasaph8210.samahangnayon.callback.RefreshReservation;
import nasaph8210.samahangnayon.model.Transaction;
import nasaph8210.samahangnayon.util.Messenger;
import nasaph8210.samahangnayon.view.BookingDetailsActivity;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder> {
    private Context context;
    private List<Transaction> transactionList;


    public TransactionAdapter(Context context, List<Transaction> transactionList) {
        this.context = context;
        this.transactionList = transactionList;

    }

    public void updateData(List<Transaction> newTransactions) {
        this.transactionList.clear();
        this.transactionList.addAll(newTransactions);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_transaction, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Transaction transaction = transactionList.get(position);

        holder.dateRangeTextView.setText(transaction.getDateCheckIn() + " - " + transaction.getDateCheckOut());
        holder.roomTypeTextView.setText("Room Type: " + transaction.getRoom().getRoom().getRoomType());
        holder.reservationNumberTextView.setText("Reservation #: " + transaction.getReservationId());

        holder.roomImageView.setImageResource(R.drawable.room_example);
            holder.cardView.setOnClickListener(e -> {
                Intent intent = new Intent(context, BookingDetailsActivity.class);
                intent.putExtra("transaction", transaction);
                context.startActivity(intent);


            });

    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView dateRangeTextView;
        TextView roomTypeTextView;
        TextView reservationNumberTextView;
        ImageView roomImageView;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            dateRangeTextView = itemView.findViewById(R.id.date_range_text_view);
            roomTypeTextView = itemView.findViewById(R.id.room_type_text_view);
            reservationNumberTextView = itemView.findViewById(R.id.reservation_number_text_view);
            roomImageView = itemView.findViewById(R.id.room_image_view);
            cardView = itemView.findViewById(R.id.card_view);
        }
    }
}
