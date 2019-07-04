package com.yunze.vehiclemanagement.util;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

/**
 * 文件上传工具类
 *
 * @author lhw
 *
 */
public class UploadUtil {
    /**
     * 文件上传（上传到tomcat临时文件夹）
     * @param request
     * @param files  需要上传的文件
     * @return 返回上传后的路径（单文件返回单文件路径，多文件返回路径集合）
     */
    public static List<String> uploadfiles( HttpServletRequest request, MultipartFile... files) {
        List<String> paths = new ArrayList<>();

//        String path = request.getContextPath()+"/upload/"; //项目所在路径的盘符根目录(如 E：)
        // tomcat临时文件夹路径（注意：一定要使用绝对路径），默认10天后删除。
        /**
         * springboot内置tomcat有一个特点，每启动一次会重新生成一个临时文件夹存储文件，
         * 会导致下次重启之后，会找不到上次存储的文件（临时文件夹不同）。如果上传的文件
         * 是上传到临时文件夹中，那么，下次重启就会发现网页上的图片都挂了
         */
        String path = request.getServletContext().getRealPath("/")+ "upload/";
        try {
            //上传路径
            File dir = new File(path);
            if (!dir.exists()){
                dir.mkdirs();
            }
            if (files != null && files.length>1){
                //多文件上传
                for (int i = 0; i < files.length; i++) {
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    //创建文件
                    File targetFile = new File(path,files[i].getOriginalFilename());
                    // MultipartFile自带的解析方法
                    files[i].transferTo(targetFile);
                    paths.add(path+targetFile.getName());
                }
            }else {//单文件上传
                //创建文件
                File targetFile = new File(path,files[0].getOriginalFilename());
//                System.out.println("文件绝对路径 ："+targetFile.getAbsolutePath());
                files[0].transferTo(targetFile);
                paths.add(path+files[0].getOriginalFilename());
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return paths;
    }


    /**
     * 文件上传（上传至自定义本地绝对路径）
     * @param location 自定义文件上传绝对路径
     * @param urlMapping 映射路径
     * @param files
     * @return
     */
    public static List<String> uploadfiles( String location, String urlMapping, MultipartFile... files) {
        List<String> paths = new ArrayList<>();
        // 去掉映射路径后面的"**"
        urlMapping = urlMapping.substring(0,urlMapping.indexOf("*"));

//        String path = request.getContextPath()+"/upload/"; //项目所在路径的盘符根目录(如 E：)
        // tomcat临时文件夹路径（注意：一定要使用绝对路径），默认10天后删除。
        /**
         * springboot内置tomcat有一个特点，每启动一次会重新生成一个临时文件夹存储文件，
         * 会导致下次重启之后，会找不到上次存储的文件（临时文件夹不同）。如果上传的文件
         * 是上传到临时文件夹中，那么，下次重启就会发现网页上的图片都挂了
         */
        try {
            //上传路径
            File dir = new File(location);
            if (!dir.exists()){
                dir.mkdirs();
            }
            if (files != null && files.length>1){
                //多文件上传
                for (int i = 0; i < files.length; i++) {
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    //创建文件
                    File targetFile = new File(location,files[i].getOriginalFilename());
                    // MultipartFile自带的解析方法
                    files[i].transferTo(targetFile);
                    paths.add(urlMapping+targetFile.getName());
                }
            }else {//单文件上传
                //创建文件
                File targetFile = new File(location,files[0].getOriginalFilename());
//                System.out.println("文件绝对路径 ："+targetFile.getAbsolutePath());
                files[0].transferTo(targetFile);
                paths.add(urlMapping+files[0].getOriginalFilename());
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return paths;
    }

    /**
     * 上传文件到ftp服务器的方法
     * @param file 要上传的文件
     * @param path 上传的服务器目录
     * @return  返回上传到服务器之后的文件名
     */
    public static String uploadFile(MultipartFile file, String path) {
        //得到上传时提交的文件名
        String fileName = file.getOriginalFilename();
        //扩展名
        String fileExtensionName = fileName.substring(fileName.lastIndexOf( ".")+1);
        //上传到服务器的文件名
        String uploadFileName = UUID.randomUUID().toString().replaceAll("-","")+"."+fileExtensionName;

        //创建目录(在项目所在服务器的项目路径下创建的path目录)
        File fileDir = new File(path);
        if(!fileDir.exists()){
            //设置文件可写
            fileDir.setWritable(true);
            fileDir.mkdirs();
        }
        //创建文件
        File targetFile = new File(path,uploadFileName);

        try {
            //上传文件
            file.transferTo(targetFile);
//            List<File> fileList = new ArrayList<File>();
//            fileList.add(targetFile);
//            //将targetFile上传到FTP服务器
//            FTPUtil.uploadFile(fileList);
//            //上传到FTP服务器之后，删除tomcat服务器上的文件
//            targetFile.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return targetFile.getName();
    }
}