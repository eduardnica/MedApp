package csie.aplicatielicenta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class FirstActivity extends AppCompatActivity {

    MaterialButton btnDashboard, btnMyConsultations, btnConsultation, btnChat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        btnDashboard = findViewById(R.id.btnDashboard);
        btnMyConsultations = findViewById(R.id.btnMyConsultations);
        btnConsultation = findViewById(R.id.btnConsultation);
        btnChat = findViewById(R.id.btnChat);

        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DashboardActivity.class);
                startActivity(intent);
            }
        });

        btnMyConsultations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myConsultations = new Intent(view.getContext(), MyConsultationsActivity.class);
                startActivity(myConsultations);
            }
        });

        btnConsultation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent consultation = new Intent(view.getContext(), ConsultationActivity.class);
                startActivity(consultation);
            }
        });

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(view.getContext(), ChatActivity.class);
                startActivity(chat);
            }
        });



    }


}