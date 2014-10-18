package com.peaceful.auth.util;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: chaohua.luo
 * Date: 12-4-9
 * Time: 下午2:28
 * To change this template use File | Settings | File Templates.
 */
public class PropertyUtil {

    private static final Logger LOG = LoggerFactory.getLogger(PropertyUtil.class);

    private static Properties property = new Properties();
    private static Properties emailPro=new Properties();

    /**
     * 加载配置文件数据
     * @param propertyFile, 配置文件路径
     * @return
     */
    public static Properties load(String propertyFile) {
        InputStream is = PropertyUtil.class.getResourceAsStream("/" + propertyFile);
        try {
            property.load(is);
        } catch (IOException e) {
            LOG.error("无法载入配置文件{}",  propertyFile, e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOG.warn("配置文件{}输入流关闭异常", propertyFile, e);
                }
            }
        }

        return property;
    }

    /**
     * 保存Properties到文件
     * @param property
     * @param fileName
     */
    public static void store(Properties property, String fileName) {
        try {
            // 获取配置文件路径，并处理文件路径中的空格字符
            String dir = PropertyUtil.class.getResource("/").getPath().replaceAll("%20", " ");
            File file = new File(dir + fileName);

            // 备份配置文件
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
            String backupFile = dir + "backup/" + sdf.format(new Date()) + "-" + fileName;
            FileUtils.copyFile(file, new File(backupFile));
            LOG.info("配置文件{}已备份至{}", fileName, backupFile);

            List<String> lines = FileUtils.readLines(file, "UTF-8");
            // 更新配置文件
            List<String> list = new ArrayList<String>();
            for (String line: lines) {
                if (line.contains("=") && !line.startsWith("#")) {
                    String key = line.substring(0, line.indexOf('='));
                    String value = property.getProperty(key);
                    line = key + "=" + value;
                    // 移除已经处理过的配置记录
                    property.remove(key);
                }
                list.add(line);
            }

            // 获取为处理的配置记录
            if (!property.isEmpty()) {
                list.add("# Add the following properties at " + new Date().toString());
                Iterator<Object> iterator = property.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    String value = property.getProperty(key);
                    list.add(key + "=" + value);
                }
            }

            FileUtils.writeLines(file, "UTF-8", list);
            LOG.info("配置文件{}更新成功", fileName);
        } catch (FileNotFoundException e) {
            LOG.warn("未找到配置文件{}", fileName, e);
        } catch (IOException e) {
            LOG.warn("配置文件{}保存异常", fileName, e);
        }
    }

    /*
   * 新加
   * */
    public static Properties getEmailPro(String propertyFile) {
        InputStream is = null;
        try {
            is = PropertyUtil.class.getResourceAsStream("/" + propertyFile);
            emailPro.load(is);
        } catch (IOException e) {
            LOG.info("无法载入配置文件：{}", propertyFile, e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    LOG.warn("配置文件{}输入流关闭异常", propertyFile, e);
                }
            }
        }

        return emailPro;
    }
    /*
    * 新加
    * */
    public static boolean set(Properties properties,String propertyFile) {
        try {
            //URL url=ClassLoader.getSystemResource("/"+propertyFile);
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if(classLoader == null){
                classLoader = PropertyUtil.class.getClassLoader();
            }
            URL url=classLoader.getResource(propertyFile);
            //Configuration configLoader = ConfigurationManager.getInstance().getByFileName(FILE_REDIS_CONFIG);

            LOG.info("映射文件路径为：{}",url.getPath());
            File file=new File(url.toURI());
            OutputStream os = new FileOutputStream(file);
            properties.store(os,null);
            os.flush();
            os.close();
        }catch (Exception e){
            LOG.warn("无法将内容载入到{}文件中",propertyFile);
        }
        return true;
    }
}