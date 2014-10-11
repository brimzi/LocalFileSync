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

}
