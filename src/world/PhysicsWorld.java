package world;

import bodies.RigidBody2;
import collisiondetection.CollisionDetector;
import collisionresolver.CollisionResolver;

/**This object represents the space in which all the 2D rigid bodies exist.*/
public class PhysicsWorld {
	private CollisionDetector cDetector;
	private MovementResolver mResolver;
	private CollisionResolver cResolver;
	
	public PhysicsWorld() {
		cDetector = new CollisionDetector();
		mResolver = new MovementResolver();
		cResolver = new CollisionResolver();
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
		boolean isFrameResolved = false;
		while(!isFrameResolved) {
			isFrameResolved = cResolver.resolveCollisions(cDetector.detectCollisions(mResolver.returnBodies()));
		}
	}
}
