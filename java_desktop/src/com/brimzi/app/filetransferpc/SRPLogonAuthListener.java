package com.brimzi.app.filetransferpc;

import java.util.Map;
import java.util.HashMap;

import org.alljoyn.bus.AuthListener;


public class SRPLogonAuthListener implements AuthListener {
	static final String SRP_LOGON_MECHANISM="ALLJOYN_SRP_LOGON";
	/*
	 *we are going to use this as a cache of the db knowing that there is only going to be
	 *a few users otherwise using single calls to the db for a particular user would be efficient.
	 */
	static Map<String,char[]> users;

	@Override
	public void completed(String mechanism, String peerName,
			boolean authenticated) {
		
		
	}

	@Override
	public boolean requested(String mechanism, String peerName, int count,
			String userName, AuthRequest[] requests) {
		
		if(mechanism.equals(SRP_LOGON_MECHANISM)){
			if(users==null){
				loadUsers();
			}
				char[] password=users.get(userName);
				if(password!=null){
				for(AuthRequest request:requests){
					if(request instanceof PasswordRequest){
						((PasswordRequest) request).setPassword(password);
						break;
					}
				}
				
			}
		}
			return true;
	}

	private void loadUsers() {
		users=new HashMap<>();
		//TODO: load users from db here and delete below
		
		users.put("user1","user1".toCharArray());
		users.put("user2","user2".toCharArray());
		
		
	}
	

}
