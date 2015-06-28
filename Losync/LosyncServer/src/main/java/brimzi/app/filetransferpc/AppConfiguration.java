package brimzi.app.filetransferpc;

import brimzi.app.filetransferpc.network.NetworkUtility;
import java.io.IOException;

public class AppConfiguration {

    private int port;
    private static AppConfiguration instance;

    private AppConfiguration() {
        port = -1;
    }

    public static AppConfiguration getInstance(){
        if(instance==null)
            instance=new AppConfiguration();
        
        return instance;
    }
    
    public static String getServerConnAddress() {
        // TODO Auto-generated method stub
        return getServerReqRepPort();
    }

    public static String getServerReqRepPort() {
        // TODO Auto-generated method stub
        return "4242";
    }

    public int getServerBinDataPort() throws IOException {
        int tries=0;
        while (port == -1) {
            port = NetworkUtility.getAvailblePort();
            
            if(port==-1 && tries>=10)
                throw new IOException("Could not find availble port for binary data trans");
        }
        return port;
    }

    public static int getOnBoardingPort() {
        return 8888;
    }

    public static String getLocalDataDirectory() {
        // TODO Auto-generated method stub
        return "storage";
    }

}
