package com.brimzi.app.filetransferpc;

public class AppConfiguration {

	public static String getServerConnAddress() {
		// TODO Auto-generated method stub
		return getServerReqRepPort();
	}

	public static String getServerReqRepPort() {
		// TODO Auto-generated method stub
		return "4242";
	}

	public static String getServerBinDataPort() {
		// TODO Auto-generated method stub
		return "4243";
	}
	
	public static int getOnBoardingPort(){
		return 8888;
	}

	public static String getLocalDataDirectory() {
		// TODO Auto-generated method stub
		return "storage";
	}

	

}
