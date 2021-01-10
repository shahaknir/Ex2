package tests;

import api.*;
import org.junit.jupiter.api.Test;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;


public class DWGraphAlgoTest {

	@Test
	public void NodeId() {
		directed_weighted_graph graph = CreateGraph();
		node_data FirstNode = graph.getNode(0);
		node_data SecondNode = graph.getNode(2);
		assertNotEquals(FirstNode.getKey(), SecondNode.getKey());
	}


	@Test
	public void GetNodes() {
		dw_graph_algorithms algo = new DWGraph_Algo();

		DWGraph_DS graph =  (DWGraph_DS) CreateGraph();
		algo.init(graph);
		directed_weighted_graph graphCopy = algo.copy();
		Queue<node_data> queNodes = new LinkedList<>();
		queNodes.addAll(algo.getGraph().getV());
		while(!queNodes.isEmpty()) {
			node_data current = queNodes.poll();
			node_data copCurent = graphCopy.getNode(current.getKey());
			assertEquals(current.toString(),copCurent.toString());

		}
	}

//	@Test
//		public void HasEdge() {
//			DWGraph_DS graph = (DWGraph_DS) SmallGraph();// All nodes are connected to each other
//			for(node_data CurrNode : graph.getV()) {
//				for(node_data SecNode : graph.getV()) {
//					if (CurrNode.getKey() != SecNode.getKey()) {
//						assertTrue(graph.hasEdge(CurrNode.getKey(), SecNode.getKey()));
//					}
//				}
//
//			}
//		}

	@Test
		public void AddNodeEx() {// No change was made in addNode
			directed_weighted_graph graph = SmallGraph();
			int originalNodeCount = graph.nodeSize();
			node_data node0 = graph.getNode(0);
			graph.addNode(node0);
			assertEquals(originalNodeCount, graph.nodeSize());
		}

	@Test
		public void ConnectedAllNodes() { //check that method connected does'nt add to any count
			directed_weighted_graph graph = CreateGraph();
			int originalNodeCount = graph.nodeSize();
			int originalEdgeCount = graph.edgeSize();
			int originalModeCount = graph.getMC();

			graph.connect(0,1,8);
	        graph.connect(0,2,26);
	        graph.connect(0,3,68);

	        graph.connect(1,4,17);
	        graph.connect(1,5,1);

	        graph.connect(2,4,1);
	        graph.connect(2,10,1);

	        graph.connect(3,5,10);
	        graph.connect(3,6,100);
	        graph.connect(3,9,10);

	        graph.connect(4,10,1);

	        graph.connect(5,7,1.1);
	        graph.connect(5,6,1.1);
	        graph.connect(5,9,10);

	        graph.connect(6,7,1.53);
	        graph.connect(6,8,30.400);
	        graph.connect(6,2,1.53);
	        graph.connect(6,4,30.400);
	        graph.connect(6,10,2);

	        graph.connect(7,10,2);
	        graph.connect(7,3,2);

	        graph.connect(8,10,2);
	        graph.connect(8,10,10);
	        graph.connect(8,6,10);

	        assertEquals(originalNodeCount, graph.nodeSize());
	        assertEquals(originalEdgeCount, graph.edgeSize());
	        assertEquals(originalModeCount, graph.getMC());
		}

	@Test
		public void NumGraphNode() {
			directed_weighted_graph graph = CreateGraph();
			directed_weighted_graph graphSmall = SmallGraph();
			directed_weighted_graph Unconnectedgraph = CreateUnconnectedGraph();

			assertEquals(graph.getV().size() + 1,Unconnectedgraph.getV().size());
			assertEquals(graphSmall.getV().size(), 4);

		}

    @Test
    public void isConnected(){

        DWGraph_Algo ga = new DWGraph_Algo();
        DWGraph_DS gd = new DWGraph_DS();

        assertTrue(ga.isConnected());
        gd.addNode(new NodeData(0));
        assertTrue(ga.isConnected());
        gd.addNode(new NodeData(1));
        gd.addNode(new NodeData(2));
        gd.addNode(new NodeData(3));
        gd.connect(0, 1, 3);
        gd.connect(0, 3, 1);
        gd.connect(1, 2, 4);
        gd.connect(2, 0, 2);
        gd.connect(3, 2, 5);
        ga.init(gd);

        assertTrue(ga.isConnected());
        gd.removeEdge(0, 1);
        ga.init(gd);
        assertFalse(ga.isConnected());

    }

	@Test
	public void TestShortestPath() {
		// Arrange
		directed_weighted_graph graph = new DWGraph_DS();
		dw_graph_algorithms algo = new DWGraph_Algo();

		for (int i = 0; i < 5; i++) {
			node_data node100 = new NodeData(i,Integer.MAX_VALUE,"",0,null);
			graph.addNode(node100);
		}

		graph.connect(0, 1, 0.5);
		graph.connect(1, 2, 0.5);
		graph.connect(2, 3, 0.5);
		graph.connect(3, 4, 0.5);
		graph.connect(0, 4, 5);

		algo.init(graph);

		// Act
		var minDistance = algo.shortestPathDist(0, 4);
		var minPath = algo.shortestPath(0, 4);
		var isConnected = algo.isConnected();

		// Assert
		assertEquals(2.0, minDistance);
		assertEquals(5, minPath.size());
		for (int i = 0; i < 5; i++) {
			assertEquals(i, minPath.get(i).getKey());
		}
		assertEquals(false, isConnected);
	}

