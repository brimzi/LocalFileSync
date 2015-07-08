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
class FileSession {

    private final ZMQ.Socket socket;
    private int reqOffset;
    private final int maxPieceSize;
    private final String filename;
    private final int size;
    private int currentOffset;
    
    

    public FileSession(ZMQ.Socket socket,String filename,int size,int maxPieceSize) {
        this.socket = socket;
        this.filename=filename;
        this.size=size;
        this.maxPieceSize=maxPieceSize;
    }

    void requestPiece() {

        socket.sendMore(MessageCommands.GET_FILE);
        //socket.sendMore(file.getName);//if multiple clients are connected
        socket.sendMore(Integer.toString(reqOffset));
        socket.send(Integer.toString(maxPieceSize));
        reqOffset += maxPieceSize;
    }

    boolean moreToRequest() {
        
       return reqOffset< size;
    }

    byte[] receivePiece() {
        byte[] p=socket.recv();
        currentOffset+=p.length;
        return p;
    }

    int getExpectedOffset() {
        return currentOffset;
    }

}
