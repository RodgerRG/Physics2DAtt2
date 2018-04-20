package world;

import java.util.Collection;

import bodies.RigidBody2;
import collisiondetection.CollisionDetector;
import collisionresolver.CollisionResolver;
import vecmath.Vec2;

/**This object represents the space in which all the 2D rigid bodies exist.*/
public class PhysicsWorld {
	private CollisionDetector cDetector;
	private MovementResolver mResolver;
	private CollisionResolver cResolver;
	
	public PhysicsWorld() {
		
	}
	
	/**Adds a rigid body into the physics world.*/
	public void addBody(RigidBody2 body) {
		mResolver.addBody(body);
	}
	
	/**Removes a rigid body from the physics world.*/
	public void removeBody(RigidBody2 body) {
		mResolver.removeBody(body);
	}
	
	public void tick(int millis) {
		mResolver.moveBodies(millis);
		cResolver.resolveCollisions(cDetector.detectCollisions(mResolver.returnBodies()));
		
	}
}
