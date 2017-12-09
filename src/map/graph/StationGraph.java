/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import graph.AbstractEdgeWeightedGraph;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import map.data.Station;

/**
 *
 * @author nafi
 */
public class StationGraph extends AbstractEdgeWeightedGraph<Station>{

    private HashSet<Station> stationSet;
    
    //private final HashMap<Station, Set<Station>> mipMap;
    
    
    
    
    
    
    
    @Override
    public boolean containsNode(Station node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isAdjacent(Station from, Station to) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Iterator<Station> nodeIterator(Station from) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double edgeWeight(Station from, Station to) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
