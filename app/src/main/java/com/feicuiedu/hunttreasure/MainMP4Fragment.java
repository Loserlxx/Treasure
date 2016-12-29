package com.feicuiedu.hunttreasure;


import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feicuiedu.hunttreasure.commons.ActivityUtils;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * 这里我们主要是进行视频的播放
 */
public class MainMP4Fragment extends Fragment implements TextureView.SurfaceTextureListener {

    private TextureView mTextureView;
    ActivityUtils activityUtils=new ActivityUtils(this);
    private MediaPlayer mediaPlayer;

    /**
     * 1.使用MediaPlayer来进行播放视频
     * 2.展示视频播放：SurfaceView（单独窗体），我们在这里使用的是TextureView
     * 3.使用TextureView :需要使用的是SurfaceTexture：使用这个来渲染、呈现你要播放的内容
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //fragment全屏显示播放视频的控件
        mTextureView = new TextureView(getContext());
        return mTextureView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //设置监听:因为播放显示内容需要SurfaceTexture ，所以设置一个监听，看SurfaceTexture又没用准备好，有没有变化
        mTextureView.setSurfaceTextureListener(this);
    }

    /**
     * 确实已经准备好了， 可以播放了
     * @param surface
     * @param i
     * @param i1
     */
    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surface, int i, int i1) {
        /**
         * 1.播放，找到我们播放的资源
         * 2.可以使用播放了MediaPlayer进行播放
         *      创建、设置播放的资源、设置播放的一个同意不等。。
         *      MediaPlayer 有没有准备好，好了就直接播放，没有准备好的就报出异常
         * 3.页面销毁了：关闭MediaPlayer资源释放掉。。。
         */

        try {
            //打开播放的资源文件。获取到文件目录。将文件添加进来
            AssetFileDescriptor openFd=getContext().getAssets().openFd("welcome.mp4");
            //拿到MediaPlayer 需要的资源类型
            FileDescriptor fileDescriptor = openFd.getFileDescriptor();
            //创建出来MediaPlayer
            mediaPlayer = new MediaPlayer();
            //可以播放本地，或是网络的视频
            //设置播放的资源给MediaPlayer  openFd.getStartOffset()开始多少，openFd.getLength()资源文件大小
            mediaPlayer.setDataSource(fileDescriptor,openFd.getStartOffset(),openFd.getLength());
            //防止阻塞线程
            mediaPlayer.prepareAsync();//异步
            //开始播放，监听是否准备好
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                //确实准备好了，开始播放吧
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    Surface mSurface = new Surface(surface);
                    //设置一下展示
                    mediaPlayer.setSurface(mSurface);
                    //循环播放
                    mediaPlayer.setLooping(true);
                    //再次播放从0开始
                    mediaPlayer.seekTo(0);
                    //开始播放
                    mediaPlayer.start();
                }
            });
        } catch (IOException e) {
            activityUtils.showToast("媒体文件播放失败了");
        }
    }

    /**
     *
     * @param surfaceTexture
     * @param i
     * @param i1
     */
    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }

    /**
     * 优化释放资源
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer !=null){
            mediaPlayer.release();
            //判为空，等待回收
            mediaPlayer=null;
        }
    }
}
