//package com.kidd.test.file;
//
//import it.sauronsoftware.jave.*;
//import lombok.extern.slf4j.Slf4j;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.nio.channels.FileChannel;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @description 读文件、转换类型
// * <p>
// * mvn install:install-file -Dfile=jave-1.0.2.jar -DgroupId=com.jave -DartifactId=jave -Dversion=1.0.2 -Dpackaging=jar
// * @auth chaijd
// * @date 2021/12/24
// */
//@Slf4j
//public class FileReaderAndWriter {
//
//    public void fileReader() throws Exception {
//
//        File file = new File("D:\\workspace\\github\\kidd\\src\\main\\webapp\\D3680.exe");
//
//        FileInputStream fin = new FileInputStream(file);
//        byte b[] = new byte[(int) file.length()];
//        fin.read(b);
//
//        File nf = new File("D:\\workspace\\github\\kidd\\src\\main\\webapp\\D3680.mp4");
//        FileOutputStream fw = new FileOutputStream(nf);
//        fw.write(b);
//        fw.flush();
//        fw.close();
//
//    }
//
//    private static Map<String, Object> getVoideMsg(String path) {
//        Map<String, Object> map = new HashMap<>();
//        File file = new File(path);
//        Encoder encoder = new Encoder();
//        FileChannel fc = null;
//        String size ="";
//
//
//        if(file==null){
//            return map;
//        }
//        try {
//            MultimediaInfo m = encoder.getInfo(file);
//        }catch (Exception e){
//
//        }finally {
//            if(fc!=null){
//                try {
//                    fc.close();
//                }catch (Exception e){
//                    log.error("Exception",e);
//                }
//            }
//        }
//        return map;
//    }
//
//
//    private void fileChange(){
//
////        {
////            File source = new File("D:\\workspace\\github\\kidd\\src\\main\\webapp\\D3680.exe");
////            File target = new File("D:\\workspace\\github\\kidd\\src\\main\\webapp\\D3680.mp4");
////            AudioAttributes audio = new AudioAttributes();
////            audio.setCodec("libmp3lame");
////            audio.setBitRate(new Integer(64000));
////            audio.setChannels(new Integer(1));
////            audio.setSamplingRate(new Integer(22050));
////            VideoAttributes video = new VideoAttributes();
////            video.setCodec("flv");
////            video.setBitRate(new Integer(160000));
////            video.setFrameRate(new Integer(15));
////            video.setSize(new VideoSize(400, 300));
////            EncodingAttributes attrs = new EncodingAttributes();
////            attrs.setFormat("flv");
////            attrs.setAudioAttributes(audio);
////            attrs.setVideoAttributes(video);
////            Encoder encoder = new Encoder();
////            encoder.encode(source, target, attrs);
////        }
//
//        {
//            File source = new File("D:\\\\workspace\\\\github\\\\kidd\\\\src\\\\main\\\\webapp\\\\D3680.exe");
//            File target = new File("D:\\\\workspace\\\\github\\\\kidd\\\\src\\\\main\\\\webapp\\\\D3680.avi");// 转MP4
//
//            System.out.println(source.length());
//            AudioAttributes audio = new AudioAttributes();
//            audio.setCodec("libmp3lame");
//            audio.setBitRate(new Integer(64000));
//            audio.setChannels(new Integer(1));
//            audio.setSamplingRate(new Integer(22050));
//            VideoAttributes video = new VideoAttributes();
//            video.setCodec("libxvid");// 转MP4
//            video.setBitRate(new Integer(180000));// 180kb/s比特率
//            video.setFrameRate(new Integer(1));// 1f/s帧频，1是目前测试比较清楚的，越大越模糊
//            EncodingAttributes attrs = new EncodingAttributes();
//            attrs.setFormat("avi");// 转MP4
////            attrs.setFormat("exe");// 转MP4
////            attrs.setFormat("swf");// 转MP4
//            attrs.setAudioAttributes(audio);
//            attrs.setVideoAttributes(video);
//
//
//            Encoder encoder = new Encoder();
//            long beginTime = System.currentTimeMillis();
//            try {
//                // 获取时长
//                MultimediaInfo m = encoder.getInfo(source);
//                System.out.println(m.getDuration());
//                System.out.println("获取时长花费时间是：" + (System.currentTimeMillis() - beginTime));
//                beginTime = System.currentTimeMillis();
//                encoder.encode(source, target, attrs);
//                System.out.println("视频转码花费时间是：" + (System.currentTimeMillis() - beginTime));
//            } catch (IllegalArgumentException e) {
//                e.printStackTrace();
//            } catch (InputFormatException e) {
//                e.printStackTrace();
//            } catch (EncoderException e) {
//                e.printStackTrace();
//            }
//        }
//
//    }
//
//
//    public static void main(String[] args) {
//        try {
//            //new FileReaderAndWriter().fileReader();
//            new FileReaderAndWriter().fileChange();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//}
