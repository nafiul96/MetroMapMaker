/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package map.trans;

import jtps.jTPS;

/**
 *
 * @author nafi
 */
public class History {
    
    private static jTPS tps;
    
    private  History(){
    
        tps = new jTPS();
    }
    
    
    public static History init(){
    
        return new History();
    }

    public static jTPS getTps() {
        return tps;
    }
    
    
    
}
