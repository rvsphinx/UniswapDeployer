package com.company;

import com.sun.jna.*;
import com.sun.jna.ptr.IntByReference;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.util.Date;

import javax.swing.*;
//自定义画布

public class Main{
    static private int NOWQQ = 0;
    static private int preX = -1;
    static private int preY = -1;
    static private int mouseSta = 0;
    static String imgPath = "D://out.jpg";
    static BufferedImage image = new BufferedImage(1280, 720, BufferedImage.TYPE_4BYTE_ABGR);
    static BufferedImage image2 = new BufferedImage(1280, 720, BufferedImage.TYPE_4BYTE_ABGR);
    static byte[] data = new byte[1280*720*4];
    static byte[] data2 = new byte[1280*720*4];
    static JP pan = new JP();
    static JP2 pan2 = new JP2();
    static JFrame frames[] = {new JFrame("java-dx"),new JFrame("java-dx")};
     static class JP extends JPanel {
        @Override
        public void paint(Graphics g) {
            g.drawImage(image, 0, 0, null);
        }
    }
    static class JP2 extends JPanel {
        @Override
        public void paint(Graphics g) {
            g.drawImage(image2, 0, 0, null);
        }
    }

    public static void main(String[] args) {


        frames[0].setDefaultCloseOperation(frames[0].EXIT_ON_CLOSE);
        frames[1].setDefaultCloseOperation(frames[0].EXIT_ON_CLOSE);
        for(int t=0;t<1;t++) {
        int i = 0;
            frames[i].add(pan);
            frames[i].pack();
            frames[i].setVisible(true);
            frames[i].setSize(1280, 720);
            // 监听鼠标称动事件
            pan.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if(!frames[0].isFocusOwner()) return;
                    dll1.INSTANCE.setMouseMotion(1, e.getX(), e.getY(),0);
                    float dt = dll1.INSTANCE.getDeltatime();
                    Main.frames[0].setTitle("java-dx : " + " fps: " + (int)(1.0/dt));
                    //System.out.println("0000moved!" + e.getX() + " , " + e.getY() + "\n");

                }
            });

            frames[i].addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(!frames[0].isFocusOwner()) return;
                    int key = e.getKeyCode();
                    dll1.INSTANCE.setKeyPressed(key,0);
                }
            });
            pan.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if(!frames[0].isFocusOwner()) return;
                    // TODO Auto-generated method stub
                    super.mouseReleased(e);
                    dll1.INSTANCE.setMouseClicked(0x03, e.getX(), e.getY(),0);
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    if(!frames[0].isFocusOwner()) return;
                    // TODO Auto-generated method stub
                    super.mouseClicked(e);
                    dll1.INSTANCE.setMouseClicked(0x01, e.getX(), e.getY(),1);

                }
            });

        i = 1;
        frames[i].add(pan2);
        frames[i].pack();
        frames[i].setVisible(true);
        frames[i].setSize(1280, 720);
        // 监听鼠标称动事件
            pan2.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(!frames[1].isFocusOwner()) return;
                dll1.INSTANCE.setMouseMotion(1, e.getX(), e.getY(), 1);
                System.out.println("moved!" + e.getX() + " , " + e.getY() + "\n");

            }
        });

        frames[i].addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(!frames[1].isFocusOwner()) return;
                int key = e.getKeyCode();
                dll1.INSTANCE.setKeyPressed(key,1);
            }
        });

            pan2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(!frames[1].isFocusOwner()) return;
                // TODO Auto-generated method stub
                super.mouseReleased(e);
                mouseSta = 0;
                dll1.INSTANCE.setMouseClicked(0x03, e.getX(), e.getY(),1);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(!frames[1].isFocusOwner()) return;

                // TODO Auto-generated method stub
                super.mouseClicked(e);
                dll1.INSTANCE.setMouseClicked(0x01, e.getX(), e.getY(), 1);

            }
        });
        }

        int times = 0;
        Date date1 = new Date();
        long gtime = date1.getTime();

        dll1.VideoCBack vcb = new dll1.VideoCBack_Impl();
        dll1.AudioCBack acb = new dll1.AduioCBack_Impl();

        dll1.INSTANCE.d3dInitialize(0,vcb,acb);

        dll1.INSTANCE.setPlayerEnter(0,0,0,0,0,null);
        dll1.INSTANCE.setPlayerEnter(1,0,0,0,0,null);

    }
}
