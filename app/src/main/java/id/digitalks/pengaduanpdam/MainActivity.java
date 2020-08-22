package id.digitalks.pengaduanpdam;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Button btnLapor, btnRiwayat, btnMeter, btnTentang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLapor = (Button) findViewById(R.id.buttonLapor);
        btnRiwayat = (Button) findViewById(R.id.buttonRiwayat);
        btnMeter = (Button) findViewById(R.id.buttonMeter);
        btnTentang = (Button) findViewById(R.id.buttonTentang);

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
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
    }
}