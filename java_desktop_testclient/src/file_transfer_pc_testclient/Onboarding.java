package file_transfer_pc_testclient;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.charset.spi.CharsetProvider;

import com.brimzi.app.filetransferpc.AppConfiguration;


public class Onboarding {
	private static final int BUFF_SIZE = 512;
	private String serverAdd;
	public void run(){
		
		DatagramSocket socket =null; 
		try {
			socket=new DatagramSocket();
			socket.setBroadcast(true);
			byte[] msgTosend="What is the address!".getBytes();
			int port=AppConfiguration.getOnBoardingPort();
			DatagramPacket sendPacket = new DatagramPacket(msgTosend,msgTosend.length,InetAddress.getByName("255.255.255.255"),port);
			socket.send(sendPacket);
			System.out.println(getClass().getName() + ">>> Request packet sent to: 255.255.255.255");
			
			byte[] buf = new byte[BUFF_SIZE];
			DatagramPacket packet = new DatagramPacket(buf, BUFF_SIZE);

			socket.receive(packet);
			serverAdd=packet.getAddress()+":"+new String(packet.getData(),StandardCharsets.UTF_8);
			if(serverAdd.startsWith("/"))
				serverAdd=serverAdd.substring(1).trim();
			System.out.println("Reply: "+serverAdd);
			
			

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	String getServerAdd(){
		return serverAdd;
	}
}
