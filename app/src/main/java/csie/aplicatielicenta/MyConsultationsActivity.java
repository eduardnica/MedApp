package csie.aplicatielicenta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import csie.aplicatielicenta.Adapters.ConsultationAdapter;
import csie.aplicatielicenta.Models.Consultation;


public class MyConsultationsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    ConsultationAdapter consultationAdapter;
    ArrayList<Consultation> list;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_consultations);

        recyclerView = findViewById(R.id.consultatiuonRecyclerView);
        database = FirebaseDatabase.getInstance().getReference("Consultations").child(uid);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<>();
        consultationAdapter = new ConsultationAdapter(list, this);
        recyclerView.setAdapter(consultationAdapter);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Consultation consultation = dataSnapshot.getValue(Consultation.class);
                    list.add(consultation);
                }
                recyclerView.setAdapter(consultationAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });

    }
}