package csie.aplicatielicenta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class FirstActivity extends AppCompatActivity {

//    ActivityFirstBinding binding;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        binding = ActivityFirstBinding.inflate((getLayoutInflater()));
//        setContentView(binding.getRoot());
//        replaceFragment(new DashboardFragment());
//
//        binding.bottomNavigation.setOnItemSelectedListener(item -> {
//            switch (item.getItemId()){
//
//                case R.id.dashboard:
//                    replaceFragment(new DashboardFragment());
//                    break;
//
//                case R.id.settings:
//                    replaceFragment(new SettingsFragment());
//                    break;
//            }
//            return true;
//        });
//    }
//
//    private void replaceFragment(Fragment fragment){
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, fragment);
//        fragmentTransaction.commit();
//
//    }

    MaterialButton btnDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        btnDashboard = findViewById(R.id.btnDashboard);

        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });



    }


}