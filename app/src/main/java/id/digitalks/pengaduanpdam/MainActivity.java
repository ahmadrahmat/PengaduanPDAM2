package id.digitalks.pengaduanpdam;

import static id.digitalks.pengaduanpdam.LoginActivity.TAG_ID;
import static id.digitalks.pengaduanpdam.LoginActivity.TAG_USERNAME;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import id.digitalks.pengaduanpdam.Model.ModelData;
import id.digitalks.pengaduanpdam.Util.AppController;
import id.digitalks.pengaduanpdam.Util.ServerAPI;

public class MainActivity extends AppCompatActivity {

    Button btnLapor, btnRiwayat, btnMeter, btnTentang;
    ProgressDialog pd;
    TextView tv_infodetail, tv_tgl, tv_info;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);

        btnLapor = (Button) findViewById(R.id.buttonLapor);
        btnRiwayat = (Button) findViewById(R.id.buttonRiwayat);
        btnMeter = (Button) findViewById(R.id.buttonMeter);
        btnTentang = (Button) findViewById(R.id.buttonTentang);
        tv_info = (TextView) findViewById(R.id.tvInfo);
        tv_infodetail = (TextView) findViewById(R.id.tvInfoDetail);
        tv_tgl = (TextView) findViewById(R.id.tvInfoTgl);


        pd = new ProgressDialog(MainActivity.this);

        btnLapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LaporPengaduan.class);
                startActivity(intent);
            }
        });

        btnRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Filter.class);
                intent.putExtra("filter", 0);
                startActivity(intent);
            }
        });

        btnMeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Filter.class);
                intent.putExtra("filter", 1);
                startActivity(intent);
            }
        });

        btnTentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(MainActivity.this);

                //Memasang Title / Judul pada Custom Dialog
                dialog.setTitle("Tentang Aplikasi");

                //Memasang Desain Layout untuk Custom Dialog
                dialog.setContentView(R.layout.layout_about);

                //Memasang Listener / Aksi saat tombol OK di Klik
                Button DialogButton = dialog.findViewById(R.id.ok);
                DialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putBoolean(LoginActivity.session_status, false);
                        editor.putString(TAG_ID, null);
                        editor.putString(TAG_USERNAME, null);
                        editor.commit();

                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        finish();
                        startActivity(intent);

                    }
                });

                dialog.show();
            }
        });

        loadinfo();

    }

    private void loadinfo() {
//        pd.setMessage("Mengambil Data");
//        pd.setCancelable(false);
//        pd.show();

        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.POST, ServerAPI.URL_INFORMASI, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
//                        pd.cancel();
                        Log.d("volley", "response : " + response.toString());
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                //ModelData md = new ModelData();
                                //md.setId_pengaduan(data.getString("id_pengaduan"));
                                //md.setStatus(data.getString("status"));
                                //md.setTanggal_pengaduan(data.getString("tanggal_pengaduan"));
                                //md.setPengaduan(data.getString("pengaduan"));
                                //md.setPenyelesaian_pengaduan(data.getString("penyelesaian_pengaduan"));
                                //mItems.add(md);
                                //Toast.makeText(DetailLaporan.this, data.toString(), Toast.LENGTH_SHORT).show();
                                tv_info.setText(data.getString("judul_pengumuman"));
                                tv_infodetail.setText(data.getString("isi_pengumuman"));
                                tv_tgl.setText("("+data.getString("tgl_pengumuman")+")");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        //mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        pd.cancel();
                        Log.d("volley", "error : " + error.getMessage());
                    }
                });

        AppController.getInstance().addToRequestQueue(reqData);
    }
}