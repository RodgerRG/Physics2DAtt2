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
	
	public RigidBody2(Vec2 center, Vec2 startV, double sizeX, double sizeY, double mass) {
		this.velocity = startV;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.center = center;
		this.mass = mass;
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
	
	public abstract Collection<Vec2> generateContactPoints(RigidBody2 body);
}
