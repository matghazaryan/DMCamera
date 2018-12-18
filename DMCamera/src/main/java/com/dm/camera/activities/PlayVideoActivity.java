package com.dm.camera.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

import com.dm.camera.R;
import com.dm.camera.constants.IDMCameraConstants;

import static com.dm.camera.constants.IDMCameraConstants.BundleKey.POSITION;

public class PlayVideoActivity extends AppCompatActivity {

    private VideoView mVideoView;
    private int mStopPosition = 0;
    private MediaController mMediaControls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_play_video);

        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView != null && mVideoView.isPlaying() && mVideoView.canPause()) {
            mStopPosition = mVideoView.getCurrentPosition();
            mVideoView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null && !mVideoView.isPlaying()) {
            mVideoView.seekTo(mStopPosition);
            mVideoView.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
    }

    private void init() {
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        final Intent intent = getIntent();
        if (intent != null) {
            final Bundle bundle = intent.getExtras();
            if (bundle != null) {
                initVideoPlayer(bundle);

                findViewById(R.id.btn_done).setOnClickListener(v -> {
                    final Intent intent1 = new Intent();
                    intent1.putExtra(IDMCameraConstants.BundleKey.PATH, bundle.getString(IDMCameraConstants.BundleKey.PATH));
                    setResult(RESULT_OK, intent1);
                    finish();
                });

                findViewById(R.id.btn_replay).setOnClickListener(v -> finish());
            }
        }
    }

    private void initVideoPlayer(final Bundle bundle) {
        if (mMediaControls == null) {
            mMediaControls = new MediaController(this);
        }

        mVideoView = findViewById(R.id.video_view);

        try {
            mVideoView.setMediaController(mMediaControls);
            mVideoView.setVideoPath(bundle.getString(IDMCameraConstants.BundleKey.PATH));
            mVideoView.requestFocus();
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }

        mVideoView.requestFocus();

        mVideoView.setOnPreparedListener(mediaPlayer -> {
            mVideoView.seekTo(mStopPosition);
            if (mStopPosition != 0) {
                mVideoView.pause();
            }
        });
    }

    @Override
    public void onSaveInstanceState(final Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(POSITION, mStopPosition);
        mVideoView.pause();
    }

    @Override
    public void onRestoreInstanceState(final Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mStopPosition = savedInstanceState.getInt(POSITION);
        mVideoView.seekTo(mStopPosition);
    }
}
