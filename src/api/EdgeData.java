package api;

public class EdgeData implements edge_data {

	private int source;
	private int destination;
	private double weight;
	private String info;
	private int tag;


	public EdgeData(int source, int destination, double weight, String info, int tag) {

		this.source = source;
		this.destination = destination;
		this.weight = weight;
		this.info = info;
		this.tag = tag;
	}

	public EdgeData(int source, int destination, double weight) {
		this.source = source;
		this.destination = destination;
		this.weight = weight;
		this.info = "";
		this.tag = 0;
	}

	public EdgeData() {

		this.source = 0;
		this.destination = 0;
		this.weight = 0;
		this.info = "";
		this.tag = 0;
	}

	@Override
	public int getSrc() {

		return this.source;
	}

	@Override
	public int getDest() {
		return this.destination;
	}

	@Override
	public double getWeight() {
		return this.weight;
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
