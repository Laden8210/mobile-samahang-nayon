package nasaph8210.samahangnayon.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.callback.SelectRoom;
import nasaph8210.samahangnayon.view.RoomDetails;
import nasaph8210.samahangnayon.api.PostCallback;
import nasaph8210.samahangnayon.api.PostTask;
import nasaph8210.samahangnayon.model.Room;
import nasaph8210.samahangnayon.util.ImageUtil;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.MyViewHolder> {


    private Context context;
    private List<Room> roomList;
    private SelectRoom selectRoom;
    private static final StrikethroughSpan STRIKE_THROUGH_SPAN = new StrikethroughSpan();

    public RoomAdapter(Context context, List<Room> roomList, SelectRoom selectRoom) {

        this.context = context;
        this.roomList = roomList;
        this.selectRoom = selectRoom;
    }

    @NonNull
    @Override
    public RoomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_room, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomAdapter.MyViewHolder holder, int position) {
        Room room = roomList.get(position);
        holder.tvRoomType.setText(room.getRoomType());

        holder.tvDescription.setText(room.getDescription());
        holder.cvRoom.setOnClickListener(e ->{
            selectRoom.onRoomSelected(room);
        });

        holder.tvRoomNumber.setText("Room "+room.getRoomNumber());

        if (room.getDiscount() == null) {
            holder.cardView.setVisibility(View.GONE);
            String formattedPrice = String.format(Locale.getDefault(), "%.2f/night", room.getRoomPrice());
            holder.tvPrice.setText("₱"+ formattedPrice);
        } else{

            if (room.getDiscount() != 0) {
                holder.tvDiscount.setText("-"+room.getDiscount()+"%");

                double totalDiscount =  room.getRoomPrice() -  (room.getRoomPrice() * ((float) room.getDiscount() / 100));
                holder.tvPrice.setText("₱"+ String.format("%.2f", totalDiscount)+"/night",  TextView.BufferType.SPANNABLE);

            }
        }

        try {
            JSONObject object = new JSONObject();
            object.put("room_id", room.getRoomId());
            new PostTask(context, new PostCallback() {
                @Override
                public void onPostSuccess(String responseData) {
                    try {
                        String base64Image = new JSONObject(responseData).getString("image");
                        ImageUtil.createImage(context, base64Image, holder.ivRoomImage);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                @Override
                public void onPostError(String errorMessage) {

                }
            }, "Error", "rooms/image").execute(object);
        }catch (Exception e){

        }
    }

    @Override
    public int getItemCount() {
        return roomList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivRoomImage;
        private TextView tvRoomType;
        private TextView tvDescription;
        private TextView tvPrice;
        private CardView cvRoom;

        private TextView tvRoomNumber;
        private CardView cardView;
        private TextView tvDiscount;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRoomType = itemView.findViewById(R.id.tv_room_type);
            tvDescription = itemView.findViewById(R.id.tv_description);
            tvPrice = itemView.findViewById(R.id.tv_price);
            ivRoomImage = itemView.findViewById(R.id.iv_image);
            cvRoom = itemView.findViewById(R.id.cv_room);
            cardView = itemView.findViewById(R.id.cardView);
            tvDiscount = itemView.findViewById(R.id.tv_discount);
            tvRoomNumber = itemView.findViewById(R.id.tv_roomNumber);
        }
    }
}
