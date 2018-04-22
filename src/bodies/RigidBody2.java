package bodies;

import java.util.Collection;

import vecmath.Vec2;

public abstract class RigidBody2 {
	protected Vec2 velocity;
	//the size variable is the bounding box of the object.
	protected double sizeX;
	protected double sizeY;
	protected Vec2 center;
	protected BodyType bodyType;
	protected double mass;
	protected double density;
	protected double restitution;
	protected double angularMomentum;
	protected double rotationalInertia;
	protected Vec2 com;
	
	public RigidBody2(Vec2 center, Vec2 com, Vec2 startV, double sizeX, double sizeY, double mass, double restitution, double angularMomentum, double rotationalInertia) {
		this.velocity = startV;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.center = center;
		this.restitution = restitution;
		this.mass = mass;
		this.com = com;
		this.angularMomentum = angularMomentum;
		this.rotationalInertia = rotationalInertia;
	}
	
	public Vec2 getVelocity() {
		return velocity;
	}
	
	public Vec2 getPosition() {
		return center;
	}
	
	public double getSizeX() {
		return sizeX;
	}
	
	public double getSizeY() {
		return sizeY;
	}
	
	public BodyType getType() {
		return bodyType;
	}
	
	public double getMass() {
		return mass;
	}
	
	public double getRestitution() {
		return restitution;
	}
	
	public Vec2 getCOM() {
		return com;
	}
	
	public double getAngularMomentum() {
		return angularMomentum;
	}
	
	public void addAngularMomentum(double deltaM) {
		angularMomentum += deltaM;
	}
	
	public double getRotationalIntertia() {
		return rotationalInertia;
	}
	
	public abstract Collection<Vec2> generateContactPoints(RigidBody2 body);
}
