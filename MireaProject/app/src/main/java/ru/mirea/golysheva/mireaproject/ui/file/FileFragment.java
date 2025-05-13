package ru.mirea.golysheva.mireaproject.ui.file;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import ru.mirea.golysheva.mireaproject.R;

public class FileFragment extends Fragment {

    private ListView fileListView;
    private List<String> fileList = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file, container, false);
        fileListView = view.findViewById(R.id.fileListView);
        FloatingActionButton fab = view.findViewById(R.id.fabAddFile);

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, fileList);
        fileListView.setAdapter(adapter);

        updateFileList();

        fab.setOnClickListener(v -> showCreateFileDialog());

        fileListView.setOnItemClickListener((parent, view1, position, id) -> {
            String filename = fileList.get(position);
            readAndShowFile(filename);
        });

        return view;
    }

    private void updateFileList() {
        String[] files = requireContext().fileList();
        fileList.clear();
        fileList.addAll(Arrays.asList(files));
        adapter.notifyDataSetChanged();
    }

    private void showCreateFileDialog() {
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_file_create, null);
        EditText fileNameInput = dialogView.findViewById(R.id.editFileName);
        EditText fileContentInput = dialogView.findViewById(R.id.editFileContent);

        new AlertDialog.Builder(requireContext())
                .setTitle("Создание файла")
                .setView(dialogView)
                .setPositiveButton("Сохранить", (dialog, which) -> {
                    String fileName = fileNameInput.getText().toString();
                    String content = fileContentInput.getText().toString();
                    try (FileOutputStream fos = requireContext().openFileOutput(fileName, Context.MODE_PRIVATE)) {
                        fos.write(content.getBytes());
                        updateFileList();
                        Toast.makeText(getContext(), "Файл сохранён", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Ошибка записи", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    private void readAndShowFile(String filename) {
        try (FileInputStream fis = requireContext().openFileInput(filename);
             BufferedReader reader = new BufferedReader(new InputStreamReader(fis))) {

            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }

            new AlertDialog.Builder(requireContext())
                    .setTitle("Содержимое файла: " + filename)
                    .setMessage(builder.toString())
                    .setPositiveButton("OK", null)
                    .show();

        } catch (Exception e) {
            Toast.makeText(getContext(), "Ошибка чтения файла", Toast.LENGTH_SHORT).show();
        }
    }
}