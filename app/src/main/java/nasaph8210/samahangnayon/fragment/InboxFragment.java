package nasaph8210.samahangnayon.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.adapter.InboxAdapter;
import nasaph8210.samahangnayon.adapter.MessageAdapter;
import nasaph8210.samahangnayon.adapter.RoomAdapter;
import nasaph8210.samahangnayon.api.PostCallback;
import nasaph8210.samahangnayon.api.PostTask;
import nasaph8210.samahangnayon.model.Inbox;
import nasaph8210.samahangnayon.model.Message;
import nasaph8210.samahangnayon.model.Room;
import nasaph8210.samahangnayon.util.Messenger;
import nasaph8210.samahangnayon.util.SessionManager;
import nasaph8210.samahangnayon.view.LoginActivity;


public class InboxFragment extends Fragment implements PostCallback {


    private RecyclerView rvMessage;
    private MessageAdapter messageAdapter;

    private ImageButton btnSend;
    private TextInputLayout tilMessage;

    private Handler handler;
    private Runnable retrieveMessagesRunnable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_inbox, container, false);



        if (SessionManager.getInstance(getContext()).getToken() == null) {
            Messenger.showAlertDialog(getContext(), "Session Expired", "Your session has expired. Please login again.", "Ok", null,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(getContext(), LoginActivity.class));
                            getActivity().finish();
                        }
                    }, null).show();
        }



        rvMessage = view.findViewById(R.id.rv_message);
        rvMessage.setLayoutManager(new LinearLayoutManager(getContext()));


        btnSend = view.findViewById(R.id.imageButton);
        tilMessage = view.findViewById(R.id.textInputLayout2);

        btnSend.setOnClickListener(this::sendMessage);
        handler = new Handler();

        // Runnable to periodically retrieve messages
        retrieveMessagesRunnable = new Runnable() {
            @Override
            public void run() {
                retrieveMessages();
                handler.postDelayed(this, 1000); // Repeat every second
            }
        };

        // Start the periodic retrieval of messages
        handler.post(retrieveMessagesRunnable);

        return view;
    }


    @Override
    public void onPostSuccess(String responseData) {

        Gson gson = new Gson();
        Type messageListType = new TypeToken<List<Message>>() {}.getType();
        List<Message> messageList = gson.fromJson(responseData, messageListType);

        if (messageList != null && !messageList.isEmpty()) {
            messageAdapter = new MessageAdapter(getContext(), messageList);
            rvMessage.setAdapter(messageAdapter);
            rvMessage.scrollToPosition(messageList.size() - 1);
        } else {
            Log.d("ViewMessage", "No messages to display.");
        }

    }

    @Override
    public void onPostError(String errorMessage) {
        Log.e("ViewMessage", "Error: " + errorMessage);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(retrieveMessagesRunnable);
    }

    private void sendMessage(View view) {
        String message = tilMessage.getEditText().getText().toString();
        if (!message.isEmpty()) {
            try {
                JSONObject data = new JSONObject();

                data.put("Message", message);
                new PostTask(getContext(), new PostCallback() {
                    @Override
                    public void onPostSuccess(String responseData) {
                        tilMessage.getEditText().setText("");
                    }

                    @Override
                    public void onPostError(String errorMessage) {
                        Log.e("ViewMessage", "Error: " + errorMessage); // Log the error message
                    }
                }, "hello", "message/sendGuestMessage").execute(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void retrieveMessages() {
        try {
            JSONObject data = new JSONObject();

            new PostTask(getContext(), this, "hello", "message/retrieveUserMessage").execute(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}