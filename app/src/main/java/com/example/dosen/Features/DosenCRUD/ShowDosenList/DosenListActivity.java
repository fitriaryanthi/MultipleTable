package com.example.dosen.Features.DosenCRUD.ShowDosenList;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.dosen.Features.DosenCRUD.CreateDosen.DosenCreateListener;
import com.example.dosen.Model.Dosen;
import com.example.dosen.R;
import com.example.dosen.Database.DatabaseQueryClass;
import com.example.dosen.Features.DosenCRUD.CreateDosen.DosenCreateDialogFragment;
import com.example.dosen.Util.Config;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DosenListActivity extends AppCompatActivity implements DosenCreateListener {
    private int idProdi;
    private DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(this);

    private List<Dosen> dosenList = new ArrayList<>();

    private TextView summaryTextView;
    private TextView dosenListEmptyTextView;
    private RecyclerView recyclerView;
    private DosenListRecyclerViewAdapter dosenListRecyclerViewAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dosen_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        summaryTextView = findViewById(R.id.summaryTextView);
        dosenListEmptyTextView = findViewById(R.id.emptyListTextView);

        idProdi = getIntent().getIntExtra(Config.PRODI_REGISTRATION,2);

        dosenList.addAll(databaseQueryClass.getAllDosenByRegNo(idProdi));
//        Log.d("STDL",dosenList.toString());

//        for(String a : dosenList)
//        {
//            Log.v("Tag",a);
//        }


        dosenListRecyclerViewAdapter = new DosenListRecyclerViewAdapter(this, dosenList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(dosenListRecyclerViewAdapter);

        viewVisibility();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDosenCreateDialog();
            }
        });
    }

    private void openDosenCreateDialog() {
        DosenCreateDialogFragment dosenCreateDialogFragment = DosenCreateDialogFragment.newInstance(idProdi, this);
        dosenCreateDialogFragment.show(getSupportFragmentManager(), Config.CREATE_DOSEN);
    }

    private void printSummary() {
        long dosenNum = databaseQueryClass.getNumberOfDosen();
        long prodiNum = databaseQueryClass.getNumberOfProdi();

        summaryTextView.setText(getResources().getString(R.string.database_summary, dosenNum, prodiNum));
    }

    public void viewVisibility() {
        if(dosenList.isEmpty())
            dosenListEmptyTextView.setVisibility(View.VISIBLE);
        else
            dosenListEmptyTextView.setVisibility(View.GONE);
        printSummary();
    }

    @Override
    public void onDosenCreated(Dosen dosen) {
        dosenList.add(dosen);
        dosenListRecyclerViewAdapter.notifyDataSetChanged();
        viewVisibility();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_delete:
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Are you sure, You wanted to delete all dosen?");
                alertDialogBuilder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                boolean isAllDeleted = databaseQueryClass.deleteAllDosenByRegNum(idProdi);
                                if(isAllDeleted){
                                    dosenList.clear();
                                    dosenListRecyclerViewAdapter.notifyDataSetChanged();
                                    viewVisibility();
                                }
                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }
}
