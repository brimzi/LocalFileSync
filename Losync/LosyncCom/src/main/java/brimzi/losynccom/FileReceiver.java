/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brimzi.losynccom;

import org.zeromq.ZMQ;

/**
 *
 * @author brimzi
 */
public class FileReceiver {
    
    private final ZMQ.Context context;
    private final String address;
    private ZMQ.Socket socket;

    public FileReceiver(String address,ZMQ.Context context) {
        this.address = address;
        this.context=context;
        socket= context.socket(ZMQ.PAIR);
    }
    
    
    public void receiveFile(String filename){
        
        while(true){
            
        }
        
    }
    
    
}
