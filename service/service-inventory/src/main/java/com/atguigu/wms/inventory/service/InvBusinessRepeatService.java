package com.atguigu.wms.inventory.service;

import com.atguigu.wms.model.inventory.InvBusinessRepeat;
import com.baomidou.mybatisplus.extension.service.IService;

public interface InvBusinessRepeatService extends IService<InvBusinessRepeat> {

    /**
     *  业务去重判断
     * @param businessNo 全局唯一
     * @param remarks
     * @return true：重复  false：不重复
     */
    Boolean isRepeat(String businessNo, String remarks);
}
