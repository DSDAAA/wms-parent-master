package com.atguigu.wms.acl.controller;

import com.atguigu.wms.common.result.Result;
import com.atguigu.wms.common.util.MD5;
import com.atguigu.wms.acl.service.RoleService;
import com.atguigu.wms.acl.service.AdminService;
import com.atguigu.wms.model.acl.Admin;
import com.atguigu.wms.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

//import org.springframework.security.access.prepost.PreAuthorize;

/**
 * <p>
 * 用户管理 前端控制器
 * </p>
 *
 * @author Dunston
 * @since 2019-11-08
 */
@RestController
@RequestMapping("/admin/acl/user")
@Api(tags = "用户管理")
//@CrossOrigin //跨域
@Slf4j
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

   // @PreAuthorize("hasAuthority('user.list')")
    @ApiOperation(value = "获取管理用户分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "userQueryVo", value = "查询对象", required = false)
                    AdminQueryVo userQueryVo) {
        Page<Admin> pageParam = new Page<>(page, limit);
        IPage<Admin> pageModel = adminService.selectPage(pageParam, userQueryVo);
        return Result.ok(pageModel);
    }

    //@PreAuthorize("hasAuthority('user.list')")
    @ApiOperation(value = "获取管理用户")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        Admin user = adminService.getById(id);
        return Result.ok(user);
    }

    //@PreAuthorize("hasAuthority('user.add')")
    @ApiOperation(value = "新增管理用户")
    @PostMapping("save")
    public Result save(@RequestBody Admin user) {
        user.setPassword(MD5.encrypt(user.getPassword()));
        adminService.save(user);
        return Result.ok();
    }

    //@PreAuthorize("hasAuthority('user.update')")
    @ApiOperation(value = "修改管理用户")
    @PutMapping("update")
    public Result updateById(@RequestBody Admin user) {
        adminService.updateById(user);
        return Result.ok();
    }

    //@PreAuthorize("hasAuthority('user.remove')")
    @ApiOperation(value = "删除管理用户")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        adminService.removeById(id);
        return Result.ok();
    }

    //@PreAuthorize("hasAuthority('user.remove')")
    @ApiOperation(value = "根据id列表删除管理用户")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        adminService.removeByIds(idList);
        return Result.ok();
    }

    //@PreAuthorize("hasAuthority('user.assgin')")
    @ApiOperation(value = "根据用户获取角色数据")
    @GetMapping("/toAssign/{adminId}")
    public Result toAssign(@PathVariable Long adminId) {
        Map<String, Object> roleMap = roleService.findRoleByUserId(adminId);
        return Result.ok(roleMap);
    }

    //@PreAuthorize("hasAuthority('user.assgin')")
    @ApiOperation(value = "根据用户分配角色")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestParam Long adminId,@RequestParam Long[] roleId) {
        roleService.saveUserRoleRealtionShip(adminId,roleId);
        return Result.ok();
    }

    //@PreAuthorize("hasAuthority('user.assgin')")
    @ApiOperation(value = "给用户分配仓库")
    @PostMapping("/doAssignWarehouse")
    public Result doAssignWarehouse(@RequestParam Long adminId,@RequestParam Long warehouseId) {
        adminService.doAssignWarehouse(adminId,warehouseId);
        return Result.ok();
    }

    @ApiOperation(value = "根据关键字查看")
    @GetMapping("findByKeyword/{keyword}")
    public Result findByKeyword(@PathVariable String keyword) {
        return Result.ok(adminService.findByKeyword(keyword));
    }

    @ApiOperation(value = "获取管理用户")
    @GetMapping("getNameById/{id}")
    public String getNameById(@PathVariable Long id) {
        Admin user = adminService.getById(id);
        return user.getName();
    }
}

