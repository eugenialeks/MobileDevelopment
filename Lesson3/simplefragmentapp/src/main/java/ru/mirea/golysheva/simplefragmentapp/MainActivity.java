package ru.mirea.golysheva.simplefragmentapp;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public class MainActivity extends AppCompatActivity {

    private boolean isLandscape;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;

        if (isLandscape) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_first_container, new FirstFragment())
                    .replace(R.id.fragment_second_container, new SecondFragment())
                    .commit();
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();

            Button btnFirst = findViewById(R.id.btnFirstFragment);
            Button btnSecond = findViewById(R.id.btnSecondFragment);

            fragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainerView, new FirstFragment())
                    .commit();

            btnFirst.setOnClickListener(v ->
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, new FirstFragment())
                            .commit()
            );

            btnSecond.setOnClickListener(v ->
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, new SecondFragment())
                            .commit()
            );
        }
    }
}
