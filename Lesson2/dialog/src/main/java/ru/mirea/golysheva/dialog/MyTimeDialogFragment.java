package ru.mirea.golysheva.dialog;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import java.util.Calendar;

public class MyTimeDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),
                (TimePicker view, int hourOfDay, int minute1) ->
                        Toast.makeText(getActivity(),
                                "Вы выбрали: " + hourOfDay + ":" + minute1,
                                Toast.LENGTH_LONG).show(),
                hour, minute, DateFormat.is24HourFormat(getActivity()));
    }
}