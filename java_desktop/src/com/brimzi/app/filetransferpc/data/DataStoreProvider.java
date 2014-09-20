package com.brimzi.app.filetransferpc.data;
import java.util.Map;
import java.util.UUID;

public class DataStoreProvider {
	
	public static Map<String,char[]> getUsersFromdb(){
		return null;
	}

	public static String createNewSession(String userProfile) {
		// TODO Maybe check if this profile has any ongoing session/interrupted etc and act accordingly
		String sessionId=UUID.randomUUID().toString();
		
		return SqlLiteDatabase.insertSession(userProfile,sessionId)? sessionId:null;
	}

	public static boolean profileExists(String userProfile) {
		//TODO we can cache the Profile object
		return SqlLiteDatabase.getProfile(userProfile)!= null;
	}

	public static boolean isSessionRunning(String syncSessionId) {
		// TODO Auto-generated method stub
		return false;
	}

	public static boolean cancelSession(String sessionId) {
		// TODO perform any necessary clean up
		return SqlLiteDatabase.closeSession(sessionId);
	}

	public static boolean endSyncSession(String sessionId) {
		// TODO perform any necessary clean up
		return SqlLiteDatabase.closeSession(sessionId);
	}
	
	

}
