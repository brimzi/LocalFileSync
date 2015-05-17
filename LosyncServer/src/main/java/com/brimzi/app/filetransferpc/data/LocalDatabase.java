package com.brimzi.app.filetransferpc.data;

import java.util.Map;

/**
 * This represents the database used for local storage.
 *
 */
interface LocalDatabase {
	 /**
	 * @return
	 * 		true if the tables were created successfully
	 */
	boolean createTables();
	/**
	 * @param userProfile
	 * 	the id of the user profile	
	 * @param sessionId
	 * 	id of this session
	 * @return
	 * 	true if the session was inserted successfully
	 */
	boolean insertSession(String userProfile, String sessionId);
	/**
	 * @param sessionId
	 * @return
	 *  true if the session was closed
	 */
	boolean closeSession(String sessionId);
	/**
	 * @param syncSessionId
	 * @return
	 * 	true if session is active
	 */
	boolean isSessionActive(String syncSessionId);
	/**
	 * @return
	 *  
	 */
	Map<String, UserProfile> getAllProfiles();
	/**
	 * @param owner
	 * @param path
	 * @param storageId
	 * @param storageMetadata
	 * @return
	 */
	boolean insertFilesMetadata(String owner,String path,String storageId,String storageMetadata);
	
	/*
	 * @param userProfile
	 * @return 
	 * 	files data
	 */
	String getFilesData(String userProfile);
	 
}
