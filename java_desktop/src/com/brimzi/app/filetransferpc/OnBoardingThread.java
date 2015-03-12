package com.brimzi.app.filetransferpc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class OnBoardingThread extends Thread {
	private static final int BUFF_SIZE = 512;
	DatagramSocket socket;
	AppStateProvider app;
	

	public OnBoardingThread(AppStateProvider app) {
		this.app=app;
	}

	public void run() {
		// read config
		int port = 8888;
		// open port
		try {
			socket = new DatagramSocket(port, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);
			while (app.getApplicationState()==AppState.APP_STATE_RUNNING) {
				byte[] buf = new byte[BUFF_SIZE];
				DatagramPacket packet = new DatagramPacket(buf, BUFF_SIZE);

				socket.receive(packet);

				String message = deserializePacket(packet);

				if (message == null || message.trim().equals(""))
					continue;
				
				InetAddress from=packet.getAddress();
				int fromPort=packet.getPort();

				processRequest(message,from,fromPort,socket);

			}

		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void processRequest(String message,InetAddress fromAdd,int fromPort,DatagramSocket socket) throws IOException {
		String reply = AppConfiguration.getServerConnAddress();
		byte[] dataToSend=reply.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(dataToSend,dataToSend.length,fromAdd,fromPort);
		socket.send(sendPacket);
	}

	private String deserializePacket(DatagramPacket packet) {
		String msg = new String(packet.getData());
		return validOnBoardMsg(msg) ? msg : null;
	}

	private boolean validOnBoardMsg(String msg) {
		// TODO Auto-generated method stub
		return true;
	}

}
