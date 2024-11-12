package nasaph8210.samahangnayon.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.model.Inbox;
import nasaph8210.samahangnayon.view.ViewMessage;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.MyViewHolder> {

    private Context context;
    private List<Inbox> inboxList;

    public InboxAdapter(Context context, List<Inbox> inboxList) {
        this.context = context;
        this.inboxList = inboxList;
    }

    @NonNull
    @Override
    public InboxAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_inbox, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InboxAdapter.MyViewHolder holder, int position) {
        Inbox inbox = inboxList.get(position);
        holder.tvTitle.setText(inbox.getEmployeeName());
        holder.tvMessage.setText(inbox.getLastMessage());

        holder.tvDate.setText(inbox.getLastMessageDate());
        holder.tvBadge.setText(String.valueOf(inbox.getTotalUnreadMessage()));
        if (inbox.getUnreadCount() == 1){
            holder.cvBadge.setVisibility(View.GONE);
        }

        holder.cvHolder.setOnClickListener(v -> {

            Intent intent = new Intent(context, ViewMessage.class);
            intent.putExtra("employee_id", inbox.getEmployeeID());
            Log.d("InboxAdapter", "Employee ID: " + inbox.getEmployeeID());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return inboxList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvMessage, tvDate, tvBadge;
        CardView cvBadge, cvHolder;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvName);
            tvMessage = itemView.findViewById(R.id.tv_message);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvBadge = itemView.findViewById(R.id.tvBadge);
            cvBadge = itemView.findViewById(R.id.badge);
            cvHolder = itemView.findViewById(R.id.cvHolder);
        }
    }
}
