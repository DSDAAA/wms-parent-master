package com.atguigu.wms.base.client;

import com.atguigu.wms.base.client.impl.DictDegradeFeignClient;
import com.atguigu.wms.base.client.impl.GoodsInfoDegradeFeignClient;
import com.atguigu.wms.model.base.GoodsInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * <p>
 * 会员用户API接口
 * </p>
 *
 * @author qy
 */
@FeignClient(value = "service-base", fallback = DictDegradeFeignClient.class)
public interface DictFeignClient {

    @ApiOperation(value = "根据id获取名称")
    @GetMapping("/admin/base/dict/getNameById/{id}")
    String getNameById(@PathVariable Long id);

}