package ru.mirea.golysheva.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class MyProgressDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        ProgressBar progressBar = new ProgressBar(getActivity());
        progressBar.setIndeterminate(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Пожалуйста, подождите...")
                .setView(progressBar)
                .setCancelable(true);

        return builder.create();
    }
}