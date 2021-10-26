package id.digitalks.pengaduanpdam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Filter extends AppCompatActivity {
    EditText inpNosambung;
    Button btnCekriwayat, btnCekmeter;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        sharedpreferences = getSharedPreferences(LoginActivity.my_shared_preferences, Context.MODE_PRIVATE);
        Intent data = getIntent();
        final int cek = data.getIntExtra("filter", 0);

        if (cek == 0) {
            getSupportActionBar().setTitle("Cek Riwayat Pengaduan");
        } else {
            getSupportActionBar().setTitle("Cek Meter Pelanggan");
        }


        inpNosambung = (EditText) findViewById(R.id.inp_nosambung);
        btnCekriwayat = (Button) findViewById(R.id.btn_cekriwayat);
        btnCekmeter = (Button) findViewById(R.id.btn_cekmeter);
        inpNosambung.setEnabled(false);
        inpNosambung.setText(sharedpreferences.getString("username","null"));
        if (cek == 0) {
            btnCekriwayat.setVisibility(View.VISIBLE);
            btnCekmeter.setVisibility(View.GONE);
        } else {
            btnCekriwayat.setVisibility(View.GONE);
            btnCekmeter.setVisibility(View.VISIBLE);
        }

        btnCekriwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Filter.this, inpNosambung.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent riwayat = new Intent(Filter.this, CekRiwayat.class);
                riwayat.putExtra("no_sambung", inpNosambung.getText().toString());
                startActivity(riwayat);
            }
        });

        btnCekmeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent meter = new Intent(Filter.this, CekMeter.class);
                meter.putExtra("no_sambung", inpNosambung.getText().toString());
                startActivity(meter);
            }
        });
    }
}