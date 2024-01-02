package com.atguigu.wms.common.util;

import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.List;

public class GeneralConv {
    /**
     * 对象通用转换
     *
     * @param source           源对象
     * @param destinationClass 目标类
     * @param <T>
     * @return 返回得到destinationClass
     */
    public static <T> T conv(Object source, Class<T> destinationClass) {
        if(null == source){
            return null;
        }
        DozerBeanMapper dozerMapper = new DozerBeanMapper();
        T convObj = dozerMapper.map(source, destinationClass);
        return convObj;
    }

    /**
     * 集合转换
     *
     * @param sourceList       源集合
     * @param destinationClass 目标类
     * @param <T>
     * @return 返回得到destinationClass的集合结果
     */
    public static <T> List<T> convert2List(List<?> sourceList, Class<T> destinationClass) {
        List<T> destinationList =new ArrayList<>();
        sourceList.forEach(source -> {
            destinationList.add(GeneralConv.conv(source, destinationClass));
        });
        return destinationList;
    }
}
