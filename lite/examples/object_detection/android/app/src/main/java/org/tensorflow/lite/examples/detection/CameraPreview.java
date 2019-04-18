package org.tensorflow.lite.examples.detection;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;

import java.io.IOException;

public class CameraPreview extends TextureView implements TextureView.SurfaceTextureListener {
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private Surface mSurface=null;
    //video recording
    private static String fileName = null;
    private MediaRecorder recorder = null;
    CameraPreview mPreview;
    private TextureView mTexture;
    private SurfaceTexture mSurfaceTexture=null;
    Runnable gettingBitmaps = new Runnable() {
        @Override
        public void run() {

        }
    };
    public CameraPreview(Context context, Camera camera){
        super(context);
        mCamera = camera;
        /*
        mHolder = getHolder();
        mHolder.addCallback(this);
        */
        //mTexture = new TextureView(context);
        //mTexture.setSurfaceTextureListener(mTexture.getSurfaceTextureListener());
    }
    /*
    public void surfaceCreated(SurfaceHolder holder) {
        if(Build.VERSION.SDK_INT<26) {
            try {
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (IOException e) {
                Log.d(TAG, "Error setting camera preview: " + e.getMessage());
            }
        }
        else{
            mSurface=holder.getSurface();
        }
    }*/
    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        //mCamera = Camera.open();
        //mCamera.open();
        Camera.Size previewSize = mCamera.getParameters().getPreviewSize();

        /*mTexture.setLayoutParams(new FrameLayout.LayoutParams(
                previewSize.width, previewSize.height, Gravity.CENTER));*/
        //mTexture.setLayoutParams(this.getLayoutParams());
        try {
            mCamera.setPreviewTexture(surface);
            //mCamera.startPreview();
        } catch (IOException t) {
        }
        //mTexture.setAlpha(1.0f);
        //mTexture.setRotation(90.0f);
        //mTexture.setSurfaceTexture(this.getSurfaceTexture());
        //mTexture.setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        mCamera.stopPreview();
        mCamera.release();
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
    }
    /*
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        if (mHolder.getSurface() == null){
            // preview surface does not exist
            return;
        }

        // stop preview before making changes
        try {
            mCamera.stopPreview();
        } catch (Exception e){
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here

        // start preview with new settings
        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    }
    */
    //video rec
    public void startRecording(CameraPreview previewFromMain, TextureView textureViewFromMain) {
        mPreview=previewFromMain;
        String state = Environment.getExternalStorageState();
        mSurfaceTexture=textureViewFromMain.getSurfaceTexture();
        mSurface = new Surface(mSurfaceTexture);
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            recorder = new MediaRecorder();

            fileName = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
            fileName += "/mirjavideo1.mp4";
            try {
                //mCamera.stopPreview();
                //mCamera.stopPreview();
                //mCamera.setPreviewTexture(mSurfaceTexture);
                //mCamera.startPreview();
                mCamera.lock();
                mCamera.unlock();
                recorder.setCamera(mCamera);

                recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                recorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);

                recorder.setProfile(CamcorderProfile
                        .get(Camera.CameraInfo.CAMERA_FACING_BACK, CamcorderProfile.QUALITY_HIGH));

                recorder.setOutputFile(fileName);
                recorder.setMaxFileSize(50000000); // Approximately 5 megabytes
                //recorder.setPreviewDisplay(mSurface);
                /*recorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {

                    @Override
                    public void onInfo(MediaRecorder mr, int what, int extra) {
                        if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_FILESIZE_REACHED) {

                            mCamera.stopPreview();
                            stopRecording();

                            finish();
                        }

                    }
                });*/

                recorder.prepare();
                recorder.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RuntimeException e){
                e.printStackTrace();
            }
        }
    }

    public void stopRecording() {
        try {
            //mCamera.startPreview();
            recorder.stop();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (RuntimeException e){
            e.printStackTrace();
        }
        recorder.release();
        recorder = null;
    }
    // /video rec
}
