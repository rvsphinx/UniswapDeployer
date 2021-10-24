package com.company;

import com.sun.jna.*;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;

import java.awt.*;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;

public interface dll1 extends Library {
    dll1 INSTANCE = (dll1) Native.loadLibrary("D:\\毕设\\d3dmaster2\\Chapter 15 First Person Camera and Dynamic Indexing\\Chapter 15 First Person Camera and Dynamic Indexing\\CameraAndDynamicIndexing\\x64\\Debug\\CameraAndDynamicIndexing",dll1.class);
    int d3dInitialize(int gameID,Callback videoframeCback,Callback audioframeCback);
    public interface VideoCBack extends Callback {
        public void onEvent(Pointer buffer, int w, int h, int size, int userid);
    }

    public static class VideoCBack_Impl implements VideoCBack {
        public void onEvent(Pointer buffer, int w, int h, int size, int userid) {
            // TODO Auto-generated method stub
            //System.out.println("gggg\n");
            if(userid == 0) {
                Main.data = buffer.getByteArray(0, 1280 * 720 * 4);
                //Main.image.setData(Main.data);
                //DataBufferByte buf = new DataBufferByte(Main.data, Main.data.length);
                //buf.getData() = Main.data;
                Main.image.setData(Raster.createRaster(Main.image.getSampleModel(), new DataBufferByte(Main.data, Main.data.length), new Point()));
                Main.pan.repaint();

                float dt = dll1.INSTANCE.getDeltatime();
                Main.frames[0].setTitle("java-dx : " + " fps: " + (int)(2.0/dt));
            }
            else {
                Main.data2 = buffer.getByteArray(0, 1280 * 720 * 4);
                Main.image2.setData(Raster.createRaster(Main.image2.getSampleModel(), new DataBufferByte(Main.data2, Main.data2.length), new Point()));
                Main.pan2.repaint();
                float dt = dll1.INSTANCE.getDeltatime();
                //Main.frames[1].setTitle("java-dx : " + " fps: " + (int)(2.0/dt));
            }

            // 此处添加需要的数据处理操作
        }
    }
    public interface AudioCBack extends Callback {
        public void onEvent(Pointer buffer, int size, int userid);
    }

    public static class AduioCBack_Impl implements AudioCBack {
        public void onEvent(Pointer buffer, int size, int userid) {
            // TODO Auto-generated method stub

            ;
        }
    }

     void setMouseMotion(int mouseStatus,int mouseX,int mouseY, int userid);//鼠标没有移动也要传，为了每帧获取鼠标状态
    /*
     * MouseStatus(int 0x00 = 左右都弹起状态, 0x01 = 左键按住状态, 0x02 = 右键按住状态, 3同时按住)
     */

     void setMouseClicked(int mouseClickKey, int mouseX, int mouseY, int userid);
    /*
     * MouseClickKey(int 0=没有click事件,1=左按下一瞬间,2=右按下一瞬间,3=左按弹起瞬间,4=右按弹起瞬间)
     */

     void setKeyPressed(int keycode, int userid);
    /*
     * keycode(int=ASCII 键盘XXX字符的按下一瞬间)
     */

     void setPlayerEnter(int id, int frameWidth, int frameHeight, int kbps, int fps, Pointer pUserData);
     void setPlayerQuit(int id);

     float getTotaltime();//获取游戏总时长
     float getDeltatime();//获取帧间隔时间t (fps = 1000/t)
}
