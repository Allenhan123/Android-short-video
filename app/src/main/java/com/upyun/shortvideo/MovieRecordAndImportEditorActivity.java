/**
 * TuSDKVideoDemo
 * MovieRecordActivity.java
 *
 * @author Yanlin
 * @Date 7:19:13 PM
 * @Copright (c) 2015 tusdk.com. All rights reserved.
 */
package com.upyun.shortvideo;

import org.lasque.tusdk.core.TuSdk;
import org.lasque.tusdk.core.utils.StringHelper;
import org.lasque.tusdk.core.video.TuSDKVideoResult;
import com.upyun.shortvideo.component.MovieEditorActivity;
import com.upyun.shortvideo.component.MovieRecordKeepModeActivity;
import com.upyun.shortvideo.suite.MoviePreviewAndCutActivity;
import com.upyun.shortvideo.utils.UriUtils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

/**
 * 断点续拍录制相机 + 视频编辑
 *
 * @author Yanlin
 */
public class MovieRecordAndImportEditorActivity extends MovieRecordKeepModeActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getRecordView().getMovieImportButton().setVisibility(View.VISIBLE);
    }

    @Override
    protected void initCamera() {
        super.initCamera();

        mVideoCamera.setWaterMarkImage(null);
    }

    @Override
    public void onMovieRecordComplete(TuSDKVideoResult result) {
        super.onMovieRecordComplete(result);

        //录制成功回调 录制视频：result.videoPath.toString()

        startActivityWithClassName(MovieEditorActivity.class.getName(), result.videoPath.toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;

        Uri selectedMediaUri = data.getData();

        String path = UriUtils.getFileAbsolutePath(getApplicationContext(), selectedMediaUri);

        if (!StringHelper.isEmpty(path)) {
            startActivityWithClassName(MoviePreviewAndCutActivity.class.getName(), path);
        } else {
            TuSdk.messageHub().showToast(getApplicationContext(), R.string.lsq_video_empty_error);
        }
    }
}