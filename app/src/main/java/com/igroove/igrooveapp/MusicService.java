package com.igroove.igrooveapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import java.io.IOException;
import java.util.Objects;

public class MusicService extends Service {
    public static final String ACTION_PAUSE = "ACTION_PAUSE";
    public static final String ACTION_PLAY = "ACTION_PLAY";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String EXTRA_AUDIO_PATH = "audioPath";
    private static final String TAG = "MusicService";

    private MediaPlayer mediaPlayer;
    String currentAudioPath;

    @Override
    public IBinder onBind(Intent intent) {
        return null; // This is a started service, not a bound service
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            String audioPath = intent.getStringExtra(EXTRA_AUDIO_PATH);

            switch (Objects.requireNonNull(action)) {
                case ACTION_PLAY:
                    if (audioPath != null) {
                        currentAudioPath = audioPath;
                        playAudio(audioPath);
                    }
                    break;
                case ACTION_PAUSE:
                    pauseAudio();
                    break;
                case ACTION_STOP:
                    stopAudio();
                    stopSelf(); // Stop the service completely
                    break;
                default:
                    Log.w(TAG, "Unknown action received: " + action);
                    break;
            }
        }
        return START_STICKY;
    }

    private synchronized void playAudio(String audioPath) {
        if (mediaPlayer != null) {
            Log.d(TAG, "Stopping current playback before starting new one.");
            stopAudio();
        }

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioPath);
            mediaPlayer.prepareAsync();

            mediaPlayer.setOnPreparedListener(mp -> {
                Log.d(TAG, "MediaPlayer prepared. Starting playback.");
                mp.start();
                broadcastPlaybackState(true);
            });

            mediaPlayer.setOnCompletionListener(mp -> {
                Log.d(TAG, "Playback completed.");
                stopAudio();
            });

            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                Log.e(TAG, "Error during playback. What: " + what + ", Extra: " + extra);
                stopAudio();
                return true;
            });

        } catch (IOException e) {
            Log.e(TAG, "Error setting data source: " + e.getMessage(), e);
            stopAudio();
        }
    }

    private synchronized void pauseAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            Log.d(TAG, "Playback paused.");
            broadcastPlaybackState(false);
        } else {
            Log.d(TAG, "Pause requested but MediaPlayer is not playing.");
        }
    }

    private synchronized void stopAudio() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                Log.d(TAG, "Stopping playback.");
                mediaPlayer.stop();
            }
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            Log.d(TAG, "MediaPlayer released.");
        }
        broadcastPlaybackState(false);
    }

    private void broadcastPlaybackState(boolean isPlaying) {
        Intent intent = new Intent("PLAYBACK_STATE");
        intent.putExtra("isPlaying", isPlaying);
        sendBroadcast(intent);
        Log.d(TAG, "Broadcasting playback state: " + (isPlaying ? "Playing" : "Paused/Stopped"));
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "Service destroyed. Stopping and releasing MediaPlayer.");
        stopAudio();
        super.onDestroy();
    }
}
