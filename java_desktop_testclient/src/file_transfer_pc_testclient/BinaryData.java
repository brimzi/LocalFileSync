package file_transfer_pc_testclient;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.zeromq.ZMQ;

import com.brimzi.app.filetransferpc.AppConfiguration;
import com.brimzi.app.filetransferpc.DataMessage;
public class BinaryData {
	String address;
	BinaryData(String add){
		this.address=add;
	}

	public void run(File file){
		ZMQ.Context context= ZMQ.context(1);
		ZMQ.Socket socket=context.socket(ZMQ.PUSH);
		
		socket.connect("tcp://"+address);
		try {
			FileInputStream input=new FileInputStream(file);
			byte[] data=new byte[102800];
			input.read(data);
			DataMessage msg=DataMessage.newBlankDataMessage();
			msg.setData(data);
			msg.setFileName(file.getName());
			msg.setDataSize(data.length);
			msg.setSessionID("Test Session");
			 
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream objOutput = new ObjectOutputStream(byteOut);
			
            objOutput.writeObject(msg);
            byte[] msgBytes=byteOut.toByteArray();
			socket.send(msgBytes);
			System.out.println("Sent out file: "+file.getName()+" to "+address);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		socket.close();
		context.term();
		
		
	}
}
