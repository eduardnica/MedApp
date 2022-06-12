package csie.aplicatielicenta;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import csie.aplicatielicenta.Adapters.CountyAdaptor;
import csie.aplicatielicenta.Models.County;

public class CountyDataActivity extends AppCompatActivity {

    private RecyclerView countyRecyclerView;
    private CountyAdaptor countyAdapter;
    private ArrayList<County> countyArrayList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private EditText countySearch;

    private String county_id, county_name, county_population, total_cases;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_county);

        Init();
        FetchCountyData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                FetchCountyData();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //Search
        countySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Filter(s.toString());
            }
        });

    }

    private void Init() {
        swipeRefreshLayout = findViewById(R.id.activityCountySwipeRefreshLayout);
        countySearch = findViewById(R.id.countySearch);

        countyRecyclerView = findViewById(R.id.countyRecyclerView);
        countyRecyclerView.setHasFixedSize(true);
        countyRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        countyArrayList = new ArrayList<>();
        countyAdapter = new CountyAdaptor(countyArrayList,CountyDataActivity.this);
        countyRecyclerView.setAdapter(countyAdapter);
    }

    private void FetchCountyData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String apiURL = "https://www.graphs.ro/json.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                apiURL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray covidRomania = response.getJSONArray("covid_romania");
                            JSONObject covidRomaniaToday = covidRomania.getJSONObject(0);
                            JSONArray allCountyData = covidRomaniaToday.getJSONArray("county_data");
                            countyArrayList.clear();

                            for (int i = 1; i < allCountyData.length() ; i++){
                                JSONObject countyData = allCountyData.getJSONObject(i);

                                //After fetching, storing the data into strings
                                county_id = countyData.getString("county_id");
                                county_name = countyData.getString("county_name");
                                total_cases = countyData.getString("total_cases");

                                County county = new County(county_id, county_name, total_cases);
                                //adding data to our arraylist
                                countyArrayList.add(county);
                            }

                            Handler makeDelay = new Handler();
                            makeDelay.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    countyAdapter.notifyDataSetChanged();
                                }
                            }, 1000);

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        requestQueue.add(jsonObjectRequest);
    }

    private void Filter(String text) {
        ArrayList<County> filteredList = new ArrayList<>();
        for (County item : countyArrayList) {
            if (item.getCountyName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        countyAdapter.filterList(filteredList);
    }

}