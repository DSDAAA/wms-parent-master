package com.atguigu.wms.base.service.impl;

import com.atguigu.wms.base.mapper.StoreareaInfoMapper;
import com.atguigu.wms.base.mapper.StorehouseInfoMapper;
import com.atguigu.wms.base.mapper.StoreshelfInfoMapper;
import com.atguigu.wms.base.service.StorehouseInfoService;
import com.atguigu.wms.model.base.StoreareaInfo;
import com.atguigu.wms.model.base.StorehouseInfo;
import com.atguigu.wms.model.base.StoreshelfInfo;
import com.atguigu.wms.vo.base.StorehouseInfoQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class StorehouseInfoServiceImpl extends ServiceImpl<StorehouseInfoMapper, StorehouseInfo> implements StorehouseInfoService {

    @Autowired
    private StorehouseInfoMapper storehouseInfoMapper;
    @Autowired
    private StoreshelfInfoMapper storeshelfInfoMapper;
    @Autowired
    private StoreareaInfoMapper storeareaInfoMapper;

    /**
     * 分页查询库位列表信息
     * Path：http://192.168.200.1:8100/admin/base/storehouseInfo/{page}/{limit}
     * Method：Post
     *
     * @param retPage
     * @param storehouseInfoQueryVo
     * @return
     */
    @Override
    public IPage<StorehouseInfo> getPageList(Page<StorehouseInfo> retPage, StorehouseInfoQueryVo storehouseInfoQueryVo) {
        String name = storehouseInfoQueryVo.getName();
        Long storeshelfId = storehouseInfoQueryVo.getStoreshelfId();
        Long storeareaId = storehouseInfoQueryVo.getStoreareaId();
        Long warehouseId = storehouseInfoQueryVo.getWarehouseId();
        QueryWrapper queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        if (storeareaId != 0) {
            queryWrapper.eq("storearea_id", storeareaId);
        }
        if (storeareaId != 0) {
            queryWrapper.eq("storeshelf_id", storeshelfId);
        }
        if (warehouseId != 0) {
            queryWrapper.eq("warehouse_id", warehouseId);
        }
        queryWrapper.eq("is_deleted", 0);
        IPage<StorehouseInfo> ipage = storehouseInfoMapper.selectPage(retPage, queryWrapper);
        return ipage;
    }

    /**
     * 添加库位
     * Path：http://192.168.200.1:8100/admin/base/storeshelfInfo/save
     * Method：post
     *
     * @param storehouseInfo
     */
    @Override
    public void saveStorehouse(StorehouseInfo storehouseInfo) {
        save(storehouseInfo);
        Long storeshelfId = storehouseInfo.getStoreshelfId();
        Long storeareaId = storehouseInfo.getStoreareaId();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", storeshelfId);
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("id", storeareaId);
        StoreareaInfo storeareaInfo = storeareaInfoMapper.selectOne(queryWrapper1);
        StoreshelfInfo storeshelfInfo = storeshelfInfoMapper.selectById(storeshelfId);
        storeareaInfo.setUpdateTime(new Date());
        storeshelfInfo.setUpdateTime(new Date());
        if (storeareaInfo.getStorehouseCount() > 0) {
            storeareaInfo.setStorehouseCount(storeareaInfo.getStorehouseCount() + 1);
        }
        if (storeshelfInfo.getStorehouseCount() > 0) {
            storeshelfInfo.setStorehouseCount(storeshelfInfo.getStorehouseCount() + 1);
        }
        storeshelfInfoMapper.updateById(storeshelfInfo);
        storeareaInfoMapper.updateById(storeareaInfo);
    }

    /**
     * 根据库位id,查询库位信息"
     * Path：http://192.168.200.1:8100/admin/base/storehouseInfo/getStorehouseInfo/{id}
     * Method：Get
     *
     * @param id
     * @return
     */
    @Override
    public StorehouseInfo getStorehouseInfo(Integer id) {
        StorehouseInfo storehouseInfo = storehouseInfoMapper.selectById(id);
        return storehouseInfo;
    }

    /**
     * 修改库位信息
     *
     * @param storehouseInfo
     */
    @Override
    public void update(StorehouseInfo storehouseInfo) {
        update(storehouseInfo);
    }

    /**
     * 删除库位信息
     * Path：http://192.168.200.1:8100/admin/base/storehouseInfo/remove/{id}
     * Method：delete
     *
     * @param id
     */
    @Override
    public void remove(Integer id) {
        StorehouseInfo storehouseInfo = storehouseInfoMapper.selectById(id);
        Long storeshelfId = storehouseInfo.getStoreshelfId();
        Long storeareaId = storehouseInfo.getStoreareaId();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", storeshelfId);
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("id", storeareaId);
        StoreareaInfo storeareaInfo = storeareaInfoMapper.selectOne(queryWrapper1);
        StoreshelfInfo storeshelfInfo = storeshelfInfoMapper.selectById(storeshelfId);
        storeareaInfo.setUpdateTime(new Date());
        storeshelfInfo.setUpdateTime(new Date());
        if (storeareaInfo.getStorehouseCount() > 0) {
            storeareaInfo.setStorehouseCount(storeareaInfo.getStorehouseCount() - 1);
        }
        if (storeshelfInfo.getStorehouseCount() > 0) {
            storeshelfInfo.setStorehouseCount(storeshelfInfo.getStorehouseCount() - 1);
        }
        storeshelfInfoMapper.updateById(storeshelfInfo);
        storeareaInfoMapper.updateById(storeareaInfo);
        removeById(id);
    }


}
