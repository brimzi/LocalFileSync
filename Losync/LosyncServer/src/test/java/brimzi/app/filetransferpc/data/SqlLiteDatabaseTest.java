package brimzi.app.filetransferpc.data;
import brimzi.app.filetransferpc.data.DatabaseSchema;
import brimzi.app.filetransferpc.data.SqlLiteDatabase;
import brimzi.app.filetransferpc.data.LocalDatabase;
import java.io.File;
import java.nio.file.Files;
import java.sql.*;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class SqlLiteDatabaseTest {
	static LocalDatabase localDatabase=new SqlLiteDatabase();
	@BeforeClass
	public static void setUp() throws Exception {
		File db=new File(DatabaseSchema.DB_NAME+".db");
		Files.deleteIfExists(db.toPath());
	}

	@Test
	public void testCreateDatabase() throws SQLException {
		Connection conn=SqlLiteDatabase.getDbConnection();
		assertFalse("Database creation failed", conn.isClosed());
		conn.close();
	}
	
	@Test
	public void testCreateTables() {
	 assertTrue("Table creation failed",localDatabase.createTables());
	}
//TODO Test the class instead not SQLite!!!! 
	@Test
	public void testSessionTable() throws SQLException {
		Connection conn=SqlLiteDatabase.getDbConnection();
		java.util.Date now=new java.util.Date();
		String insertion_sql = "INSERT INTO "+DatabaseSchema.Session.NAME+"(" +
				DatabaseSchema.Session.COLUMN_USERPROFILE+"," + 
				DatabaseSchema.Session.COLUMN_SESSIONID+"," + 
				DatabaseSchema.Session.COLUMN_TIMESTAMP+"," + 
				DatabaseSchema.Session.COLUMN_STATUS+") VALUES('TestProfile','TestSessionId',"+now.getTime()+",1)";
		Statement st=conn.createStatement();
		st.executeUpdate(insertion_sql);
		
		String select_sql="select "+DatabaseSchema.Session.COLUMN_TIMESTAMP+" FROM "+DatabaseSchema.Session.NAME ;
		ResultSet res=st.executeQuery(select_sql);
		assert(res.next());
		long rTS=res.getLong(1);
		
		assertEquals("time stamp values differ",now.getTime(), rTS);
		st.close();conn.close();
	}
	
	@Test
	public void testProfilesTable()throws SQLException{
		Connection conn=SqlLiteDatabase.getDbConnection();
		String profile="TestProfile";
		String insertion_sql = "INSERT INTO "+DatabaseSchema.Profiles.NAME+"(" +
				DatabaseSchema.Profiles.COLUMN_USERPROFILE+"," + 
				DatabaseSchema.Profiles.COLUMN_PASSWORD+"," + 
				DatabaseSchema.Profiles.COLUMN_FRIENDLY_NAME+ 
				") VALUES('"+profile+"','TestPassword','Nice Name')";
		
		Statement st=conn.createStatement();
		st.executeUpdate(insertion_sql);
		
		String select_sql="select "+DatabaseSchema.Profiles.COLUMN_USERPROFILE+" FROM "+DatabaseSchema.Profiles.NAME ;
		ResultSet res=st.executeQuery(select_sql);
		assert(res.next());
		String resVal=res.getString(DatabaseSchema.Profiles.COLUMN_USERPROFILE);
		assertEquals("Profile names differ",resVal,profile );
		
		st.close();
		conn.close();
	}
	
	@Test
	public void testfilesmetadataTable()throws SQLException{
		Connection conn=SqlLiteDatabase.getDbConnection();
		String path="TestPath",owner="TestOwner",storageid="TestStorageId",storageMeta="TestJson";
		String insertion_sql =String.format("INSERT INTO %s(%s,%s,%s,%s) VALUES(?,?,?,?)",
				DatabaseSchema.FilesMetadata.NAME,
				DatabaseSchema.FilesMetadata.COLUMN_PATH,
				DatabaseSchema.FilesMetadata.COLUMN_OWNER,
				DatabaseSchema.FilesMetadata.COLUMN_STORAGEID,
				DatabaseSchema.FilesMetadata.COLUMN_STORAGE_META);
		
		PreparedStatement st=conn.prepareStatement(insertion_sql);
		st.setString(1, path);
		st.setString(2, owner);
		st.setString(3, storageid);
		st.setString(4, storageMeta);
		st.executeUpdate();
		st.close();
		
		String select_sql=String.format("select * FROM %s ",DatabaseSchema.FilesMetadata.NAME);
		Statement st2=conn.createStatement();
		ResultSet res=st2.executeQuery(select_sql);
		assert(res.next());
		String resPath=res.getString(DatabaseSchema.FilesMetadata.COLUMN_PATH);
		String resOwner=res.getString(DatabaseSchema.FilesMetadata.COLUMN_OWNER);
		String resStorageid=res.getString(DatabaseSchema.FilesMetadata.COLUMN_STORAGEID);
		String resstorageMeta=res.getString(DatabaseSchema.FilesMetadata.COLUMN_STORAGE_META);
		assertEquals("path names differ",path,resPath);
		assertEquals("owner names differ",owner,resOwner );
		assertEquals("storage names differ",storageid,resStorageid );
		assertEquals("storage names differ",storageMeta,resstorageMeta );
		
		st.close();
		conn.close();
	}

}
