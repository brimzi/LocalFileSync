package com.brimzi.app.filetransferpc;

import org.zeromq.ZMQ;

import com.brimzi.app.filetransferpc.data.DataStoreProvider;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BinaryDataRecvThread implements Runnable{

        private static final String TAG=BinaryDataRecvThread.class.getName();
	private final ZMQ.Context context;
	private ZMQ.Socket socket;
	public  BinaryDataRecvThread(ZMQ.Context context){
		this.context=context;
		Thread.currentThread().setName(OnBoardingThread.class.getName());
	}
	
	@Override
	public void run(){
            try {
                socket= context.socket(ZMQ.PULL);
                int port =AppConfiguration.getInstance().getServerBinDataPort();
                socket.bind("tcp://*:"+port);
                
                while(!Thread.currentThread().isInterrupted()){
                    
                    byte[] data =socket.recv();
                    if(data==null)
                        continue;
                    DataMessage dataMsg=DataMessage.newDataMessage(data);
                    
                    
                   
                    boolean done=processData(dataMsg);
                    
                    if(done){
                        //we are happy
                        System.out.println("Received and processed file: "+dataMsg.getFileName());
                    }
                    
                }
                socket.close();
            } catch (IOException ex) {
                Logger.getLogger(TAG).log(Level.SEVERE, null, ex);
            }
	}

	

	private boolean processData(DataMessage data) {
		// TODO save or buffer ,most likely fill will be spit into parts while may arrive in an unordered manner
		return DataStoreProvider.saveFile(data);
	}

	

	
}
