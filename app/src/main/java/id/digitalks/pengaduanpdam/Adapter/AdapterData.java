package id.digitalks.pengaduanpdam.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import id.digitalks.pengaduanpdam.DetailLaporan;
import id.digitalks.pengaduanpdam.LaporPengaduan;
import id.digitalks.pengaduanpdam.Model.ModelData;
import id.digitalks.pengaduanpdam.R;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {
    private List<ModelData> mItems;
    private Context context;

    public AdapterData(Context context, List<ModelData> items) {
        this.mItems = items;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelData md = mItems.get(position);
        holder.tvIdPengaduan.setText(md.getId_pengaduan());

        if (md.getStatus().equals("0")) {
            holder.tvStatusPengaduan.setText("Sedang Diproses");
            holder.tvStatusPengaduan.setTextColor(Color.parseColor("#FF9800"));
        } else {
            holder.tvStatusPengaduan.setText("Selesai");
            holder.tvStatusPengaduan.setTextColor(Color.parseColor("#4CAF50"));
        }

        String strCurrentDate= md.getTanggal_pengaduan();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
            format = new SimpleDateFormat("dd MMM yyyy");
            String date = format.format(newDate);
            holder.tvTglLapor.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (md.getPengaduan().equals("1")) {
            holder.tvKategori.setText("Bocor Pipa Distribusi");
        } else if (md.getPengaduan().equals("2")) {
            holder.tvKategori.setText("Bocor Pipa Dinas");
        } else if (md.getPengaduan().equals("3")) {
            holder.tvKategori.setText("Bocor Instalasi Meter");
        } else if (md.getPengaduan().equals("4")) {
            holder.tvKategori.setText("Meter Pecah, Buram, Dll.");
        } else if (md.getPengaduan().equals("5")) {
            holder.tvKategori.setText("Kesalahan Angka Meter");
        } else if (md.getPengaduan().equals("6")) {
            holder.tvKategori.setText("Kesalahan Input Rekening");
        } else if (md.getPengaduan().equals("7")) {
            holder.tvKategori.setText("Tekanan Air Lemah");
        } else if (md.getPengaduan().equals("8")) {
            holder.tvKategori.setText("Tidak Ada Air");
        } else if (md.getPengaduan().equals("9")) {
            holder.tvKategori.setText("Pemakaian Tinggi");
        }

        holder.md = md;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class HolderData extends RecyclerView.ViewHolder {
        TextView tvIdPengaduan, tvStatusPengaduan, tvTglLapor, tvKategori;
        ModelData md;

        public HolderData(View view) {
            super(view);

            tvIdPengaduan = (TextView) view.findViewById(R.id.tv_idpengaduan);
            tvStatusPengaduan = (TextView) view.findViewById(R.id.tv_statuspengaduan);
            tvTglLapor = (TextView) view.findViewById(R.id.tv_tgllapor);
            tvKategori = (TextView) view.findViewById(R.id.tv_kategori);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(context, DetailLaporan.class);
                    update.putExtra("cek", 1);
                    update.putExtra("id_pengaduan", md.getId_pengaduan());

                    context.startActivity(update);
                }
            });
        }
    }
}
