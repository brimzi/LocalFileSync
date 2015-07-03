/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brimzi.losynccom;

/**
 *
 * @author brimzi
 */
public class FileMeta {
    private final  String name;
    private final int size;

    public FileMeta(String name, int size) {
        this.name = name;
        this.size = size;
    }
    
    public String getName(){
        return name;
    }
    
    public int getSize(){
        return size;
    }
}
