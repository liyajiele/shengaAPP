package com.sxp.sa.basic.utils;


import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.sxp.sa.basic.constant.Const;
import com.sxp.sa.basic.entity.Pager;
import com.sxp.sa.basic.exception.BusinessException;
import org.springframework.data.domain.Page;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by miss on 2015/11/19.
 */
public class Util {



    public static <T,K> Pager<K> p2pr(Page<T> page, Class clazz){
        List<T> ts = page.getContent();
        List<K> ks = BeanMapper.mapList(ts,clazz);
        Pager<K> pager = new Pager<>();
        pager.setContent(ks);
        pager.setLast(page.isLast());
        pager.setFirst(page.isFirst());
        pager.setNumber(page.getNumber());
        pager.setNumberOfElements(page.getNumberOfElements());
        pager.setSize(page.getSize());
        pager.setTotalElements(page.getTotalElements());
        pager.setTotalPages(page.getTotalPages());
        return pager;
    }

    /**
     * 判断对象是否Empty(null或元素为0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj
     *            待检查对象
     * @return boolean 返回的布尔值
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object pObj) {
        if (pObj == null)
            return true;
        if ("".equals(pObj))
            return true;
        if (pObj instanceof String) {
            if (((String) pObj).length() == 0) {
                return true;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return true;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断对象是否为NotEmpty(!null或元素>0)<br>
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj
     *            待检查对象
     * @return boolean 返回的布尔值
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(Object pObj) {
        if (pObj == null)
            return false;
        if ("".equals(pObj))
            return false;
        if (pObj instanceof String) {
            if (((String) pObj).length() == 0) {
                return false;
            }
        } else if (pObj instanceof Collection) {
            if (((Collection) pObj).size() == 0) {
                return false;
            }
        } else if (pObj instanceof Map) {
            if (((Map) pObj).size() == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否为数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }

    /**
     * 数据格式化.
     *
     * @param pattern
     *            the pattern
     * @param
     * @return the string
     */
    public static String codeFormat(String pattern, Object value) {
        DecimalFormat df = new DecimalFormat(pattern);
        return df.format(value);
    }

    /**
     * 格式化时间.
     *
     * @param date
     *            the date
     * @return the string
     */
    public static String fomatDate(String date) {
        if (isNotEmpty(date)) {
            return date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
                    + date.substring(6, 8);
        }
        return null;
    }

    /**
     * 格式化时间.
     *
     * @param date
     *            the date
     * @return the string
     */
    public static String fomatLongDate(String date) {
        if (isNotEmpty(date)) {
            return date.substring(0, 4) + "-" + date.substring(4, 6) + "-"
                    + date.substring(6, 8) + " " + date.substring(8, 10) + ":"
                    + date.substring(10, 12) + ":" + date.substring(12, 14);
        }
        return null;
    }

    /**
     * 格式化时间.
     *
     * @param date
     *            the date
     * @return the string
     */
    public static String fomatDateTime2String(String date) {
        if (isNotEmpty(date)) {
            return date.replace("-", "").replace("T", "").replace(":", "")
                    .replace(" ", "");
        }
        return null;
    }

