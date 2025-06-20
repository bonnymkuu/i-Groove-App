package com.igroove.igrooveapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class PlayingMusicActivity extends AppCompatActivity {
    String audioUriString;
    private ImageView forwardButton;
    private ImageView loopButton;
    private ImageView loopOneButton;
    MediaPlayer mediaPlayer;
    private ImageView moreButton;
    TextView musicName;
    private ImageView nextButton;
    private ImageView playPauseButton;
    TextView playingTextView;
    private ImageView previousButton;
    private ProgressBar progress;
    private ImageView rewindButton;
    String songName;
    String songPath;
    TextView time;
    Handler handler = new Handler();
    //private final int jumpTime = 30000;
    private boolean isPlaying = false;
    private final BroadcastReceiver playbackReceiver = new BroadcastReceiver() {
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent != null && intent.hasExtra("isPlaying")) {
                PlayingMusicActivity.this.isPlaying = intent.getBooleanExtra("isPlaying", false);
                Log.d("PlaybackReceiver", "Playback state updated: " + (PlayingMusicActivity.this.isPlaying ? "Playing" : "Paused"));
                PlayingMusicActivity.this.playPauseButton.setImageResource(PlayingMusicActivity.this.isPlaying ? R.drawable.baseline_pause_circle_24 : R.drawable.baseline_play_circle_filled_24);
                PlayingMusicActivity.this.playingTextView.setText(PlayingMusicActivity.this.isPlaying ? "Playing" : "Paused");
                return;
            }
            Log.e("PlaybackReceiver", "Broadcast missing 'isPlaying' extra!");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) throws Resources.NotFoundException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing_music);

        initializeViews();

        this.songPath = getIntent().getStringExtra("songPath");
        this.audioUriString = getIntent().getStringExtra("audioUri");
        this.songName = getIntent().getStringExtra("songName");

        stopPreviousAudio();

        Intent playIntent = new Intent(this, MusicService.class);
        playIntent.setAction(MusicService.ACTION_PLAY);
        String audioSource = (this.audioUriString != null) ? this.audioUriString : this.songPath;
        playIntent.putExtra(MusicService.EXTRA_AUDIO_PATH, audioSource);
        startService(playIntent);

        this.playPauseButton.setOnClickListener(this::togglePlayPause);

        setupButtonListeners();
        updateProgressBar();
    }

    private void togglePlayPause(View view) {
        Intent intent = new Intent(this, MusicService.class);
        if (this.isPlaying) {
            intent.setAction(MusicService.ACTION_PAUSE);
            this.playingTextView.setText(R.string.paused);
            this.playPauseButton.setImageResource(R.drawable.baseline_play_circle_filled_24);
        } else {
            intent.setAction(MusicService.ACTION_PLAY);
            String audioSource = (this.audioUriString != null) ? this.audioUriString : this.songPath;
            intent.putExtra(MusicService.EXTRA_AUDIO_PATH, audioSource);
            this.playingTextView.setText(R.string.playing);
            this.playPauseButton.setImageResource(R.drawable.baseline_pause_circle_24);
        }
        startService(intent);
    }

    private void initializeViews() throws Resources.NotFoundException {
        this.time = findViewById(R.id.time);
        this.moreButton = findViewById(R.id.more);
        this.loopButton = findViewById(R.id.loop);
        this.nextButton = findViewById(R.id.next);
        this.progress = findViewById(R.id.progress);
        this.musicName = findViewById(R.id.musicName);
        this.rewindButton = findViewById(R.id.rewind);
        this.playPauseButton = findViewById(R.id.play);
        this.forwardButton = findViewById(R.id.forward);
        this.loopOneButton = findViewById(R.id.loopOne);
        this.previousButton = findViewById(R.id.previous);
        this.playingTextView = findViewById(R.id.playing);
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate_animation);
        findViewById(R.id.rotatingIcon).startAnimation(rotate);
    }

    private void stopPreviousAudio() {
        Intent stopIntent = new Intent(this, MusicService.class);
        stopIntent.setAction(MusicService.ACTION_STOP);
        startService(stopIntent);
    }

    private void setupButtonListeners() {
        this.loopButton.setOnClickListener(view -> {
            MediaPlayer mediaPlayer = new MediaPlayer() ;
            mediaPlayer.setLooping(true);
            loopOneButton.setVisibility(View.VISIBLE);
            loopButton.setVisibility(View.GONE);
            Toast.makeText(PlayingMusicActivity.this, "Repeat current song", Toast.LENGTH_SHORT).show();
        });

        this.loopOneButton.setOnClickListener(view -> {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setLooping(false);
            loopOneButton.setVisibility(View.GONE);
            loopButton.setVisibility(View.VISIBLE);
        });

        this.nextButton.setOnClickListener(view -> jumpForward());

        this.forwardButton.setOnClickListener(view -> jumpForward());

        this.previousButton.setOnClickListener(view -> jumpBackward());

        this.rewindButton.setOnClickListener(view -> jumpBackward());

        this.moreButton.setOnClickListener(view -> showPopupMenu());
    }

    private void jumpForward() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            int currentPosition = this.mediaPlayer.getCurrentPosition();
            int duration = this.mediaPlayer.getDuration();
            this.mediaPlayer.seekTo(Math.min(currentPosition + 30000, duration));
        }
    }

    private void jumpBackward() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.mediaPlayer;
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            int currentPosition = this.mediaPlayer.getCurrentPosition();
            this.mediaPlayer.seekTo(Math.max(currentPosition - 30000, 0));
        }
    }

    private void showPopupMenu() {
    }

    public String formatDuration(int durationInMillis) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(durationInMillis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(durationInMillis) - TimeUnit.MINUTES.toSeconds(minutes);
        return String.format(Locale.US, "%02d:%02d", minutes, seconds);
    }

    private void updateProgressBar() {
        this.handler.postDelayed(new Runnable() {
            @Override // java.lang.Runnable
            public void run() {
                if (PlayingMusicActivity.this.mediaPlayer != null && PlayingMusicActivity.this.mediaPlayer.isPlaying()) {
                    PlayingMusicActivity.this.progress.setProgress(PlayingMusicActivity.this.mediaPlayer.getCurrentPosition());
                    TextView textView = PlayingMusicActivity.this.time;
                    PlayingMusicActivity playingMusicActivity = PlayingMusicActivity.this;
                    textView.setText(playingMusicActivity.formatDuration(playingMusicActivity.mediaPlayer.getCurrentPosition()));
                }
                PlayingMusicActivity.this.handler.postDelayed(this, 1000L);
            }
        }, 1000L);
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(this.playbackReceiver, new IntentFilter("PLAYBACK_STATE"), Context.RECEIVER_NOT_EXPORTED);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(this.playbackReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.handler.removeCallbacksAndMessages(null);
    }
}
