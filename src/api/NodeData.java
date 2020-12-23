package api;

import gameClient.util.Point3D;

import java.util.HashMap;

public class NodeData implements node_data{

	private int key;
	private int tag;
	private String info;
	private double weight;
	private geo_location nodeLocation;
	private static int keyGenerator = 0;

	public NodeData(int key, int tag, String info, double weight, geo_location nodeLocation) {

		this.key = key;
		this.tag = tag;
		this.info = info;
		this.weight = weight;
		this.nodeLocation = nodeLocation;
	}


	public NodeData() {

		this.key = keyGenerator;
		this.tag = 0;
		this.info = "";
		this.weight = Double.POSITIVE_INFINITY;
		this.nodeLocation = null;
		keyGenerator++;
	}
	
	public NodeData (node_data nd) {
		
		this.key = nd.getKey();
		this.tag = nd.getTag();
		this.info = nd.getInfo();
		this.weight = nd.getWeight();
		this.nodeLocation = nd.getLocation();
	}


	public NodeData(int id) {
		this.key = id;
		this.tag = 0;
		this.info = "";
		this.weight = 0.0;
		this.nodeLocation = new Point3D(0, 0,0);
	}


	@Override
	public int getKey() {
		return this.key;
	}

	@Override
	public geo_location getLocation() {
		return this.nodeLocation;
	}

	@Override
	public void setLocation(geo_location p) {
		this.nodeLocation = p;

	}

	@Override
	public double getWeight() {
		return this.weight;
	}

	@Override
	public void setWeight(double w) {
		this.weight = w;
	}

	@Override
	public String getInfo() {
		return this.info;
	}

	@Override
	public void setInfo(String s) {
		this.info = s;

	}

	@Override
	public int getTag() {
		return this.tag;
	}

	@Override
	public void setTag(int t) {
		this.tag = t;
	}

}


