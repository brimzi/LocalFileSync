/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brimzi.losynccom;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.zeromq.ZMQ;

/**
 * Creates a connection to the file server and provides mechanism to fetch files from the server
 * @author brimzi
 */
public class FileReceiver {

    private final ZMQ.Context context;
    private final String address;
    private final ZMQ.Socket socket;

    public FileReceiver(String address, ZMQ.Context context) {
        this.address = address;
        this.context = context;
        socket = context.socket(ZMQ.DEALER);
        socket.connect(address);
    }

    public void receiveFile(String filename) {
        int offset = 0;
        int maxPieceSize = 0;
        int piecesInAdvance = 10;
        boolean start=beginFileTransfer(filename);
        
        while (true) {
            while (piecesInAdvance > 0) {
                socket.sendMore(MessageCommands.GET_FILE);
                //socket.sendMore(filename);
                socket.sendMore(Integer.toString(offset));
                socket.send(Integer.toString(maxPieceSize));
                offset += maxPieceSize;
                piecesInAdvance--;
            }

            byte[] piece = socket.recv();
            
            if(piece.length==1 && piece[0]==MessageCommands.END){
                commitFile();
                break;
            }

            if (save(offset, piece)) {
                piecesInAdvance++;
            } else {
                Logger.getLogger(FileReceiver.class.getName()).log(Level.SEVERE,String.format("failed to save file: %s and piece: %d", filename,offset));
            }

        }

    }

    private boolean save(int offset, byte[] piece) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean beginFileTransfer(String filename) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void commitFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
