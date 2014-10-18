package com.brimzi.app.filetransferpc;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusObject;

import com.brimzi.app.filetransferpc.data.DataStoreProvider;
import com.brimzi.app.filetransferpc.data.SqlLiteDatabase;

public class TransferMethodService implements TransferMethodInterface,BusObject {
	
	public TransferMethodService(){
		
	}
	
	@Override
	public String currentFiles(String userProfile)throws BusException{
		return DataStoreProvider.getAllFilesForUser(userProfile);
	}

	@Override
	public synchronized String startSyncSession(String userProfile) {
		
		if(!DataStoreProvider.profileExists(userProfile))
			return StatusCode.USER_PROFILE_INVALID.toString();
		
		String sessionId=DataStoreProvider.createNewSession(userProfile);
		
		return  sessionId == null ?StatusCode.ERROR_CREATING_PROFILE.toString():sessionId;
	}

	@Override
	public int cancelSyncSession(String syncSessionId) {
		if(DataStoreProvider.isSessionRunning(syncSessionId)){
			return StatusCode.SESSION_CLOSED.ordinal();
		}
		return DataStoreProvider.cancelSession(syncSessionId)?StatusCode.OK.ordinal():StatusCode.ERROR.ordinal();
	}

	@Override
	public int endSyncSession(String syncSessionId) {
		if(!DataStoreProvider.isSessionRunning(syncSessionId)){
			return StatusCode.SESSION_CLOSED.ordinal();
		}
		return DataStoreProvider.endSyncSession(syncSessionId)?StatusCode.OK.ordinal():StatusCode.ERROR.ordinal();
	}

	

}
