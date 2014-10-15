package com.brimzi.app.filetransferpc.data;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class SqlLiteDatabase {
	
	 static Connection getDbConnection() {
		Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:"+DatabaseSchema.DB_NAME+".db");
	    } catch ( SQLException e ) {
	    	e.printStackTrace();
	    } catch (ClassNotFoundException e) {
			e.printStackTrace();
		} 
	    return c;
	}
	
	
	 static boolean createTables(){
		boolean rtVal=false;
		Connection conn=getDbConnection();
		String session_table_sql = "CREATE TABLE IF NOT EXISTS Session(" +
				DatabaseSchema.Session.COLUMN_ID+" INTEGER PRIMARY KEY  NOT NULL," +
				DatabaseSchema.Session.COLUMN_USERPROFILE+" TEXT    NOT NULL, " + 
				DatabaseSchema.Session.COLUMN_SESSIONID+" TEXT     NOT NULL, " + 
				DatabaseSchema.Session.COLUMN_TIMESTAMP+" timestamp REAL, " + 
				DatabaseSchema.Session.COLUMN_STATUS+" INTEGER)";
		
		String profiles_table_sql ="CREATE TABLE IF NOT EXISTS "+DatabaseSchema.Profiles.NAME+"(" +
				DatabaseSchema.Profiles.COLUMN_ID+" INTEGER PRIMARY KEY  NOT NULL," +
				DatabaseSchema.Profiles.COLUMN_USERPROFILE+" TEXT    NOT NULL, " + 
				DatabaseSchema.Profiles.COLUMN_PASSWORD+" TEXT     NOT NULL, " + 
				DatabaseSchema.Profiles.COLUMN_FRIENDLY_NAME+" timestamp REAL " + 
				")";
		try{
			Statement st=conn.createStatement();
			st.addBatch(session_table_sql);
			st.addBatch(profiles_table_sql);
			st.executeBatch();
			rtVal=true;
			st.close();

		}catch(SQLException e){
			//TODO:Do something useful here
			e.printStackTrace();
		}finally{
			try {
				conn.close();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		return rtVal;
	}


	public static boolean insertSession(String userProfile, String sessionId) {
		boolean rtVal=false;
		String insertsql="INSERT INTO Session(userprofile,sessionid,timestamp,status) VALUES(?,?,?,?)";
		Connection conn=getDbConnection();
		try{
			PreparedStatement st=conn.prepareStatement(insertsql);
			st.setString(1, userProfile);
			st.setString(2, sessionId);
			long  date = new java.util.Date().getTime();
			st.setLong(3, date);
			st.setInt(4, DatabaseSchema.Session.codes.SESSION_OPEN.ordinal());
			st.executeUpdate();
			rtVal=true;
			st.close();
		}catch(SQLException e){
			
		}finally{
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		
		return rtVal;
		
	}


	


	public static boolean closeSession(String sessionId) {
		boolean rtVal=false;
		String insertsql="UPDATE Session SET "+DatabaseSchema.Session.COLUMN_STATUS+
				 "= ? WHERE "+DatabaseSchema.Session.COLUMN_SESSIONID+"="+sessionId;
		Connection conn=getDbConnection();
		try{
			PreparedStatement st=conn.prepareStatement(insertsql);
			st.setInt(1,DatabaseSchema.Session.codes.SESSION_CLOSED.ordinal());
			st.executeUpdate();
			rtVal=true;
			st.close();
		}catch(SQLException e){
			
		}finally{
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		
		
		return rtVal;
	}


	public static boolean isSessionActive(String syncSessionId) {
		boolean rtVal=false;
		String query = "SELECT "+DatabaseSchema.Session.COLUMN_STATUS +" from "+
				DatabaseSchema.Session.NAME +" WHERE " +DatabaseSchema.Session.COLUMN_SESSIONID+
				" = '" +syncSessionId+"'";
		Connection conn=getDbConnection();
		try{
		Statement st=conn.createStatement();
		ResultSet res=st.executeQuery(query);
		
		if(!res.next()) rtVal=false;;
		int status=res.getInt(DatabaseSchema.Session.COLUMN_STATUS);
		
		if(status== DatabaseSchema.Session.codes.SESSION_OPEN.ordinal());
			rtVal=true;
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return rtVal;
	}


	public static Map <String,UserProfile> getAllProfiles() {
		Map<String,UserProfile> rtVal=null;
		String query = "SELECT * from "+ //hop we don't have more than 50 users for a local store
				DatabaseSchema.Profiles.NAME ;
		Connection conn=getDbConnection();
		try{
		Statement st=conn.createStatement();
		ResultSet res=st.executeQuery(query);
		rtVal=new HashMap<>();//TODO memory overhead a bit high(hash) but no problem since we have few users
		while(res.next()) {
			String name=res.getString(DatabaseSchema.Profiles.COLUMN_USERPROFILE);
			String pwd=res.getString(DatabaseSchema.Profiles.COLUMN_PASSWORD);
			String friendlyName=res.getString(DatabaseSchema.Profiles.COLUMN_FRIENDLY_NAME);
			rtVal.put(name, new UserProfile(name, pwd, friendlyName));
		}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			try{
				conn.close();
			}catch(SQLException e){
				e.printStackTrace();
			}
		}
		return rtVal;
	}


	


	


	


	
	
	
	

}
