package com.atguigu.wms.base.service.impl;

import com.atguigu.wms.base.service.*;
import com.atguigu.wms.common.helper.BaiduMapHelper;
import com.atguigu.wms.common.security.AuthContextHolder;
import com.atguigu.wms.enums.WarehouseType;
import com.atguigu.wms.model.base.*;
import com.atguigu.wms.model.inventory.InventoryInfo;
import com.atguigu.wms.vo.base.WarehouseInfoQueryVo;
import com.atguigu.wms.base.mapper.WarehouseInfoMapper;
import com.atguigu.wms.vo.outbound.OutOrderAddressVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class WarehouseInfoServiceImpl extends ServiceImpl<WarehouseInfoMapper, WarehouseInfo> implements WarehouseInfoService {

    @Autowired
    private WarehouseInfoMapper warehouseInfoMapper;

    @Resource
    private DictService dictService;

    @Resource
    private StoreareaInfoService storeareaInfoService;

    @Resource
    private StoreshelfInfoService storeshelfInfoService;

    @Resource
    private StorehouseInfoService storehouseInfoService;


    @Override
    public String getNameById(Long id) {
        if (null == id) return "";
        WarehouseInfo warehouseInfo = this.getById(id);
        return warehouseInfo.getName();
    }


    //@Cacheable(value = "warehouseInfo",keyGenerator = "keyGenerator")
    @Override
    public List<Map<String, Object>> findNodes() {

        List<WarehouseInfo> warehouseInfoList = this.findAllWare();
        List<StoreareaInfo> storeareaInfoList = storeareaInfoService.list(new LambdaQueryWrapper<StoreareaInfo>().eq(StoreareaInfo::getStatus, 1));
        List<StoreshelfInfo> storeshelfInfoList = storeshelfInfoService.list(new LambdaQueryWrapper<StoreshelfInfo>().eq(StoreshelfInfo::getStatus, 1));
        List<StorehouseInfo> storehouseInfoList = storehouseInfoService.list(new LambdaQueryWrapper<StorehouseInfo>().eq(StorehouseInfo::getStatus, 1));

        Map<Long, List<StoreareaInfo>> warehouseIdToStoreareaInfoMap = storeareaInfoList.stream().collect(Collectors.groupingBy(StoreareaInfo::getWarehouseId, Collectors.toList()));
        Map<Long, List<StoreshelfInfo>> storeareaIdToStoreshelfInfoMap = storeshelfInfoList.stream().collect(Collectors.groupingBy(StoreshelfInfo::getStoreareaId, Collectors.toList()));
        Map<Long, List<StorehouseInfo>> storeshelfIdToStorehouseInfoMap = storehouseInfoList.stream().collect(Collectors.groupingBy(StorehouseInfo::getStoreshelfId, Collectors.toList()));
        List<Map<String, Object>> nodes = new ArrayList<>();
        for (WarehouseInfo warehouseInfo : warehouseInfoList) {
            Map<String, Object> warehouseInfoMap = new HashMap<>();
            warehouseInfoMap.put("label", warehouseInfo.getName());
            warehouseInfoMap.put("value", warehouseInfo.getId());
            List<StoreareaInfo> currentStoreareaInfoList = warehouseIdToStoreareaInfoMap.get(warehouseInfo.getId());
            if (CollectionUtils.isEmpty(currentStoreareaInfoList)) continue;
            List<Map<String, Object>> warehouseInfoChildren = new ArrayList<>();
            for (StoreareaInfo storeareaInfo : currentStoreareaInfoList) {
                Map<String, Object> storeareaInfoMap = new HashMap<>();
                storeareaInfoMap.put("label", storeareaInfo.getName());
                storeareaInfoMap.put("value", storeareaInfo.getId());
                List<StoreshelfInfo> currentStoreshelfInfoList = storeareaIdToStoreshelfInfoMap.get(storeareaInfo.getId());
                if (CollectionUtils.isEmpty(currentStoreshelfInfoList)) continue;
                List<Map<String, Object>> storeareaInfoChildren = new ArrayList<>();
                for (StoreshelfInfo storeshelfInfo : currentStoreshelfInfoList) {
                    Map<String, Object> storeshelfInfoMap = new HashMap<>();
                    storeshelfInfoMap.put("label", storeshelfInfo.getName());
                    storeshelfInfoMap.put("value", storeshelfInfo.getId());
                    List<StorehouseInfo> currentStorehouseInfoList = storeshelfIdToStorehouseInfoMap.get(storeshelfInfo.getId());
                    if (CollectionUtils.isEmpty(currentStorehouseInfoList)) continue;
                    List<Map<String, Object>> storeshelfInfoChildren = new ArrayList<>();
                    for (StorehouseInfo storehouseInfo : currentStorehouseInfoList) {
                        Map<String, Object> storehouseInfoMap = new HashMap<>();
                        storehouseInfoMap.put("label", storehouseInfo.getName());
                        storehouseInfoMap.put("value", storehouseInfo.getId());

                        storeshelfInfoChildren.add(storehouseInfoMap);
                    }
                    storeshelfInfoMap.put("children", storeshelfInfoChildren);
                    storeareaInfoChildren.add(storeshelfInfoMap);
                }
                storeareaInfoMap.put("children", storeareaInfoChildren);
                warehouseInfoChildren.add(storeareaInfoMap);
            }
            warehouseInfoMap.put("children", warehouseInfoChildren);
            nodes.add(warehouseInfoMap);
        }
        return nodes;
    }

    private List<WarehouseInfo> findAllWare() {
        LambdaQueryWrapper<WarehouseInfo> queryWrapper = new LambdaQueryWrapper<WarehouseInfo>();
        queryWrapper.eq(WarehouseInfo::getStatus, 1);
        if (null != AuthContextHolder.getWarehouseId()) {
            queryWrapper.eq(WarehouseInfo::getId, AuthContextHolder.getWarehouseId());
        }
        return this.list(queryWrapper);
    }

    @Override
    public List<String> findNameByIdList(List<Long> idList) {
        List<String> list = new ArrayList<>();
        for (Long id : idList) {
            list.add(this.getNameById(id));
        }
        return list;
    }

    /**
     * 根据用户地址给满足条件的仓库指定优先级
     *
     * @param outOrderAddressVo
     * @return
     */
    @Override
    public List<Long> findPriorityWarehouseIdList(OutOrderAddressVo outOrderAddressVo) {
        List<Long> result = new ArrayList<>();
        //满足条件的全部仓库
        List<Long> warehouseIdList = outOrderAddressVo.getWarehouseIdList();
        List<WarehouseInfo> warehouseInfoList = this.listByIds(warehouseIdList);

        //优选选择区域仓库
        if (null != outOrderAddressVo.getProvinceId()) {
            List<WarehouseInfo> cityWarehouseInfoList = warehouseInfoList.stream().filter(item -> (item.getProvinceId().longValue() == outOrderAddressVo.getProvinceId().longValue() && item.getType() == WarehouseType.CITY.getCode())).collect(Collectors.toList());
            if (cityWarehouseInfoList.size() > 1) {
                //根据百度地址计算仓库与收获地址距离，排序 BaiduMapHelper
                for (WarehouseInfo warehouseInfo : cityWarehouseInfoList) {
                    Double distance = BaiduMapHelper.getMapDistance(outOrderAddressVo.getDeliveryAddress(), this.getFullAddress(warehouseInfo));
                    warehouseInfo.setDistance(distance);
                }
                Collections.sort(cityWarehouseInfoList);
                result.addAll(cityWarehouseInfoList.stream().map(WarehouseInfo::getId).collect(Collectors.toList()));
            } else {
                if (cityWarehouseInfoList.size() == 1) {
                    result.add(cityWarehouseInfoList.get(0).getId());
                }
            }
        }

        //最后选择中心仓库
        List<WarehouseInfo> centreWarehouseInfoList = warehouseInfoList.stream().filter(item -> item.getType() == WarehouseType.CENTRE.getCode()).collect(Collectors.toList());
        if (centreWarehouseInfoList.size() > 1) {
            //根据百度地址计算仓库与收获地址距离，排序 BaiduMapHelper
            for (WarehouseInfo warehouseInfo : centreWarehouseInfoList) {
                Double distance = BaiduMapHelper.getMapDistance(outOrderAddressVo.getDeliveryAddress(), this.getFullAddress(warehouseInfo));
                warehouseInfo.setDistance(distance);
            }
            Collections.sort(centreWarehouseInfoList);
            result.addAll(centreWarehouseInfoList.stream().map(WarehouseInfo::getId).collect(Collectors.toList()));
        } else {
            if (centreWarehouseInfoList.size() == 1) {
                result.add(centreWarehouseInfoList.get(0).getId());
            }
        }

        //最后返回优先级从高到底的一个仓库id数组对象
        return result;
    }

    /**
     * 分页查询仓库列表信息
     * Path：http://192.168.200.1:8100/admin/base/warehouseInfo/{page}/{limit}
     * Method：Get
     *
     * @param retPage
     * @param warehouseInfoQueryVo
     * @return
     */
    @Override
    public IPage<WarehouseInfo> getPageList(Page<WarehouseInfo> retPage, WarehouseInfoQueryVo warehouseInfoQueryVo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        if (warehouseInfoQueryVo != null) {
            Long id = warehouseInfoQueryVo.getId();
            Boolean type = warehouseInfoQueryVo.getType();
            String name = warehouseInfoQueryVo.getName();
            Long cityId = warehouseInfoQueryVo.getCityId();
            Long areaId = warehouseInfoQueryVo.getAreaId();
            Long provinceId = warehouseInfoQueryVo.getProvinceId();
            if (id != 0) {
                queryWrapper.eq("id", id);
            }
            if (type != null) {
                queryWrapper.eq("type", type);
            }
            if (!StringUtils.isEmpty(name)) {
                queryWrapper.eq("name", name);
            }
            if (cityId != 0) {
                queryWrapper.eq("city_id", cityId);
            }
            if (areaId != 0) {
                queryWrapper.eq("area_id", areaId);
            }
            if (provinceId != 0) {
                queryWrapper.eq("province_id", provinceId);
            }
        }
        queryWrapper.eq("is_deleted", 0);
        IPage ipage = warehouseInfoMapper.selectPage(retPage, queryWrapper);
        return ipage;
    }

    /**
     * 根据仓库id,查询仓库信息
     * Path：http://192.168.200.1:8100 /admin/base/warehouseInfo/get/{id}
     * Method：Get
     *
     * @param id
     * @return
     */
    @Override
    public WarehouseInfo get(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        WarehouseInfo warehouseInfo = warehouseInfoMapper.selectOne(queryWrapper);
        return warehouseInfo;
    }

    /**
     * 修改仓库
     * Path：http://192.168.200.1:8100/admin/base/warehouseInfo/update
     * Method：put
     *
     * @param warehouseInfo
     */
    @Override
    public void update(WarehouseInfo warehouseInfo) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", warehouseInfo.getCityId());
        warehouseInfoMapper.update(warehouseInfo, queryWrapper);
    }

    /**
     * 删除仓库
     * Path：http://192.168.200.1:8100/admin/base/warehouseInfo/remove/{id}
     * Method：delete
     *
     * @param id
     */
    @Override
    public void remove(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        warehouseInfoMapper.delete(queryWrapper);
    }

    /**
     * 查询所有仓库
     * Path：http://192.168.200.1:8100/admin/base/warehouseInfo/findAll
     * Method：GET
     *
     * @return
     */
    @Override
    public List<WarehouseInfo> findAll() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("is_deleted", 0);
        List<WarehouseInfo> list = warehouseInfoMapper.selectList(queryWrapper);
        return list;
    }

    /**
     * 获取仓库完整地址
     *
     * @param warehouseInfo
     * @return
     */
    private String getFullAddress(WarehouseInfo warehouseInfo) {
        String province = dictService.getNameById(warehouseInfo.getProvinceId());
        String city = dictService.getNameById(warehouseInfo.getCityId());
        String area = dictService.getNameById(warehouseInfo.getAreaId());
        return province + city + area + warehouseInfo.getAddress();
    }

//	public List<Long> getCentreWarehouseId(Long provinceId) {
//		LambdaQueryWrapper<WarehouseInfo> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.ge(WarehouseInfo::getProvinceId, provinceId);
//		queryWrapper.ge(WarehouseInfo::getType, WarehouseType.CENTRE);
//		queryWrapper.select(WarehouseInfo::getId);
//		return this.list(queryWrapper).stream().map(WarehouseInfo::getId).collect(Collectors.toList());
//	}
//
//	public List<Long> getCityWarehouseId(Long cityId) {
//		LambdaQueryWrapper<WarehouseInfo> queryWrapper = new LambdaQueryWrapper<>();
//		queryWrapper.ge(WarehouseInfo::getCityId, cityId);
//		queryWrapper.ge(WarehouseInfo::getType, WarehouseType.CITY);
//		queryWrapper.select(WarehouseInfo::getId);
//		return this.list(queryWrapper).stream().map(WarehouseInfo::getId).collect(Collectors.toList());
//	}


}
