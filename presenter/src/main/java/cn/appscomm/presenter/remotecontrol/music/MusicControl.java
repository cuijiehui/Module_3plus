package cn.appscomm.presenter.remotecontrol.music;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.text.TextUtils;

import cn.appscomm.presenter.PresenterAppContext;
import cn.appscomm.presenter.remotecontrol.RemoteControlManager;
import cn.appscomm.presenter.util.LogUtil;

/**
 * 作者：hsh
 * 日期：2017/4/26
 * 说明：
 */
public enum MusicControl {
    INSTANCE;

    private static final String TAG = MusicControl.class.getSimpleName();
    private Context context = PresenterAppContext.INSTANCE.getContext();
    private long lastSendTimeStamp = 0L;
    private AudioManager mAudioManager;
    private Intent intent;
    private boolean playing = false;                                                                // 是否播放音乐
    private String songName = "";                                                                   // 歌曲名
    private boolean isSendSongName = false;                                                         // 是否需要发送歌名

    public void init() {
        registerMusicReceiver();
        mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        intent = new Intent("com.android.music.musicservicecommand");
    }

    /**
     * 注册音乐控制接收者
     */
    private void registerMusicReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.android.music.metachanged");
        intentFilter.addAction("com.android.music.queuechanged");
        intentFilter.addAction("com.android.music.playbackcomplete");
        intentFilter.addAction("com.android.music.playstatechanged");
//        intentFilter.addAction("com.android.music.musicservicecommand");
        context.registerReceiver(musicControlBroadcastReceiver, intentFilter);
    }

    // 检查音乐状态
    public void checkMusicStatus() {
        playing = mAudioManager.isMusicActive();
        if (TextUtils.isEmpty(songName)) {                                                          // 播放器没打开
            if (playing) {
                pauseSong();
//                playSong();
            } else {
                RemoteControlManager.INSTANCE.sendStop();
            }
        } else {                                                                                    // 因为不知道播放器的状态，所以把音乐打开
            isSendSongName = true;
            sendSongName();
        }
    }

    // 播放
    public void playSong() {
        LogUtil.i(TAG, "播放音乐...");
//         mAudioManager.abandonAudioFocus(mListener);
        isSendSongName = true;
        if (playing && !TextUtils.isEmpty(songName)) {
            LogUtil.i(TAG, "播放器目前的状态是:播放，可能发送广播播放无效，现在单独发送播放状态...!!!");
            sendSongName();
        }
        intent.putExtra("command", "playSong");
        LogUtil.i(TAG, "设备控制--->播放");
        context.sendBroadcast(intent);
    }

    // 暂停
    public void pauseSong() {
        LogUtil.i(TAG, "暂停音乐...");
        // mAudioManager.requestAudioFocus(mListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        isSendSongName = true;
        if (!playing && songName != "") {
            LogUtil.i(TAG, "播放器目前的状态是:暂停，可能发送广播暂停无效，现在单独发送暂停状态...!!!");
            sendSongName();
        }
        intent.putExtra("command", "pauseSong");
        LogUtil.i(TAG, "设备控制--->暂停");
        context.sendBroadcast(intent);
    }

    // 上一首
    public void preSong() {
        isSendSongName = true;
        intent.putExtra("command", "previous");
        LogUtil.i(TAG, "设备控制--->上一首");
        context.sendBroadcast(intent);
    }

    // 下一首
    public void nextSong() {
        isSendSongName = true;
        intent.putExtra("command", "nextSong");
        LogUtil.i(TAG, "设备控制--->下一首");
        context.sendBroadcast(intent);
    }

    /**
     * 这个是适配谷歌音乐的
     *
     * @param intent
     */
    private void playMusic2(Intent intent) {
        String track = intent.getStringExtra("track");                                              // 歌曲名
        boolean playFlag = intent.getBooleanExtra("playing", false);                                // 播放状态 不准确
        int listPosition = intent.getIntExtra("listPosition", 0);                                   // 第几首
//        LogUtil.i(TAG, "接收到广播，广播内容:" + intent.getExtras().toString());

        if (TextUtils.isEmpty(track)) {
            return;
        } else {
            isSendSongName = true;
            playing = playFlag;
            songName = track;
            sendSongName();
        }
    }

    /**
     * 发送歌曲名
     */
    private void sendSongName() {
        if (isSendSongName && Math.abs(System.currentTimeMillis() - lastSendTimeStamp) > 500) {
            lastSendTimeStamp = System.currentTimeMillis();
            isSendSongName = false;
            LogUtil.i(TAG, "发送歌曲名 : " + songName + " 歌名长度 : " + songName.getBytes().length + " isplaying : " + playing);
            RemoteControlManager.INSTANCE.sendSongName(playing, songName);
        }
    }

    /**
     * 音乐控制接收者
     */
    private BroadcastReceiver musicControlBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                playMusic2(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private class MyOnAudioFocusChangeListener implements AudioManager.OnAudioFocusChangeListener {
        @Override
        public void onAudioFocusChange(int focusChange) {
        }
    }
}