    @Test
    public void TestShortestDis() {
        // Arrange
        directed_weighted_graph graph = new DWGraph_DS();
        dw_graph_algorithms algo = new DWGraph_Algo();

        for (int i = 0; i < 5; i++) {
            node_data node100 = new NodeData(i, Integer.MAX_VALUE, "", 0, null);
            graph.addNode(node100);
        }

        graph.connect(0, 1, 0.5);
        graph.connect(1, 2, 0.5);
        graph.connect(2, 3, 0.5);
        graph.connect(3, 4, 0.5);
        graph.connect(0, 4, 5);

        algo.init(graph);

        // Act
        double minDistance = algo.shortestPathDist(0, 4);
        List<node_data> minPath = algo.shortestPath(0, 4);
        boolean isConnected = algo.isConnected();

        // Assert
        assertEquals(2.0, minDistance);
        assertEquals(5, minPath.size());
        for (int i = 0; i < 5; i++) {
            assertEquals(i, minPath.get(i).getKey());
        }
        assertFalse(isConnected);
        graph.removeNode(3);
        minDistance = algo.shortestPathDist(0, 4);
        assertNotEquals(2.0, minDistance);
        graph.removeNode(0);
        isConnected = algo.isConnected();
        assertFalse(isConnected);

        graph.connect(1, 4, 5);

        graph.connect(2, 1, 0.5);
        graph.connect(1, 4, 7853);
        graph.removeNode(0);
        isConnected = algo.isConnected();
        assertFalse(isConnected);
        graph.removeNode(1);
        isConnected = algo.isConnected();
        assertFalse(isConnected);
        graph.removeNode(3);
        isConnected = algo.isConnected();
        assertFalse(isConnected);
        graph.removeNode(2);
        isConnected = algo.isConnected();
        assertTrue(isConnected);
    }

    @Test
    public void SaveLoadTest() {
        // Arrange
        directed_weighted_graph originalGraph = new DWGraph_DS();
        dw_graph_algorithms algo = new DWGraph_Algo();

        for (int i = 0; i < 5; i++) {
            node_data node100 = new NodeData(i, Integer.MAX_VALUE, "", 0, null);
            originalGraph.addNode(node100);
        }


        originalGraph.connect(0, 1, 0.5);
        originalGraph.connect(1, 2, 0.5);
        originalGraph.connect(2, 3, 0.5);
        originalGraph.connect(3, 4, 0.5);
        originalGraph.connect(0, 4, 5);

        // Act
        algo.init(originalGraph);
        boolean saveSuccess = algo.save("GraphSave");
        boolean loadSuccess = algo.load("GraphSave");
        directed_weighted_graph loadedGraph = algo.getGraph();

        // Assert
        assertTrue(saveSuccess);
        assertTrue(loadSuccess);
        assertEquals(originalGraph, loadedGraph);
        loadedGraph.removeNode(0);
        assertNotEquals(originalGraph, loadedGraph);
        node_data node0 = new NodeData(0, Integer.MAX_VALUE, "", 0, null);
        loadedGraph.addNode(node0);
        loadedGraph.connect(0, 1, 0.5);
        loadedGraph.connect(0, 4, 5);
        assertEquals(originalGraph, loadedGraph);
        loadedGraph.removeEdge(0, 4);
        assertNotEquals(originalGraph, loadedGraph);
    }

    ///////////////////////
    // Private functions //
    ///////////////////////

    private static directed_weighted_graph SmallGraph() {
        directed_weighted_graph SmallConnectedGraph = new DWGraph_DS();
        node_data node0 = new NodeData();
        node_data node1 = new NodeData();
        node_data node2 = new NodeData();
        node_data node3 = new NodeData();

        SmallConnectedGraph.addNode(node0);
        SmallConnectedGraph.addNode(node1);
        SmallConnectedGraph.addNode(node2);
        SmallConnectedGraph.addNode(node3);

        SmallConnectedGraph.connect(0, 1, 1);
        SmallConnectedGraph.connect(0, 2, 6);
        SmallConnectedGraph.connect(0, 3, 10);

        SmallConnectedGraph.connect(1, 0, 874512);
        SmallConnectedGraph.connect(1, 2, 1);
        SmallConnectedGraph.connect(1, 3, 1);

        SmallConnectedGraph.connect(2, 0, 2);
        SmallConnectedGraph.connect(2, 1, 2);
        SmallConnectedGraph.connect(2, 3, 2);

        SmallConnectedGraph.connect(3, 0, 6);
        SmallConnectedGraph.connect(3, 1, 9);
        SmallConnectedGraph.connect(3, 2, 7894865);

        return SmallConnectedGraph;
    }


