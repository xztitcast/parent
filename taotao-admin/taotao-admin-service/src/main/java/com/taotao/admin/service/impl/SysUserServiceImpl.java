package com.taotao.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.taotao.admin.entity.SysUser;
import com.taotao.admin.mapper.SysUserMapper;
import com.taotao.admin.service.SysUserRoleService;
import com.taotao.admin.service.SysUserService;
import com.taotao.common.entity.P;
import com.taotao.common.exce.TaotaoAdminException;
import com.taotao.common.utils.Constant;

@Component("sysUserService")
@Service(interfaceClass = SysUserService.class)
public class SysUserServiceImpl implements SysUserService {
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	private SysUserRoleService sysUserRoleService;

	@Override
	public P<SysUser> queryList(int pageNum, int pageSize, Long userId) {
		Page<SysUser> page = PageHelper.startPage(pageNum, pageSize);
		if (Constant.SUPER_ADMIN == userId) userId = null;
		sysUserMapper.queryListByUserId(userId);
		return new P<>(page.getTotal(), page.getResult());
	}

	@Override
	public List<String> queryAllPerms(Long userId) {
		return sysUserMapper.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return sysUserMapper.queryAllMenuId(userId);
	}

	@Override
	public SysUser queryByUserName(String username) {
		return sysUserMapper.queryByUserName(username);
	}

	@Override
	public SysUser queryByUserId(Long userId) {
		return sysUserMapper.selectById(userId);
	}

	@Override
	@Transactional
	public void saveOrUpdate(SysUser user) {
		if(user.getUserId() == null) {
			sysUserMapper.insert(user);
		}else {
			sysUserMapper.updateById(user);
		}
		
		//check user's permissions
		checkRole(user);
		
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
		
	}

	@Override
	@Transactional
	public void deleteBatch(Long... userIds) {
		sysUserMapper.deleteByIds(userIds);
	}
	
	private void checkRole(SysUser user) {
		if (CollectionUtils.isEmpty(user.getRoleIdList())) {
			return ;
		}
		
		//if you not a super admin, you need to check if the user's role is self-created
		if(user.getCreateUserId() == Constant.SUPER_ADMIN) {
			return;
		}
		//query the list of roles created by the user
		List<Long> idList = sysUserRoleService.queryRoleIdList(user.getCreateUserId());
		
		//check permission
		if(!idList.containsAll(user.getRoleIdList())) {
			throw new TaotaoAdminException("新增用户所选角色，不是本人创建");
		}
		
	}

	
}
