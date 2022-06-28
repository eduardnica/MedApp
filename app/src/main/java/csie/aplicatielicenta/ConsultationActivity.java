package csie.aplicatielicenta;

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

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ConsultationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spinnerCity, spinnerHospital, spinnerSpecialization;
    ArrayAdapter<CharSequence> adapterCity, adapterHospitalsBucharest,adapterHospitalsCluj, adapterSpecialization;
    EditText editTextDateTime;
    MaterialButton btnRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultation);

        spinnerCity = findViewById(R.id.spinnerCity);
        spinnerHospital = findViewById(R.id.spinnerHospital);
        spinnerSpecialization = findViewById(R.id.spinnerSpecialization);
        editTextDateTime = findViewById(R.id.editTextDateTime);
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

        editTextDateTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimeDialog(editTextDateTime);
            }
        });
        editTextDateTime.setVisibility(View.INVISIBLE);





    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {

        if( parent.getItemAtPosition(position).equals("Choose City")){
            spinnerHospital.setAdapter(null);
            spinnerSpecialization.setAdapter(null);
            editTextDateTime.setVisibility(View.INVISIBLE);

        }else if (parent.getItemAtPosition(position).equals("Bucuresti")) {
            spinnerHospital.setAdapter(adapterHospitalsBucharest);
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


}