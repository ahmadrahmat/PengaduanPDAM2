package id.digitalks.pengaduanpdam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import id.digitalks.pengaduanpdam.Adapter.AdapterDataMeter;
import id.digitalks.pengaduanpdam.Model.ModelData;
import id.digitalks.pengaduanpdam.Util.AppController;
import id.digitalks.pengaduanpdam.Util.ServerAPI;

public class CekMeter extends AppCompatActivity {
    RecyclerView mRecyclerview;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    List<ModelData> mItems;
    ProgressDialog pd;
    String intent_nosambung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cek_meter);

        // get data from intent
        Intent data = getIntent();
        intent_nosambung = data.getStringExtra("no_sambung");
//         intent_nosambung = "7216";
        // end get

        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerviewTempMeter);
        pd = new ProgressDialog(CekMeter.this);
        mItems = new ArrayList<>();

        loadjson(intent_nosambung);

        mManager = new LinearLayoutManager(CekMeter.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter = new AdapterDataMeter(CekMeter.this, mItems);
        mRecyclerview.setAdapter(mAdapter);
    }

    private void loadjson(String no_sambung) {
        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        pd.show();

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST, ServerAPI.URL_METERBYNOSAMBUNG+"?no_sambung="+no_sambung, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pd.cancel();
                        Log.d("volley", "response : " + response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ModelData md = new ModelData();
                                md.setId(data.getString("id"));
                                md.setTanggal_meter(data.getString("tanggal_meter"));
                                md.setMeter(data.getString("meter"));
                                md.setNo_sambung(data.getString("no_sambung"));
                                mItems.add(md);
                                //Toast.makeText(DetailLaporan.this, data.toString(), Toast.LENGTH_SHORT).show();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Log.d("volley", "error : " + error.getMessage());
                    }
                });

        AppController.getInstance().addToRequestQueue(reqData);
    }
}