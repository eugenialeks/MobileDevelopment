package ru.mirea.golysheva.employeedb;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ru.mirea.golysheva.employeedb.App;
import ru.mirea.golysheva.employeedb.R;
import ru.mirea.golysheva.employeedb.data.Hero;
import ru.mirea.golysheva.employeedb.data.HeroDao;

public class MainActivity extends AppCompatActivity {

    private HeroDao heroDao;
    private TextView tvLog;
    private EditText etName, etPower, etRating;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        heroDao = App.get().db().heroDao();

        etName   = findViewById(R.id.etName);
        etPower  = findViewById(R.id.etPower);
        etRating = findViewById(R.id.etRating);
        tvLog    = findViewById(R.id.tvLog);
        tvLog.setMovementMethod(new ScrollingMovementMethod());

        Button btnAdd  = findViewById(R.id.btnAdd);
        Button btnShow = findViewById(R.id.btnShow);

        btnAdd.setOnClickListener(v -> addHero());
        btnShow.setOnClickListener(v -> showHeroes());
    }

    private void addHero() {
        String name   = etName.getText().toString().trim();
        String power  = etPower.getText().toString().trim();
        String ratingStr = etRating.getText().toString().trim();

        if (name.isEmpty() || power.isEmpty() || ratingStr.isEmpty()) {
            toast("Заполните все поля!");
            return;
        }

        int rating;
        try {
            rating = Integer.parseInt(ratingStr);
            if (rating < 0 || rating > 100) {
                toast("Рейтинг должен быть от 0 до 100");
                return;
            }
        } catch (NumberFormatException e) {
            toast("Рейтинг — это число");
            return;
        }

        Hero hero = new Hero();
        hero.name = name;
        hero.power = power;
        hero.rating = rating;

        heroDao.insert(hero);
        toast("Герой добавлен!");

        // очищаем поля
        etName.setText("");
        etPower.setText("");
        etRating.setText("");
    }

    private void showHeroes() {
        List<Hero> list = heroDao.getAll();
        StringBuilder sb = new StringBuilder("Герои в базе:\n");
        for (Hero h : list) {
            sb.append(h.id).append(". ")
                    .append(h.name).append(" — ")
                    .append(h.power).append(" (")
                    .append(h.rating).append(")\n");
        }
        tvLog.setText(sb.toString());
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
