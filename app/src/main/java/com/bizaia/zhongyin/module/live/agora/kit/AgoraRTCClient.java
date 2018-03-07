package com.bizaia.zhongyin.module.live.agora.kit;

import com.bizaia.zhongyin.module.live.agora.MediaManager;
import com.ksyun.media.streamer.util.gles.GLRender;


public class AgoraRTCClient {

    private MediaManager mMediaManager;
    private AgoraRTCIO mIO;

    public AgoraRTCClient(GLRender glRender, MediaManager mediaManager) {
        mMediaManager = mediaManager;
        mIO = new AgoraRTCIO(glRender, mediaManager);
    }

    public void release() {

    }

    public AgoraRTCIO getRTCIO() {
        return mIO;
    }

    public void joinChannel(String channel) {
        enableObserver(true);
        mMediaManager.joinChannel(channel);
    }

    public void leaveChannel() {
        enableObserver(false);
        mMediaManager.leaveChannel();
    }

    public void enableObserver(boolean enable) {
        mIO.enableObserver(enable);
    }
}
