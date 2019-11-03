package com.taotao.admin.web.controller;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.admin.entity.SysMenu;
import com.taotao.admin.service.ShiroService;
import com.taotao.admin.service.SysMenuService;
import com.taotao.common.entity.R;
import com.taotao.common.exce.TaotaoAdminException;
import com.taotao.common.utils.Constant;

@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends BaseController {

	@Reference(interfaceClass = SysMenuService.class)
	private SysMenuService sysMenuService;
	
	@Reference(interfaceClass = ShiroService.class)
	private ShiroService shiroService;
	
	@GetMapping("/nav")
	public R nav() {
		List<SysMenu> menuList = sysMenuService.getUserMenuList(getUserId());
		Set<String> permissions = shiroService.getUserPermissions(getUserId());
		return R.ok().put("menuList", menuList).put("permissions", permissions);
	}
	
	@GetMapping("/list")
	@RequiresPermissions("sys:menu:list")
	public List<SysMenu> list(){
		List<SysMenu> menuList = sysMenuService.queryList();
		menuList.forEach(m -> {
			SysMenu menu = sysMenuService.queryById(m.getParentId());
			m.setParentName(menu.getName());
		});
		return menuList;
	}
	
	@GetMapping("/select")
	@RequiresPermissions("sys:menu:select")
	public R select() {
		List<SysMenu> menuList = sysMenuService.queryNotButtonList();
		
		SysMenu root = new SysMenu();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);
		
		return R.ok().put("menuList", menuList);
	}
	
	/**
	 * 菜单信息
	 */
	@GetMapping("/info/{menuId}")
	@RequiresPermissions("sys:menu:info")
	public R info(@PathVariable("menuId") Long menuId){
		SysMenu menu = sysMenuService.queryById(menuId);
		return R.ok().put("menu", menu);
	}
	
	/**
	 * 保存
	 */
	@PostMapping("/saveOrUpdate")
	@RequiresPermissions({"sys:menu:save", "sys:menu:update"})
	public R saveOrUpdate(@RequestBody SysMenu menu){
		//数据校验
		verifyForm(menu);
		
		sysMenuService.saveOrUpdate(menu);
		
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping("/delete/{menuId}")
	@RequiresPermissions("sys:menu:delete")
	public R delete(@PathVariable("menuId") long menuId){
		if(menuId <= 31){
			return R.error("系统菜单，不能删除");
		}

		//判断是否有子菜单或按钮
		List<SysMenu> menuList = sysMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return R.error("请先删除子菜单或按钮");
		}

		sysMenuService.delete(menuId);

		return R.ok();
	}
	
	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenu menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new TaotaoAdminException("菜单名称不能为空");
		}
		
		if(menu.getParentId() == null){
			throw new TaotaoAdminException("上级菜单不能为空");
		}
		
		//菜单
		if(menu.getType() == Constant.MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new TaotaoAdminException("菜单URL不能为空");
			}
		}
		
		//上级菜单类型
		int parentType = Constant.MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenu parentMenu = sysMenuService.queryById(menu.getParentId());
			parentType = parentMenu.getType();
		}
		
		//目录、菜单
		if(menu.getType() == Constant.MenuType.CATALOG.getValue() ||
				menu.getType() == Constant.MenuType.MENU.getValue()){
			if(parentType != Constant.MenuType.CATALOG.getValue()){
				throw new TaotaoAdminException("上级菜单只能为目录类型");
			}
			return ;
		}
		
		//按钮
		if(menu.getType() == Constant.MenuType.BUTTON.getValue()){
			if(parentType != Constant.MenuType.MENU.getValue()){
				throw new TaotaoAdminException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
	
}
