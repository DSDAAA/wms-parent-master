package com.atguigu.wms.common.helper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.wms.common.util.HttpUtil;
import org.springframework.util.StringUtils;

import java.awt.geom.Path2D;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

/**
 * 百度地图操作工具类
 */
public class BaiduMapHelper {

    private static String AK = "bTvHpZaVzw4Q1mzRl2uN8PHFIChpxyln";

    /**
     * 调用百度地图轻量级路线规划接口，计算金纬度两点方案距离
     * https://lbsyun.baidu.com/index.php?title=webapi/directionlite-v1
     * @param origin
     * @param destination
     * @return
     */
    public static Double getMapDistance(String origin,String destination){
//        String httpUrl = "http://api.map.baidu.com/directionlite/v1/driving?origin="+origin+"&destination="+destination+"&ak=" + AK;
//        String result = HttpUtil.doGet(httpUrl);
//        if(!StringUtils.isEmpty(result)) {
//            JSONObject object = JSON.parseObject(result);
//            if ("0".equals(object.getString("status"))) {
//                JSONArray jsonArray = (JSONArray) object.getJSONObject("result").get("routes");
//                JSONObject distanceObject = (JSONObject) jsonArray.get(0);
//                double distance = Double.parseDouble(distanceObject.get("distance") == null ? "0" : distanceObject.get("distance").toString());
//                return distance;
//            }
//        }
        return Math.random();
    }

}
