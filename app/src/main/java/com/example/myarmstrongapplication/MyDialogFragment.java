package com.example.myarmstrongapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class MyDialogFragment extends DialogFragment {
    private OnGetDialogResult onGetDialogResult;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
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

    public void setOnGetDialogResult(OnGetDialogResult onGetDialogResult) {
        this.onGetDialogResult = onGetDialogResult;
    }

    public interface OnGetDialogResult {
        void onResponse(boolean isYes);
    }
}
