package org.wangyi.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class TestEncode {

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
		
		
	@Test
	public void testEncode(){
		System.out.println(new Md5Hash("123","admin").toHex()); //16进制
		System.out.println(new Md5Hash("123","admin").toBase64()); //64进制
	}
	
	/*
	 * 使用shiro默认的加加密方式和匹配 PasswordService
	 */
	@Test
	public void testPasswordService(){
		DefaultPasswordService service=new DefaultPasswordService();
		String str=service.encryptPassword("123");
		String str1=service.encryptPassword("123");
		//如果使用PasswordService，验证的时候必须使用service.passwordMatcher();因为颜值加密到数据中去了
		System.out.println(service.passwordsMatch("123", str)); //true
		System.out.println(service.passwordsMatch("123", str1)); //true
	}
	
	
	/*
	 * 测试加密
	 */
	@Test
	public void testPwd(){
		Subject subject=login("admin", "123");
	}
}
