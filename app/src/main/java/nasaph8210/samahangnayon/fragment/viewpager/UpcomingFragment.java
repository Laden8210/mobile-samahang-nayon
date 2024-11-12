package nasaph8210.samahangnayon.fragment.viewpager;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.adapter.TransactionAdapter;
import nasaph8210.samahangnayon.api.PostCallback;
import nasaph8210.samahangnayon.api.PostTask;
import nasaph8210.samahangnayon.callback.RefreshReservation;
import nasaph8210.samahangnayon.model.Transaction;

public class UpcomingFragment extends Fragment implements PostCallback {

    private RecyclerView rvTransaction;
    private Runnable retrieveMessagesRunnable;
    private Handler handler;
    private TransactionAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        rvTransaction = view.findViewById(R.id.rv_transaction);

        // Initialize handler
        handler = new Handler();

        Log.d("UpcomingFragment", "onCreateView called");

        // Fetch data initially
        fetchReservations();

        // Setup periodic retrieval of messages
        retrieveMessagesRunnable = new Runnable() {
            @Override
            public void run() {
                fetchReservations();
                handler.postDelayed(this, 5000); // Refresh every 5 seconds (adjust if needed)
            }
        };

        // Start the periodic task
        handler.post(retrieveMessagesRunnable);

        return view;
    }

    private void fetchReservations() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", "Booked");
            new PostTask(getContext(), this, "error", "reservation/getReservation").execute(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostSuccess(String responseData) {
        Log.d("UpcomingFragment", "Response Data: " + responseData); // Log the response
        Gson gson = new Gson();

        List<Transaction> transactionList = new ArrayList<>();
        Type transactionType = new TypeToken<List<Transaction>>() {}.getType();
        transactionList = gson.fromJson(responseData, transactionType);

        // Make sure the data is actually being parsed correctly
        Log.d("UpcomingFragment", "Transaction List Size: " + transactionList.size());

        if (rvTransaction == null) {
            return;
        }

        if (rvTransaction.getLayoutManager() == null) {
            rvTransaction.setLayoutManager(new LinearLayoutManager(getContext()));
        }


        if (rvTransaction.getAdapter() == null) {
            adapter = new TransactionAdapter(getContext(), transactionList);
            rvTransaction.setAdapter(adapter);
        }else {

            adapter.updateData(transactionList);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        fetchReservations(); // Refresh data when fragment becomes visible
        handler.post(retrieveMessagesRunnable); // Start the periodic task
    }

    @Override
    public void onPostError(String errorMessage) {
        Log.e("UpcomingFragment", "PostError: " + errorMessage);
        if (adapter != null){
            adapter.notifyDataSetChanged();
        }

    }

    // Stop periodic data fetching when Fragment is no longer visible
    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(retrieveMessagesRunnable);
    }


}
