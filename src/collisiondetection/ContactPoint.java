package collisiondetection;

import java.util.Collection;

import bodies.RigidBody2;
import vecmath.Vec2;

public class ContactPoint {
	private RigidBody2 bodyA;
	private RigidBody2 bodyB;
	private Collection<Vec2> contactPoints;
	
	public ContactPoint(RigidBody2 bodyA, RigidBody2 bodyB, Collection<Vec2> contactPoints) {
		this.bodyA = bodyA;
		this.bodyB = bodyB;
		this.contactPoints = contactPoints;
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
}
