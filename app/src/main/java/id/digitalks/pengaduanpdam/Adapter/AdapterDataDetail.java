package id.digitalks.pengaduanpdam.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import id.digitalks.pengaduanpdam.LaporPengaduan;
import id.digitalks.pengaduanpdam.Model.ModelData;
import id.digitalks.pengaduanpdam.R;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.WHITE;

public class AdapterDataDetail extends RecyclerView.Adapter<AdapterDataDetail.HolderData> {
    private List<ModelData> mItems;
    private Context context;

    public AdapterDataDetail(Context context, List<ModelData> items) {
        this.mItems = items;
        this.context = context;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_row_detail, parent, false);
        HolderData holderData = new HolderData(layout);
        return holderData;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        ModelData md = mItems.get(position);
        holder.tvIdPengaduan.setText(md.getId_pengaduan());

        if (md.getStatus().equals("0")) {
            holder.tvStatus.setText("SEDANG DIPROSES");
            holder.tvStatus.setTextColor(Color.parseColor("#FF9800"));
        } else {
            holder.tvStatus.setText("SELESAI");
            holder.tvStatus.setTextColor(Color.parseColor("#4CAF50"));
        }

        String strCurrentDate= md.getTanggal_pengaduan();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = null;
        try {
            newDate = format.parse(strCurrentDate);
            format = new SimpleDateFormat("dd MMM yyyy");
            String date = format.format(newDate);
            holder.tvCreatedat.setText(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (md.getPengaduan().equals("1")) {
            holder.tvIsiPengaduan.setText("Bocor Pipa Distribusi");
        } else if (md.getPengaduan().equals("2")) {
            holder.tvIsiPengaduan.setText("Bocor Pipa Dinas");
        } else if (md.getPengaduan().equals("3")) {
            holder.tvIsiPengaduan.setText("Bocor Instalasi Meter");
        } else if (md.getPengaduan().equals("4")) {
            holder.tvIsiPengaduan.setText("Meter Pecah, Buram, Dll.");
        } else if (md.getPengaduan().equals("5")) {
            holder.tvIsiPengaduan.setText("Kesalahan Angka Meter");
        } else if (md.getPengaduan().equals("6")) {
            holder.tvIsiPengaduan.setText("Kesalahan Input Rekening");
        } else if (md.getPengaduan().equals("7")) {
            holder.tvIsiPengaduan.setText("Tekanan Air Lemah");
        } else if (md.getPengaduan().equals("8")) {
            holder.tvIsiPengaduan.setText("Tidak Ada Air");
        } else if (md.getPengaduan().equals("9")) {
            holder.tvIsiPengaduan.setText("Pemakaian Tinggi");
        }

        holder.tvPenyelesaian.setText(md.getPenyelesaian_pengaduan());

        holder.md = md;

        try {
            holder.generateQRcode("http://sippdam.com/auth/penyelesaian/"+md.getId_pengaduan());
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }


    class HolderData extends RecyclerView.ViewHolder {
        private String fileName;
        private Bitmap generatedBitmap;
        private ImageView mImageView;
        TextView tvIdPengaduan, tvStatus, tvCreatedat, tvIsiPengaduan, tvPenyelesaian;
        ModelData md;

        public HolderData(View view) {
            super(view);

            tvIdPengaduan = (TextView) view.findViewById(R.id.tv_pengaduan);
            tvStatus = (TextView) view.findViewById(R.id.tv_status);
            tvCreatedat = (TextView) view.findViewById(R.id.tv_createdat);
            tvIsiPengaduan = (TextView) view.findViewById(R.id.tv_isipengaduan);
            tvPenyelesaian = (TextView) view.findViewById(R.id.tv_penyelesaian);
            mImageView = view.findViewById(R.id.outputBitmap);

//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent update = new Intent(context, LaporPengaduan.class);
//                    update.putExtra("update", 1);
//                    update.putExtra("npm", md.getNpm());
//                    update.putExtra("nama", md.getNama());
//                    update.putExtra("prodi", md.getProdi());
//                    update.putExtra("fakultas", md.getFakultas());
//
//                    context.startActivity(update);
//                }
//            });
        }

        private void generateQRcode(String s) throws WriterException {
            fileName = s;
            BitMatrix result;
            result = new MultiFormatWriter().encode(s, BarcodeFormat.QR_CODE, 1080, 1080, null);
            int w = result.getWidth();
            int h = result.getHeight();
            int[] pixels = new int[w * h];
            for (int y = 0; y < h; y++) {
                int offset = y * w;
                for (int x = 0; x < w; x++) {
                    pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
                }
            }
            Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            bitmap.setPixels(pixels, 0, 1080, 0, 0, w, h);
            generatedBitmap = bitmap;
            mImageView.setImageBitmap(bitmap);
        }
    }
}
