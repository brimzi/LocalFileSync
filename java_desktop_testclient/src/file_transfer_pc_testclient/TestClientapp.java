package file_transfer_pc_testclient;

import java.util.Scanner;

import org.alljoyn.bus.BusAttachment;
import org.alljoyn.bus.BusException;
import org.alljoyn.bus.BusListener;
import org.alljoyn.bus.Mutable;
import org.alljoyn.bus.ProxyBusObject;
import org.alljoyn.bus.SessionListener;
import org.alljoyn.bus.SessionOpts;
import org.alljoyn.bus.Status;



public class TestClientapp {
    static { 
        System.loadLibrary("alljoyn_java");
    }
    private static final short CONTACT_PORT=50;
    static BusAttachment mBus;
    
    private static ProxyBusObject mProxyObj;
    private static TransferMethodInterface mSampleInterface;
    
    private static boolean isJoined = false;
    
    static class MyBusListener extends BusListener {
        public void foundAdvertisedName(String name, short transport, String namePrefix) {
            System.out.println(String.format("BusListener.foundAdvertisedName(%s, %d, %s)", name, transport, namePrefix));
            short contactPort = CONTACT_PORT;
            SessionOpts sessionOpts = new SessionOpts();
            sessionOpts.traffic = SessionOpts.TRAFFIC_MESSAGES;
            sessionOpts.isMultipoint = false;
            sessionOpts.proximity = SessionOpts.PROXIMITY_ANY;
            sessionOpts.transports = SessionOpts.TRANSPORT_ANY;
            
            Mutable.IntegerValue sessionId = new Mutable.IntegerValue();
            
            mBus.enableConcurrentCallbacks();
            
            Status status = mBus.joinSession(name, contactPort, sessionId, sessionOpts,    new SessionListener());
            if (status != Status.OK) {
                return;
            }
            System.out.println(String.format("BusAttachement.joinSession successful sessionId = %d", sessionId.value));
            
            mProxyObj =  mBus.getProxyBusObject("com.brimzi.app.filetransferpc.server",
                                                "/transferService",
                                                sessionId.value,
                                                new Class<?>[] { TransferMethodInterface.class});

            mSampleInterface = mProxyObj.getInterface(TransferMethodInterface.class);
            isJoined = true;
            
        }
        public void nameOwnerChanged(String busName, String previousOwner, String newOwner){
            if ("com.brimzi.app.filetransferpc.server".equals(busName)) {
                System.out.println("BusAttachement.nameOwnerChagned(" + busName + ", " + previousOwner + ", " + newOwner);
            }
        }
        
    }
    
    public static void main(String[] args) {
        mBus = new BusAttachment("AppName", BusAttachment.RemoteMessage.Receive);
        SRPLogonAuthClient authListener = new SRPLogonAuthClient();
        Status status = mBus.registerAuthListener("ALLJOYN_SRP_LOGON", authListener);
        if (status != Status.OK) {
        	System.out.println("Registering Auth Listner failed!");
        return;
        }
        
        BusListener listener = new MyBusListener();
        mBus.registerBusListener(listener);
        
         status = mBus.connect();
        if (status != Status.OK) {
            return;
        }
        
        System.out.println("BusAttachment.connect successful on " + System.getProperty("org.alljoyn.bus.address"));
        
        status = mBus.findAdvertisedName("com.brimzi.app.filetransferpc.server");
        if (status != Status.OK) {
            return;
        }
        System.out.println("BusAttachment.findAdvertisedName successful " + "com.brimzi.app.filetransferpc.server");
        
        while(!isJoined) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Program interupted");
            }
        }
        
        try {
        	Scanner sc=new Scanner(System.in);
        	String sessionId= mSampleInterface.startSyncSession("TestProfile");
            System.out.println("Session Create: " +sessionId);
            String next=sc.nextLine();
            
            System.out.println("Session close : " + mSampleInterface.endSyncSession(sessionId));
            System.out.println("press return to exit");
            String next2=sc.nextLine();
            sc.close();

        } catch (Exception e1) {
            e1.printStackTrace();
        }

        

    }

}
