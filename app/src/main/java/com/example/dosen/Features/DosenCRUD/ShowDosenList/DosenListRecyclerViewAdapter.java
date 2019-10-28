package com.example.dosen.Features.DosenCRUD.ShowDosenList;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.example.dosen.Model.Dosen;
import com.example.dosen.Database.DatabaseQueryClass;
import com.example.dosen.Features.DosenCRUD.UpdateDosenInfo.DosenUpdateDialogFragment;
import com.example.dosen.Features.DosenCRUD.UpdateDosenInfo.DosenUpdateListener;
import com.example.dosen.Util.Config;
import com.example.dosen.R;

import java.util.List;

public class DosenListRecyclerViewAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private Context context;
    private List<Dosen> dosenList;

    public DosenListRecyclerViewAdapter(Context context, List<Dosen> dosenList) {
        this.context = context;
        this.dosenList = dosenList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_dosen, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final int listPosition = position;
        final Dosen dosen = dosenList.get(position);

        holder.dosenNipTextView.setText(dosen.getNip());
        holder.dosenNameTextView.setText(dosen.getName());
        holder.dosenPhoneTextView.setText(dosen.getPhone());
        holder.dosenEmailTextView.setText(dosen.getEmail());
        holder.crossButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete this dosen?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                deleteDosen(dosen);
                            }
                        });

                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        holder.editButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editDosen(dosen.getId(), listPosition);
            }
        });
    }

    private void editDosen(long dosenId, int listPosition) {
        DosenUpdateDialogFragment dosenUpdateDialogFragment = DosenUpdateDialogFragment.newInstance(dosenId, listPosition, new DosenUpdateListener() {
            @Override
            public void onDosenInfoUpdate(Dosen dosen, int position) {
                dosenList.set(position, dosen);
                notifyDataSetChanged();
            }
        });
        dosenUpdateDialogFragment.show(((DosenListActivity) context).getSupportFragmentManager(), Config.UPDATE_DOSEN);
    }

    private void deleteDosen(Dosen dosen) {
        DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(context);
        boolean isDeleted = databaseQueryClass.deleteDosenByid(dosen.getId());

        if (isDeleted) {
            dosenList.remove(dosen);
            notifyDataSetChanged();
            ((DosenListActivity) context).viewVisibility();
        } else
            Toast.makeText(context, "Cannot delete!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return dosenList.size();
    }
}

