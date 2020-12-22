package api;

import java.util.Collection;
import java.util.HashMap;

public class DWGraph_DS implements directed_weighted_graph {

	private int edgeCount;
	private int mc;

	private HashMap <Integer, node_data> nodes;
	private HashMap <Integer, HashMap<Integer, edge_data>> edges;
	private HashMap <Integer, HashMap<Integer, edge_data>> reverseEdges;


	public DWGraph_DS() {

		this.edgeCount = 0;
		this.mc = 0;
		this.nodes = new HashMap <Integer, node_data>();
		this.edges = new HashMap <Integer, HashMap<Integer, edge_data>>();
		this.reverseEdges = new HashMap <Integer, HashMap<Integer, edge_data>>();

	}


	@Override
	public node_data getNode(int key) {
		return nodes.get(key);
	}

	@Override
	public edge_data getEdge(int src, int dest) {

		if(edges.containsKey(src) && edges.containsKey(dest)) {
			if(edges.get(src).containsKey(dest)) {
				edge_data edge1 = edges.get(src).get(dest);
				return edge1;
			}
		}
		return null;
	}

	@Override
	public void addNode(node_data n) {

		if (nodes.containsKey(n.getKey())) return;
		nodes.put(n.getKey(), n);
		edges.put(n.getKey(), new HashMap<Integer, edge_data>());
		reverseEdges.put(n.getKey(), new HashMap<Integer, edge_data>());
		mc++;
	}

	@Override
	public void connect(int src, int dest, double w) {

		edge_data edge = new EdgeData(src, dest, w);
		if (src == dest || w <= 0) return;
		if (!nodes.containsKey(src) || !nodes.containsKey(dest)) return;
		if (!edges.get(src).containsKey(dest)) {
			edgeCount++;
		}
		edges.get(src).put(dest, edge);
		reverseEdges.get(dest).put(src, edge);
		mc++;
	}

	@Override
	public Collection<node_data> getV() {
		return nodes.values();
	}

	@Override
	public Collection<edge_data> getE(int node_id) {

		if(!nodes.containsKey(node_id)) return null;
		return edges.get(node_id).values();
	}

	@Override
	public node_data removeNode(int key) {

		if (!nodes.containsKey(key)) return null;

		for (Integer k : this.reverseEdges.get(key).keySet()) {

			this.reverseEdges.get(k).remove(key);

			this.edgeCount--;
			this.mc++;

		}

		this.edgeCount -= this.edges.get(key).size();
		this.mc += this.edges.get(key).size();

		this.edges.get(key).clear();

		return nodes.remove(key);
	}

	@Override
	public edge_data removeEdge(int src, int dest) {

		if (!nodes.containsKey(src) || !nodes.containsKey(dest)) return null;
		if(src == dest) return null;

		if(edges.get(src).containsKey(dest)) {
			edge_data edge = edges.get(src).get(dest);
			edges.get(src).remove(dest);
			reverseEdges.get(dest).remove(src);
			mc++;
			edgeCount--;
			return edge;
		}
		

		return null;
	}

	@Override
	public int nodeSize() {
		return nodes.size();
	}

	@Override
	public int edgeSize() {
		return edgeCount;
	}

	@Override
	public int getMC() {
		return mc;
	}

	public void setMC(int mc) {
		this.mc = mc;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;

		if (obj == null)
			return false;

		if (getClass() != obj.getClass())
			return false;

		DWGraph_DS other = (DWGraph_DS) obj;

		boolean flag = nodeSize() == other.nodeSize()
				&& edgeSize() == other.edgeSize()
				&& getMC() == other.getMC();

		for (Integer src : nodes.keySet()) {

			for (Integer dest : edges.get(src).keySet()) {

				if (other.getEdge(src, dest).getSrc() != src || other.getEdge(src, dest).getDest() != dest)
					flag = false;
			}

		}

		return flag;
	}

}
