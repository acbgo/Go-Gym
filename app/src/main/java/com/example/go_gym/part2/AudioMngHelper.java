package com.example.go_gym.part2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.RequiresApi;

public class AudioMngHelper {
    private static final String TAG = "Main_AudioMngHelper";

    private AudioManager _AudioManager;
    private Context _Context;

    private static final String ACTION_VOLUME_CHANGED = "android.media.VOLUME_CHANGED_ACTION";
    private static final String EXTRA_VOLUME_STREAM_TYPE = "android.media.EXTRA_VOLUME_STREAM_TYPE";

    private MyVolumeReceiver mVolumeReceiver;

    public AudioMngHelper(Context context){

        _Context = context;
        _AudioManager =(AudioManager) _Context.getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * // 通话音量
     * @return
     */
    public int GetCallVoiceMax(){

        return  _AudioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL) ;
    }

    /**
     * // 通话音量
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public int GetCallVoiceMin(){

        return  _AudioManager.getStreamMinVolume(AudioManager.STREAM_VOICE_CALL) ;
    }

    /**
     * // 通话音量
     * @return
     */
    public int GetCallVoiceCurrentValue(){

        return  _AudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL) ;
    }



    /**
     * // 系统音量
     * @return
     */
    public int GetSystemVoiceMax(){

        return  _AudioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM) ;
    }

    /**
     * // 系统音量
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public int GetSystemVoiceMin(){

        return  _AudioManager.getStreamMinVolume(AudioManager.STREAM_SYSTEM) ;
    }

    /**
     * // 系统音量
     * @return
     */
    public int GetSystemVoiceCurrentValue(){

        return  _AudioManager.getStreamVolume(AudioManager.STREAM_SYSTEM) ;
    }


    /**
     * // 铃声音量
     * @return
     */
    public int GetRingVoiceMax(){

        return  _AudioManager.getStreamMaxVolume(AudioManager.STREAM_RING) ;
    }

    /**
     * // 铃声音量
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public int GetRingVoiceMin(){

        return  _AudioManager.getStreamMinVolume(AudioManager.STREAM_RING) ;
    }

    /**
     * // 铃声音量
     * @return
     */
    public int GetRingVoiceCurrentValue(){

        return  _AudioManager.getStreamVolume(AudioManager.STREAM_RING) ;
    }


    /**
     * // 音乐音量
     * @return
     */
    public int GetMusicVoiceMax(){

        return  _AudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC) ;
    }

    /**
     * // 音乐音量
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public int GetMusicVoiceMin(){

        return  _AudioManager.getStreamMinVolume(AudioManager.STREAM_MUSIC) ;
    }

    /**
     * // 音乐音量
     * @return
     */
    public int GetMusicVoiceCurrentValue(){

        return  _AudioManager.getStreamVolume(AudioManager.STREAM_MUSIC) ;
    }

    /**
     * 增加音乐音量
     * @param value
     */
    public void AddMusicVoiceVolumn(int value){
        int addValue = (GetMusicVoiceCurrentValue() + value) ;
        // 防止音量值越界
        addValue = addValue > GetMusicVoiceMax() ?GetMusicVoiceMax():addValue;
        _AudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,addValue,AudioManager.FLAG_PLAY_SOUND);
    }

    /**
     * 减少音乐音量
     * @param value
     */
    @RequiresApi(api = Build.VERSION_CODES.P)
    public void ReduceMusicVoiceVolumn(int value){
        int reduceValue = (GetMusicVoiceCurrentValue() - value) ;
        // 防止音量值越界
        reduceValue = reduceValue < GetMusicVoiceMin() ?GetMusicVoiceMin():reduceValue;
        _AudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,reduceValue,AudioManager.FLAG_PLAY_SOUND);
    }

    /**
     * 注册广播监听
     */
    public void registerVolumeReceiver() {
        mVolumeReceiver = new MyVolumeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        _Context.registerReceiver(mVolumeReceiver, filter);
    }

    /**
     * 取消注册广播监听
     */
    public void unregisterVolumeReceiver() {

        if (mVolumeReceiver != null) _Context.unregisterReceiver(mVolumeReceiver);

    }


    /**
     * 音量变化广播类
     */
    private class MyVolumeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (isReceiveVolumeChange(intent) == true) {
                int currVolume = _AudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                Toast.makeText(context, currVolume + " ", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "onReceive:isReceiveVolumeChange currVolume "+currVolume);
            }
        }
    }

    /**
     * 判断是否是音乐音量变化（音量键改变的音量）
     * @param intent
     * @return
     */
    private boolean isReceiveVolumeChange(Intent intent) {
        return intent.getAction() != null
                && intent.getAction().equals(ACTION_VOLUME_CHANGED)
                && intent.getIntExtra(EXTRA_VOLUME_STREAM_TYPE, -1) == AudioManager.STREAM_MUSIC
                ;
    }

}
