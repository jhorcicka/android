package cz.jhorcicka.features;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import cz.jhorcicka.logging.Logger;

import java.io.IOException;

/**
 * Created by horcicka on 29. 09 . 2015.
 */
public class SoundTester {
    private static final int RECORDING_TIME = 3000;
    private static final Logger logger = new Logger(SoundTester.class);
    private static String mFileName = null;
    private MediaRecorder mRecorder = null;
    private MediaPlayer   mPlayer = null;

    public SoundTester(Activity activity) {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName = activity
                .getApplicationContext()
                .getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                .getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
    }

    public void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    public void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();

        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            logger.log("prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        try {
            mRecorder = new MediaRecorder();
            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setOutputFile(mFileName);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mRecorder.prepare();
            mRecorder.start();
            Thread.sleep(RECORDING_TIME);
            mRecorder.stop();
        } catch (IOException | InterruptedException e) {
            logger.log(e.getMessage());
        }
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }
}
