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
	 * 用户登录完成后在此处进行
	 * 用来进行授权的
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
		info.addObjectPermission(new WildcardPermission("test:add"));//使用shiro自己的permission
		return info;
	}

	/**
	 * 用来进行认证的
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String username = token.getPrincipal().toString();//用户名
		String password = new String((char[])(token.getCredentials()));
		if(!Objects.equals("wy", username))throw new UnknownAccountException("用户名不存在！");
		if(!Objects.equals("123", password))throw new IncorrectCredentialsException("密码错误！");
		System.out.println(username+"---------"+password);
		return new SimpleAuthenticationInfo("2531592408@qq.com", password,getName());
	}

}
