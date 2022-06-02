package csie.aplicatielicenta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import csie.aplicatielicenta.databinding.ActivityFirstBinding;
import csie.aplicatielicenta.fragments.DashboardFragment;
import csie.aplicatielicenta.fragments.SettingsFragment;

public class FirstActivity extends AppCompatActivity {

    ActivityFirstBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirstBinding.inflate((getLayoutInflater()));
        setContentView(binding.getRoot());
        replaceFragment(new DashboardFragment());

        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){

                case R.id.dashboard:
                    replaceFragment(new DashboardFragment());
                    break;

                case R.id.settings:
                    replaceFragment(new SettingsFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

    }
}