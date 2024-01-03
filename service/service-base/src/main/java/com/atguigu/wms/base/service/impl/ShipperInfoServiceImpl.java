package com.atguigu.wms.base.service.impl;

import com.atguigu.wms.base.mapper.ShipperInfoMapper;
import com.atguigu.wms.base.service.ShipperInfoService;
import com.atguigu.wms.model.base.ShipperInfo;
import com.atguigu.wms.vo.base.ShipperInfoQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ShipperInfoServiceImpl extends ServiceImpl<ShipperInfoMapper, ShipperInfo> implements ShipperInfoService {
    @Autowired
    ShipperInfoMapper shipperInfoMapper;

    /**
     * 根据货主id 查询货主信息
     * Path：http://192.168.200.1/admin/base/shipperInfo/get/{id}
     * Method：Get
     *
     * @param id
     * @return
     */
    @Override
    public ShipperInfo get(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        ShipperInfo shipperInfo = shipperInfoMapper.selectOne(queryWrapper);
        return shipperInfo;
    }

    /**
     * 修改货主
     * Path：http://192.168.200.1/admin/base/shipperInfo/update
     * Method：Put
     *
     * @param shipperInfo
     */
    @Override
    public void update(ShipperInfo shipperInfo) {
        Long id = shipperInfo.getId();
        UpdateWrapper updateWrapper = new UpdateWrapper();
        updateWrapper.eq("id", id);
        shipperInfoMapper.update(shipperInfo, updateWrapper);
    }

    /**
     * 删除货主
     * Path：http://192.168.200.1/admin/base/shipperInfo/remove/{id}
     * Method：delete
     *
     * @param id
     */
    @Override
    public void remove(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        shipperInfoMapper.delete(queryWrapper);
    }

    @Override
    public IPage<ShipperInfo> getPageList(IPage<ShipperInfo> retPage, ShipperInfoQueryVo shipperInfoQueryVo) {
        Long areaId = shipperInfoQueryVo.getAreaId();
        String keyword = shipperInfoQueryVo.getKeyword();
        Long cityId = shipperInfoQueryVo.getCityId();
        Long provinceId = shipperInfoQueryVo.getProvinceId();
        QueryWrapper queryWrapper = new QueryWrapper();
        if (areaId != 0) {
            queryWrapper.eq("area_id", areaId);
        }
        if (!StringUtils.isEmpty(keyword)) {
            queryWrapper.like("name", keyword);
        }
        if (cityId != 0) {
            queryWrapper.eq("city_id", cityId);
        }
        if (provinceId != 0) {
            queryWrapper.eq("province_id", provinceId);
        }
        queryWrapper.eq("is_deleted", 0);
        IPage<ShipperInfo> iPage = shipperInfoMapper.selectPage(retPage, queryWrapper);
        return iPage;
    }

}
