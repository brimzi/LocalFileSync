package com.brimzi.app.filetransferpc;

import org.zeromq.ZMQ;

public class Server extends Thread{
	BinaryDataRecvThread dataRecThread;
	Server(ApplicationLaunch applicationLaunch){
		
	}
	
	public void run(){
		
		//open port
		ZMQ.Context context= ZMQ.context(1);
		dataRecThread=new BinaryDataRecvThread(context);
		ZMQ.Socket reqRepSocket=context.socket(ZMQ.REP);//TODO we need to think about the right type to use here
		reqRepSocket.bind("tcp://*:"+AppConfiguration.getServerReqRepPort());
		
		
		dataRecThread.start();
		while(!Thread.currentThread().isInterrupted()){
			//process short requests from clients,they have to be short
			byte[] packet=reqRepSocket.recv();
			
			String msg = UtilityClass.DeserializeBytes(packet);
			if(!messageValid(msg))
				continue;
				
			String rep = processReq(msg);
			
			reqRepSocket.send(rep);
			
		}
		
		//TODO:You need to tell you children its time to shut down
		dataRecThread.interrupt();
		try {
			dataRecThread.join(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		context.term();
		
	}

	private boolean messageValid(String msg) {
		// TODO Auto-generated method stub
		return true;
	}

	private String processReq(String msg) {
		String[] msgParts=msg.split(UtilityClass.MESSAGE_DELIMETTER);
		String rep=null;
		switch(msgParts[0]){
		case CommConstants.CLOSE_SESSION:
			
			break;
			
		case CommConstants.NEW_USER_CREATION:
			
			break;
		case CommConstants.INIT_SESSION :
			
			break;
			
		case CommConstants.LOGIN:
			
			break;
		
			
		default:
		
			break;
		
		}
		
		return rep;
	}
}
