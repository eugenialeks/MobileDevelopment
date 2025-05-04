package ru.mirea.golysheva.lesson4;

import androidx.appcompat.app.AppCompatActivity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import ru.mirea.golysheva.lesson4.databinding.ActivityMusicPlayerBinding;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

public class MainActivity extends AppCompatActivity {

    private ActivityMusicPlayerBinding binding;
    private MusicViewModel viewModel;
    private boolean isPlaying = false;
    private Handler handler = new Handler();

    private final Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            MediaPlayer player = viewModel.getMediaPlayer();
            if (player != null && isPlaying) {
                binding.seekBar.setProgress(player.getCurrentPosition());
                binding.currentTime.setText(formatTime(player.getCurrentPosition()));
                handler.postDelayed(this, 500);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMusicPlayerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MusicViewModel.class);
        viewModel.createMediaPlayerIfNeeded();

        MediaPlayer player = viewModel.getMediaPlayer();
        binding.seekBar.setMax(player.getDuration());
        binding.durationTime.setText(formatTime(player.getDuration()));
        binding.currentTime.setText(formatTime(player.getCurrentPosition()));

        binding.playPauseButton.setOnClickListener(v -> {
            if (isPlaying) {
                player.pause();
                binding.playPauseButton.setImageResource(R.drawable.ic_play);
            } else {
                player.start();
                binding.playPauseButton.setImageResource(R.drawable.ic_pause);
                handler.post(updateSeekBar);
            }
            isPlaying = !isPlaying;
        });

        binding.seekBar.setOnSeekBarChangeListener(new android.widget.SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(android.widget.SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) player.seekTo(progress);
            }
            @Override public void onStartTrackingTouch(android.widget.SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(android.widget.SeekBar seekBar) {}
        });

        binding.prevButton.setOnClickListener(v -> {
            int pos = Math.max(0, player.getCurrentPosition() - 10000);
            player.seekTo(pos);
        });

        binding.nextButton.setOnClickListener(v -> {
            int pos = Math.min(player.getDuration(), player.getCurrentPosition() + 10000);
            player.seekTo(pos);
        });
    }

    private String formatTime(int millis) {
        int minutes = millis / 60000;
        int seconds = (millis % 60000) / 1000;
        return String.format("%d:%02d", minutes, seconds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(updateSeekBar);
    }
}
