package file_transfer_pc_testclient;

import java.io.File;
import java.util.Scanner;



public class TestClientapp {
   
    
    public static void main(String args[]){
    	Onboarding on=new Onboarding();
    	on.run();
    	String serveradd=on.getServerAdd();
    	ServerClassClient serverClient=new ServerClassClient(serveradd);
    	serverClient.run();
    	
    	BinaryData d=new BinaryData(serverClient.getBinDataAddress());
    	File file=new File("/home/brimzi/master_thesis/pseudo_code.pdf");
    	if(file.exists())
    		d.run(file);
    	else
    		System.out.println("File does not exist!");
    }

}
