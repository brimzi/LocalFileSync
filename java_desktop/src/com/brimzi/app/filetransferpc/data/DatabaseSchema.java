package com.brimzi.app.filetransferpc.data;

public class DatabaseSchema {
	public static final String DB_NAME="filetransfer";
	
	public static class Session{
		static enum codes{SESSION_CLOSED,SESSION_OPEN}
		static final String NAME="session";
		static final String COLUMN_ID="id";
		static final String COLUMN_USERPROFILE="userprofile";
		static final String COLUMN_SESSIONID="sessionid";
		static final String COLUMN_TIMESTAMP="timestamp";
		static final String COLUMN_STATUS="status";
	}
	
	public static class Profiles{
		static final String NAME="profiles";
		static final String COLUMN_ID="id";
		static final String COLUMN_USERPROFILE="userprofile";
		static final String COLUMN_PASSWORD="password";
		static final String COLUMN_FRIENDLY_NAME="friendlyname";
	}
	
	public static class FilesMetadata{
		static final String NAME="filesMetadata";
		static final String COLUMN_ID="id";
		static final String COLUMN_PATH="path";
		static final String COLUMN_OWNER="ownerprofile";
		static final String COLUMN_STORAGEID="filesMetadata";
		static final String COLUMN_STORAGE_META="storagemeta";
	}

}
