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

	//��¼����
		private Subject login(String username,String password){
			SecurityManager manager=new IniSecurityManagerFactory("classpath:shiro.ini").getInstance();
			SecurityUtils.setSecurityManager(manager);
			Subject subject = SecurityUtils.getSubject();
			UsernamePasswordToken token=new UsernamePasswordToken(username,password);
			try {
				//����subject.login(token)��shiroͨ��SecurityManager����Ϣ�ύ��Authentictor��֤������֤�����realm�л�ȡ��Ĭ����shiro.ini realm,�����Լ�ʵ��realm
				subject.login(token);
				return subject;
			} catch (UnknownAccountException e) {
				System.out.println("�û��������ڣ�");
			}catch(IncorrectCredentialsException e){
				System.out.println("�������");
			}catch (AuthenticationException e) {
				System.out.println("��������");
			}
			return null;
		}
		
		
	@Test
	public void testEncode(){
		System.out.println(new Md5Hash("123","admin").toHex()); //16����
		System.out.println(new Md5Hash("123","admin").toBase64()); //64����
	}
	
	/*
	 * ʹ��shiroĬ�ϵļӼ��ܷ�ʽ��ƥ�� PasswordService
	 */
	@Test
	public void testPasswordService(){
		DefaultPasswordService service=new DefaultPasswordService();
		String str=service.encryptPassword("123");
		String str1=service.encryptPassword("123");
		//���ʹ��PasswordService����֤��ʱ�����ʹ��service.passwordMatcher();��Ϊ��ֵ���ܵ�������ȥ��
		System.out.println(service.passwordsMatch("123", str)); //true
		System.out.println(service.passwordsMatch("123", str1)); //true
	}
	
	
	/*
	 * ���Լ���
	 */
	@Test
	public void testPwd(){
		Subject subject=login("admin", "123");
	}
}
