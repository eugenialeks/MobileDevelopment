package ru.mirea.golysheva.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;


public class AlertDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MainActivity parent = (MainActivity) getActivity();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Здравствуй МИРЭА!")
                .setMessage("Успех близок?")
                .setIcon(R.mipmap.ic_launcher_round)
                .setPositiveButton("Иду дальше", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Вызываем метод в MainActivity
                        parent.onOkClicked();
                    }
                })
                .setNeutralButton("На паузе", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        parent.onNeutralClicked();
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        parent.onCancelClicked();
                    }
                });

        return builder.create();
    }
}
