package com.atguigu.wms.base.service.impl;

import com.atguigu.wms.base.mapper.WarehouseInfoMapper;
import com.atguigu.wms.base.service.DictService;
import com.atguigu.wms.base.service.WarehouseInfoService;
import com.atguigu.wms.model.base.StoreareaInfo;
import com.atguigu.wms.model.base.StorehouseInfo;
import com.atguigu.wms.model.base.WarehouseInfo;
import com.atguigu.wms.vo.base.StoreareaInfoQueryVo;
import com.atguigu.wms.base.mapper.StoreareaInfoMapper;
import com.atguigu.wms.base.service.StoreareaInfoService;
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
public class StoreareaInfoServiceImpl extends ServiceImpl<StoreareaInfoMapper, StoreareaInfo> implements StoreareaInfoService {
    @Autowired
    private StoreareaInfoMapper storeareaInfoMapper;
    @Autowired
    private WarehouseInfoMapper warehouseInfoMapper;

    @Cacheable(value = "storeareaInfo", keyGenerator = "keyGenerator")
    @Override
    public String getNameById(Long id) {
        if (null == id) return "";
        StoreareaInfo storeareaInfo = this.getById(id);
        return storeareaInfo.getName();
    }

    /**
     * 分页查询库区列表信息
     * Path：http://192.168.200.1:8100/admin/base/storeareaInfo/{page}/{limit}
     * Method：Get
     *
     * @param retPage
     * @param storeareaInfoQueryVo
     * @return
     */
    @Override
    public IPage<StoreareaInfo> getPageList(Page<StoreareaInfo> retPage, StoreareaInfoQueryVo storeareaInfoQueryVo) {
        String name = storeareaInfoQueryVo.getName();
        Long warehouseId = storeareaInfoQueryVo.getWarehouseId();
        Long areaTypeId = storeareaInfoQueryVo.getAreaTypeId();
        QueryWrapper queryWrapper = new QueryWrapper();
        if (!StringUtils.isEmpty(name)) {
            queryWrapper.eq("name", name);
        }
        if (warehouseId != 0) {
            queryWrapper.eq("warehouse_id", warehouseId);
        }
        if (areaTypeId != 0) {
            queryWrapper.eq("area_type_id", areaTypeId);
        }
        queryWrapper.eq("is_deleted", 0);
        IPage<StoreareaInfo> ipage = storeareaInfoMapper.selectPage(retPage, queryWrapper);
        return ipage;
    }

    /**
     * 根据库区id,查询库区信息
     * Path：http://192.168.200.1:8100 /admin/base/warehouseInfo/get/{id}
     * Method：Get
     *
     * @param id
     * @return
     */
    @Override
    public StoreareaInfo get(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        StoreareaInfo storeareaInfo = storeareaInfoMapper.selectOne(queryWrapper);
        return storeareaInfo;
    }

    /**
     * 修改库区
     * Path：http://192.168.200.1:8100/admin/base/storeareaInfo/update
     * Method：update
     *
     * @param storeareaInfo
     */
    @Override
    public void update(StoreareaInfo storeareaInfo) {
        Long id = storeareaInfo.getId();


        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        storeareaInfoMapper.update(storeareaInfo, queryWrapper);
    }

    /**
     * 删除库区
     * Path：http://192.168.200.1:8100/admin/base/storeareaInfo/remove/{id}
     * Method：delete
     *
     * @param id
     */
    @Override
    public void remove(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        StoreareaInfo storeareaInfo = storeareaInfoMapper.selectOne(queryWrapper);
        Long warehouseId = storeareaInfo.getWarehouseId();
        QueryWrapper queryWrapper1 = new QueryWrapper();
        queryWrapper1.eq("id", warehouseId);
        WarehouseInfo warehouseInfo = warehouseInfoMapper.selectOne(queryWrapper1);
        warehouseInfo.setStoreareaCount(warehouseInfo.getStoreareaCount() - 1);
        warehouseInfo.setUpdateTime(new Date());
        warehouseInfoMapper.update(warehouseInfo, queryWrapper1);
        storeareaInfoMapper.delete(queryWrapper);
    }

    /**
     * 删除库区信息
     * Path：http://192.168.200.1:8100/admin/base/storeareaInfo/remove/{id}
     * Method：delete
     *
     * @param storeareaInfo
     */
    @Override
    public void saveWarehouse(StoreareaInfo storeareaInfo) {
        Long warehouseId = storeareaInfo.getWarehouseId();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", warehouseId);
        WarehouseInfo warehouseInfo = warehouseInfoMapper.selectOne(queryWrapper);
        warehouseInfo.setUpdateTime(new Date());
        warehouseInfo.setStoreareaCount(warehouseInfo.getStoreareaCount() + 1);
        warehouseInfoMapper.update(warehouseInfo, queryWrapper);
        storeareaInfoMapper.insert(storeareaInfo);
    }

    /**
     * 根据仓库id,查询仓库下的所有库区
     * Path：http://192.168.200.1:8100/admin/base/storeareaInfo/findByWarehouseId/{warehouseId}
     * Method：Get
     *
     * @param warehouseId
     * @return
     */
    @Override
    public List<StoreareaInfo> findByWarehouseId(Integer warehouseId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("warehouse_id", warehouseId);
        List<StoreareaInfo> list = storeareaInfoMapper.selectList(queryWrapper);
        return list;
    }


}
