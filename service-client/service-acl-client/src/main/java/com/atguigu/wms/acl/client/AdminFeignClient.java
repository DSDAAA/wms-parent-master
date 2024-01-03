package com.atguigu.wms.acl.client;

import com.atguigu.wms.acl.client.impl.AdminDegradeFeignClient;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Dunston
 *
 */
@FeignClient(value = "service-acl", fallback = AdminDegradeFeignClient.class)
public interface AdminFeignClient {

	@ApiOperation(value = "获取管理用户")
	@GetMapping("/admin/acl/user/getNameById/{id}")
	String getNameById(@PathVariable Long id);
}

