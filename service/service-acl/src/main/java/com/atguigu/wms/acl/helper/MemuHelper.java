package com.atguigu.wms.acl.helper;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.wms.model.acl.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 根据权限数据构建登录用户左侧菜单数据
 * </p>
 *
 * @author Dunston
 * @since 2019-11-11
 */
public class MemuHelper {

    /**
     * 构建菜单
     * @param treeNodes
     * @return
     */
    public static List<JSONObject> bulid(List<Permission> treeNodes) {
        List<JSONObject> meuns = new ArrayList<>();
        if(treeNodes.size() == 1) {
            Permission topNode = treeNodes.get(0);
            //左侧一级菜单
            List<Permission> oneMeunList = topNode.getChildren();
            for(Permission one :oneMeunList) {
                JSONObject oneMeun = new JSONObject();
                oneMeun.put("name", one.getName());
                oneMeun.put("code", one.getCode());

                List<JSONObject> children = new ArrayList<>();
                List<Permission> twoMeunList = one.getChildren();
                for(Permission two :twoMeunList) {
                    JSONObject twoMeun = new JSONObject();
                    twoMeun.put("name", two.getName());
                    twoMeun.put("code", two.getCode());
                    children.add(twoMeun);

                    List<JSONObject> threeChildren = new ArrayList<>();
                    List<Permission> threeMeunList = two.getChildren();
                    for(Permission three :threeMeunList) {

                        JSONObject threeMeun = new JSONObject();
                        threeMeun.put("name", three.getName());
                        threeMeun.put("code", three.getCode());
                        threeMeun.put("toCode", three.getToCode());
                        threeChildren.add(threeMeun);
                    }
                    twoMeun.put("buttons", threeChildren);
                }
                oneMeun.put("children", children);
                meuns.add(oneMeun);
            }
        }
        return meuns;
    }
}