    /**
     * 将时间字符串格式化成一个日期(java.util.Date)
     *
     * @param dateStr
     *            要格式化的日期字符串，如"2014-06-15 12:30:12"
     * @param formatStr
     *            格式化模板，如"yyyy-MM-dd HH:mm:ss"
     * @return the string
     */
    public static Date formatDateString2Date(String dateStr, String formatStr) {
        DateFormat dateFormat = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = dateFormat.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将时间字符串格式化成一个日期(java.util.Date)
     *
     * @param date
     *            要格式化的日期字符串，如"2014-06-15 12:30:12"
     * @param formatStr
     *            格式化模板，如"yyyy-MM-dd HH:mm:ss"
     * @return the string
     */
    public static String formatDate2String(Date date, String formatStr) {
        DateFormat dateFormat = new SimpleDateFormat(formatStr);
        String result = null;
        try {
            result = dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 将一个毫秒数时间转换为相应的时间格式
     *
     * @param longSecond
     * @return
     */
    public static String formateDateLongToString(long longSecond) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(longSecond);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(gc.getTime());
    }

    /**
     * 格式化金额.
     *
     * @param value
     *            the value
     * @return the string
     */
    public static String formatCurrency2String(Long value) {
        if (value == null || "0".equals(String.valueOf(value))) {
            return "0.00";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(value / 100.00);
    }

    /**
     * 格式化金额.
     *
     * @param priceFormat
     *            the price format
     * @return the long
     */
    public static Long formatCurrency2Long(String priceFormat) {
        BigDecimal bg = new BigDecimal(priceFormat);
        Long price = bg.multiply(new BigDecimal(100)).longValue();
        return price;
    }

    /**
     * 获取当前时间.
     *
     *            the current date
     * @return
     * @throws
     */
    public static String getToDayStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 获取当前时间当作文件名称
     *
     * @return
     */
    public static String getToDayStrAsFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return sdf.format(new Date());
    }

    public static Date getToDay() throws ParseException {
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // Date date = sdf.parse(String.valueOf(System.currentTimeMillis()));
        return new Date();
    }

    /**
     * 获取下一天.
     *
     * @param currentDate
     *            the current date
     * @return the next date str
     * @throws ParseException
     *             the parse exception
     */
    public static String getNextDateStr(String currentDate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = sdf.parse(currentDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        String nextDate = sdf.format(calendar.getTime());
        return nextDate;
    }

    /**
     * 获取上一天.
     *
     * @param currentDate
     *            the current date
     * @return the next date str
     * @throws ParseException
     *             the parse exception
     */
    public static String getYesterdayStr(String currentDate)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date date = sdf.parse(currentDate);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, -1);
        String nextDate = sdf.format(calendar.getTime());
        return nextDate;
    }

    /**
     * 根据日期获取星期
     *
     * @param strdate
     * @return
     */
    public static String getWeekDayByDate(String strdate) {
        final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
                "星期六" };
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date = new Date();
        try {
            date = sdfInput.parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (dayOfWeek < 0)
            dayOfWeek = 0;
        return dayNames[dayOfWeek];
    }

    /**
     * 生成固定长度的随机字符和数字
     *
     * @param len
     * @return
     */
    public static String generateRandomCharAndNumber(Integer len) {
        StringBuffer sb = new StringBuffer();
        for (Integer i = 0; i < len; i++) {
            int intRand = (int) (Math.random() * 52);
            int numValue = (int) (Math.random() * 10);
            char base = (intRand < 26) ? 'A' : 'a';
            char c = (char) (base + intRand % 26);
            if (numValue % 2 == 0) {
                sb.append(c);
            } else {
                sb.append(numValue);
            }
        }
        return sb.toString();
    }

    public static String readPropertiesFile(String key) {
        String fileName = "/filepath_win.properties";
        String filePath = "";
        InputStream inputStream = Util.class.getResourceAsStream(fileName);
        Properties pros = new Properties();
        try {
            pros.load(inputStream);
            filePath = pros.getProperty(key);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return filePath;
    }

    /**
     * 方法描述：将系统限定的路径转换为绝对正确的路径
     *
     * @param originalPath
     * @return
     */
    public static String getGeneralFilePath(String originalPath) {
        if ((null != originalPath) && !("".equals(originalPath))) {
            String strPath[] = originalPath.split("\\\\|/");
            originalPath = "";
            // 拼接jar路径
            for (int i = 0; i < strPath.length; i++) {
                if (!("".equals(strPath[i])) && !("".equals(strPath[i].trim()))) {
                    originalPath = originalPath + strPath[i].trim();
                    if (i < strPath.length - 1) {
                        originalPath = originalPath + File.separator;
                    }
                }
            }
        }
        return originalPath;
    }



    /**
     * 复制文件(以超快的速度复制文件)
     *
     * @return 实际复制的字节数，如果文件、目录不存在、文件为null或者发生IO异常，返回-1
     */
    @SuppressWarnings("resource")
    public static long copyFile(File srcFile, File destFile) throws Exception {
        long copySizes = 0;
        FileChannel fcin = new FileInputStream(srcFile).getChannel();
        FileChannel fcout = new FileOutputStream(destFile).getChannel();
        long size = fcin.size();
        fcin.transferTo(0, fcin.size(), fcout);
        fcin.close();
        fcout.close();
        copySizes = size;
        return copySizes;
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     *            被删除文件的文件名
     * @return 单个文件删除成功返回true,否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            file.delete();
            System.out.println("删除单个文件" + fileName + "成功！");
            return true;
        } else {
            System.out.println("删除单个文件" + fileName + "失败！");
            return false;
        }
    }


    /**
     * @Title: getRandomNumber
     * @Description: 获取随机数
     * @param count 位数，如果是1就产生1位的数字，如果是2就产生2位数字，依次类推
     * @return
     */
    public static String getRandomNumber(int count){
        String result="";
        for(int i=0;i<count;i++){
            int rand = (int) (Math.random()*10);
            result+=rand;
        }
        return result;
    }

    public static void makeDir(File dir) {
        if(! dir.getParentFile().exists()) {
            makeDir(dir.getParentFile());
        }
        dir.mkdir();
    }


    /**
     *
     * @param s 空字符串
     * @param ia 数组
     * @param n 几个
     * @param rst 结果
     */
    public static void combination(String s, String[] ia, int n,List<String> rst) {
        if(n == 1) {
            for(int i = 0; i < ia.length; i++) {
                //System.out.println(s+ia[i]);
                rst.add(s+ia[i]);
            }
        } else {
            for(int i = 0; i < ia.length-(n-1); i++) {
                String ss = "";
                ss = s+ia[i]+", ";
                //建立从i开始的子数组
                String[] ii = new String[ia.length-i-1];
                for(int j = 0; j < ia.length-i-1; j++) {
                    ii[j] = ia[i+j+1];
                }
                combination(ss, ii, n-1,rst);
            }
        }
    }
    /**
     * 生成订单编号
     */
    public static String getOrderNum(String type){
        SimpleDateFormat sdf = new SimpleDateFormat("MMddhhmmssSSSS");
        return type+sdf.format(new Date())+getRandomNumber(3);
    }




    public static String getMd5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }


    /**
     * 获取 用户ip
     * @param request
     * @return
     */
    public static String getRemoteHost(HttpServletRequest request){

        String ip = request.getHeader("x-forwarded-for");

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){

            ip = request.getHeader("Proxy-Client-IP");

        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){

            ip = request.getHeader("WL-Proxy-Client-IP");

        }

        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)){

            ip = request.getRemoteAddr();

        }

        return ip.equals("0:0:0:0:0:0:0:1")?"127.0.0.1":ip;

    }

    /**
     * 检查校验结果
     * @throws BusinessException
     */
    public static void validParam(ComplexResult ret)throws BusinessException{
        if(!ret.isSuccess()){
            throw new BusinessException(Const.Code.PARAM_FORMAT_ERROR,"参数有误",ret);
        }
    }

    /**
     * 微信回调后的返回信息
     * @param return_code
     * @param return_msg
     * @return
     */
    public static String setXML(String return_code, String return_msg) {
        return "<xml><return_code><![CDATA[" + return_code
                + "]]></return_code><return_msg><![CDATA[" + return_msg
                + "]]></return_msg></xml>";

    }

    /**
     * base64编码
     * @param str 内容
     * @param charset 编码方式
     * @throws UnsupportedEncodingException
     */
    public static String base64(String str, String charset) throws UnsupportedEncodingException{
        String encoded = com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(str.getBytes(charset));
        return encoded;
    }

    /**
     * url转化
     * @param str
     * @param charset
     * @return
     * @throws UnsupportedEncodingException
     */
    @SuppressWarnings("unused")
    public static String urlEncoder(String str, String charset) throws UnsupportedEncodingException{
        String result = URLEncoder.encode(str, charset);
        return result;
    }


    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 通过经纬度获取距离(单位：米)
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return
     */
    public static double getDistance(double lat1, double lng1, double lat2,
                                     double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * 6378.137;
        s = Math.round(s * 10000d) / 10000d;
        s = s*1000;
        return s;
    }

}