    public static directed_weighted_graph CreateGraph() {
        directed_weighted_graph GraphCreated = new DWGraph_DS();
        node_data node0 = new NodeData();
        node_data node1 = new NodeData();
        node_data node2 = new NodeData();
        node_data node3 = new NodeData();
        node_data node4 = new NodeData();
        node_data node5 = new NodeData();
        node_data node6 = new NodeData();
        node_data node7 = new NodeData();
        node_data node8 = new NodeData();
        node_data node9 = new NodeData();
        node_data node10 = new NodeData();

        GraphCreated.addNode(node0);
        GraphCreated.addNode(node1);
        GraphCreated.addNode(node2);
        GraphCreated.addNode(node3);
        GraphCreated.addNode(node4);
        GraphCreated.addNode(node5);
        GraphCreated.addNode(node6);
        GraphCreated.addNode(node7);
        GraphCreated.addNode(node8);
        GraphCreated.addNode(node9);
        GraphCreated.addNode(node10);

        GraphCreated.connect(0, 1, 8);
        GraphCreated.connect(0, 2, 26);
        GraphCreated.connect(0, 3, 68);

        GraphCreated.connect(1, 4, 17);
        GraphCreated.connect(1, 5, 1);

        GraphCreated.connect(2, 4, 1);
        GraphCreated.connect(2, 10, 1);

        GraphCreated.connect(3, 5, 10);
        GraphCreated.connect(3, 6, 100);
        GraphCreated.connect(3, 9, 10);

        GraphCreated.connect(4, 10, 1);

        GraphCreated.connect(5, 7, 1.1);
        GraphCreated.connect(5, 6, 1.1);
        GraphCreated.connect(5, 9, 10);

        GraphCreated.connect(6, 7, 1.53);
        GraphCreated.connect(6, 8, 30.400);
        GraphCreated.connect(6, 2, 1.53);
        GraphCreated.connect(6, 4, 30.400);
        GraphCreated.connect(6, 10, 2);

        GraphCreated.connect(7, 10, 2);
        GraphCreated.connect(7, 3, 2);

        GraphCreated.connect(8, 10, 2);
        GraphCreated.connect(8, 10, 10);
        GraphCreated.connect(8, 6, 10);

        return GraphCreated;
    }


    public static directed_weighted_graph CreateUnconnectedGraph() {
        directed_weighted_graph UnconnectedGraph = CreateGraph();
        node_data node100 = new NodeData(100, Integer.MAX_VALUE, "", 0, null);
        UnconnectedGraph.addNode(node100);
        return UnconnectedGraph;
    }





	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
			// Arrange
			directed_weighted_graph graph = new DWGraph_DS();
			dw_graph_algorithms algo = new DWGraph_Algo();

			for (int i = 0; i < 5; i++) {
				node_data node100 = new NodeData(i,Integer.MAX_VALUE,"",0,null);
				graph.addNode(node100);
			}

			graph.connect(0, 1, 0.5);
			graph.connect(1, 2, 0.5);
			graph.connect(2, 3, 0.5);
			graph.connect(3, 4, 0.5);
			graph.connect(0, 4, 5);

			algo.init(graph);

			// Act
			var minDistance = algo.shortestPathDist(0, 4);
			var minPath = algo.shortestPath(0, 4);
			var isConnected = algo.isConnected();

			// Assert
			if(minDistance == 2.0)System.out.println("true, minDist = 2.0");;
			if(minPath.size() == 5)System.out.println("true, minPath = 5");;
			
			if(!isConnected) System.out.println("algo is not connected");
			
			graph.removeNode(3);
			minDistance = algo.shortestPathDist(0, 4);
			
			if(minDistance !=2.0) System.out.println("minDist !=2.0 as it should");
			graph.removeNode(0);
			isConnected = algo.isConnected();
			if(!isConnected) System.out.println("algo is not connected");

			
			graph.connect(1, 4, 5);
			
			graph.connect(2, 1, 0.5);
			graph.connect(2, 4, 7853);
			graph.removeNode(0);
			isConnected = algo.isConnected();
			if(!isConnected) System.out.println("algo is not connected1");
			graph.removeNode(1);
			System.out.println(graph.edgeSize());
			isConnected = algo.isConnected();
			if(!isConnected) System.out.println("algo is not connected2");
			System.out.println(graph.edgeSize());
			graph.removeNode(3);
			isConnected = algo.isConnected();
			if(!isConnected) System.out.println("algo is not connected3");
			System.out.println(graph.edgeSize());
			graph.removeNode(5);
			isConnected = algo.isConnected();
			if(!isConnected) System.out.println("algo is not connected4");
			System.out.println(graph.edgeSize());
			graph.removeNode(0);
			isConnected = algo.isConnected();
			if(!isConnected) System.out.println("algo is not connected5");
			System.out.println(graph.edgeSize());
			graph.removeNode(0);
			isConnected = algo.isConnected();
			if(!isConnected) System.out.println("algo is not connected6");
			
	}*/

}
