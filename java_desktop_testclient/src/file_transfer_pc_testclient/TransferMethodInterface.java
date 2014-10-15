package file_transfer_pc_testclient;

import org.alljoyn.bus.BusException;
import org.alljoyn.bus.annotation.BusInterface;
import org.alljoyn.bus.annotation.BusMethod;
import org.alljoyn.bus.annotation.Secure;



/*
 * TODO Will have to decide if we really need to define our own sync session id when 
 * by virtual of connecting to alljoyn we have a session and unique id
 */
@BusInterface(name = "com.brimzi.app.filetransferpc")
@Secure
public interface TransferMethodInterface {

	public enum StatusCode{
		OK, // Positive response confirming operation was successful
		ERROR,// General response indicating that an error occurred 
		USER_PROFILE_INVALID,
		ERROR_CREATING_PROFILE,
		SESSION_RUNNING,//any session that is currently running 
		SESSION_CLOSED //any session that is not currently on going,it may have been cancelled or completed
	}
	
	@BusMethod
	public String startSyncSession(String userProfile)throws BusException;;
	
	@BusMethod
	public int cancelSyncSession(String syncSessionId)throws BusException;;
	
	@BusMethod
	public int endSyncSession(String syncSessionId)throws BusException;;
	
}

 
