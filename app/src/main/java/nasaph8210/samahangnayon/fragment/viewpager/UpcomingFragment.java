package nasaph8210.samahangnayon.fragment.viewpager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.adapter.TransactionAdapter;

public class UpcomingFragment extends Fragment {

    private RecyclerView rvTransaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_upcoming, container, false);
        rvTransaction = view.findViewById(R.id.rv_transaction);
        rvTransaction.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTransaction.setAdapter(new TransactionAdapter());
        return view;
    }
}