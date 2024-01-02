package com.atguigu.wms.inventory.service.impl;

import com.atguigu.wms.inventory.mapper.InvBusinessRepeatMapper;
import com.atguigu.wms.inventory.service.InvBusinessRepeatService;
import com.atguigu.wms.model.inventory.InvBusinessRepeat;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class InvBusinessRepeatServiceImpl extends ServiceImpl<InvBusinessRepeatMapper, InvBusinessRepeat> implements InvBusinessRepeatService {

	@Resource
	private InvBusinessRepeatMapper invBusinessRepeatMapper;


	@Override
	public Boolean isRepeat(String businessNo, String remarks) {
		try {
			InvBusinessRepeat invBusinessRepeat = new InvBusinessRepeat();
			invBusinessRepeat.setBusinessNo(businessNo);
			invBusinessRepeat.setRemarks(remarks);
			this.save(invBusinessRepeat);
			return false;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
