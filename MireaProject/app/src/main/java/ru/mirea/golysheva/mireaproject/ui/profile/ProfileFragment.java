package ru.mirea.golysheva.mireaproject.ui.profile;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ru.mirea.golysheva.mireaproject.R;

public class ProfileFragment extends Fragment {
    private static final String PREF_NAME = "user_profile";
    private static final String KEY_NAME = "name";
    private static final String KEY_GROUP = "group";

    private EditText nameInput, groupInput;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nameInput = view.findViewById(R.id.editName);
        groupInput = view.findViewById(R.id.editGroup);
        Button btnSave = view.findViewById(R.id.btnSave);

        SharedPreferences prefs = requireActivity().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Восстановление
        nameInput.setText(prefs.getString(KEY_NAME, ""));
        groupInput.setText(prefs.getString(KEY_GROUP, ""));

        btnSave.setOnClickListener(v -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(KEY_NAME, nameInput.getText().toString());
            editor.putString(KEY_GROUP, groupInput.getText().toString());
            editor.apply();
            Toast.makeText(getContext(), "Профиль сохранён", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}