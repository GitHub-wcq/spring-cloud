package com.wcq.springcloud.servicea.controller;

import com.wcq.springcloud.servicea.entity.SysAdmin;
import com.wcq.springcloud.servicea.service.SysAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(value = "服务A模块", description = "服务A的接口")
@RestController
@RequestMapping("/service-a")
public class ServiceAController {

    @Autowired
    SysAdminService sysAdminService;

    @ApiOperation(value = "getHello请求")
    @ApiImplicitParam(name = "name", value = "name", dataType = "String",required = true, paramType = "query")
    @GetMapping("/hello")
    public String getHello(String name){
        return "hello : " + name;
    }

    @ApiOperation(value = "saveUser请求")
    @PostMapping("/saveUser")
    public String  saveUser(@RequestBody SysAdmin entity){
        return sysAdminService.saveSysAdmin(entity) + "";
    }

    @ApiOperation(value = "getById请求")
    @ApiImplicitParam(name = "id", value = "id", dataType = "Integer",required = true, paramType = "query")
    @GetMapping("/getById")
    public SysAdmin getById(@RequestParam(name = "id",required = true) Integer id){
        return sysAdminService.getById(id);
    }

    @ApiOperation(value = "updateSysAdmin请求")
    @PostMapping("/updateSysAdmin")
    public String updateSysAdmin(@RequestBody SysAdmin entity){
        return sysAdminService.updateSysAdmin(entity) + "";
    }



}
