/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.renren.io
 *
 * 版权所有，侵权必究！
 */

package io.cisco.modules.sys.service.impl;

import io.cisco.common.utils.Constant;
import io.cisco.modules.sys.dao.SysMenuDao;
import io.cisco.modules.sys.entity.SysMenuEntity;
import io.cisco.modules.sys.service.SysMenuService;
import io.cisco.modules.sys.service.SysRoleMenuService;
import io.cisco.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("sysMenuService")
public class SysMenuServiceImpl  implements SysMenuService {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleMenuService sysRoleMenuService;
	@Autowired
	private SysMenuDao sysMenuDao;


	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenuEntity> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}
		
		List<SysMenuEntity> userMenuList = new ArrayList<>();
		for(SysMenuEntity menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<SysMenuEntity> queryListParentId(Long parentId) {
		return sysMenuDao.queryListParentId(parentId);
	}

	@Override
	public List<SysMenuEntity> queryNotButtonList() {
		return sysMenuDao.queryNotButtonList();
	}

	@Override
	public List<SysMenuEntity> getUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == Constant.SUPER_ADMIN){
			return getAllMenuList(null);
		}
		
		//用户菜单列表
		List<Long> menuIdList = sysUserService.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}



	@Override
	public void delete(Long menuId){
		//删除菜单
		sysMenuDao.delete(menuId);
		//删除菜单与角色关联
		sysRoleMenuService.remove(menuId);
	}

	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenuEntity> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<SysMenuEntity> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysMenuEntity> getMenuTreeList(List<SysMenuEntity> menuList, List<Long> menuIdList){
		List<SysMenuEntity> subMenuList = new ArrayList<SysMenuEntity>();
		
		for(SysMenuEntity entity : menuList){
			//目录
			if(entity.getType() == Constant.MenuType.CATALOG.getValue()){
				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		
		return subMenuList;
	}

	@Override
	public List<SysMenuEntity> list() {
		return sysMenuDao.list();
	}

	@Override
	public SysMenuEntity getById(long id){
		return  sysMenuDao.getById(id);
	}

	@Override
	public void save(SysMenuEntity sysMenuEntity){

		sysMenuDao.save(sysMenuEntity);
	}

	@Override
	public void updateById(SysMenuEntity sysMenuEntity){
		sysMenuDao.updateById(sysMenuEntity);
	}


}
