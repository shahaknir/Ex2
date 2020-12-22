package api;

import api.*;

public class EdgeLocation implements edge_location {

	private edge_data edge;
	
	@Override
	public edge_data getEdge() {
		return this.edge;
	}

	
	/**
     * Returns the distance between the edge source and destination
     * @return
     */
	@Override
	public double getRatio() {
		double ans = 0;
		EdgeData edge = (EdgeData) this.edge;
		int dest = edge.getDest();
		int src = edge.getSrc();
		return ans;
	}

}
