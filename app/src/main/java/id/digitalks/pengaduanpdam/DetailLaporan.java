package id.digitalks.pengaduanpdam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import id.digitalks.pengaduanpdam.Adapter.AdapterDataDetail;
import id.digitalks.pengaduanpdam.Model.ModelData;
import id.digitalks.pengaduanpdam.Util.AppController;
import id.digitalks.pengaduanpdam.Util.ServerAPI;

public class DetailLaporan extends AppCompatActivity {
    RecyclerView mRecyclerview;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mManager;
    List<ModelData> mItems;
    ProgressDialog pd;
    String intent_nosambung, intent_idpengaduan;
    Button btnKembali;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_laporan);

        // get data from intent
        Intent data = getIntent();
        final int cek = data.getIntExtra("cek", 0);
        if (cek == 0) {
            intent_nosambung = data.getStringExtra("no_sambung");
        } else {
            intent_idpengaduan = data.getStringExtra("id_pengaduan");
        }
        btnKembali = (Button) findViewById(R.id.btn_kembali);
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerviewTempDetail);
        pd = new ProgressDialog(DetailLaporan.this);
        mItems = new ArrayList<>();

        if (cek == 0) {
            loadjson(intent_nosambung);
        } else {
            loadjsonbyidpengaduan(intent_idpengaduan);
        }

        mManager = new LinearLayoutManager(DetailLaporan.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerview.setLayoutManager(mManager);
        mAdapter = new AdapterDataDetail(DetailLaporan.this, mItems);
        mRecyclerview.setAdapter(mAdapter);

        if (cek == 0) {
            btnKembali.setText("Kembali Ke Menu");
        } else {
            btnKembali.setText("Kembali");
        }

        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cek == 0) {
                    Intent mainAc = new Intent(DetailLaporan.this, MainActivity.class);
                    mainAc.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(mainAc);
                } else {
                    onBackPressed();
                }
            }
        });

    }

    private void loadjson(String no_sambung) {
        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        pd.show();

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST, ServerAPI.URL_DATABYID+"?no_sambung="+no_sambung, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pd.cancel();
                        Log.d("volley", "response : " + response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ModelData md = new ModelData();
                                md.setId_pengaduan(data.getString("id_pengaduan"));
                                md.setStatus(data.getString("status"));
                                md.setTanggal_pengaduan(data.getString("tanggal_pengaduan"));
                                md.setPengaduan(data.getString("pengaduan"));
                                md.setPenyelesaian_pengaduan(data.getString("penyelesaian_pengaduan"));
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

    private void loadjsonbyidpengaduan(String id_pengaduan) {
        pd.setMessage("Mengambil Data");
        pd.setCancelable(false);
        pd.show();

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST, ServerAPI.URL_DATABYIDPENGADUAN+"?id_pengaduan="+id_pengaduan, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pd.cancel();
                        Log.d("volley", "response : " + response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ModelData md = new ModelData();
                                md.setId_pengaduan(data.getString("id_pengaduan"));
                                md.setStatus(data.getString("status"));
                                md.setTanggal_pengaduan(data.getString("tanggal_pengaduan"));
                                md.setPengaduan(data.getString("pengaduan"));
                                md.setPenyelesaian_pengaduan(data.getString("penyelesaian_pengaduan"));
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