package ru.mirea.golysheva.mireaproject.ui.background;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import ru.mirea.golysheva.mireaproject.R;
import ru.mirea.golysheva.mireaproject.ui.background.MyWorker;

public class WorkFragment extends Fragment {

    private TextView textViewStatus;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_background_task, container, false);

        textViewStatus = root.findViewById(R.id.textStatus);
        Button startButton = root.findViewById(R.id.buttonStartWork);

        startButton.setOnClickListener(v -> {
            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class).build();

            WorkManager.getInstance(requireContext()).enqueue(workRequest);

            WorkManager.getInstance(requireContext())
                    .getWorkInfoByIdLiveData(workRequest.getId())
                    .observe(getViewLifecycleOwner(), new Observer<WorkInfo>() {
                        @Override
                        public void onChanged(WorkInfo workInfo) {
                            if (workInfo != null) {
                                textViewStatus.setText("Статус: " + workInfo.getState().name());
                            }
                        }
                    });
        });

        return root;
    }
}