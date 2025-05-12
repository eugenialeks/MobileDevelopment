package ru.mirea.golysheva.mireaproject.ui.hardware;

import android.Manifest;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.io.IOException;

import ru.mirea.golysheva.mireaproject.R;

public class MicrophoneFragment extends Fragment {

    private MediaRecorder recorder;
    private MediaPlayer player;
    private String fileName;
    private final int RECORD_REQUEST_CODE = 102;

    private Button btnPlay;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_microphone, container, false);

        Button btnRecord = view.findViewById(R.id.btnRecord);
        Button btnStop = view.findViewById(R.id.btnStop);
        btnPlay = view.findViewById(R.id.btnPlay);

        fileName = requireContext().getExternalFilesDir(null).getAbsolutePath() + "/recorded_audio.3gp";

        btnPlay.setVisibility(View.GONE); // Скрыть кнопку воспроизведения до записи

        btnRecord.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO)
                    != android.content.pm.PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(),
                        new String[]{Manifest.permission.RECORD_AUDIO},
                        RECORD_REQUEST_CODE);
            } else {
                startRecording();
            }
        });

        btnStop.setOnClickListener(v -> stopRecording());

        btnPlay.setOnClickListener(v -> playAudio());

        return view;
    }

    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            recorder.prepare();
            recorder.start();
            Toast.makeText(getContext(), "Запись началась", Toast.LENGTH_SHORT).show();
            btnPlay.setVisibility(View.GONE); // Скрываем, если пользователь начал новую запись
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopRecording() {
        if (recorder != null) {
            recorder.stop();
            recorder.release();
            recorder = null;
            Toast.makeText(getContext(), "Запись завершена", Toast.LENGTH_SHORT).show();
            btnPlay.setVisibility(View.VISIBLE); // Показываем кнопку воспроизведения
        }
    }

    private void playAudio() {
        player = new MediaPlayer();
        try {
            player.setDataSource(fileName);
            player.prepare();
            player.start();
            Toast.makeText(getContext(), "Воспроизведение...", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(getContext(), "Ошибка воспроизведения", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            player.release();
            player = null;
        }
    }
}