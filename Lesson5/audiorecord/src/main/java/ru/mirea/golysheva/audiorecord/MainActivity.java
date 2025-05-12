package ru.mirea.golysheva.audiorecord;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;

import ru.mirea.golysheva.audiorecord.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "AudioRecordDemo";
    private static final String FILE_NAME = "audiorecordtest.3gp";

    private ActivityMainBinding binding;

    private MediaRecorder recorder;
    private MediaPlayer   player;

    private boolean isRecording = false;
    private boolean isPlaying   = false;
    private String  recordPath;

    private final ActivityResultLauncher<String[]> permissionLauncher =
            registerForActivityResult(
                    new ActivityResultContracts.RequestMultiplePermissions(),
                    new ActivityResultCallback<java.util.Map<String, Boolean>>() {
                        @Override
                        public void onActivityResult(java.util.Map<String, Boolean> result) {
                            boolean granted = true;
                            for (Boolean b : result.values()) granted &= b;
                            if (!granted) {
                                Toast.makeText(MainActivity.this,
                                        "Разрешения не даны – работа невозможна",
                                        Toast.LENGTH_LONG).show();
                                finish();
                            }
                        }
                    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recordPath = new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), FILE_NAME)
                .getAbsolutePath();

        askPermissionsIfNeeded();

        Button recordBtn = binding.recordButton;
        Button playBtn   = binding.playButton;

        playBtn.setEnabled(false);

        recordBtn.setOnClickListener(v -> {
            if (!isRecording) {
                startRecording();
                recordBtn.setText("Stop recording");
                playBtn.setEnabled(false);
            } else {
                stopRecording();
                recordBtn.setText("Start recording  •  Группа 09-22");
                playBtn.setEnabled(true);
            }
            isRecording = !isRecording;
        });

        playBtn.setOnClickListener(v -> {
            if (!isPlaying) {
                startPlaying();
                playBtn.setText("Stop playing");
                recordBtn.setEnabled(false);
            } else {
                stopPlaying();
                playBtn.setText("Start playing");
                recordBtn.setEnabled(true);
            }
            isPlaying = !isPlaying;
        });
    }


    private void startRecording() {
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(recordPath);

        try {
            recorder.prepare();
            recorder.start();
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed", e);
            Toast.makeText(this, "Не удалось начать запись", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopRecording() {
        try {
            recorder.stop();
        } catch (RuntimeException e) {
            Log.e(TAG, "stop() failed, deleting empty file", e);
            new File(recordPath).delete();
        }
        recorder.release();
        recorder = null;
    }


    private void startPlaying() {
        player = new MediaPlayer();
        try {
            player.setDataSource(recordPath);
            player.prepare();
            player.start();
            player.setOnCompletionListener(mp -> {
                stopPlaying();
                binding.playButton.setText("Start playing");
                binding.recordButton.setEnabled(true);
                isPlaying = false;
            });
        } catch (IOException e) {
            Log.e(TAG, "prepare() failed", e);
            Toast.makeText(this, "Не удалось воспроизвести файл", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopPlaying() {
        if (player != null) {
            player.release();
            player = null;
        }
    }

    private void askPermissionsIfNeeded() {
        java.util.List<String> need = new java.util.ArrayList<>();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            need.add(Manifest.permission.RECORD_AUDIO);
        }

        if (Build.VERSION.SDK_INT <= 28 &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            need.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        if (!need.isEmpty()) permissionLauncher.launch(need.toArray(new String[0]));
    }


    @Override protected void onStop() {
        super.onStop();
        if (isRecording) {
            stopRecording();
            isRecording = false;
        }
        if (isPlaying) {
            stopPlaying();
            isPlaying = false;
        }
    }
}