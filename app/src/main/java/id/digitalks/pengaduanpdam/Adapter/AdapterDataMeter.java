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

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.digitalks.pengaduanpdam.DetailLaporan;
import id.digitalks.pengaduanpdam.Model.ModelData;
import id.digitalks.pengaduanpdam.R;

public class AdapterDataMeter extends RecyclerView.Adapter<AdapterDataMeter.HolderData> {
    private List<ModelData> mItems;
    private Context context;

    public AdapterDataMeter(Context context, List<ModelData> items) {
        this.mItems = items;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row_meter, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelData md = mItems.get(position);

        String no_meter = md.getMeter();
        Integer meter = Integer.parseInt(no_meter);

        String str = NumberFormat.getNumberInstance(Locale.US).format(meter);

        holder.tvMeter.setText(str);

        //holder.tvMeter.setText(md.getMeter());

        holder.tvTanggalMeter.setText(md.getBulan_meter() +" "+md.getTahun_meter());

        holder.tvNoSambung.setText(md.getNo_sambung());

        holder.md = md;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class HolderData extends RecyclerView.ViewHolder {
        TextView tvMeter, tvTanggalMeter, tvNoSambung;
        ModelData md;

        public HolderData(View view) {
            super(view);

            tvMeter = (TextView) view.findViewById(R.id.tv_meter);
            tvTanggalMeter = (TextView) view.findViewById(R.id.tv_tanggal_meter);
            tvNoSambung = (TextView) view.findViewById(R.id.tv_no_sambung);

//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent update = new Intent(context, DetailLaporan.class);
//                    update.putExtra("cek", 1);
//                    update.putExtra("id_pengaduan", md.getId_pengaduan());
//
//                    context.startActivity(update);
//                }
//            });
        }
    }
}
