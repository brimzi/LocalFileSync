package com.brimzi.app.filetransferpc;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class DataMessage implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 8748042581242694752L;
	private String fileName;
	private int dataSize;
	private String sessionID;
	private byte[] data;
	
	public static DataMessage newDataMessage(byte[] data){
		ByteArrayInputStream byteIn=new ByteArrayInputStream(data);
		ObjectInputStream objInput;
		DataMessage msg=null;
		try {
			objInput = new ObjectInputStream(byteIn);
			msg=(DataMessage)objInput.readObject();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
        
		return msg;
	}
	public String getFileName(){
		return fileName;
	}

	public int getDataSize(){
		return dataSize;
	}
	
	public String getSessionID(){
		return sessionID;
	}
	public byte[] getData() {
		
		return data;
	}
}
