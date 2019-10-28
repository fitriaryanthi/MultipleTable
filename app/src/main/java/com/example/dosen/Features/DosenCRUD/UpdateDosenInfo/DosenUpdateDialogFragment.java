package com.example.dosen.Features.DosenCRUD.UpdateDosenInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.dosen.R;
import com.example.dosen.Database.DatabaseQueryClass;
import com.example.dosen.Model.Dosen;
import com.example.dosen.Util.Config;

public class DosenUpdateDialogFragment extends DialogFragment {
    private EditText dosenNameEditText;
    private EditText dosenNipEditText;
    private EditText dosenPhoneEditText;
    private EditText dosenEmailEditText;
    private Button updateButton;
    private Button cancelButton;
    private DatabaseQueryClass databaseQueryClass;
    private Dosen mDosen;

    private static DosenUpdateListener dosenUpdateListener;
    private static long dosenId;
    private static int position;

    public DosenUpdateDialogFragment() {

    }

    public static DosenUpdateDialogFragment newInstance(long studId, int pos, DosenUpdateListener listener){
        dosenId = studId;
        position = pos;
        dosenUpdateListener = listener;

        DosenUpdateDialogFragment dosenUpdateDialogFragment = new DosenUpdateDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", "Update dosen information");
        dosenUpdateDialogFragment.setArguments(args);

        dosenUpdateDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog);
        return dosenUpdateDialogFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_dosen_update_dialog_fragment, container, false);
        dosenNipEditText = view.findViewById(R.id.dosenNipEditText);
        dosenNameEditText = view.findViewById(R.id.dosenNameEditText);
        dosenPhoneEditText = view.findViewById(R.id.dosenPhoneEditText);
        dosenEmailEditText = view.findViewById(R.id.dosenEmailEditText);
        updateButton = view.findViewById(R.id.updateDosenInfoButton);
        cancelButton = view.findViewById(R.id.cancelButton);

        databaseQueryClass = new DatabaseQueryClass(getContext());
        String title = getArguments().getString(Config.TITLE);
        getDialog().setTitle(title);

        Dosen dosen = databaseQueryClass.getDosenById((int) dosenId);
        Log.e("DOSEN","sdf "+dosen.getEmail());

        dosenNipEditText.setText(dosen.getNip());
        dosenNameEditText.setText(dosen.getName());
        dosenPhoneEditText.setText(dosen.getPhone());
        dosenEmailEditText.setText(dosen.getEmail());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dosenNip = dosenNipEditText.getText().toString();
                String dosenName = dosenNameEditText.getText().toString();
                String dosenPhone = dosenPhoneEditText.getText().toString();
                String dosenEmail = dosenEmailEditText.getText().toString();

                Dosen dosen = new Dosen(-1, dosenNip, dosenName, dosenPhone, dosenEmail);

                long rowCount = databaseQueryClass.updateDosenInfo(dosen);

                if (rowCount > 0) {
                    dosenUpdateListener.onDosenInfoUpdate(dosen, position);
                    getDialog().dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

    public void show(FragmentManager supportFragmentManager, String updateDosen) {
    }
}
