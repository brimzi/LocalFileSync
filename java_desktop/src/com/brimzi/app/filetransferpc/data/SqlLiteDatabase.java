package com.brimzi.app.filetransferpc.data;
import java.sql.*;


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
		
		try{
			Statement st=conn.createStatement();
			st.addBatch(session_table_sql);
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
			Timestamp  sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
			st.setTimestamp(3, sqlDate);
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


	public static Object getProfile(String userProfile) {
		// TODO Auto-generated method stub
		return null;
	}


	public static boolean closeSession(String sessionId) {
		boolean rtVal=false;
		String insertsql="UPDATE Session SET "+DatabaseSchema.Session.COLUMN_STATUS+
				 "= ? WHERE "+DatabaseSchema.Session.COLUMN_SESSIONID+"="+sessionId;
		Connection conn=getDbConnection();
		try{
			PreparedStatement st=conn.prepareStatement(insertsql);
			st.setInt(4,DatabaseSchema.Session.codes.SESSION_CLOSED.ordinal());
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


	


	


	


	
	
	
	

}
