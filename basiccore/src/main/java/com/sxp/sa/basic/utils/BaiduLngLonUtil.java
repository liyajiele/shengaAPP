package com.sxp.sa.basic.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lex
 * 百度地图反查工具类，根据地址查询对应的经纬度
 *
 */
public class BaiduLngLonUtil {

    public static final String ak="696e6574253d1eb36c1c4b705c504b20";     //注册百度地图后获得的key

    public static Map<String, Double> getAddressLngLon(String address){
        try{
            Map<String, Double> map=new HashMap<String, Double>();
            //请求地址
            String url="http://api.map.baidu.com/geocoder/v2/?address="+address+"&output=json&ak="+ak;
            HttpResponse response = HttpRequest.get(url).send();
            String jsonText =response.bodyText();
            System.out.println(jsonText);
            JSONObject jsonObj = JSON.parseObject(jsonText);
            String statusStr =jsonObj.get("status").toString();
            if("1".equals(statusStr)){
                /************定位失败***************/
                map.put("lat", 0d);
                map.put("lng", 0d);
                return map;
            }
            String resultStr =jsonObj.get("result").toString();
            JSONObject resultJsonObj = JSON.parseObject(resultStr);
            String locationStr=resultJsonObj.get("location").toString();
            JSONObject locationJsonObj = JSON.parseObject(locationStr);
            Double lat =Double.valueOf(locationJsonObj.get("lat").toString());
            Double lng =Double.valueOf(locationJsonObj.get("lng").toString());
            map.put("lat", lat);
            map.put("lng", lng);
            return map;
        }catch(Exception e ){
            e.printStackTrace();
        }
        return null;

    }

    public static String getCity(Double lng, Double lat){
        Map<String, Double> map=new HashMap<String, Double>();
        //请求地址
        String url = "http://api.map.baidu.com/geocoder/v2/?location="+lat+","+lng+"&output=json&ak="+ak;
        HttpResponse response = HttpRequest.get(url).send();
        String jsonText =response.bodyText();
        JSONObject jsonObj = JSON.parseObject(jsonText);
        JSONObject result =jsonObj.getJSONObject("result");
        String city = null;
        if (result != null) {
            JSONObject addressComponent =result.getJSONObject("addressComponent");
            if (addressComponent != null) {
                city = addressComponent.getString("city");
            }
        }
        return city;
    }

    public static Map<String,String > getLocationInfo(Double lng, Double lat){
        Map<String, Double> map=new HashMap<String, Double>();
        //请求地址
        String url = "http://api.map.baidu.com/geocoder/v2/?location="+lat+","+lng+"&output=json&ak="+ak;
        HttpResponse response = HttpRequest.get(url).send();
        String jsonText =response.bodyText();
        JSONObject jsonObj = JSON.parseObject(jsonText);
        JSONObject result =jsonObj.getJSONObject("result");

        Map<String,String > infos = new HashMap<String,String>();
        String province = null;
        String city = null;
        String district = null;
        if (result != null) {
            JSONObject addressComponent =result.getJSONObject("addressComponent");
            if (addressComponent != null) {
                city = addressComponent.getString("city");
                province = addressComponent.getString("province");
                district = addressComponent.getString("district");

                infos.put("city",city);
                infos.put("province",province);
                infos.put("district",district);


            }
        }
        return infos;
    }

    public static void main(String[] args) {
        System.out.println(getLocationInfo(116.322987,39.983424));
        System.out.println(getCity(0.0,0.0));
        //System.out.println(getAddressLngLon("西安市小寨赛格国际"));
    }

}
