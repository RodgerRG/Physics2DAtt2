package bodies;

import java.util.ArrayList;
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
	
	//an angle offset from the x axis, anti-clockwise.
	protected double angle;
	
	protected Collection<Vec2> previousContacts = new ArrayList<>();
	protected Collection<Vec2> previousNormals = new ArrayList<>();
	
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
	
	public void clearContacts() {
		previousContacts.clear();
		previousNormals.clear();
	}
	
	public double getAngle() {
		return angle;
	}
	
	public void addAngle(double angle) {
		this.angle += angle;
	}
	
	protected void lineLineCollisionContact(Vec2 originA, Vec2 originB, Vec2 lineA, Vec2 lineB) {
		double scaleB = (originB.getY() + originA.getX() * lineA.getY() - originA.getY() - originB.getX() * lineA.getY()) / (lineA.getY() * lineB.getX() - lineB.getY());
		double scaleA = (originB.getY() + lineB.getY() * scaleB - originA.getX()) / lineA.getY();
		
		if(scaleA == 1 && scaleB == 1) {
			Vec2 cp = new Vec2(lineA.scale(scaleA));
			cp.addVec(originA);
			previousContacts.add(cp);
			
			//calculate the normal of the collision
			double cosA = lineA.dotProduct(lineB) / (lineA.getLength() * lineB.getLength());
			double angle = Math.acos(cosA) / 2;
			
			double normX = Math.cos(angle) * lineA.getX() + Math.sin(angle) * lineA.getY();
			double normY = - (Math.sin(angle)) * lineA.getX() + Math.cos(angle) * lineA.getY();
			
			previousNormals.add(new Vec2(normX, normY));
		}
	}
	
	public abstract Collection<Vec2> generateContactPoints(RigidBody2 body);
	public abstract Collection<Vec2> getNormals(RigidBody2 body);
	public abstract void updateRoughCollisionBox();
}
