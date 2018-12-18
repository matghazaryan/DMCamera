package camera.dm.com.dmcameraexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dm.camera.activities.DMCameraActivity;
import com.dm.camera.constants.IDMCameraConstants;
import com.dm.camera.models.MediaData;

public class MainActivity extends AppCompatActivity {

     final int REQUEST_CODE_CAMERA = 101;
    private MediaData mMediaData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(this, DMCameraActivity.class);

        intent.putExtra(IDMCameraConstants.BundleKey.CAMERA_TYPE, IDMCameraConstants.Camera.PHOTO);
        intent.putExtra(IDMCameraConstants.BundleKey.IS_MULTIPLY_GALLERY_IMAGE, false);
        intent.putExtra(IDMCameraConstants.BundleKey.IS_MULTIPLY_GALLERY_VIDEO, true);
        intent.putExtra(IDMCameraConstants.BundleKey.VIDEO_DURATION_IN_SECONDS, 10);
        intent.putExtra(IDMCameraConstants.BundleKey.PICKER_TYPE, IDMCameraConstants.Picker.DEFAULT);
        intent.putExtra(IDMCameraConstants.BundleKey.ACTIONBAR_TITLE, "Gallery");
        intent.putExtra(IDMCameraConstants.BundleKey.MAX_COUNT, 3);


        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_CAMERA:
                    if (data != null) {
                        final Bundle bundle = data.getExtras();
                        if (bundle != null) {
                            mMediaData = bundle.getParcelable(IDMCameraConstants.BundleKey.MEDIA_DATA);

                            for (final String path : mMediaData.getSelectedImagesOrVideosPathList()) {
                                Log.d("GPU", "before photo path ---- > " + path);
                            }
                        }
                    }
                    break;

            }
        }
    }
}
