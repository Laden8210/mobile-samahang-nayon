package nasaph8210.samahangnayon.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.adapter.ViewPagerAdapter;
import nasaph8210.samahangnayon.callback.RefreshReservation;
import nasaph8210.samahangnayon.util.Messenger;
import nasaph8210.samahangnayon.util.SessionManager;
import nasaph8210.samahangnayon.view.LoginActivity;


public class BookingFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_booking, container, false);
        tabLayout = view.findViewById(R.id.tab_layout);
        viewPager2 = view.findViewById(R.id.view_pager);
        viewPagerAdapter = new ViewPagerAdapter(this.getActivity());
        viewPager2.setAdapter(viewPagerAdapter);

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

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
                Fragment fragment = viewPagerAdapter.createFragment(tab.getPosition());
                if (fragment instanceof RefreshReservation) {
                    ((RefreshReservation) fragment).refresh(); // Call refresh method
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Fragment fragment = viewPagerAdapter.createFragment(tab.getPosition());
                if (fragment instanceof RefreshReservation) {
                    ((RefreshReservation) fragment).refresh(); // Call refresh method
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Fragment fragment = viewPagerAdapter.createFragment(tab.getPosition());
                if (fragment instanceof RefreshReservation) {
                    ((RefreshReservation) fragment).refresh(); // Call refresh method
                }
            }


        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        return view;
    }
}