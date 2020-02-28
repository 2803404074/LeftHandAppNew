package com.tencent.liteav.demo.player.superplayer;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.liteav.demo.play.SuperPlayerConst;
import com.tencent.liteav.demo.play.SuperPlayerGlobalConfig;
import com.tencent.liteav.demo.play.SuperPlayerModel;
import com.tencent.liteav.demo.play.SuperPlayerVideoId;
import com.tencent.liteav.demo.play.SuperPlayerView;
import com.tencent.liteav.demo.player.R;
import com.tencent.rtmp.TXLiveConstants;
import com.zhutian.baselibrary.ui.anim.HiddenAnimUtils;
import com.zhutian.baselibrary.util.Conver;
import com.zhutian.baselibrary.util.DialogUtil;
import com.zhutian.baselibrary.util.ScreenUtils;
import com.zhutian.baselibrary.util.StatusBarUtil;
import com.zhutian.baselibrary.util.StringUtils;
import com.zhutian.baselibrary.util.ToastUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Update by huangshihao on 2020/2/23
 * 超级播放器主Activity
 */
public class SuperPlayerActivityTest extends Activity implements View.OnClickListener,
        SuperPlayerView.OnSuperPlayerViewCallback,
        TCVodPlayerListAdapter.OnItemClickLitener {
    //播放器标题
    private RelativeLayout mLayoutTitle;
    private LinearLayout llInput;
    private ImageView mIvBack;

    //内容标题
    private TextView tvTitle;
    private EditText etInput;


    //超级播放器View
    private SuperPlayerView mSuperPlayerView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.INSTANCE.setStatusBarColor(this, R.color.white);
        setContentView(R.layout.activity_supervod_player_test);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        checkPermission();

        initView();

        //设置全局悬浮窗
        initSuperVodGlobalSetting();
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(this, permissions.toArray(new String[0]), 100);
            }
        }
    }

    private void initView() {
        mIvBack =  findViewById(R.id.iv_back);
        mIvBack.setOnClickListener(this);

        etInput = findViewById(R.id.etInput);
        findViewById(R.id.tvPlay).setOnClickListener(this);

        mLayoutTitle = findViewById(R.id.layout_title);
        llInput = findViewById(R.id.llInput);


        mSuperPlayerView = findViewById(R.id.superVodPlayerView);
        mSuperPlayerView.setPlayerViewCallback(this);
        mSuperPlayerView.setNetCallBack(new SuperPlayerView.NetCallBack() {
            @Override
            public void netCallBack() {
            }
        });
    }

    private void initSuperVodGlobalSetting() {
        SuperPlayerGlobalConfig prefs = SuperPlayerGlobalConfig.getInstance();
        // 开启悬浮窗播放
        prefs.enableFloatWindow = true;
        // 设置悬浮窗的初始位置和宽高
        SuperPlayerGlobalConfig.TXRect rect = new SuperPlayerGlobalConfig.TXRect();
        rect.x = 0;
        rect.y = 0;
        rect.width = 810;
        rect.height = 540;
        prefs.floatViewRect = rect;
        // 播放器默认缓存个数
        prefs.maxCacheItem = 5;
        // 设置播放器渲染模式
        prefs.enableHWAcceleration = true;
        prefs.renderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION;
        //需要修改为自己的时移域名
        //prefs.playShiftDomain = "vcloudtimeshift.qcloud.com";
    }

    private void playDefaultVideo(String mTitle, String mUrl) {
        VideoModel videoModel = new VideoModel();
        videoModel.title = mTitle;
        videoModel.videoURL = mUrl;
        playVideoModel(videoModel);
    }

    @Override
    public void onItemClick(int position, final VideoModel videoModel) {
        playVideoModel(videoModel);
    }

    private void playVideoModel(VideoModel videoModel) {
        final SuperPlayerModel superPlayerModelV3 = new SuperPlayerModel();

        if (!TextUtils.isEmpty(videoModel.videoURL)) {
            superPlayerModelV3.title = videoModel.title;
            superPlayerModelV3.url = videoModel.videoURL;
            superPlayerModelV3.qualityName = "原画";

            superPlayerModelV3.multiURLs = new ArrayList<>();
            if (videoModel.multiVideoURLs != null) {
                for (VideoModel.VideoPlayerURL modelURL : videoModel.multiVideoURLs) {
                    superPlayerModelV3.multiURLs.add(new SuperPlayerModel.SuperPlayerURL(modelURL.url, modelURL.title));
                }
            }
        } else if (!TextUtils.isEmpty(videoModel.fileid)) {
            superPlayerModelV3.videoId = new SuperPlayerVideoId();
            superPlayerModelV3.videoId.fileId = videoModel.fileid;
        }
        mSuperPlayerView.playWithModel(superPlayerModelV3);

        //设置内容
        //tvTitle.setText(videoModel.title);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back) {  //悬浮窗播放
            finish();
        }else if (id == R.id.tvPlay){
            playDefaultVideo("测试标题",etInput.getText().toString());
        }
    }


    /**
     * 悬浮窗播放
     */
    private void showFloatWindow() {
        if (mSuperPlayerView.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAYING) {
            mSuperPlayerView.requestPlayMode(SuperPlayerConst.PLAYMODE_FLOAT);
        } else {
            mSuperPlayerView.resetPlayer();
            finish();
        }
    }

    @Override
    public void onStartFullScreenPlay() {
        // 隐藏其他元素实现全屏
        mLayoutTitle.setVisibility(GONE);
        llInput.setVisibility(GONE);
    }

    @Override
    public void onStopFullScreenPlay() {
        // 恢复原有元素
        mLayoutTitle.setVisibility(VISIBLE);
        llInput.setVisibility(VISIBLE);
    }

    @Override
    public void onClickFloatCloseBtn() {
        // 点击悬浮窗关闭按钮，那么结束整个播放
        mSuperPlayerView.resetPlayer();
        finish();
    }

    @Override
    public void onClickSmallReturnBtn() {
        // 点击小窗模式下返回按钮，开始悬浮播放
        showFloatWindow();
    }

    @Override
    public void onStartFloatWindowPlay() {
        // 开始悬浮播放后，直接返回到桌面，进行悬浮播放
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSuperPlayerView.getPlayState() == SuperPlayerConst.PLAYSTATE_PLAYING) {
            mSuperPlayerView.onResume();
            if (mSuperPlayerView.getPlayMode() == SuperPlayerConst.PLAYMODE_FLOAT) {
                mSuperPlayerView.requestPlayMode(SuperPlayerConst.PLAYMODE_WINDOW);
            }
        }
        if (mSuperPlayerView.getPlayMode() == SuperPlayerConst.PLAYMODE_FULLSCREEN) {
            //隐藏虚拟按键，并且全屏
            View decorView = getWindow().getDecorView();
            if (decorView == null) return;
            if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
                decorView.setSystemUiVisibility(View.GONE);
            } else if (Build.VERSION.SDK_INT >= 19) {
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
                decorView.setSystemUiVisibility(uiOptions);
            }
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (mSuperPlayerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
            mSuperPlayerView.onPause();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSuperPlayerView.release();
        if (mSuperPlayerView.getPlayMode() != SuperPlayerConst.PLAYMODE_FLOAT) {
            mSuperPlayerView.resetPlayer();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if (mSuperPlayerView.getPlayMode() == SuperPlayerConst.PLAYMODE_FULLSCREEN){
                mSuperPlayerView.requestPlayMode(SuperPlayerConst.PLAYMODE_WINDOW);
                return false;
            }else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
