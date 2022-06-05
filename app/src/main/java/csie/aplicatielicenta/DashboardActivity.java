package csie.aplicatielicenta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.eazegraph.lib.charts.PieChart;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

public class DashboardActivity extends AppCompatActivity {

    private String version;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String appUrl;
    private TextView twTotalCasesTitle, twTotalCasesNumber, twTotalCasesNumberNew,
            twTotalTestsTitle, twTotalTestsNumber, twTotalTestsNumberNew,
            twTotalDeathsTitle, twTotalDeathsNumber, twTotalDeathsNumberNew,
            twIntensiveCareTitle, twIntensiveCareNumber, twIntensiveCareNumberNew,
            twInfectedHospitalizedTitle, twInfectedHospitalizedNumber, twInfectedHospitalizedNumberNew;


    private SwipeRefreshLayout swipeRefreshLayout;

    private PieChart pieChart;

    private LinearLayout lin_state_data, lin_world_data;

    private String strTotalCasesNumber, strTotalCasesNumberNew,
            strTotalTestsTitle, strTotalTestsNumber, strTotalTestsNumberNew,
            strTotalDeathsTitle, strTotalDeathsNumber, strTotalDeathsNumberNew,
            strIntensiveCareTitle, strIntensiveCareNumber, strIntensiveCareNumberNew,
            strInfectedHospitalizedTitle, strInfectedHospitalizedNumber, strInfectedHospitalizedNumberNew;
    private int int_active_new;
    private ProgressDialog progressDialog;
    private boolean doubleBackToExitPressedOnce = false;
    private Toast backPressToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Init();
        FetchData();

    }


    private void Init() {
        twTotalCasesNumber = findViewById(R.id.twTotalCasesNumber);
        twTotalCasesNumberNew = findViewById(R.id.twTotalCasesNumberNew);
        twTotalTestsNumber = findViewById(R.id.twTotalTestsNumber);
        twTotalTestsNumberNew = findViewById(R.id.twTotalTestsNumberNew);
        twTotalDeathsNumber = findViewById(R.id.twTotalDeathsNumber);
        twTotalDeathsNumberNew = findViewById(R.id.twTotalDeathsNumberNew);

        twIntensiveCareNumber = findViewById(R.id.twIntensiveCareNumber);
        twInfectedHospitalizedNumber = findViewById(R.id.twInfectedHospitalizedNumber);

        pieChart = findViewById(R.id.activity_main_piechart);
        swipeRefreshLayout = findViewById(R.id.activity_main_swipe_refresh_layout);
    }



    private void FetchData() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiUrl = "https://www.graphs.ro/json.php";

        pieChart.clearChart();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray covidRomania = null;
                        JSONArray total_cases = null;
                        JSONArray new_cases_today = null;

                        try {
                            covidRomania = response.getJSONArray("covid_romania");
                            JSONObject dataRomania = covidRomania.getJSONObject(0);

                            strTotalCasesNumber = dataRomania.getString("total_cases");
                            strTotalCasesNumberNew = dataRomania.getString("new_cases_today");
                            strTotalTestsNumber = dataRomania.getString("total_tests");
                            strTotalTestsNumberNew = dataRomania.getString("new_tests_today");
                            strTotalDeathsNumber = dataRomania.getString("total_deaths");
                            strTotalDeathsNumberNew = dataRomania.getString("new_deaths_today");
                            strIntensiveCareNumber = dataRomania.getString("intensive_care_right_now");
                            strInfectedHospitalizedNumber = dataRomania.getString("infected_hospitalized");



                            Handler delayProgress = new Handler();

                            delayProgress.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    twTotalCasesNumber.setText(NumberFormat.getInstance().format(Integer.parseInt(strTotalCasesNumber)));
                                    twTotalCasesNumberNew.setText("Today: +" + NumberFormat.getInstance().format(Integer.parseInt(strTotalCasesNumberNew)));
                                    twTotalTestsNumber.setText(NumberFormat.getInstance().format(Integer.parseInt(strTotalTestsNumber)));
                                    twTotalTestsNumberNew.setText("Today: +" + NumberFormat.getInstance().format(Integer.parseInt(strTotalTestsNumberNew)));
                                    twTotalDeathsNumber.setText(NumberFormat.getInstance().format(Integer.parseInt(strTotalDeathsNumber)));
                                    twTotalDeathsNumberNew.setText("Today: +" + NumberFormat.getInstance().format(Integer.parseInt(strTotalDeathsNumberNew)));
                                    twIntensiveCareNumber.setText(NumberFormat.getInstance().format(Integer.parseInt(strIntensiveCareNumber)));
                                    twInfectedHospitalizedNumber.setText(NumberFormat.getInstance().format(Integer.parseInt(strInfectedHospitalizedNumber)));
                                }
                            },  1000);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);




    }





}