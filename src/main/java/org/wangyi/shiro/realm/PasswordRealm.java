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
 * �������
 */
public class PasswordRealm extends AuthorizingRealm {

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		return null;
	}

	//��֤
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		//���ƾ֤�����뷵��ʱ���һ��ʼ��������бȽϣ������ͬ�Ĳŷ�����֤ͨ����������֤ʧ��
		/*
		 *  shiro�Լ��ļ��ܷ�ʽ
		 *  String p="$shiro1$SHA-256$500000$Fxb/0mvf2Fluxkm0EOf/Cw==$VB+PYZ4DvGcCfl7aJL204XWhihGfnuYV8wKMv2uHHTY=";
		 *  return new SimpleAuthenticationInfo("23324523423@qq.com", p ,getName());
		 */
		//�Զ�����ܷ�ʽ
		String salt="admin";  //��ֵ
		String p="0192023a7bbd73250516f069df18b500"; //shiro���ܺ�Ĭ����16���б��룬����Լ�����base64���룬��Ҫ��shiro.ini�е����������н�������
		SimpleAuthenticationInfo info=new SimpleAuthenticationInfo("23324523423@qq.com", p ,getName());
		info.setCredentialsSalt(ByteSource.Util.bytes(salt));   //������֤����ֵ	
		return info;
	}

}
