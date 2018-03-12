package com.hnkeystone.rxandroid.library.upload;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

public class ProgressRequestBody extends RequestBody {
    public static final int UPDATE = 0x01;
    private RequestBody requestBody;
    private ProgressListener mListener;
    private BufferedSink bufferedSink;
    private MyHandler myHandler;

    public ProgressRequestBody(RequestBody body, ProgressListener listener) {
        requestBody = body;
        mListener = listener;
        if (myHandler == null) {
            myHandler = new MyHandler();
        }
    }

    class MyHandler extends Handler {

        public MyHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE:
                    ProgressModel progressModel = (ProgressModel) msg.obj;
                    if (mListener != null)
                        mListener.onProgress(progressModel.getCurrentBytes(), progressModel.getContentLength(), progressModel.isDone());
                    break;
            }
        }
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        CountingSink countingSink = new CountingSink(sink);
        if (bufferedSink == null) {
            bufferedSink = Okio.buffer(countingSink);
        }
        //写入
        requestBody.writeTo(bufferedSink);
        //刷新
        bufferedSink.flush();
    }

    protected final class CountingSink extends ForwardingSink {
        //当前写入字节数
        long bytesWritten = 0L;
        //总字节长度，避免多次调用contentLength()方法
        long contentLength = 0L;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            if (contentLength == 0) {
                //获得contentLength的值，后续不再调用
                contentLength = contentLength();
            }
            //增加当前写入的字节数
            bytesWritten += byteCount;
            //回调
            Message msg = Message.obtain();
            msg.what = UPDATE;
            msg.obj = new ProgressModel(bytesWritten, contentLength, bytesWritten == contentLength);
            myHandler.sendMessage(msg);
        }
    }
}