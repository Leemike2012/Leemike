package com.image.experiments;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageExperiments {
    public static String path = "D:/Workspace/Images/";
    public static String imageName = "diablo.jpg";
    public static void main(String args[]) throws IOException {
        ImageExperiments ix = new ImageExperiments();
        //1.把RGB三通道保留⼀个,剩下两个分别置0,并查看效果: (R,0,0),(0,G,0),(0,0,B)
        ix.RGBMethod(path+imageName);
        //2.把⼀个彩⾊图⽚转换成灰度图
        ix.ColorToGrey(path+imageName);
        //3.图像按百分⽐缩放
        ix.ImageZoom(path+imageName,0.1);
    }
    public void RGBMethod(String imagePath) throws IOException {
        BufferedImage imageR = ImageIO.read(new File(imagePath));
        //获取图像长宽
        int width = imageR.getWidth();
        int height = imageR.getHeight();
        //遍历所有像素点
        //(R,0,0)实现
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //获取该像素点的R
                int pixel = imageR.getRGB(i, j);
                int r = (pixel >> 16) & 0xff;
                Color color = new Color(r, 0, 0);
                imageR.setRGB(i, j, color.getRGB());
            }
        }
        ImageIO.write(imageR, "png", new File(path+"diablo-R00.png"));
        //(0,G,0)实现
        BufferedImage imageG = ImageIO.read(new File(imagePath));
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //获取该像素点的R
                int pixel = imageG.getRGB(i, j);
                int  g = (pixel >> 8) & 0xff;
                Color color = new Color(0,g, 0);
                imageG.setRGB(i, j, color.getRGB());
            }
        }
        ImageIO.write(imageG, "png", new File(path+"diablo-0G0.png"));
        //(0,0,B)实现
        BufferedImage imageB = ImageIO.read(new File(imagePath));
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //获取该像素点的R
                int pixel = imageB.getRGB(i, j);
                int b = pixel & 0xff;
                Color color = new Color(0,0, b);
                imageB.setRGB(i, j, color.getRGB());
            }
        }
        ImageIO.write(imageB, "png", new File(path+"diablo-00B.png"));
    }
    public void ColorToGrey(String imagePath) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        //获取图像长宽
        int width = image.getWidth();
        int height = image.getHeight();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                // 获得像素的颜色
                int pixel = image.getRGB(i, j);
                int r = (pixel >> 16) & 0xff;
                int g = (pixel >> 8) & 0xff;
                int b = pixel & 0xff;
                int grey = (int) (0.3 * r + 0.59 * g + 0.11 * b);
                Color color = new Color(grey, grey, grey);

                image.setRGB(i,j,color.getRGB());
            }
        }
        ImageIO.write(image, "png", new File(path+"diablo-Grey.png"));
    }
    public void ImageZoom(String imagePath,Double proportion) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        //获取图像长宽
        int width = image.getWidth();
        int height = image.getHeight();
        //获取图片的缩放【宽高都是*了缩放比例的再取整】
        Image scaledInstance = image.getScaledInstance(Double.valueOf(width*proportion).intValue(),Double.valueOf(height * proportion).intValue(),Image.SCALE_DEFAULT);
        //将Image类型转换成BufferedImage对象[BufferedImage.TYPE_INT_ARGB：表示具有8位RGBA颜色成分的整数像素的图像]
        BufferedImage newImage = new BufferedImage(Double.valueOf(width*proportion).intValue(),Double.valueOf(height * proportion).intValue(),BufferedImage.TYPE_INT_ARGB);
        // 一个新的图形上下文，这是这个图形上下文的副本
        Graphics g = newImage.getGraphics();
        // 绘制图片大小
        boolean b = g.drawImage(scaledInstance, 0, 0, null);
        // 释放文件资源
        g.dispose();
        // 将新的图片文件写入到指定的文件夹中
        ImageIO.write(newImage,"png",new File(path+"diablo-zoom.png"));
        System.out.println("缩放图片生成成功！");
    }
}
