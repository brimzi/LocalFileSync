package com.brimzi.app.filetransferpc;

import java.util.Map;
import java.util.HashMap;

import org.alljoyn.bus.AuthListener;

import com.brimzi.app.filetransferpc.data.DataStoreProvider;
import com.brimzi.app.filetransferpc.data.UserProfile;


public class SRPLogonAuthListener implements AuthListener {
	static final String SRP_LOGON_MECHANISM="ALLJOYN_SRP_LOGON";
	/*
	 *we are going to use this as a cache of the db knowing that there is only going to be
	 *a few users otherwise using single calls to the db for a particular user would be efficient.
	 */
	

	@Override
	public void completed(String mechanism, String peerName,
			boolean authenticated) {
		
		
	}

	@Override
	public boolean requested(String mechanism, String peerName, int count,
			String userName, AuthRequest[] requests) {
		
		if(mechanism.equals(SRP_LOGON_MECHANISM)){
				UserProfile user=DataStoreProvider.getUser(userName);
				if(user==null)
					return true;
				
				char[] password=user.getPwd();
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

	
	

}
