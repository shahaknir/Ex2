package tests;


import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import api.*;


import api.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DWGraphTest {

	

	private static Random _rnd = null;

   /* @Test
    public void nodeSize() {
        directed_weighted_graph g = new DWGraph_DS();
        node_data node0 = new NodeData();
        node_data node1 = new NodeData();
        node_data node2 = new NodeData(node1.getKey(),Integer.MAX_VALUE,"",0,null);
        
        g.addNode(node0);
        g.addNode(node1);
        g.addNode(node2);

        g.removeNode(2);
        g.removeNode(1);
        g.removeNode(1);
        int s = g.nodeSize();
        assertEquals(1,s);

    }*/

   @Test
    public void edgeSize() {
    	directed_weighted_graph g = new DWGraph_DS();
    	NodeData node0 = new NodeData();
    	NodeData node1 = new NodeData();
    	NodeData node2 = new NodeData();
    	NodeData node3 = new NodeData();
    	
    	g.addNode(node0);
        g.addNode(node1);
        g.addNode(node2);
        g.addNode(node3);
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(0,1,1);
        int e_size =  g.edgeSize();
        assertEquals(3, e_size);
        EdgeData w03 = (EdgeData) g.getEdge(0,3);
        EdgeData w30 = (EdgeData) g.getEdge(3,0);
        assertNotEquals(w03, w30);
        assertEquals(w03.getDest(), 3);
    }

    /*@Test
    public void getV() {
    	directed_weighted_graph g = new DWGraph_DS();
    	NodeData node0 = new NodeData();
    	NodeData node1 = new NodeData();
    	NodeData node2 = new NodeData();
    	NodeData node3 = new NodeData();
    	
    	g.addNode(node0);
        g.addNode(node1);
        g.addNode(node2);
        g.addNode(node3);
       
        g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.connect(0,1,1);
        Collection<node_data> v = g.getV();
        Iterator<node_data> iter = v.iterator();
        while (iter.hasNext()) {
            node_data n = iter.next();
            assertNotNull(n);
        }
    }*/

   /* @Test
    public void hasEdge() {
        int v = 10, e = v*(v-1)/2;
        DWGraph_DS g = (DWGraph_DS) graph_creator(v,e,1);
        for(int i=0;i<v;i++) {
            for(int j=i+1;j<v;j++) {
            	boolean b = g.hasEdge(i,j);
                assertTrue(b);
                assertTrue(g.hasEdge(j,i));
            }
        }
    }*/

    /*@Test
    public void connect() {
    	DWGraph_DS g = new DWGraph_DS();
    	NodeData node0 = new NodeData();
    	NodeData node1 = new NodeData();
    	NodeData node2 = new NodeData();
    	NodeData node3 = new NodeData();
    	
    	g.addNode(node0);
        g.addNode(node1);
        g.addNode(node2);
        g.addNode(node3);
       
    	g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.removeEdge(0,1);
        assertFalse(g.hasEdge(1,0));
        g.removeEdge(2,1);
        g.connect(0,1,1);
        EdgeData w = (EdgeData) g.getEdge(0,1);
        assertEquals(1,w.getDest());
    }*/


    /*@Test
    public void removeNode() {
    	DWGraph_DS g = new DWGraph_DS();
    	NodeData node0 = new NodeData();
    	NodeData node1 = new NodeData();
    	NodeData node2 = new NodeData();
    	NodeData node3 = new NodeData();
    	
    	g.addNode(node0);
        g.addNode(node1);
        g.addNode(node2);
        g.addNode(node3);
       
    	g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        g.removeNode(4);
        g.removeNode(0);
        assertFalse(g.hasEdge(node1.getKey(),node0.getKey()));
        int e = g.edgeSize();
        assertEquals(0,e);
        assertEquals(3,g.nodeSize());
    }*/

   /* @Test
    public void removeEdge() {
    	DWGraph_DS g = new DWGraph_DS();
    	NodeData node0 = new NodeData();
    	NodeData node1 = new NodeData();
    	NodeData node2 = new NodeData();
    	NodeData node3 = new NodeData();
    	
    	g.addNode(node0);
        g.addNode(node1);
        g.addNode(node2);
        g.addNode(node3);
     
    	g.connect(0,1,1);
        g.connect(0,2,2);
        g.connect(0,3,3);
        double w = g.getEdge(0,3).getWeight();
        g.removeEdge(0,3);
        assertEquals(3,w);
    }*/


    ///////////////////////////////////
    /**
     * Generate a random graph with v_size nodes and e_size edges
     * @param v_size
     * @param e_size
     * @param seed
     * @return
     */
    public static directed_weighted_graph graph_creator(int v_size, int e_size, int seed) {
    	directed_weighted_graph g = new DWGraph_DS();
        _rnd = new Random(seed);
        for(int i=0;i<v_size;i++) {
        	NodeData node = new NodeData(i,Integer.MAX_VALUE,"",0,null);
        	g.addNode(node);
        }
        // Iterator<node_data> itr = V.iterator(); // Iterator is a more elegant and generic way, but KIS is more important
        int[] nodes = nodes(g);
        while(g.edgeSize() < e_size) {
            int a = nextRnd(0,v_size);
            int b = nextRnd(0,v_size);
            int i = nodes[a];
            int j = nodes[b];
            double w = _rnd.nextDouble();
            g.connect(i,j, w);
        }
        return g;
    }
    private static int nextRnd(int min, int max) {
        double v = nextRnd(0.0+min, (double)max);
        int ans = (int)v;
        return ans;
    }
    private static double nextRnd(double min, double max) {
        double d = _rnd.nextDouble();
        double dx = max-min;
        double ans = d*dx+min;
        return ans;
    }
    /**
     * Simple method for returning an array with all the node_data of the graph,
     * Note: this should be using an Iterator<node_edge> to be fixed in Ex1
     * @param g
     * @return
     */
    private static int[] nodes(directed_weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_data> V = g.getV();
        node_data[] nodes = new node_data[size];
        V.toArray(nodes); // O(n) operation
        int[] ans = new int[size];
        for(int i=0;i<size;i++) {ans[i] = nodes[i].getKey();}
        Arrays.sort(ans);
        return ans;
    }
	
}
