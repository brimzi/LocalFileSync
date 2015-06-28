/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brimzi.app.filetransferpc.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brimzi
 */
public class NetworkUtility {
    
    
    public static int getAvailblePort(){
        try (ServerSocket socket = new ServerSocket(0)) {
            socket.setReuseAddress(true);
            return socket.getLocalPort();
        } catch (IOException ex) {
            Logger.getLogger(NetworkUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
}
