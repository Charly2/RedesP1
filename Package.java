/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redes2;

import java.io.Serializable;
import java.util.HashMap;


/**
 *
 * @author Wauricio
 */
public class Package implements Serializable {
    private HashMap <Object , Object> fileTree;
    private int buffSizel;
    private boolean isNagle;
    private int time;
    

    public Package(HashMap<Object, Object> fileTree, int buffSizel, boolean isNagle, int time) {
        this.fileTree = fileTree;
        this.buffSizel = buffSizel;
        this.isNagle = isNagle;
        this.time = time;
    }

    public HashMap<Object, Object> getFileTree() {
        return fileTree;
    }

    public int getBuffSizel() {
        return buffSizel;
    }

    public boolean isIsNagle() {
        return isNagle;
    }

    public int getTime() {
        return time;
    }



    
    
    
    
}
