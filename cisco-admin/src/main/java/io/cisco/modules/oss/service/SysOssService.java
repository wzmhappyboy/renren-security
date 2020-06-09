/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.cisco.modules.oss.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import io.cisco.modules.oss.entity.SysOssEntity;


import java.util.List;
import java.util.Map;

/**
 * 文件上传
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface SysOssService {

	List<SysOssEntity> queryPage(Map<String, Object> params);

	void save(SysOssEntity sysOssEntity);

	void removeByIds(List<Long > ids);

}