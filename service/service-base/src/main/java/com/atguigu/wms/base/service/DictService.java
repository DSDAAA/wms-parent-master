package com.atguigu.wms.base.service;


import com.atguigu.wms.model.base.Dict;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;;import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface DictService extends IService<Dict> {

    String getNameById(Long id);

    List<Dict> findByParentId(Integer parentId);

    List<Dict> findByDictCode(Integer dictCode);

    /**
     * 根据上级编码与值获取数据字典名称
     * @param parentDictCode
     * @param value
     * @return
     */
    //String getNameByParentDictCodeAndValue(String parentDictCode, String value);

}
