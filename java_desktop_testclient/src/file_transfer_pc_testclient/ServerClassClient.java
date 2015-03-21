package file_transfer_pc_testclient;
import java.nio.charset.StandardCharsets;

import org.zeromq.ZMQ;

import com.brimzi.app.filetransferpc.BinaryDataRecvThread;
import com.brimzi.app.filetransferpc.CommConstants;
public class ServerClassClient {
	private String serverAdd;
	private String dataAddress;
	ServerClassClient(String add){
		this.serverAdd=add;
	}
	public void run(){
		ZMQ.Context context= ZMQ.context(1);
		
		ZMQ.Socket reqRepSocket=context.socket(ZMQ.REQ);
		reqRepSocket.connect("tcp://"+serverAdd);
		
		reqRepSocket.send(CommConstants.DATA_PORT.getBytes(StandardCharsets.UTF_8));
		
		byte[] rep=reqRepSocket.recv();
		
		dataAddress=serverAdd.split(":")[0]+":"+new String(rep,StandardCharsets.UTF_8).trim();
		System.out.println("Received BinData Address: "+dataAddress);
	}
	
	public String getBinDataAddress(){
		return dataAddress;
	}
}
