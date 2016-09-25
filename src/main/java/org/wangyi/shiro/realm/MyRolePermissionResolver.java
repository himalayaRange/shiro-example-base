package org.wangyi.shiro.realm;

import java.util.Arrays;
import java.util.Collection;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.RolePermissionResolver;
import org.apache.shiro.authz.permission.WildcardPermission;


public class MyRolePermissionResolver implements RolePermissionResolver {

	public Collection<Permission> resolvePermissionsInRole(String roleString) {
		if(roleString.contains("r1")){
			return Arrays.asList((Permission)new WildcardPermission("classroom:*"));
		}
		return null;
	}

}
