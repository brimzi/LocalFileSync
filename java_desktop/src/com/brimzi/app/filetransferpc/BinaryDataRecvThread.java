package com.brimzi.app.filetransferpc;

import org.zeromq.ZMQ;

import com.brimzi.app.filetransferpc.data.DataStoreProvider;

public class BinaryDataRecvThread extends Thread{


	private ZMQ.Context context;
	private ZMQ.Socket socket;
	public  BinaryDataRecvThread(ZMQ.Context context){
		this.context=context;
	}
	
	@Override
	public void run(){
		socket= context.socket(ZMQ.PULL);
		socket.bind("tcp://*:"+AppConfiguration.getServerBinDataPort());
		
		while(!Thread.currentThread().isInterrupted()){
			//receive data here
			byte[] data =socket.recv();
			
			DataMessage dataMsg=DataMessage.newDataMessage(data);
			
			if(data==null)
				continue;
			//process 
			boolean done=processData(dataMsg);
			
			if(done){
				//we are happy
			}
			
		}
		
		socket.close();
	}

	

	private boolean processData(DataMessage data) {
		// TODO save or buffer ,most likely fill will be spit into parts while may arrive in an unordered manner
		
		return DataStoreProvider.saveFile(data);
	}

	

	
}
