package com.brimzi.app.filetransferpc;

import org.zeromq.ZMQ;

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
			
			//process or delegate kaya
			processData(data);
			
		
		}
		
		socket.close();
	}

	private void processData(byte[] data) {
		// TODO save or buffer ,most likely fill will be spit into parts while may arrive in an unordered manner
		
	}
}
