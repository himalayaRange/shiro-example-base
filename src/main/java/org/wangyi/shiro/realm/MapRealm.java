package org.wangyi.shiro.realm;

import java.util.HashMap;
import java.util.Map;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.Realm;
import com.sun.org.apache.bcel.internal.util.Objects;

/**
 * 自定义Realm
 * @author wangyi
   @date 2016-8-18
 *
 */
public class MapRealm implements Realm{
	private static Map<String,String> users;
	static{
		users=new HashMap<String,String>();
		users.put("laoding", "123");
		users.put("wy", "123");
	}
	public String getName() {
		return "mapRealm";
	}

	public boolean supports(AuthenticationToken token) {
		return token instanceof UsernamePasswordToken;
	}

	public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		String username=(String)token.getPrincipal();//用户名
		String password=new String((char[])(token.getCredentials()));//凭证
		System.out.println(username+"---"+password);
		if(!users.containsKey(username))throw new UnknownAccountException("用户名不存在！");
		if(!Objects.equals(password, users.get(username)))throw new IncorrectCredentialsException("用户密码出错！");
		AuthenticationInfo info=new SimpleAuthenticationInfo(username,password,getName());
		return info;
	}

}
