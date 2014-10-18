package com.brimzi.app.filetransferpc.data;
import java.util.Map;
import java.util.UUID;

public class DataStoreProvider {
	static Map <String,UserProfile> cachedProfiles;
	static LocalDatabase localDatabase;
	 static Map <String,UserProfile> getUsers(){
		 localDatabase=new SqlLiteDatabase();//we can easily switch to another database here
	 
		if(cachedProfiles==null){
		cachedProfiles=localDatabase.getAllProfiles(); 
		
		//TODO REMOVE THIS ONLY TESTING HERE
		//cachedProfiles.put("TestProfile", new UserProfile("TestProfile", "1234", "Test Profile"));
		}
		return cachedProfiles;
	}
	
	public static UserProfile getUser(String userName) {
		
		return getUsers().get(userName);
	}

	public static String createNewSession(String userProfile) {
		// TODO Maybe check if this profile has any ongoing session/interrupted etc and act accordingly
		String sessionId=UUID.randomUUID().toString();
		
		return localDatabase.insertSession(userProfile,sessionId)? sessionId:null;
	}

	public static boolean profileExists(String userProfile) {
		//TODO we can cache the Profile object
		return getUsers().get(userProfile)!= null;
	}

	public static boolean isSessionRunning(String syncSessionId) {
		
		return localDatabase.isSessionActive(syncSessionId);
	}

	
	public static boolean cancelSession(String sessionId) {
		// TODO perform any necessary clean up
		return localDatabase.closeSession(sessionId);
	}

	public static boolean endSyncSession(String sessionId) {
		// TODO perform any necessary clean up
		return localDatabase.closeSession(sessionId);
	}

	public static boolean saveFile(String path,String owner){
		return false;
	}

	public static String getAllFilesForUser(String userProfile) {
		String files= localDatabase.getFilesData(userProfile);
		return files;
	}
	
	

}
