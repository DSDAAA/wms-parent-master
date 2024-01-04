package com.atguigu.wms.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.wms.model.base.Dict;
import com.atguigu.wms.model.base.GoodsType;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.vo.base.GoodsTypeQueryVo;
import com.atguigu.wms.base.mapper.GoodsTypeMapper;
import com.atguigu.wms.base.service.GoodsTypeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class GoodsTypeServiceImpl extends ServiceImpl<GoodsTypeMapper, GoodsType> implements GoodsTypeService {

    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    @Override
    public String getNameById(Long id) {
        GoodsType goodsType = this.getById(id);
        return goodsType.getName();
    }

    /**
     * 根据上级id获取子节点数据列表
     * Path：http://192.168.200.1/admin/base/goodsType/findByParentId/{parentId}
     * Method：Get
     *
     * @param parentId
     * @return
     */
    @Override
    public List<GoodsType> findByParentId(Integer parentId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id", parentId);
        List<GoodsType> list = goodsTypeMapper.selectList(queryWrapper);
        return list;
    }

    /**
     * 查询三级分类
     * Path：http://192.168.200.1/admin/base/goodsType/findNodes
     * Method：Get
     *
     * @return
     */
    @Override
    public List<GoodsType> findNodes() {
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("parent_id", 0);
        List<GoodsType> list1 = goodsTypeMapper.selectList(queryWrapper1);
        for (GoodsType goodsType1 : list1) {
            Long id1 = goodsType1.getId();
            QueryWrapper queryWrapper2 = new QueryWrapper();
            queryWrapper2.eq("parent_id", id1);
            List<GoodsType> list2 = goodsTypeMapper.selectList(queryWrapper2);
            for (GoodsType goodsType2 : list2) {
                Long id2 = goodsType2.getId();
                QueryWrapper queryWrapper3 = new QueryWrapper();
                queryWrapper3.eq("parent_id", id2);
                List<GoodsType> list3 = goodsTypeMapper.selectList(queryWrapper3);
                for (GoodsType goodsType3 : list3) {
                    goodsType3.setValue(goodsType3.getName());
                }
                goodsType2.setValue(goodsType2.getName());
                goodsType2.setChildren(list3);
            }
            goodsType1.setValue(goodsType1.getName());
            goodsType1.setChildren(list2);
        }
        return list1;
    }


}
