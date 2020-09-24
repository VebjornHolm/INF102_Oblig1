package compulsory1.management;

/**
 * Location represents a point of interest where a robot might go to do work.
 * All fields of Location are finite meaning a Location can not be changed once it is created
 * 
 * @author Olav Bakken og Martin Vatshelle
 *
 */
public class Location{
	public final double x, y;
	public Location(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Computes the Eucleadian distance between this location and the given location.
	 * @param l - given location
	 * @return - The distance between the points as a double
	 */
	public double dist(Location l) {
		return Math.sqrt((l.x -x)*(l.x -x) + (l.y - y)*(l.y -y));
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "("+x+","+y+")";
	}
}
