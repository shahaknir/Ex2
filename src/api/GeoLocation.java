package api;

public class GeoLocation implements geo_location {

	private double x;
	private double y;
	private double z;
	
	
	public GeoLocation (double x, double y, double z) {
		
		setX(x);
		setY(y);
		setZ(z);
	}
	
	@Override
	public double x() {
		return this.x;
	}

	@Override
	public double y() {
		return this.y;
	}

	@Override
	public double z() {
		return this.z;
	}

	@Override
	public double distance(geo_location g) {

		double dx = this.x() - g.x();
        double dy = this.y() - g.y();
        double dz = this.z() - g.z();
        double t = (dx*dx+dy*dy+dz*dz);
        return Math.sqrt(t);
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setZ(double z) {
		this.z = z;
	}


}
