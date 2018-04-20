package vecmath;

/**This object represents a mutable 2D vector for doing basic vector calculations.*/
public class Vec2 {
	private double x;
	private double y;
	
	/**Creates a new vector with x and y co-ordinates.*/
	public Vec2(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**Creates a copy of the input vector.*/
	public Vec2(Vec2 vec) {
		this.x = vec.getX();
		this.y = vec.getY();
	}
	
	/**Returns the x co-ordinate of this vector.*/
	public double getX() {
		return x;
	}
	
	/**Returns the y co-ordinate of this vector.*/
	public double getY() {
		return y;
	}
	
	/**Returns the dot product of this vector with the given vector.*/
	public double dotProduct(Vec2 vec) {
		return x * vec.getX() + y * vec.getY();
	}
	
	/**Returns the angle between the two vectors in radians. This angle will only be between 0 to pi.*/
	public double getAngle(Vec2 vec) {
		return Math.acos((double) dotProduct(vec) / (vec.getLength() * this.getLength()));
	}
	
	/**Returns the magnitude of this vector.*/
	public double getLength() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
	
	/**Adds the input vector to this vector.*/
	public void addVec(Vec2 vec) {
		this.x += vec.getX();
		this.y += vec.getY();
	}
	
	public Vec2 scale(double scale) {
		 double newX = x * scale;
		 double newY = y * scale;
		 return new Vec2(newX, newY);
	}
}
