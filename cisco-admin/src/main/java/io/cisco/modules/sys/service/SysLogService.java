/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.cisco.modules.sys.service;


import com.github.pagehelper.PageInfo;
import io.cisco.modules.sys.entity.SysLogEntity;

import java.util.Map;


/**
 * 系统日志
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysLogService  {

    PageInfo<SysLogEntity> queryPage(Map<String, Object> params);

    void  save(SysLogEntity sysLogEntity);
}
