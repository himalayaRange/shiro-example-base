package org.wangyi.shiro.test;

import java.util.Arrays;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class TestShiro {

	//登录操作
	private Subject login(String username,String password){
		SecurityManager manager=new IniSecurityManagerFactory("classpath:shiro.ini").getInstance();
		SecurityUtils.setSecurityManager(manager);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token=new UsernamePasswordToken(username,password);
		try {
			//调用subject.login(token)后，shiro通过SecurityManager将信息提交给Authentictor认证器，认证器会从realm中获取，默认是shiro.ini realm,可以自己实现realm
			subject.login(token);
			return subject;
		} catch (UnknownAccountException e) {
			System.out.println("用户名不存在！");
		}catch(IncorrectCredentialsException e){
			System.out.println("密码出错！");
		}catch (AuthenticationException e) {
			System.out.println("其他错误！");
		}
		return null;
	}
	
	/**
	 * 认证策略
	 * AtLeastOneSuccessfulStrategy 全部认证通过的，默认
	 * FirstSuccessfulStrategy 第一个认证通过的
	 * AllSuccessfulStrategy 必须全部通过的
	 */
	@Test
	public void testBase(){
			Subject subject = login("wy", "123");
			//获取凭证,可以配置多个realm,principals中如果AuthenticationInfo返回用户名相同,则合并
			PrincipalCollection principals = subject.getPrincipals();//当配置多个realm时,返回的是个数组
			System.out.println(principals.asList());
			System.out.println(principals.getRealmNames());
		
	}
	
	/**
	 * 测试角色
	 */
	@Test
	public void testRole(){
		Subject subject=login("wy","123");
		System.out.println(subject.hasRole("r1")); //是否含有某个角色
		System.out.println(subject.hasAllRoles(Arrays.asList("r1","r2"))); //是否含有所包含该的所有角色
		boolean[] hasRoles = subject.hasRoles(Arrays.asList("r1","r2","r3"));
		for(boolean r:hasRoles){
			System.out.print(r+"---");
		}
		
		//checkrole没有返回值，如果没有该角色抛出异常
		try {
			subject.checkRole("r3");
		} catch (UnauthorizedException e) {
			System.out.println("没有该角色！");
		}
	}
	
	/**
	 * 测试权限
	 */
	@Test
	public void testPerms(){
		Subject subject = login("wy","123");
		System.out.println(subject.isPermitted("user:create"));
		System.out.println(subject.isPermitted("topic:*"));
		System.out.println(subject.isPermitted("dep:view"));
		System.out.println(subject.isPermitted("classroom:create"));
	}
	@Test
	public void testPerms1(){
		Subject subject = login("zhangsan","111");
		System.out.println(subject.isPermitted("user:create"));
		System.out.println(subject.isPermitted("admin:user:create"));
		System.out.println(subject.isPermitted("test:user:view"));
		
	}
	
	@Test
	public void testMyPermission(){
		Subject subject = login("wy","123");
		System.out.println(subject.isPermitted("+user+create"));
		System.out.println(subject.isPermitted("+topic+delete+1"));
		System.out.println(subject.isPermitted("test:add")); 
		System.out.println(subject.isPermitted("classroom:add")); //自定义角色授权器
	}
	
	
}
