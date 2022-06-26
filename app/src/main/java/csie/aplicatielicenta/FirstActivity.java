package csie.aplicatielicenta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class FirstActivity extends AppCompatActivity {

    MaterialButton btnDashboard, btnProfil, btnConsultation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        btnDashboard = findViewById(R.id.btnDashboard);
        btnProfil = findViewById(R.id.btnProfil);
        btnConsultation = findViewById(R.id.btnConsultation);

        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ReadData.class);
                startActivity(intent);
            }
        });

        btnConsultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent consultation = new Intent(view.getContext(), ConsultationActivity.class);
                startActivity(consultation);
            }
        });



    }


}