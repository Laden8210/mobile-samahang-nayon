package nasaph8210.samahangnayon.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import nasaph8210.samahangnayon.fragment.viewpager.CancelFragment;
import nasaph8210.samahangnayon.fragment.viewpager.HistoryFragment;
import nasaph8210.samahangnayon.fragment.viewpager.UpcomingFragment;


public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new UpcomingFragment();
            case 1:
                return new HistoryFragment();
            case 2:
                return new CancelFragment();
            default:
                return new UpcomingFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
