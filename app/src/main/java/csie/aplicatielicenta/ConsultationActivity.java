package csie.aplicatielicenta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.contentcapture.DataShareRequest;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridLayout;
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

import csie.aplicatielicenta.Models.Consultation;

public class ConsultationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerCity, spinnerHospital, spinnerSpecialization;
    ArrayAdapter<CharSequence> adapterCity, adapterHospitalsBucharest,adapterHospitalsCluj, adapterSpecialization;
    EditText editTextDate, editTextTime;
    MaterialButton btnRequest, btnHour8, btnHour9,btnHour10, btnHour11, btnHour8_, btnHour9_, btnHour10_, btnHour11_;
    GridLayout gridLayoutHours;

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
        editTextDate = findViewById(R.id.editTextDate);
        editTextTime = findViewById(R.id.editTextTime);

        gridLayoutHours = findViewById(R.id.gridLayoutHours);
        btnHour8 = findViewById(R.id.btnHour8);
        btnHour9 = findViewById(R.id.btnHour9);
        btnHour10 = findViewById(R.id.btnHour10);
        btnHour11 = findViewById(R.id.btnHour11);
        btnHour8_ = findViewById(R.id.btnHour8_);
        btnHour9_ = findViewById(R.id.btnHour9_);
        btnHour10_ = findViewById(R.id.btnHour10_);
        btnHour11_ = findViewById(R.id.btnHour11_);
        btnRequest = findViewById(R.id.btnRequest);

        adapterCity = ArrayAdapter.createFromResource(this, R.array.city, android.R.layout.simple_spinner_item);
        adapterHospitalsBucharest = ArrayAdapter.createFromResource(this, R.array.hospitalsBucharest, android.R.layout.simple_spinner_item);
        adapterHospitalsCluj = ArrayAdapter.createFromResource(this, R.array.hospitalsCluj, android.R.layout.simple_spinner_item);
        adapterSpecialization = ArrayAdapter.createFromResource(this, R.array.specialization, android.R.layout.simple_spinner_item);

        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterHospitalsBucharest.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterHospitalsCluj.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterSpecialization.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCity.setAdapter(adapterCity);
        spinnerCity.setOnItemSelectedListener(this);

        editTextDate.setVisibility(View.INVISIBLE);
        editTextTime.setVisibility(View.INVISIBLE);
        gridLayoutHours.setVisibility(View.INVISIBLE);
        btnRequest.setVisibility(View.INVISIBLE);

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(editTextDate);
            }
        });

        checkAvailability(editTextDate);


        btnHour8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnHour8.setBackgroundColor(getResources().getColor(R.color.yellow));
                if(btnHour9.isEnabled()){ btnHour9.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour10.isEnabled()){ btnHour10.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour11.isEnabled()){ btnHour11.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour8_.isEnabled()){ btnHour8_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour9_.isEnabled()){ btnHour9_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour10_.isEnabled()){ btnHour10_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour11_.isEnabled()){ btnHour11_.setBackgroundColor(getResources().getColor(R.color.myColor));}

                editTextTime.setText(btnHour8.getText().toString());

            }
        });

        btnHour9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnHour8.isEnabled()){ btnHour8.setBackgroundColor(getResources().getColor(R.color.myColor));}
                btnHour9.setBackgroundColor(getResources().getColor(R.color.yellow));
                if(btnHour10.isEnabled()){ btnHour10.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour11.isEnabled()){ btnHour11.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour8_.isEnabled()){ btnHour8_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour9_.isEnabled()){ btnHour9_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour10_.isEnabled()){ btnHour10_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour11_.isEnabled()){ btnHour11_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                editTextTime.setText(btnHour9.getText().toString());
            }
        });

        btnHour10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnHour8.isEnabled()){ btnHour8.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour9.isEnabled()){ btnHour9.setBackgroundColor(getResources().getColor(R.color.myColor));}
                btnHour10.setBackgroundColor(getResources().getColor(R.color.yellow));
                if(btnHour11.isEnabled()){ btnHour11.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour8_.isEnabled()){ btnHour8_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour9_.isEnabled()){ btnHour9_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour10_.isEnabled()){ btnHour10_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour11_.isEnabled()){ btnHour11_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                editTextTime.setText(btnHour10.getText().toString());
            }
        });

        btnHour11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnHour8.isEnabled()){ btnHour8.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour9.isEnabled()){ btnHour9.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour10.isEnabled()){ btnHour10.setBackgroundColor(getResources().getColor(R.color.myColor));}
                btnHour11.setBackgroundColor(getResources().getColor(R.color.yellow));
                if(btnHour8_.isEnabled()){ btnHour8_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour9_.isEnabled()){ btnHour9_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour10_.isEnabled()){ btnHour10_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour11_.isEnabled()){ btnHour11_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                editTextTime.setText(btnHour11.getText().toString());
            }
        });

        btnHour8_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnHour8.isEnabled()){ btnHour8.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour9.isEnabled()){ btnHour9.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour10.isEnabled()){ btnHour10.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour11.isEnabled()){ btnHour11.setBackgroundColor(getResources().getColor(R.color.myColor));}
                btnHour8_.setBackgroundColor(getResources().getColor(R.color.yellow));
                if(btnHour9_.isEnabled()){ btnHour9_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour10_.isEnabled()){ btnHour10_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour11_.isEnabled()){ btnHour11_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                editTextTime.setText(btnHour8_.getText().toString());
            }
        });

        btnHour9_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnHour8.isEnabled()){ btnHour8.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour9.isEnabled()){ btnHour9.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour10.isEnabled()){ btnHour10.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour11.isEnabled()){ btnHour11.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour8_.isEnabled()){ btnHour8_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                btnHour9_.setBackgroundColor(getResources().getColor(R.color.yellow));
                if(btnHour10_.isEnabled()){ btnHour10_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour11_.isEnabled()){ btnHour11_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                editTextTime.setText(btnHour9_.getText().toString());
            }
        });

        btnHour10_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnHour8.isEnabled()){ btnHour8.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour9.isEnabled()){ btnHour9.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour10.isEnabled()){ btnHour10.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour11.isEnabled()){ btnHour11.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour8_.isEnabled()){ btnHour8_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour9_.isEnabled()){ btnHour9_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                btnHour10_.setBackgroundColor(getResources().getColor(R.color.yellow));
                if(btnHour11_.isEnabled()){ btnHour11_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                editTextTime.setText(btnHour10_.getText().toString());
            }
        });

        btnHour11_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnHour8.isEnabled()){ btnHour8.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour9.isEnabled()){ btnHour9.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour10.isEnabled()){ btnHour10.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour11.isEnabled()){ btnHour11.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour8_.isEnabled()){ btnHour8_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour9_.isEnabled()){ btnHour9_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                if(btnHour10_.isEnabled()){ btnHour10_.setBackgroundColor(getResources().getColor(R.color.myColor));}
                btnHour11_.setBackgroundColor(getResources().getColor(R.color.yellow));
                editTextTime.setText(btnHour11_.getText().toString());
            }
        });



        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String  city, hospital, specialization, date, time, dateAndTime;
                city = spinnerCity.getSelectedItem().toString();
                hospital = spinnerHospital.getSelectedItem().toString();
                specialization = spinnerSpecialization.getSelectedItem().toString();
                date = editTextDate.getText().toString();
                time = editTextTime.getText().toString();
                dateAndTime = date + " " + time;

                if(date.isEmpty() || time.isEmpty()){
                    Toast.makeText(ConsultationActivity.this, "Please choose a date and time!", Toast.LENGTH_LONG).show();
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Occupied_Dates").child(spinnerHospital.getSelectedItem().toString()).child(spinnerSpecialization.getSelectedItem().toString()).child(editTextDate.getText().toString() + " " + editTextTime.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                Toast.makeText(ConsultationActivity.this, "Reservation already exists!", Toast.LENGTH_LONG).show();
                            } else {
                                addConsultation(city, hospital, specialization, dateAndTime);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });


    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

        if( parent.getItemAtPosition(position).equals("Choose City")){
            spinnerHospital.setAdapter(null);
            spinnerSpecialization.setAdapter(null);
            editTextDate.setVisibility(View.INVISIBLE);
            editTextTime.setVisibility(View.INVISIBLE);
            gridLayoutHours.setVisibility(View.INVISIBLE);
            btnRequest.setVisibility(View.INVISIBLE);

        }else if (parent.getItemAtPosition(position).equals("Bucuresti")) {
            spinnerHospital.setAdapter(adapterHospitalsBucharest);
            spinnerHospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

                    if(parent.getItemAtPosition(position).equals("Choose Hospital")){
                        spinnerSpecialization.setAdapter(null);
                        editTextDate.setVisibility(View.INVISIBLE);
                        editTextTime.setVisibility(View.INVISIBLE);
                        gridLayoutHours.setVisibility(View.INVISIBLE);
                        btnRequest.setVisibility(View.INVISIBLE);
                    }else {
                        spinnerSpecialization.setAdapter(adapterSpecialization);
                        spinnerSpecialization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

                                editTextDate.getText().clear();
                                if(parent.getItemAtPosition(position).equals("Choose Specialization")){
                                    editTextDate.setVisibility(View.INVISIBLE);
                                    editTextTime.setVisibility(View.INVISIBLE);
                                    gridLayoutHours.setVisibility(View.INVISIBLE);
                                    btnRequest.setVisibility(View.INVISIBLE);
                                }else {
                                    editTextDate.setVisibility(View.VISIBLE);
                                    editTextTime.setVisibility(View.VISIBLE);
                                    gridLayoutHours.setVisibility(View.VISIBLE);
                                    btnRequest.setVisibility(View.VISIBLE);
                                    checkAvailability(editTextDate);

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


        } else if (parent.getItemAtPosition(position).equals("Cluj")){
            spinnerHospital.setAdapter(adapterHospitalsCluj);
            spinnerHospital.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

                    if(parent.getItemAtPosition(position).equals("Choose Hospital")){
                        spinnerSpecialization.setAdapter(null);
                        editTextDate.setVisibility(View.INVISIBLE);
                        editTextTime.setVisibility(View.INVISIBLE);
                        gridLayoutHours.setVisibility(View.INVISIBLE);
                        btnRequest.setVisibility(View.INVISIBLE);
                    }else {
                        spinnerSpecialization.setAdapter(adapterSpecialization);
                        spinnerSpecialization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
                                editTextDate.getText().clear();
                                if(parent.getItemAtPosition(position).equals("Choose Specialization")){
                                    editTextDate.setVisibility(View.INVISIBLE);
                                    editTextTime.setVisibility(View.INVISIBLE);
                                    gridLayoutHours.setVisibility(View.INVISIBLE);
                                    btnRequest.setVisibility(View.INVISIBLE);
                                }else {
                                    editTextDate.setVisibility(View.VISIBLE);
                                    editTextTime.setVisibility(View.VISIBLE);
                                    gridLayoutHours.setVisibility(View.VISIBLE);
                                    btnRequest.setVisibility(View.VISIBLE);
                                    checkAvailability(editTextDate);

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
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void showDateTimeDialog(EditText editTextDate){
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
                editTextDate.setText((simpleDateFormat.format(calendar.getTime())));
            }
        };
        new DatePickerDialog(ConsultationActivity.this,dateSetListener, calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    public void addConsultation( String city, String hospital, String specialization, String dateAndTime){
        Consultation consultation = new Consultation(city, hospital, specialization, dateAndTime);
        FirebaseDatabase.getInstance().getReference("Occupied_Dates").child(hospital).child(specialization).child(dateAndTime).setValue("True");
        FirebaseDatabase.getInstance().getReference("Consultations")
                .child(uid)
                .child((dateAndTime))
                .setValue(consultation).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ConsultationActivity.this, "Consultation has been created!", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ConsultationActivity.this, "Consultation has not been created!", Toast.LENGTH_LONG).show();
                }
            }});

    }

    public void checkAvailability(EditText editTextDate){
        editTextDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                FirebaseDatabase.getInstance().getReference().child("Occupied_Dates").child(spinnerHospital.getSelectedItem().toString()).child(spinnerSpecialization.getSelectedItem().toString()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(editTextDate.getText().toString() + " " + btnHour8.getText().toString()).exists()){
                            btnHour8.setBackgroundColor(getResources().getColor(R.color.gray));
                            btnHour8.setEnabled(false);
                        }else {
                            btnHour8.setBackgroundColor(getResources().getColor(R.color.myColor));
                            btnHour8.setEnabled(true);
                        }

                        if(snapshot.child(editTextDate.getText().toString() + " " + btnHour9.getText().toString()).exists()){
                            btnHour9.setBackgroundColor(getResources().getColor(R.color.gray));
                            btnHour9.setEnabled(false);
                        }else {
                            btnHour9.setBackgroundColor(getResources().getColor(R.color.myColor));
                            btnHour9.setEnabled(true);
                        }

                        if(snapshot.child(editTextDate.getText().toString() + " " + btnHour10.getText().toString()).exists()){
                            btnHour10.setBackgroundColor(getResources().getColor(R.color.gray));
                            btnHour10.setEnabled(false);
                        }else {
                            btnHour10.setBackgroundColor(getResources().getColor(R.color.myColor));
                            btnHour10.setEnabled(true);
                        }

                        if(snapshot.child(editTextDate.getText().toString() + " " + btnHour11.getText().toString()).exists()){
                            btnHour11.setBackgroundColor(getResources().getColor(R.color.gray));
                            btnHour11.setEnabled(false);
                        }else {
                            btnHour11.setBackgroundColor(getResources().getColor(R.color.myColor));
                            btnHour11.setEnabled(true);
                        }

                        if(snapshot.child(editTextDate.getText().toString() + " " + btnHour8_.getText().toString()).exists()){
                            btnHour8_.setBackgroundColor(getResources().getColor(R.color.gray));
                            btnHour8_.setEnabled(false);
                        }else {
                            btnHour8_.setBackgroundColor(getResources().getColor(R.color.myColor));
                            btnHour8_.setEnabled(true);
                        }

                        if(snapshot.child(editTextDate.getText().toString() + " " + btnHour9_.getText().toString()).exists()){
                            btnHour9_.setBackgroundColor(getResources().getColor(R.color.gray));
                            btnHour9_.setEnabled(false);
                        }else {
                            btnHour9_.setBackgroundColor(getResources().getColor(R.color.myColor));
                            btnHour9_.setEnabled(true);
                        }

                        if(snapshot.child(editTextDate.getText().toString() + " " + btnHour10_.getText().toString()).exists()){
                            btnHour10_.setBackgroundColor(getResources().getColor(R.color.gray));
                            btnHour10_.setEnabled(false);
                        }else {
                            btnHour10_.setBackgroundColor(getResources().getColor(R.color.myColor));
                            btnHour10_.setEnabled(true);
                        }

                        if(snapshot.child(editTextDate.getText().toString() + " " + btnHour11_.getText().toString()).exists()){
                            btnHour11_.setBackgroundColor(getResources().getColor(R.color.gray));
                            btnHour11_.setEnabled(false);
                        }else {
                            btnHour11_.setBackgroundColor(getResources().getColor(R.color.myColor));
                            btnHour11_.setEnabled(true);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });
            }
        });
    }



}