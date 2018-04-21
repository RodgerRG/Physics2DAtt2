package collisionresolver;

import java.util.Collection;

import bodies.RigidBody2;
import collisiondetection.ContactPoint;
import vecmath.Vec2;

public class CollisionResolver {
	public void resolveCollisions(Collection<ContactPoint> contactPoints) {
		for(ContactPoint cp : contactPoints) {
			RigidBody2 bodyA = cp.getBodyA();
			RigidBody2 bodyB = cp.getBodyB();
			Collection<Vec2> collisionPoints = cp.getContactPoints();
			
			
		}
	}	
}