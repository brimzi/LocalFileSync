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
 * This class is not thread safe
 * @author brimzi
 */
public class FileReceiver {

    private final String address;
    private final ZMQ.Socket socket;
    private final FileStorage storageProvider;

    public FileReceiver(String address, ZMQ.Context context,FileStorage storageProvider) {
        this.address = address;
        socket = context.socket(ZMQ.DEALER);
        this.storageProvider=storageProvider;
        socket.connect(this.address);
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            socket.close();
        }));
    }

    public void recAndSaveFile(FileMeta file) throws Exception {
        int offset = 0;
        int maxPieceSize = getmaxPieceSize();//in bytes
        int piecesInAdvance = getPiecesInAdvance(maxPieceSize);
        boolean start=beginFileTransfer(file.getName(),file.getSize());
        if(!start){
            throw new Exception("Cannot begin the file transfer");//TODO: define appropriate exception
        }
        
        while (true) {
            while (piecesInAdvance > 0) {
                socket.sendMore(MessageCommands.GET_FILE);
                //socket.sendMore(file.getName);//if multiple clients are connected
                socket.sendMore(Integer.toString(offset));
                socket.send(Integer.toString(maxPieceSize));
                offset += maxPieceSize;
                piecesInAdvance--;
            }

            byte[] piece = socket.recv();
            
            if(piece.length==1 && piece[0]==MessageCommands.END){
                //byte[] checksum = socket.recv();we should check that we have a valid file
                commitFile();
                break;
            }

            if (save(offset, piece)) {
                piecesInAdvance++;
            } else {
                //TODO do we give up and throw an exception
                Logger.getLogger(FileReceiver.class.getName()).log(Level.SEVERE,String.format("failed to save file: %s and piece: %d", file.getName(),offset));
            }

        }

    }

    private boolean save(int offset, byte[] piece) {
        return storageProvider.addPiece(offset,piece);
    }

    private boolean beginFileTransfer(String filename,int size) {
        return storageProvider.initFile(filename,size);
    }

    private void commitFile() {
        storageProvider.commitFile();
    }

    private int getmaxPieceSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private int getPiecesInAdvance(int pieceSize) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
