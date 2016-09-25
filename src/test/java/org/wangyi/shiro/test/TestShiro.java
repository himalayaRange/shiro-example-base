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
	
	/**
	 * ��֤����
	 * AtLeastOneSuccessfulStrategy ȫ����֤ͨ���ģ�Ĭ��
	 * FirstSuccessfulStrategy ��һ����֤ͨ����
	 * AllSuccessfulStrategy ����ȫ��ͨ����
	 */
	@Test
	public void testBase(){
			Subject subject = login("wy", "123");
			//��ȡƾ֤,�������ö��realm,principals�����AuthenticationInfo�����û�����ͬ,��ϲ�
			PrincipalCollection principals = subject.getPrincipals();//�����ö��realmʱ,���ص��Ǹ�����
			System.out.println(principals.asList());
			System.out.println(principals.getRealmNames());
		
	}
	
	/**
	 * ���Խ�ɫ
	 */
	@Test
	public void testRole(){
		Subject subject=login("wy","123");
		System.out.println(subject.hasRole("r1")); //�Ƿ���ĳ����ɫ
		System.out.println(subject.hasAllRoles(Arrays.asList("r1","r2"))); //�Ƿ����������õ����н�ɫ
		boolean[] hasRoles = subject.hasRoles(Arrays.asList("r1","r2","r3"));
		for(boolean r:hasRoles){
			System.out.print(r+"---");
		}
		
		//checkroleû�з���ֵ�����û�иý�ɫ�׳��쳣
		try {
			subject.checkRole("r3");
		} catch (UnauthorizedException e) {
			System.out.println("û�иý�ɫ��");
		}
	}
	
	/**
	 * ����Ȩ��
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
		System.out.println(subject.isPermitted("classroom:add")); //�Զ����ɫ��Ȩ��
	}
	
	
}
