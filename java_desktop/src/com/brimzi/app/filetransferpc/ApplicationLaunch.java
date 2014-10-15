package com.brimzi.app.filetransferpc;
import java.sql.*;

import org.alljoyn.bus.BusAttachment;
import org.alljoyn.bus.BusListener;
import org.alljoyn.bus.Mutable;
import org.alljoyn.bus.SessionOpts;
import org.alljoyn.bus.SessionPortListener;
import org.alljoyn.bus.Status;



public class ApplicationLaunch {

	static { 
        System.loadLibrary("alljoyn_java");
    } 
	
	private static short CONTACT_PORT = 50;
    static boolean sessionEstablished = false;
    static int sessionId;
	 private static class MyBusListener extends BusListener {
	        public void nameOwnerChanged(String busName, String previousOwner, String newOwner) {
	            if ("com.brimzi.app.filetransferpc.server".equals(busName)) {
	                System.out.println("BusAttachement.nameOwnerChanged(" + busName + ", " + previousOwner + ", " + newOwner);
	            }
	        }
	    }

	
	public static void main(String args[]){
        BusAttachment mBus;
        mBus = new BusAttachment("AppName", BusAttachment.RemoteMessage.Receive);

        Status status;

        TransferMethodService mySampleService = new TransferMethodService();

        status = mBus.registerBusObject(mySampleService, "/transferService");
        
        if (status != Status.OK) {  
        	System.out.println("BusAttachment.registerBusObject unsuccessful");
            return;
        }
        System.out.println("BusAttachment.registerBusObject successful");

        BusListener listener = new MyBusListener();
        mBus.registerBusListener(listener);
        SRPLogonAuthListener authListener = new SRPLogonAuthListener();
        status = mBus.registerAuthListener("ALLJOYN_SRP_LOGON", authListener);
        if (status != Status.OK) {
            return;
        }
        
        status = mBus.connect();
        if (status != Status.OK) {

            return;
        }
        System.out.println("BusAttachment.connect successful on " + System.getProperty("org.alljoyn.bus.address"));  
        Mutable.ShortValue contactPort = new Mutable.ShortValue(CONTACT_PORT);

        SessionOpts sessionOpts = new SessionOpts();
        sessionOpts.traffic = SessionOpts.TRAFFIC_MESSAGES;
        sessionOpts.isMultipoint = false;
        sessionOpts.proximity = SessionOpts.PROXIMITY_ANY;
        sessionOpts.transports = SessionOpts.TRANSPORT_ANY;
        
        status = mBus.bindSessionPort(contactPort, sessionOpts, 
                new SessionPortListener() {
            public boolean acceptSessionJoiner(short sessionPort, String joiner, SessionOpts sessionOpts) {
                System.out.println("SessionPortListener.acceptSessionJoiner called");
                if (sessionPort == CONTACT_PORT) {
                    return true;
                } else {
                    return false;
                }
            }
            public void sessionJoined(short sessionPort, int id, String joiner) {
                System.out.println(String.format("SessionPortListener.sessionJoined(%d, %d, %s)", sessionPort, id, joiner));
                sessionId = id;
                sessionEstablished = true;
            }
        });
        if (status != Status.OK) {
            return;
        }
        System.out.println("BusAttachment.bindSessionPort successful");

        int flags = 0; //do not use any request name flags
        status = mBus.requestName("com.brimzi.app.filetransferpc.server", flags);
        if (status != Status.OK) {
            return;
        }
        System.out.println("BusAttachment.request 'com.brimzi.app.filetransferpc.server' successful");

        status = mBus.advertiseName("com.brimzi.app.filetransferpc.server", SessionOpts.TRANSPORT_ANY);
        if (status != Status.OK) {
            System.out.println("Status = " + status);
            mBus.releaseName("com.brimzi.app.filetransferpc.server");
            return;
        }
        System.out.println("BusAttachment.advertiseName 'com.brimzi.app.filetransferpc.server' successful");
		   
		
        while (!sessionEstablished) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("Thead Exception caught");
                e.printStackTrace();
            }
        }
        System.out.println("BusAttachment session established");

        while (true) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                System.out.println("Thead Exception caught");
                e.printStackTrace();
            }
        }
	}

}
