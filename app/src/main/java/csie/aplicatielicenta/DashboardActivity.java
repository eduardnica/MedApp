package csie.aplicatielicenta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;

public class DashboardActivity extends AppCompatActivity {

    private TextView  twTotalCasesNumber, twTotalCasesNumberNew,
            twTotalTestsNumber, twTotalTestsNumberNew,
            twTotalDeathsNumber, twTotalDeathsNumberNew,
            twIntensiveCareNumber,
            twInfectedHospitalizedNumber;

    private SwipeRefreshLayout swipeRefreshLayout;
    private PieChart pieChart;
    private LinearLayout linearLayoutStateData;
    private String strTotalCasesNumber, strTotalCasesNumberNew,
            strTotalTestsNumber, strTotalTestsNumberNew,
            strTotalDeathsNumber, strTotalDeathsNumberNew,
            strIntensiveCareNumber,
            strInfectedHospitalizedNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Init();
        FetchData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        linearLayoutStateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent countyActivityIntent = new Intent(DashboardActivity.this, CountyDataActivity.class);
                startActivity(countyActivityIntent);
            }
        });

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
        pieChart = findViewById(R.id.activityDashboardPiechart);
        swipeRefreshLayout = findViewById(R.id.activity_dashboard_swipe_refresh_layout);
        linearLayoutStateData = findViewById(R.id.linearLayoutCountyData);
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

                                    pieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(strTotalCasesNumberNew), Color.parseColor("#ffa600")));
                                    pieChart.addPieSlice(new PieModel("Hospitalized", Integer.parseInt(strInfectedHospitalizedNumber), Color.parseColor("#7a5195")));
                                    pieChart.addPieSlice(new PieModel("Intensive Care", Integer.parseInt(strIntensiveCareNumber), Color.parseColor("#ef5675")));
                                    pieChart.addPieSlice(new PieModel("Deceased", Integer.parseInt(strTotalDeathsNumberNew), Color.parseColor("#003f5c")));
                                    pieChart.startAnimation();
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