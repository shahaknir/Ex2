package api;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import gameClient.util.Point3D;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class DWGraph_Algo implements dw_graph_algorithms {

	private directed_weighted_graph graph;

	private HashMap <Integer, node_data> parent;
	private HashMap <Integer, Double> distance;

	@Override
	public void init(directed_weighted_graph g) {

		this.parent = new HashMap <Integer, node_data>();
		this.distance = new HashMap<>();
		this.graph = (DWGraph_DS) g;

		for (node_data node : graph.getV()) {
			node.setTag(0);
			parent.put(node.getKey(), null);
			node.setWeight(Double.POSITIVE_INFINITY);
			distance.put(node.getKey(), Double.MAX_VALUE);
		}

	}

	@Override
	public directed_weighted_graph getGraph() {

		return graph;
	}

	@Override
	public directed_weighted_graph copy() {

		DWGraph_DS copyGraph = new DWGraph_DS ();

		for (node_data node : graph.getV()) {  // iterate over nodes
			int nodeKey = node.getKey();
			copyGraph.addNode(new NodeData(node));  // add the node to graph

			for (edge_data neighbor : graph.getE(nodeKey)) {  // iterate over his neighbors
				copyGraph.addNode(new NodeData(graph.getNode(neighbor.getDest())));
				copyGraph.connect(node.getKey(), neighbor.getDest(), neighbor.getWeight());
			}
		}
		return copyGraph;
	}

	@Override
	public boolean isConnected() {

		init(graph);
		if(graph.nodeSize() < 2) return true;
		Iterator <node_data> itr = graph.getV().iterator();
		node_data firstNode = itr.next();
		BFS(graph, firstNode);

		for (node_data nodes : graph.getV()) {
			if(nodes.getTag() == 0) return false;
		}

		DWGraph_DS reverseGraph = new DWGraph_DS ();

		for (node_data node : graph.getV()) {  // iterate over nodes
			int nodeKey = node.getKey();
			reverseGraph.addNode(node);  // add the node to graph

			for (edge_data neighbor : graph.getE(nodeKey)) {  // iterate over his neighbors
				reverseGraph.addNode(graph.getNode(neighbor.getDest()));
				reverseGraph.connect(neighbor.getDest(), node.getKey(), neighbor.getWeight());
			}
		}
		init(graph);
		BFS(reverseGraph, firstNode);
		for (node_data nodes : reverseGraph.getV()) {
			if(nodes.getTag() == 0) return false;
		}

		return true;
	}

	@Override
	public double shortestPathDist(int src, int dest) {

		init(graph);

		node_data n1 = graph.getNode(src);
		node_data n2 = graph.getNode(dest);

		if (n1 == null || n2 == null) return -1;
		if (src == dest) return 0;

		Dijkstra(n1);
		return distance.get(dest);

	}

	/**
	 * returns the the shortest path between src to dest - as an ordered List of nodes:
	 * src--> n1-->n2-->...dest
	 * see: https://en.wikipedia.org/wiki/Shortest_path_problem
	 * Note if no such path --> returns null;
	 * @param src - start node
	 * @param dest - end (target) node
	 * @return
	 */
	@Override
	public List<node_data> shortestPath(int src, int dest) {

		init(graph);

		node_data n1 = graph.getNode(src);
		node_data n2 = graph.getNode(dest);

		if (n1 == null || n2 == null) return null;

		ArrayList <node_data> list = new ArrayList <node_data>();

		Dijkstra(n1);

		if (src == dest) {
			list.add(n1);
			return list;
		}

		int i = 0;
		while (n1 != n2) {
			list.add(i, n2);;

			if (n2 == null) return null;
			n2 = parent.get(n2.getKey());
			i++;
		}

		list.add(i, n1);

		List<node_data> reverse = new ArrayList<>();

		for (int j = list.size() -1; j >= 0; j--){
			reverse.add(list.get(j));
		}

		return reverse;
	}

	public boolean save(String file) {

		try {

			JSONArray nodes = new JSONArray();
			JSONArray edges = new JSONArray();
			JSONObject both = new JSONObject();

			Iterator<node_data> it = getGraph().getV().iterator();

			while(it.hasNext()) {
				node_data n_ = it.next();
				JSONObject jsonObject = new JSONObject();


				jsonObject.put("pos", n_.getLocation().toString());
				jsonObject.put("id", n_.getKey());

				nodes.put(jsonObject);

				Iterator<edge_data> e_it = getGraph().getE(n_.getKey()).iterator();

				while (e_it.hasNext()) {

					edge_data e_ = e_it.next();
					JSONObject e_jsonObject = new JSONObject();

					e_jsonObject.put("src", e_.getSrc());
					e_jsonObject.put("w", e_.getWeight());
					e_jsonObject.put("dest", e_.getDest());

					edges.put(e_jsonObject);

				}
			}

			FileWriter fWriter;
			fWriter = new FileWriter(file);

			both.put("Edges", edges);
			both.put("Nodes" , nodes);

			fWriter.write(both.toString());
			fWriter.flush();
			fWriter.close();
		}
		catch (IOException | JSONException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}


	@Override
	public boolean load(String file) {

		directed_weighted_graph graph = new DWGraph_DS();
		
		try {

			String s = new String(Files.readAllBytes(Paths.get(file)));

			JSONObject ob = new JSONObject(s);

			JSONArray nodes = ob.getJSONArray("Nodes");
			JSONArray edges = ob.getJSONArray("Edges");

			for (int i = 0; i < nodes.length(); i++) {

				String position = nodes.getJSONObject(i).getString("pos");
				int key = nodes.getJSONObject(i).getInt("id");

				node_data n = new NodeData(key);
				n.setLocation(new Point3D(position));

				graph.addNode(n);

			}

			for (int i = 0; i < edges.length(); i++) {

				double w = edges.getJSONObject(i).getDouble("w");
				int src = edges.getJSONObject(i).getInt("src");
				int dest = edges.getJSONObject(i).getInt("dest");

				graph.connect(src, dest, w);

			}


			init(graph);
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}			

	}


	private void Dijkstra(node_data src) {

		PriorityQueue <node_data> pq = new PriorityQueue<>((x, y) -> Double.compare(x.getWeight(), y.getWeight()));

		distance.put(src.getKey(), 0.0);
		src.setWeight(0);
		pq.add(src);

		while (!pq.isEmpty()) {

			node_data current = pq.poll();

			for (edge_data neighbors : graph.getE(current.getKey())) {

				node_data node = graph.getNode(neighbors.getDest());

				double newDist = this.distance.get(current.getKey()) + neighbors.getWeight();

				if (this.distance.get(node.getKey()) > newDist) {
					node.setWeight(newDist);
					parent.put(node.getKey(), current);
					distance.put(node.getKey(), newDist);

					pq.remove(node);
					pq.add(node); //Enter the new nei with the new tag back to the PriorityQueue.
				}
			}

		}
	}

	private void BFS (directed_weighted_graph g, node_data src) {

		Queue <node_data> q = new LinkedList<node_data>();
		src.setTag(1);
		q.add(src);
		while (!q.isEmpty()) {
			node_data current = q.poll();
			for (edge_data neighbors : g.getE(current.getKey())) {
				node_data node = g.getNode(neighbors.getDest());
				if (node.getTag() == 0) {
					node.setTag(1);
					q.add(node); //Enter the new nei with the new tag back to the PriorityQueue.
				}
			}
			current.setTag(2);
		}
	}
}

