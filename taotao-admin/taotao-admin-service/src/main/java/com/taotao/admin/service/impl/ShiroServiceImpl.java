package com.taotao.admin.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.taotao.admin.entity.SysMenu;
import com.taotao.admin.entity.SysUser;
import com.taotao.admin.mapper.SysMenuMapper;
import com.taotao.admin.mapper.SysUserMapper;
import com.taotao.admin.service.ShiroService;
import com.taotao.common.utils.Constant;

@Component("shiroService")
@Service(interfaceClass = ShiroService.class)
public class ShiroServiceImpl implements ShiroService {

	private static final String SESSION_ID = "sessionId:";
	
	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private SysMenuMapper sysMenuMapper;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public String createToken(SysUser adminUser) {
		adminUser.setPassword("");
		adminUser.setSalt("");
		String token = UUID.randomUUID().toString().replace("-", "");
		redisTemplate.opsForValue().set(SESSION_ID + token, JSON.toJSONString(adminUser), 12, TimeUnit.HOURS);
		return token;
	}

	@Override
	public SysUser getByToken(String token) {
		String value = redisTemplate.opsForValue().get(SESSION_ID + token);
		return JSON.parseObject(value, SysUser.class);
	}

	@Override
	public boolean remove(String token) {
		return redisTemplate.delete(SESSION_ID + token);
	}
	
	@Override
    public Set<String> getUserPermissions(long userId) {
		List<String> permsList;
		if(userId == Constant.SUPER_ADMIN) {
			List<SysMenu> list = sysMenuMapper.selectAll();
			permsList = list.stream().map(m -> m.getPerms()).collect(Collectors.toList());
		}else {
			permsList = sysUserMapper.queryAllPerms(userId);
		}
		return permsList.stream().filter(p -> StringUtils.isNotBlank(p)).map(p -> Arrays.asList(p.trim().split(","))).collect(HashSet::new, Set::addAll, Set::addAll);
    }

}
