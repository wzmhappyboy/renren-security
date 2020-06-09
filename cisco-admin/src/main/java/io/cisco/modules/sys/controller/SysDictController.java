/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.cisco.modules.sys.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.cisco.common.utils.R;
import io.cisco.common.validator.ValidatorUtils;
import io.cisco.modules.sys.entity.SysDictEntity;
import io.cisco.modules.sys.service.SysDictService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 数据字典
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("sys/dict")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:dict:list")
    public R list(@RequestParam Map<String, Object> params){
        int page = Integer.parseInt(params.get("page").toString());
        int limit = Integer.parseInt(params.get("limit").toString());
        PageHelper.startPage(page,limit);


        PageInfo<SysDictEntity> list = sysDictService.queryPage(params);
        int r = (int)list.getTotal();
        int sum = (int) Math.floor(r/limit)+1;
        return R.ok().put("page", list).put("totalCount",r).put("currPage",page).put("totalPage",sum);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public R info(@PathVariable("id") Long id){
        SysDictEntity dict = sysDictService.getById(id);

        return R.ok().put("dict", dict);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public R save(@RequestBody SysDictEntity dict){
        //校验类型
        ValidatorUtils.validateEntity(dict);

        sysDictService.save(dict);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public R update(@RequestBody SysDictEntity dict){
        //校验类型
        ValidatorUtils.validateEntity(dict);

        sysDictService.updateById(dict);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public R delete(@RequestBody Long[] ids){
        sysDictService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
