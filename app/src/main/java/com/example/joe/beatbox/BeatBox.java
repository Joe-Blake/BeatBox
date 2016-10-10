package com.example.joe.beatbox;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 资源管理类
 * Created by joe on 2016/10/10.
 */

public class BeatBox {

    private static final String TAG = "BeatBox";
    private static final String SOUNDS_FOLDER = "sounds";

    private static final int MAX_SOUNDS = 5;

    private AssetManager mAssets;                       //AssetManager
    private List<Sound> mSounds = new ArrayList<>();    //音频列表
    private SoundPool mSoundPool;                       //音效池

    public BeatBox(Context context) {
        mAssets = context.getAssets();

        /**
         * 最大同时播放数;
         * 音频流类型;
         * 采样率转换(无用)
         * 兼容API 16
         */
        mSoundPool = new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        //mSoundPool = new SoundPool.Builder();
        loadSounds();
    }

    /**
     * 加载音频文件
     */
    private void loadSounds() {
        String[] soundNames;
        try {
            //获取所有文件名
            soundNames = mAssets.list(SOUNDS_FOLDER);
            Log.i(TAG, "Found " + soundNames.length + " sounds");
        } catch (IOException e) {
            Log.e(TAG,"Couldn't list assets", e);
            return;
        }
        //比那里添加sound
        for (String fileName : soundNames) {
            String assetPath = SOUNDS_FOLDER + "/" + fileName;
            Sound sound = new Sound(assetPath);
            try {
                load(sound);
            } catch (IOException e) {
                Log.e(TAG, "无法加载" + fileName, e);
            }
            mSounds.add(sound);
        }
    }

    public List<Sound> getSounds() {
        return mSounds;
    }

    /**
     * 加载音频资源
     * @param sound
     * @throws IOException
     */
    public void load(Sound sound) throws IOException {
        AssetFileDescriptor afd = mAssets.openFd(sound.getAssertPath());
        int soundId = mSoundPool.load(afd, 1);//优先级,目前无影响
        sound.setSoundId(soundId);
    }

    /**
     * 播放
     * @param sound
     */
    public void play(Sound sound) {
        Integer soundId = sound.getSoundId();
        if (soundId == null) {
            return;
        }
        /**
         * params id,左音量,右音量,优先级(无效),是否循环(无限循环 -1),播放速率
         * 最大音量,常速播放
         */
        mSoundPool.play(soundId, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    /**
     * 释放音效池
     */
    public void release() {
        mSoundPool.release();
    }

}
