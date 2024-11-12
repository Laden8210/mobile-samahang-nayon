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

public class HistoryFragment extends Fragment implements PostCallback, RefreshReservation {

    private RecyclerView recyclerView;
    private Handler handler;
    private Runnable refreshRunnable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history, container, false);

        // Initialize the RecyclerView
        recyclerView = view.findViewById(R.id.rv_transaction);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the Handler for periodic updates
        handler = new Handler();

        // Fetch initial data
        fetchReservations();

        // Set up periodic refresh every 5 seconds
        refreshRunnable = new Runnable() {
            @Override
            public void run() {
                fetchReservations();
                handler.postDelayed(this, 5000); // Refresh every 5 seconds
            }
        };

        // Start the periodic refresh
        handler.post(refreshRunnable);

        return view;
    }

    // Fetch reservations from the server
    private void fetchReservations() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", "Checked Out");
            new PostTask(getContext(), this, "error", "reservation/getReservation").execute(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPostSuccess(String responseData) {
        Gson gson = new Gson();

        List<Transaction> transactionList = new ArrayList<>();
        Type transactionType = new TypeToken<List<Transaction>>() {}.getType();
        transactionList = gson.fromJson(responseData, transactionType);


        if (recyclerView == null) {
            return;
        }
        // Set or update the adapter
        TransactionAdapter adapter = new TransactionAdapter(getContext(), transactionList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPostError(String errorMessage) {
        Log.e("HistoryFragment", "PostError: " + errorMessage);
    }

    // Stop the periodic task when Fragment is no longer visible
    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(refreshRunnable);
    }

    @Override
    public void refresh() {
        fetchReservations(); // Manual refresh if required
    }
}
