package com.atguigu.wms.base.service.impl;

import com.atguigu.wms.base.service.DictService;
import com.atguigu.wms.base.service.StoreareaInfoService;
import com.atguigu.wms.base.service.WarehouseInfoService;
import com.atguigu.wms.model.base.StoreareaInfo;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.base.StoreshelfInfoQueryVo;
import com.atguigu.wms.base.mapper.StoreshelfInfoMapper;
import com.atguigu.wms.base.service.StoreshelfInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class StoreshelfInfoServiceImpl extends ServiceImpl<StoreshelfInfoMapper, StoreshelfInfo> implements StoreshelfInfoService {
    @Autowired
    private StoreshelfInfoMapper storeshelfInfoMapper;

    /**
     * 分页查询货架列表信息
     * Path：http://192.168.200.1:8100/admin/base/storeshelfInfo/{page}/{limit}
     * Method：Post
     *
     * @param retPage
     * @param storeshelfInfoQueryVo
     * @return
     */
    @Override
    public IPage<StoreshelfInfo> getPageList(Page<StoreshelfInfo> retPage, StoreshelfInfoQueryVo storeshelfInfoQueryVo) {
        String name = storeshelfInfoQueryVo.getName();
        Long storeareaId = storeshelfInfoQueryVo.getStoreareaId();
        Long warehouseId = storeshelfInfoQueryVo.getWarehouseId();
        Long houseTypeId = storeshelfInfoQueryVo.getHouseTypeId();
        QueryWrapper queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        if (storeareaId != 0) {
            queryWrapper.eq("storearea_id", storeareaId);
        }
        if (warehouseId != 0) {
            queryWrapper.eq("warehouse_id", warehouseId);
        }
        if (houseTypeId != 0) {
            queryWrapper.eq("house_type_id", houseTypeId);
        }
        queryWrapper.eq("is_deleted", 0);
        IPage<StoreshelfInfo> ipage = storeshelfInfoMapper.selectPage(retPage, queryWrapper);
        return ipage;
    }


}
