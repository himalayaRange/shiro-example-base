package org.wangyi.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.permission.WildcardPermission;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import com.sun.org.apache.xalan.internal.utils.Objects;

public class StaticRealm extends AuthorizingRealm {

	/**
	 * �û���¼��ɺ��ڴ˴�����
	 * ����������Ȩ��
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		info.addRole("r1");
		info.addRole("r2");
		info.addStringPermission("+user+*");
		info.addObjectPermission(new MyPermission("+topic+create"));
		info.addObjectPermission(new MyPermission("+topic+delete+1"));
		info.addObjectPermission(new WildcardPermission("test:add"));//ʹ��shiro�Լ���permission
		return info;
	}

	/**
	 * ����������֤��
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String username = token.getPrincipal().toString();//�û���
		String password = new String((char[])(token.getCredentials()));
		if(!Objects.equals("wy", username))throw new UnknownAccountException("�û��������ڣ�");
		if(!Objects.equals("123", password))throw new IncorrectCredentialsException("�������");
		System.out.println(username+"---------"+password);
		return new SimpleAuthenticationInfo("2531592408@qq.com", password,getName());
	}

}
