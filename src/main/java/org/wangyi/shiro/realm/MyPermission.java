package org.wangyi.shiro.realm;

import org.apache.shiro.authz.Permission;

public class MyPermission implements Permission {
	
	public String resourceId; //资源

	public String operatorId; //操作
	
	public String instanceId; //实例
	
	
	public String getResourceId() {
		return resourceId;
	}



	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}



	public String getOperatorId() {
		return operatorId;
	}



	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}



	public String getInstanceId() {
		return instanceId;
	}



	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	

	@Override
	public String toString() {
		return "MyPermission [resourceId=" + resourceId + ", operatorId="
				+ operatorId + ", instanceId=" + instanceId + "]";
	}



	public MyPermission() {
		super();
	}
	
	public MyPermission(String permissionStr){
		String[] split = permissionStr.split("\\+");
		if(split.length>1){
			this.setResourceId(split[1]);
		}
		if(this.getResourceId()==null||"".equals(this.getResourceId())){
			this.setResourceId("*");
		}
		if(split.length>2){
			this.setOperatorId(split[2]);
		}
		if(split.length>3){
			this.setInstanceId(split[3]);
		}
		if(this.getInstanceId()==null||"".equals(this.getInstanceId())){
			this.setInstanceId("*");
		}
		
		System.out.println(this);
	}


	public boolean implies(Permission p) {
		if(!(p instanceof MyPermission))return false;
		MyPermission mp=(MyPermission)p;
		if(!this.getResourceId().equals("*")&&!mp.getResourceId().equals(this.getResourceId()))return false;
		if(!this.getOperatorId().equals("*")&&!mp.getOperatorId().equals(this.getOperatorId()))return false;
		if(!this.getInstanceId().equals("*")&&!mp.getInstanceId().equals(this.getInstanceId()))return false;
		return true;
	}

	
	
}
