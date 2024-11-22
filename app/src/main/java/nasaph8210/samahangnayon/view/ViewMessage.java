package nasaph8210.samahangnayon.view;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.adapter.MessageAdapter;
import nasaph8210.samahangnayon.api.PostCallback;
import nasaph8210.samahangnayon.api.PostTask;
import nasaph8210.samahangnayon.model.Message;

public class ViewMessage extends AppCompatActivity implements PostCallback {

    private int employeeID;
    private RecyclerView rvMessage;
    private MessageAdapter messageAdapter;

    private ImageButton btnSend;
    private TextInputLayout tilMessage;

    private Handler handler;
    private Runnable retrieveMessagesRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);

        rvMessage = findViewById(R.id.rv_message);
        rvMessage.setLayoutManager(new LinearLayoutManager(this));

        employeeID = getIntent().getIntExtra("employee_id", 0);
        Log.d("ViewMessage", "Employee ID: " + employeeID);

        btnSend = findViewById(R.id.imageButton);
        tilMessage = findViewById(R.id.textInputLayout2);

        btnSend.setOnClickListener(this::sendMessage);
        handler = new Handler();

        retrieveMessagesRunnable = new Runnable() {
            @Override
            public void run() {
                retrieveMessages();
                handler.postDelayed(this, 1000);
            }
        };

        handler.post(retrieveMessagesRunnable);
    }

    private void sendMessage(View view) {
        String message = tilMessage.getEditText().getText().toString();
        if (!message.isEmpty()) {
            try {
                JSONObject data = new JSONObject();
                data.put("EmployeeId", employeeID);
                data.put("Message", message);
                new PostTask(this, new PostCallback() {
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
            data.put("EmployeeId", employeeID);
            new PostTask(this, this, "hello", "message/retrieveUserMessage").execute(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostSuccess(String responseData) {
        Log.d("ViewMessage", "Response Data: " + responseData);

        Gson gson = new Gson();
        Type messageListType = new TypeToken<List<Message>>() {}.getType();
        List<Message> messageList = gson.fromJson(responseData, messageListType);

        if (messageList != null && !messageList.isEmpty()) {
            messageAdapter = new MessageAdapter(this, messageList);
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
    protected void onDestroy() {
        super.onDestroy();

        handler.removeCallbacks(retrieveMessagesRunnable);
    }
}
