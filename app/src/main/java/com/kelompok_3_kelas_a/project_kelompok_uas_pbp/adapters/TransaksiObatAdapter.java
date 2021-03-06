package com.kelompok_3_kelas_a.project_kelompok_uas_pbp.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.kelompok_3_kelas_a.project_kelompok_uas_pbp.activity.AddEditTransaksiObatActivity;
import com.kelompok_3_kelas_a.project_kelompok_uas_pbp.activity.PendaftaranActivity;
import com.kelompok_3_kelas_a.project_kelompok_uas_pbp.R;
import com.kelompok_3_kelas_a.project_kelompok_uas_pbp.activity.TransaksiObatActivity;
import com.kelompok_3_kelas_a.project_kelompok_uas_pbp.models.TransaksiObatModels;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TransaksiObatAdapter extends RecyclerView.Adapter<TransaksiObatAdapter.ViewHolder>
        implements Filterable {

    private List<TransaksiObatModels> transaksiObatModelsList, filteredTransaksiObatList;
    private Context context;

    public TransaksiObatAdapter(List<TransaksiObatModels> transaksiObatModelsList, Context context) {
        this.transaksiObatModelsList = transaksiObatModelsList;
        filteredTransaksiObatList = new ArrayList<>(transaksiObatModelsList);
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_namaPembeliTransaksiObat, tv_nomorHP_PembeliTransaksiObat,
                tv_alamat_PembeliTransaksiObat, tv_umur_PembeliTransaksiObat,
                tv_totalBeliObat, tv_totalBiayaTransaksiObat;
        CardView cv_transaksiObat;
        ImageButton btn_deleteTransaksiObat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_namaPembeliTransaksiObat = itemView.findViewById(R.id.tv_namaPembeliTransaksiObat);
            tv_nomorHP_PembeliTransaksiObat = itemView.findViewById(R.id.tv_nomorHP_PembeliTransaksiObat);
            tv_alamat_PembeliTransaksiObat = itemView.findViewById(R.id.tv_alamat_PembeliTransaksiObat);
            tv_umur_PembeliTransaksiObat = itemView.findViewById(R.id.tv_umur_PembeliTransaksiObat);
            tv_totalBeliObat = itemView.findViewById(R.id.tv_totalBeliObat);
            tv_totalBiayaTransaksiObat = itemView.findViewById(R.id.tv_totalBiayaTransaksiObat);

            cv_transaksiObat = itemView.findViewById(R.id.cv_transaksiObat);
            btn_deleteTransaksiObat = itemView.findViewById(R.id.btn_deleteTransaksiObat);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_transaksi_obat_tampil, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiObatAdapter.ViewHolder holder, int position) {
        TransaksiObatModels transaksiObatModels = filteredTransaksiObatList.get(position);

        holder.tv_namaPembeliTransaksiObat.setText(transaksiObatModels.getNamaPembeli());
        holder.tv_nomorHP_PembeliTransaksiObat.setText(transaksiObatModels.getNomorHpPembeli());
        holder.tv_alamat_PembeliTransaksiObat.setText(transaksiObatModels.getAlamatPembeli());
        holder.tv_umur_PembeliTransaksiObat.setText(transaksiObatModels.getUmurPembeli());

        holder.tv_totalBeliObat.setText(String.valueOf(transaksiObatModels.getJumlahBeli()));
        holder.tv_totalBiayaTransaksiObat.setText(String.valueOf(transaksiObatModels.getTotalBayarObat()));

        holder.btn_deleteTransaksiObat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder =
                        new MaterialAlertDialogBuilder(context);
                materialAlertDialogBuilder.setTitle("Konfirmasi")
                        .setMessage("Apakah anda yakin ingin menghapus transaksi obat ini?")
                        .setNegativeButton("Batal", null)
                        .setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (context instanceof TransaksiObatActivity)
                                    ((TransaksiObatActivity) context).deleteTransaksiObat(transaksiObatModels.getIdTransaksiObat());
                            }
                        })
                        .show();
            }
        });

        holder.cv_transaksiObat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, AddEditTransaksiObatActivity.class);
                i.putExtra("id", transaksiObatModels.getIdObat());
                i.putExtra("lemparId", 1);
                Log.v("jona", String.valueOf(transaksiObatModels.getIdTransaksiObat()));
                i.putExtra("idTransaksi", transaksiObatModels.getIdTransaksiObat());

                if (context instanceof TransaksiObatActivity)
                    ((TransaksiObatActivity) context).startActivityForResult(i,
                            TransaksiObatActivity.LAUNCH_ADD_ACTIVITY);
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredTransaksiObatList.size();
    }


    public void setTransaksiObatModelsList(List<TransaksiObatModels> transaksiObatModelsList) {
        this.transaksiObatModelsList = transaksiObatModelsList;
        filteredTransaksiObatList = new ArrayList<>(filteredTransaksiObatList);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charSequenceString = charSequence.toString();
                List<TransaksiObatModels> filtered = new ArrayList<>();

                if (charSequenceString.isEmpty()) {
                    filtered.addAll(transaksiObatModelsList);
                } else {
                    for (TransaksiObatModels transaksiObatModels : transaksiObatModelsList) {
                        if (transaksiObatModels.getNamaPembeli().toLowerCase()
                                .contains(charSequenceString.toLowerCase()))
                            filtered.add(transaksiObatModels);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filtered;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredTransaksiObatList.clear();
                filteredTransaksiObatList.addAll((List<TransaksiObatModels>) filterResults.values);
                notifyDataSetChanged();
            }
        };
    }
}
