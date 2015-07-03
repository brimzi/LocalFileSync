/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brimzi.losynccom;

/**
 * 
 * Not thread safe
 * @author brimzi
 */
public interface FileStorage {

    boolean initFile(String filename, int size);

    public void commitFile();

    public boolean addPiece(int offset, byte[] piece);
    
    void abort();
    
}
