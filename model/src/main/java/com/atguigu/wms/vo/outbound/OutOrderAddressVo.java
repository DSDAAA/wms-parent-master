package com.atguigu.wms.vo.outbound;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class OutOrderAddressVo {

	@ApiModelProperty(value = "省")
	private Long provinceId;

	@ApiModelProperty(value = "送货地址")
	private String deliveryAddress;

	@ApiModelProperty(value = "满足条件的仓库id列表")
	private List<Long> warehouseIdList;

}

