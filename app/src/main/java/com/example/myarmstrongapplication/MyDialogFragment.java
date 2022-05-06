package com.example.myarmstrongapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myarmstrongapplication.ceiling.armstrong.Armstrong;

public class MyDialogFragment extends DialogFragment {
    private OnGetDialogResult onGetDialogResult;
    public interface OnGetDialogResult {
        void onResponse(boolean isYes);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    public void setOnGetDialogResult(OnGetDialogResult onGetDialogResult) {
        this.onGetDialogResult = onGetDialogResult;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Вихід");
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage("Ви дійсно бажаєте вийти?");
        builder.setPositiveButton("Так", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onGetDialogResult.onResponse(true);
            }
        });
        builder.setNegativeButton("Ні", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onGetDialogResult.onResponse(false);
            }
        });
        return builder.create();
    }
}
