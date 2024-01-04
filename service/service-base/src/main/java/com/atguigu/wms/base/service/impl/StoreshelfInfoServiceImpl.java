package com.atguigu.wms.base.service.impl;

import com.atguigu.wms.base.mapper.StoreareaInfoMapper;
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
import java.util.*;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class StoreshelfInfoServiceImpl extends ServiceImpl<StoreshelfInfoMapper, StoreshelfInfo> implements StoreshelfInfoService {
    @Autowired
    private StoreshelfInfoMapper storeshelfInfoMapper;
    @Autowired
    private StoreareaInfoMapper storeareaInfoMapper;

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
        QueryWrapper queryWrapper = new QueryWrapper();
        if (storeshelfInfoQueryVo != null) {
            String name = storeshelfInfoQueryVo.getName();
            Long storeareaId = storeshelfInfoQueryVo.getStoreareaId();
            Long warehouseId = storeshelfInfoQueryVo.getWarehouseId();
            Long houseTypeId = storeshelfInfoQueryVo.getHouseTypeId();
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
        }
        queryWrapper.eq("is_deleted", 0);
        IPage<StoreshelfInfo> ipage = storeshelfInfoMapper.selectPage(retPage, queryWrapper);
        return ipage;
    }

    /**
     * 添加货架
     * Path：http://192.168.200.1:8100/admin/base/storeshelfInfo/save
     * Method：post
     *
     * @param storeshelfInfo
     */
    @Override
    public void saveStoreshelf(StoreshelfInfo storeshelfInfo) {
        save(storeshelfInfo);
        Long storeareaId = storeshelfInfo.getStoreareaId();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", storeareaId);
        StoreareaInfo storeareaInfo = storeareaInfoMapper.selectOne(queryWrapper);
        storeareaInfo.setUpdateTime(new Date());
        storeareaInfo.setStoreshelfCount(storeshelfInfo.getStorehouseCount() + 1);
        storeareaInfoMapper.updateById(storeareaInfo);
    }

    /**
     * 根据货架id,查询货架信息
     * Path：http://192.168.200.1:8100/admin/base/storeshelfInfo/get/{id}
     * Method：Get
     *
     * @param id
     * @return
     */
    @Override
    public StoreshelfInfo get(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        StoreshelfInfo storeshelfInfo = storeshelfInfoMapper.selectOne(queryWrapper);
        return storeshelfInfo;
    }

    /**
     * 修改货架信息
     * Path：http://192.168.200.1:8100/admin/base/storeshelfInfo/update
     * Method：update
     *
     * @param storeshelfInfo
     */
    @Override
    public void update(StoreshelfInfo storeshelfInfo) {
        update(storeshelfInfo);
    }

    /**
     * 删除货架信息
     * Path：http://192.168.200.1:8100/admin/base/storeshelfInfo/remove/{id}
     * Method：delete
     *
     * @param id
     */
    @Override
    public void remove(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        StoreshelfInfo storeshelfInfo = storeshelfInfoMapper.selectOne(queryWrapper);
        Long storeareaId = storeshelfInfo.getStoreareaId();
        storeshelfInfoMapper.deleteById(id);
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("id", storeareaId);
        StoreareaInfo storeareaInfo = storeareaInfoMapper.selectOne(queryWrapper1);
        storeareaInfo.setUpdateTime(new Date());
        if (storeareaInfo.getStorehouseCount() > 0) {
            storeareaInfo.setStoreshelfCount(storeshelfInfo.getStorehouseCount() - 1);
        }
        storeareaInfoMapper.updateById(storeareaInfo);
    }

    /**
     * 根据库区id,查询库区下的所有货架
     * Path：http://192.168.200.1:8100 /admin/base/storeshelfInfo/findByStoreareaId/{storeareaId}
     * Method：Get
     *
     * @param storeareaId
     * @return
     */
    @Override
    public List<StoreareaInfo> findByStoreareaId(Integer storeareaId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("storearea_id", storeareaId);
        List<StoreareaInfo> list = storeshelfInfoMapper.selectList(queryWrapper);
        return list;
    }


}
