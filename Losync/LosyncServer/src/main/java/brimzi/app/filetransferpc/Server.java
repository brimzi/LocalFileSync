package brimzi.app.filetransferpc;

import java.nio.charset.StandardCharsets;

import org.zeromq.ZMQ;

public class Server extends Thread{
	BinaryDataRecvThread dataRecThread;
	private AppStateProvider appStateProvider;
	Server(AppStateProvider appStateProvider){
		this.appStateProvider=appStateProvider;
		this.setName(Server.class.getName());
	}
	
	public void run(){
		
		//open port
		ZMQ.Context context= ZMQ.context(1);
		dataRecThread=new BinaryDataRecvThread(context);
		ZMQ.Socket reqRepSocket=context.socket(ZMQ.REP);//TODO we need to think about the right type to use here
		reqRepSocket.bind("tcp://*:"+AppConfiguration.getServerReqRepPort());
		
		
		dataRecThread.start();
		
		while(appStateProvider.getApplicationState() == AppState.APP_STATE_RUNNING){
			//process short requests from clients,they have to be short
			byte[] packet=reqRepSocket.recv();
			
			String msg = UtilityClass.DeserializeBytes(packet);
			if(!messageValid(msg))
				continue;
				
			String rep = processReq(msg);
			packet=rep.getBytes(StandardCharsets.UTF_8);
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
		//String[] msgParts=msg.split(UtilityClass.MESSAGE_DELIMETTER);
		String rep=null;
		//switch(msgParts[0]){
		switch(msg){
		case CommConstants.CLOSE_SESSION:
			
			break;
			
		case CommConstants.NEW_USER_CREATION:
			
			break;
		case CommConstants.INIT_SESSION :
			
			break;
			
		case CommConstants.LOGIN:
			
			break;
		
		case CommConstants.DATA_PORT:
			rep=getDataPortReply();
			break;
			
		default:
		
			break;
		
		}
		
		return rep;
	}

	private String getDataPortReply() {
		// TODO Auto-generated method stub
		return AppConfiguration.getServerBinDataPort();
	}
}
