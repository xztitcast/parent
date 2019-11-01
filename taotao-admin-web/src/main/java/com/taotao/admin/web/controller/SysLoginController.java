package com.taotao.admin.web.controller;

import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.taotao.admin.entity.SysUser;
import com.taotao.admin.form.LoginForm;
import com.taotao.admin.service.SysUserService;
import com.taotao.admin.service.ShiroService;
import com.taotao.common.entity.R;
import com.taotao.common.utils.captcha.Captcha;
import com.taotao.common.utils.captcha.GifCaptcha;

@RestController
@RequestMapping("/sys")
public class SysLoginController extends BaseController {
	
	@Reference(interfaceClass = SysUserService.class)
	private SysUserService sysUserService;
	
	@Reference(interfaceClass = ShiroService.class)
	private ShiroService shiroService;
	
	@Autowired
	private RedisTemplate<String, String> restTemplate;
	
	@GetMapping("/captcha.jpg")
	public void captcha(HttpServletResponse response, @NotBlank(message = "uuid不能为空!") @RequestParam String uuid) throws Exception {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/gif");
		Captcha captcha = new GifCaptcha(138,38,4);
		//输出
		captcha.out(response.getOutputStream());
		
		restTemplate.opsForValue().set(uuid, captcha.text().toLowerCase(), 5, TimeUnit.MINUTES);
	}

	@PostMapping("/login")
	public R login(LoginForm form) {
		String captcha = restTemplate.opsForValue().get(form.getUuid());
		if(StringUtils.isBlank(captcha)) {
			return R.error("验证码已失效!");
		}
		if(!captcha.equalsIgnoreCase(form.getCaptcha())) {
			return R.error("验证码不正确!");
		}
		SysUser user = sysUserService.queryByUserName(form.getUsername());
		if(user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
			return R.error("账号或密码不正确");
		}
		
		if(user.getStatus()) {
			return R.error("账号已被禁用!");
		}
		String token = shiroService.createToken(user);
		return R.ok(token);
	}
	
	@PostMapping("/logout")
	public R logout(HttpServletRequest request) {
		String token = request.getHeader("consoleToken");
		shiroService.remove(token);
		SecurityUtils.getSubject().logout();
		return R.ok();
	}
}
