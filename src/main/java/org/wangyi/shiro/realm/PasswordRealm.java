package org.wangyi.shiro.realm;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/*
 * 密码加密
 */
public class PasswordRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		return null;
	}

	//认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		//如果凭证即密码返回时会和一开始的密码进行比较，如果相同的才返回认证通过，反则认证失败
		/*
		 *  shiro自己的加密方式
		 *  String p="$shiro1$SHA-256$500000$Fxb/0mvf2Fluxkm0EOf/Cw==$VB+PYZ4DvGcCfl7aJL204XWhihGfnuYV8wKMv2uHHTY=";
		 *  return new SimpleAuthenticationInfo("23324523423@qq.com", p ,getName());
		 */
		//自定义加密方式
		String salt="admin";  //盐值
		String p="0192023a7bbd73250516f069df18b500"; //shiro加密后默认是16进行编码，如果自己进行base64编码，需要在shiro.ini中的密码属性中进行配置
		SimpleAuthenticationInfo info=new SimpleAuthenticationInfo("23324523423@qq.com", p ,getName());
		info.setCredentialsSalt(ByteSource.Util.bytes(salt));   //设置认证的盐值	
		return info;
	}

}
