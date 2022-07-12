package csie.aplicatielicenta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Objects;

import csie.aplicatielicenta.Models.Consultation;
import csie.aplicatielicenta.Models.User;

public class ConsultationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerCity, spinnerHospital, spinnerSpecialization, spinnerTime;
    ArrayAdapter<CharSequence> adapterCity, adapterHospitalsBucharest,adapterHospitalsCluj, adapterSpecialization, adapterTime;
    EditText editTextDateTime;
    MaterialButton btnRequest;

    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference userDetails = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
    DatabaseReference consultationDetails = FirebaseDatabase.getInstance().getReference().child("Consultation").child(uid);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);

        spinnerCity = findViewById(R.id.spinnerCity);
        spinnerHospital = findViewById(R.id.spinnerHospital);
        spinnerSpecialization = findViewById(R.id.spinnerSpecialization);
        editTextDateTime = findViewById(R.id.editTextDateTime);
        spinnerTime = findViewById(R.id.spinnerTime);
        btnRequest = findViewById(R.id.btnRequest);

        adapterCity = ArrayAdapter.createFromResource(this, R.array.city, android.R.layout.simple_spinner_item);
        adapterHospitalsBucharest = ArrayAdapter.createFromResource(this, R.array.hospitalsBucharest, android.R.layout.simple_spinner_item);
        adapterHospitalsCluj = ArrayAdapter.createFromResource(this, R.array.hospitalsCluj, android.R.layout.simple_spinner_item);
        adapterSpecialization = ArrayAdapter.createFromResource(this, R.array.specialization, android.R.layout.simple_spinner_item);
        adapterTime = ArrayAdapter.createFromResource(this, R.array.time, android.R.layout.simple_spinner_item);

        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterHospitalsBucharest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterHospitalsCluj.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSpecialization.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterTime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCity.setAdapter(adapterCity);
        spinnerCity.setOnItemSelectedListener(this);

        editTextDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(editTextDateTime);
            }
        });
        editTextDateTime.setVisibility(View.INVISIBLE);




        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDetails.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String patientFirstName = (String) snapshot.child("firstName").getValue();
                        String patientLastName = (String) snapshot.child("lastName").getValue();
                        String patient = patientLastName + "_" + patientFirstName;
                        String city = spinnerCity.getSelectedItem().toString();
                        String hospital = spinnerHospital.getSelectedItem().toString();
                        String specialization = spinnerSpecialization.getSelectedItem().toString();
                        String dateAndTime  = editTextDateTime.getText().toString();
                        addConsultation(patient, city, hospital, specialization, dateAndTime);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

        if( parent.getItemAtPosition(position).equals("Choose City")){
            spinnerHospital.setAdapter(null);
            spinnerSpecialization.setAdapter(null);
            spinnerTime.setAdapter(null);
            editTextDateTime.setVisibility(View.INVISIBLE);

        }else if (parent.getItemAtPosition(position).equals("Bucuresti")) {
            spinnerHospital.setAdapter(adapterHospitalsBucharest);
            spinnerHospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

                    if(parent.getItemAtPosition(position).equals("Choose Hospital")){
                        spinnerSpecialization.setAdapter(null);
                        editTextDateTime.setVisibility(View.INVISIBLE);
                        spinnerTime.setAdapter(null);
                    }else {
                        spinnerSpecialization.setAdapter(adapterSpecialization);
                        spinnerSpecialization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                                if(parent.getItemAtPosition(position).equals("Choose Specialization")){
                                    editTextDateTime.setVisibility(View.INVISIBLE);
                                    spinnerTime.setAdapter(null);
                                }else {
                                    editTextDateTime.setVisibility(View.VISIBLE);


                                    







                                    spinnerTime.setAdapter(adapterTime);
                                    spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> adapterView) {

                                        }
                                    });
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) { }
                        });
                    }

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) { }
            });

            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();




        } else if (parent.getItemAtPosition(position).equals("Cluj")){
            spinnerHospital.setAdapter(adapterHospitalsCluj);
            spinnerHospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

                    if(parent.getItemAtPosition(position).equals("Choose Hospital")){
                        spinnerSpecialization.setAdapter(null);
                        editTextDateTime.setVisibility(View.INVISIBLE);
                    }else {
                        spinnerSpecialization.setAdapter(adapterSpecialization);
                        spinnerSpecialization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                                if(parent.getItemAtPosition(position).equals("Choose Specialization")){
                                    editTextDateTime.setVisibility(View.INVISIBLE);
                                }else {
                                    editTextDateTime.setVisibility(View.VISIBLE);
                                }
                            }
                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) { }
                        });
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });



            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void showDateTimeDialog(EditText editTextDateTime){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd HH:mm");
                        editTextDateTime.setText((simpleDateFormat.format(calendar.getTime())));
                    }
                };

                new TimePickerDialog(ConsultationActivity.this, timeSetListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false).show();
            }
        };

        new DatePickerDialog(ConsultationActivity.this,dateSetListener, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    public void addConsultation(String patient, String city, String hospital, String specialization, String dateAndTime){
        Consultation consultation = new Consultation(patient, city, hospital, specialization, dateAndTime);



        FirebaseDatabase.getInstance().getReference().child("Occupied_Dates").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(dateAndTime)){
                    Toast.makeText(ConsultationActivity.this, "Exista deja data asta boss", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(ConsultationActivity.this, "idk", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        consultationDetails.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.hasChild(dateAndTime)){
//
//                } else {
//                    FirebaseDatabase.getInstance().getReference("Occupied_Dates").child(dateAndTime).setValue("True");
//                    FirebaseDatabase.getInstance().getReference("Consultations")
//                            .child(uid)
//                            .child((dateAndTime))
//                            .setValue(consultation).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful()){
//                                Toast.makeText(ConsultationActivity.this, "Consultation has been created!", Toast.LENGTH_LONG).show();
//                            }
//                            else{
//                                Toast.makeText(ConsultationActivity.this, "Consultation has not been created!", Toast.LENGTH_LONG).show();
//                            }
//                        }});
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });














        userDetails.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String firstName = (String) snapshot.child("firstName").getValue();
                String lastName = (String) snapshot.child("lastName").getValue();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



}