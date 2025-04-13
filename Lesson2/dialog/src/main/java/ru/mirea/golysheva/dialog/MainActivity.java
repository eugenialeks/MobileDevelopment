package ru.mirea.golysheva.dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickShowDialog(View view) {
        DialogFragment dialog = new AlertDialogFragment();
        dialog.show(getSupportFragmentManager(), "mirea_alert");
    }

    public void showTimeDialog(View view) {
        DialogFragment timeDialog = new MyTimeDialogFragment();
        timeDialog.show(getSupportFragmentManager(), "timeDialog");
    }

    public void showDateDialog(View view) {
        DialogFragment dateDialog = new MyDateDialogFragment();
        dateDialog.show(getSupportFragmentManager(), "dateDialog");
    }

    public void showProgressDialog(View view) {
        DialogFragment progressDialog = new MyProgressDialogFragment();
        progressDialog.show(getSupportFragmentManager(), "progressDialog");
    }

    public void onOkClicked() {
        Toast.makeText(getApplicationContext(),
                "Вы выбрали кнопку \"Иду дальше\"!",
                Toast.LENGTH_LONG).show();
    }

    public void onCancelClicked() {
        Toast.makeText(getApplicationContext(),
                "Вы выбрали кнопку \"Нет\"!",
                Toast.LENGTH_LONG).show();
    }

    public void onNeutralClicked() {
        Toast.makeText(getApplicationContext(),
                "Вы выбрали кнопку \"На паузе\"!",
                Toast.LENGTH_LONG).show();
    }
}
