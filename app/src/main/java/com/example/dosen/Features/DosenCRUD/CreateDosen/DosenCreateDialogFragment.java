package com.example.dosen.Features.DosenCRUD.CreateDosen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.dosen.R;
import com.example.dosen.Database.DatabaseQueryClass;
import com.example.dosen.Model.Dosen;

public class DosenCreateDialogFragment extends DialogFragment {
    private static int idProdi;
    private static DosenCreateListener dosenCreateListener;

    private EditText dosenNameEditText;
    private EditText dosenNipEditText;
    private EditText dosenPhoneEditText;
    private EditText dosenEmailEditText;
    private Button createButton;
    private Button cancelButton;

    private String dosenNip = "";
    private String dosenName = "";
    private String dosenPhone = "";
    private String dosenEmail = "";

    public DosenCreateDialogFragment() {

    }

    public static DosenCreateDialogFragment newInstance(int id_Prodi, DosenCreateListener listener){
        idProdi = id_Prodi;
        String testt = String.valueOf(idProdi);
        Log.d("Ayu",testt);
        dosenCreateListener = listener;

        DosenCreateDialogFragment dosenCreateDialogFragment = new DosenCreateDialogFragment();

        dosenCreateDialogFragment.setStyle(androidx.fragment.app.DialogFragment.STYLE_NORMAL, R.style.CustomDialog);

        return dosenCreateDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.activity_dosen_create_dialog_fragment, container, false);
        getDialog().setTitle("Add New Dosen");

        dosenNipEditText =view.findViewById(R.id.dosenNipEditText);
        dosenNameEditText =view.findViewById(R.id.dosenNameEditText);
        dosenPhoneEditText =view.findViewById(R.id.dosenPhoneEditText);
        dosenEmailEditText =view.findViewById(R.id.dosenEmailEditText);

        createButton = view.findViewById(R.id.createButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        createButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dosenNip = dosenNipEditText.getText().toString();
                dosenName = dosenNameEditText.getText().toString();
                dosenPhone = dosenPhoneEditText.getText().toString();
                dosenEmail = dosenEmailEditText.getText().toString();




                Dosen dosen = new Dosen(-1,dosenNip,dosenName,dosenPhone,dosenEmail);
                DatabaseQueryClass databaseQueryClass = new DatabaseQueryClass(getContext());
                long id = databaseQueryClass.insertDosen(dosen, idProdi);

                if(id>0){
                    dosen.setId(id);
                    dosenCreateListener.onDosenCreated(dosen);
                    getDialog().dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            //noinspection ConstantConditions
            dialog.getWindow().setLayout(width, height);
        }
    }
}

