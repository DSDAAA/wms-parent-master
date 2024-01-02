package com.atguigu.wms.inventory.service.impl;

import com.atguigu.wms.model.inventory.InvCountingItem;
import com.atguigu.wms.inventory.mapper.InvCountingItemMapper;
import com.atguigu.wms.inventory.service.InvCountingItemService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class InvCountingItemServiceImpl extends ServiceImpl<InvCountingItemMapper, InvCountingItem> implements InvCountingItemService {

	@Resource
	private InvCountingItemMapper invCountingItemMapper;


    @Override
    public List<InvCountingItem> findByInvCountingId(Long invCountingId) {
        return this.list(new LambdaQueryWrapper<InvCountingItem>().eq(InvCountingItem::getInvCountingId, invCountingId));
    }
}
