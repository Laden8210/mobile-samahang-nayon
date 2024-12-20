package nasaph8210.samahangnayon.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import nasaph8210.samahangnayon.R;
import nasaph8210.samahangnayon.fragment.BookingFragment;
import nasaph8210.samahangnayon.fragment.InboxFragment;
import nasaph8210.samahangnayon.fragment.ProfileFragment;
import nasaph8210.samahangnayon.fragment.SearchFragment;
import nasaph8210.samahangnayon.util.SessionManager;

public class HeroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hero);

        BottomNavigationView bnvHero = findViewById(R.id.bnvHero);

        if (savedInstanceState == null) {
            loadFragment(new SearchFragment());
        }


        if (SessionManager.getInstance(this).getToken() != null) {

            if (SessionManager.getInstance(this).isVerified() == false) {

                Intent intent = new Intent(this, VerifyLoginActivity.class);
                startActivity(intent);
            }

        }

        bnvHero.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.menu_booking) {
                selectedFragment = new BookingFragment();
                return loadFragment(selectedFragment);
            }

            if (item.getItemId() == R.id.menu_inbox) {
                selectedFragment = new InboxFragment();
                return loadFragment(selectedFragment);
            }

            if (item.getItemId() == R.id.menu_profile) {
                selectedFragment = new ProfileFragment();
                return loadFragment(selectedFragment);
            }

            if (item.getItemId() == R.id.menu_search) {
                selectedFragment = new SearchFragment();
                return loadFragment(selectedFragment);
            }

            return false;

        });
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nfvHero, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            return true;
        }
        return false;
    }
}
