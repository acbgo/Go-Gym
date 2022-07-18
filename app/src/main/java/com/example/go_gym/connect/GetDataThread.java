package com.example.go_gym.connect;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

public class GetDataThread extends Thread{
    private final InputStream myInStream;
    private final Handler myHandler;

    public GetDataThread(BluetoothSocket socket, Handler handler) {
        InputStream tmpIn = null;
        myHandler = handler;
        // 使用临时对象获取输入和输出流，因为成员流是最终的
        try {
            tmpIn = socket.getInputStream();
        } catch (IOException e) { }
        myInStream = tmpIn;
    }


    public void run() {
        byte[] buffer = new byte[1024];  // 用于流的缓冲存储
        int bytes; // 从read()返回bytes

        // 持续监听InputStream，直到出现异常
        while (true) {
            try {
                // 从InputStream读取数据
                bytes = myInStream.read(buffer);
                // 将获得的bytes发送到UI层activity
                if( bytes >0) {
                    Message message = myHandler.obtainMessage(Constant.MSG_GOT_DATA, new String(buffer, 0, bytes, "utf-8"));
                    myHandler.sendMessage(message);
                }
                Log.d("GOT MSG", "message size" + bytes);
            } catch (IOException e) {
                myHandler.sendMessage(myHandler.obtainMessage(Constant.MSG_ERROR, e));
                break;
            }
        }
    }
}
