package id.digitalks.pengaduanpdam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import id.digitalks.pengaduanpdam.Util.AppController;
import id.digitalks.pengaduanpdam.Util.ServerAPI;


public class LaporPengaduan extends AppCompatActivity {
    EditText nama, noSambung, alamat, pengaduan;
    Spinner spinnerPengaduan;
    Button btnLapor, btnBatal;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapor_pengaduan);

        nama = (EditText) findViewById(R.id.edt_nama);
        noSambung = (EditText) findViewById(R.id.edt_no_sambung);
        alamat = (EditText) findViewById(R.id.edt_alamat);
        pengaduan = (EditText) findViewById(R.id.edt_pengaduan);
        btnLapor = (Button) findViewById(R.id.btn_lapor);
        btnBatal = (Button) findViewById(R.id.btn_batal);
        pd = new ProgressDialog(LaporPengaduan.this);

        // select/spinner
        spinnerPengaduan = (Spinner) findViewById(R.id.spinner_pengaduan);
        String[] items = new String[]{"--Pilih Pengaduan--", "Bocor Pipa Distribusi", "Bocor Pipa Dinas", "Bocor Instalasi Meter",
                "Meter Pecah, Buram, Dll.", "Kesalahan Angka Meter", "Kesalahan Input Rekening", "Tekanan Air Lemah", "Tidak Ada Air",
                "Pemakaian Tinggi"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinnerPengaduan.setAdapter(adapter);

        spinnerPengaduan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //   get value by ID
                Object valueID = parent.getSelectedItemId();
                //Log.v("item", String.valueOf(valueID));
                pengaduan.setText(String.valueOf(valueID));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });

        btnLapor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pengaduan.getText().toString().equals("0")) {
                    Toast.makeText(LaporPengaduan.this, "Pengaduan Belum Dipilih", Toast.LENGTH_SHORT).show();
                } else {
                    simpanData();
                    Intent detail = new Intent(LaporPengaduan.this, DetailLaporan.class);
                    detail.putExtra("no_sambung", noSambung.getText().toString());
                    detail.putExtra("cek", 0);
                    startActivity(detail);
                }

            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(LaporPengaduan.this, MainActivity.class);
                main.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(main);
            }
        });

    }

    private void simpanData() {
        pd.setMessage("Menyimpan Data");
        pd.setCancelable(false);
        pd.show();

        StringRequest sendData = new StringRequest(Request.Method.POST, ServerAPI.URL_INSERT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Toast.makeText(LaporPengaduan.this, "pesan : "+ res.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        //startActivity(new Intent(InsertData.this, MainActivity.class));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pd.cancel();
                        Toast.makeText(LaporPengaduan.this, "pesan : Gagal Insert Data", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("nama", nama.getText().toString());
                map.put("no_sambung", noSambung.getText().toString());
                map.put("alamat", alamat.getText().toString());
                map.put("pengaduan", pengaduan.getText().toString());

                return map;
            }
        };

        AppController.getInstance().addToRequestQueue(sendData);
    }
}