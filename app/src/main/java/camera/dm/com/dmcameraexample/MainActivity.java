package camera.dm.com.dmcameraexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dm.camera.activities.DMCameraActivity;
import com.dm.camera.constants.IConstants;
import com.dm.camera.models.MediaData;

public class MainActivity extends AppCompatActivity {

     final int REQUEST_CODE_CAMERA = 101;
    private MediaData mMediaData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Intent intent = new Intent(this, DMCameraActivity.class);

        intent.putExtra(IConstants.BundleKey.CAMERA_TYPE, IConstants.Camera.PHOTO);
        intent.putExtra(IConstants.BundleKey.IS_MULTIPLY_GALLERY_IMAGE, false);
        intent.putExtra(IConstants.BundleKey.IS_MULTIPLY_GALLERY_VIDEO, true);
        intent.putExtra(IConstants.BundleKey.VIDEO_DURATION_IN_SECONDS, 10);
        intent.putExtra(IConstants.BundleKey.PICKER_TYPE, IConstants.Picker.DEFAULT);
        intent.putExtra(IConstants.BundleKey.ACTIONBAR_TITLE, "Gallery");
        intent.putExtra(IConstants.BundleKey.MAX_COUNT, 3);


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
                            mMediaData = bundle.getParcelable(IConstants.BundleKey.MEDIA_DATA);

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
