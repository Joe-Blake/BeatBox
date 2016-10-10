package com.example.joe.beatbox;

/**
 * 音频信息类
 * Created by joe on 2016/10/10.
 */

public class Sound {
    private String mAssertPath;
    private String mName;
    private Integer mSoundId;   //使用Integer--无值时设null

    public Sound(String assertPath) {
        mAssertPath = assertPath;
        //分离得到文件名并删除.wav后缀
        String[] components = assertPath.split("/");
        String fileName = components[components.length - 1];
        mName = fileName.replace(".maw", "");
    }

    public String getAssertPath() {
        return mAssertPath;
    }

    public String getName() {
        return mName;
    }

    public Integer getSoundId() {
        return mSoundId;
    }

    public void setSoundId(Integer soundId) {
        mSoundId = soundId;
    }
}
