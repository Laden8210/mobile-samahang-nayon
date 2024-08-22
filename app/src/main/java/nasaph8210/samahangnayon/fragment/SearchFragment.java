package nasaph8210.samahangnayon.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputLayout;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.adapter.RoomAdapter;
import nasaph8210.samahangnayon.fragment.bottomsheet.FilterFragment;


public class SearchFragment extends Fragment {

    private TextInputLayout tfSearch;
    private FilterFragment filterFragment;

    private RecyclerView rvRoom;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        rvRoom = view.findViewById(R.id.rv_room);
        rvRoom.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRoom.setAdapter(new RoomAdapter());
        tfSearch = view.findViewById(R.id.tf_search);
        tfSearch.setEndIconOnClickListener(this::filterAction);
        filterFragment = new FilterFragment();
        return view;
    }

    private void filterAction(View view) {
        filterFragment.show(getParentFragmentManager(), "TAG");
    }
}