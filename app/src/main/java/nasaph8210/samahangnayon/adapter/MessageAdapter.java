package nasaph8210.samahangnayon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.model.Message;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    private final List<Message> messages;
    private static final int MESSAGE_SEND = 1;
    private static final int MESSAGE_RECEIVED = 2;

    public MessageAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == MESSAGE_SEND) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_message_sender, parent, false);
            return new SenderHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_message_receiver, parent, false);
            return new ReceiverHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message message = messages.get(position);
        if (holder.getItemViewType() == MESSAGE_SEND) {
            ((SenderHolder) holder).bind(message);
        } else {
            ((ReceiverHolder) holder).bind(message);
        }
    }

    @Override
    public int getItemCount() {
        return messages != null ? messages.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return messages.get(position).getIsGuestMessage() == 0 ? MESSAGE_RECEIVED : MESSAGE_SEND;
    }

    public class ReceiverHolder extends RecyclerView.ViewHolder {

        private final TextView tvMessage;

        private final TextView tvTime;


        public ReceiverHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_message);
            tvTime = itemView.findViewById(R.id.tv_time);
        }

        public void bind(Message message) {
            tvMessage.setText(message.getMessage() != null ? message.getMessage() : "");
            tvTime.setText(message.getTimeSent() != null ? message.getTimeSent() : "");
        }
    }

    public class SenderHolder extends RecyclerView.ViewHolder {
        private final TextView tvMessage;


        public SenderHolder(@NonNull View itemView) {
            super(itemView);
            tvMessage = itemView.findViewById(R.id.tv_message);

        }

        public void bind(Message message) {
            tvMessage.setText(message.getMessage() != null ? message.getMessage() : "");

        }
    }
}
