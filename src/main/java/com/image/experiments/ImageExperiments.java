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
        //4-1.图像左右翻转
        ix.MirrorImage(path+imageName);
        //4-2.图像上下翻转
        ix.ReverseImage(path+imageName);
        //5.把彩⾊图的某个区域的透明度调高
        ix.ImageOpacity(path+imageName,50);
        //6.3x3,5x5,7x7模糊
        //ix.Fuzzy(path+imageName,5);
        //7.多张图片拼接
        String[] fileList = new String[]{path+"diablo-R00.png",path+"diablo-0G0.png",path+"diablo-00B.png"};
        ix.MergeImage(fileList,2);
        //8.遍历图像, 使⽤kernel来计算像素值,代替⽬标像素点
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
        ImageIO.write(newImage,"png",new File(path+"zelda-zoom.png"));
        System.out.println("缩放图片生成成功！");
    }


    public void MirrorImage(String imagePath) throws IOException {
        BufferedImage imageV = ImageIO.read(new File(imagePath));
        //获取图像长宽
        int width = imageV.getWidth();
        int height = imageV.getHeight();
        for (int i = 0; i < height; i++) {
            for (int j = 0, b = width - 1; j < b; j++, b--) {
                int pl = imageV.getRGB(j, i);
                int pr = imageV.getRGB(b, i);
                imageV.setRGB(j, i, pr);
                imageV.setRGB(b, i, pl);  
            }
        }  
        ImageIO.write(imageV, "png", new File(path+"diablo-LRreverse.png"));
    }
    public void ReverseImage(String imagePath) throws IOException {
        BufferedImage imageRe = ImageIO.read(new File(imagePath));
        //获取图像长宽
        int width = imageRe.getWidth();
        int height = imageRe.getHeight();
        for (int i = 0; i < width; i++) {
            for (int t = 0, b = height - 1;t < b; t++, b--) {
                int pt = imageRe.getRGB(i, t);
                int pb = imageRe.getRGB(i, b);
                imageRe.setRGB(i, t, pb);
                imageRe.setRGB(i, b, pt); 
            }
        } 
        ImageIO.write(imageRe, "png", new File(path+"diablo-UDreverse.png"));
    }

    public void ImageOpacity(String imagePath,int opacityValue) throws IOException {
        BufferedImage image = ImageIO.read(new File(imagePath));
        //获取图像长宽
        int width = image.getWidth();
        int height = image.getHeight();

        //透明图片文件
        BufferedImage output = new BufferedImage(width ,height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = output.createGraphics();
        output = g2.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2.dispose();
        g2 = output.createGraphics();


        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                int rgb = image.getRGB(i, j);
                Color color = new Color(rgb);
                Color newcolor = new Color(color.getRed(), color.getGreen(),color.getBlue(), opacityValue);
                output.setRGB(i,j,newcolor.getRGB());
            }
        }
        g2.setComposite(AlphaComposite.SrcIn);
        g2.drawImage(image, 0, 0, width,height, null);
        g2.dispose();
        ImageIO.write(output, "png", new File(path+"diablo-Opacity.png"));
    }


    

    public void MergeImage(String[] fileList,int type){
        int len = fileList.length;
        if (len < 1) {
            throw new RuntimeException("图片数量小于1");
        }
        File[] src = new File[len];
        BufferedImage[] images = new BufferedImage[len];
        int[][] ImageArrays = new int[len][];
        for (int i = 0; i < len; i++) {
            try {
                src[i] = new File(fileList[i]);
                images[i] = ImageIO.read(src[i]);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            int width = images[i].getWidth();
            int height = images[i].getHeight();
            ImageArrays[i] = new int[width * height];
            ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
        }
        int newHeight = 0;
        int newWidth = 0;
        for (int i = 0; i < images.length; i++) {
            // 横向
            if (type == 1) {
                newHeight = newHeight > images[i].getHeight() ? newHeight : images[i].getHeight();
                newWidth += images[i].getWidth();
            } else if (type == 2) {// 纵向
                newWidth = newWidth > images[i].getWidth() ? newWidth : images[i].getWidth();
                newHeight += images[i].getHeight();
            }
        }
        if (type == 1 && newWidth < 1) {
            return;
        }
        if (type == 2 && newHeight < 1) {
            return;
        }

        // 生成新图片
        try {
            BufferedImage ImageNew = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
            int height_i = 0;
            int width_i = 0;
            for (int i = 0; i < images.length; i++) {
                if (type == 1) {
                    ImageNew.setRGB(width_i, 0, images[i].getWidth(), newHeight, ImageArrays[i], 0,
                            images[i].getWidth());
                    width_i += images[i].getWidth();
                } else if (type == 2) {
                    ImageNew.setRGB(0, height_i, newWidth, images[i].getHeight(), ImageArrays[i], 0, newWidth);
                    height_i += images[i].getHeight();
                }
            }
            //输出想要的图片
            ImageIO.write(ImageNew, "png", new File(path+"diablo-merge.png"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

    
