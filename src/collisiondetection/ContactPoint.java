package collisiondetection;

import java.util.Collection;

import bodies.RigidBody2;
import vecmath.Vec2;

public class ContactPoint {
	private RigidBody2 bodyA;
	private RigidBody2 bodyB;
	private Collection<Vec2> contactPoints;
	private Collection<Vec2> normals;
	
	public ContactPoint(RigidBody2 bodyA, RigidBody2 bodyB, Collection<Vec2> contactPoints, Collection<Vec2> normals) {
		this.bodyA = bodyA;
		this.bodyB = bodyB;
		this.contactPoints = contactPoints;
		this.normals = normals;
	}
	
	public RigidBody2 getBodyA() {
		return bodyA;
	}
	
	public RigidBody2 getBodyB() {
		return bodyB;
	}
	
	public Collection<Vec2> getContactPoints() {
		return contactPoints;
	}
	
	public Collection<Vec2> getNormals() {
		return normals;
	}
}
