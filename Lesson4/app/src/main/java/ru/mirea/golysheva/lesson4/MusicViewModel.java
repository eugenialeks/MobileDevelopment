package ru.mirea.golysheva.lesson4;

import android.app.Application;
import android.media.MediaPlayer;
import androidx.lifecycle.AndroidViewModel;

public class MusicViewModel extends AndroidViewModel {

    private MediaPlayer mediaPlayer;

    public MusicViewModel(Application application) {
        super(application);
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void createMediaPlayerIfNeeded() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getApplication(), R.raw.sample);
            mediaPlayer.setLooping(true);
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}