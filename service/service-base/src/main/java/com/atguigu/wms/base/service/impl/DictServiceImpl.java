package com.atguigu.wms.base.service.impl;


import com.atguigu.wms.base.service.DictService;
import com.atguigu.wms.base.mapper.DictMapper;
import com.atguigu.wms.common.excel.ExcelHelper;
import com.atguigu.wms.model.base.Dict;
import com.atguigu.wms.vo.base.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    @Autowired
    private DictMapper dictMapper;

    /**
     * 根据上级id获取子节点数据列表
     * allEntries = true: 方法调用后清空所有缓存
     * beforeInvocation = true：方法调用前清空所有缓存
     * @param parentId
     */


    /**
     * 导入
     * allEntries = true: 方法调用后清空所有缓存
     * beforeInvocation = true：方法调用前清空所有缓存
     *
     * @param file
     */
    //@CacheEvict(value = "dict", allEntries=true)
    @Cacheable(value = "dict", keyGenerator = "keyGenerator")
    @Override
    public String getNameById(Long id) {
        Dict dict = this.getById(id);
        if (null == dict) return "";
        return dict.getName();
    }

    /**
     * 根据上级id获取子节点数据列表（需要先实现，后面会复用,很重要）
     * Path：http://192.168.200.1 /admin/base/dict/findByParentId/{parentId}
     * Method：Get
     *
     * @param parentId
     * @return
     */
    @Override
    public List<Dict> findByParentId(Integer parentId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id", parentId);
        List<Dict> list = dictMapper.selectList(queryWrapper);
        return list;
    }

    /**
     * 根据数据字典数据编码找到下面所有的子节点
     * Path：http://192.168.200.1/admin/base/dict//{dictCode}
     * Method：Get
     *
     * @param dictCode
     * @return
     */
    @Override
    public List<Dict> findByDictCode(String dictCode) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("dict_code", dictCode);
        List<Dict> list = dictMapper.selectList(queryWrapper);
        return list;
    }


//	@Cacheable(value = "dict",keyGenerator = "keyGenerator")
//	@Override
//	public String getNameByParentDictCodeAndValue(String parentDictCode) {
//		//如果value能唯一定位数据字典，parentDictCode可以传空，例如：省市区的value值能够唯一确定
//		if(StringUtils.isEmpty(parentDictCode)) {
//			Dict dict = dictMapper.selectOne(new QueryWrapper<Dict>().eq("value", value));
//			if(null != dict) {
//				return dict.getName();
//			}
//		} else {
//			Dict parentDict = this.getByDictsCode(parentDictCode);
//			if(null == parentDict) return "";
//			Dict dict = dictMapper.selectOne(new QueryWrapper<Dict>().eq("parent_id", parentDict.getId()).eq("value", value));
//			if(null != dict) {
//				return dict.getName();
//			}
//		}
//		return "";
//	}

//	/**
//	 * https://alibaba-easyexcel.github.io/quickstart/read.html
//	 * @param file
//	 */
//	@Override
//	public void importData(MultipartFile file) {
//		try {
//			EasyExcel.read(file.getInputStream(), DictEeVo.class, new DictDataListener(dictMapper)).sheet().doRead();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 * https://alibaba-easyexcel.github.io/quickstart/write.html
//	 * @param response
//	 */
//	@Override
//	public void exportData(HttpServletResponse response) {
//		try {
//			response.setContentType("application/vnd.ms-excel");
//			response.setCharacterEncoding("utf-8");
//			// 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
//			String fileName = URLEncoder.encode("数据字典", "UTF-8");
//			response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
//
//			List<Dict> dictList = dictMapper.selectList(null);
//			List<DictEeVo> dictVoList = new ArrayList<>(dictList.size());
//			for(Dict dict : dictList) {
//				DictEeVo dictVo = new DictEeVo();
//				BeanUtils.copyBean(dict, dictVo, DictEeVo.class);
//				dictVoList.add(dictVo);
//			}
//
//			EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("数据字典").doWrite(dictVoList);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}


}
