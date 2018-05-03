package world;

import java.util.Collection;
import java.util.Iterator;

import bodies.RigidBody2;
import collisiondetection.CollisionDetector;
import collisionresolver.CollisionResolver;

/**This object represents the space in which all the 2D rigid bodies exist.*/
public class PhysicsWorld {
	private CollisionDetector cDetector;
	private MovementResolver mResolver;
	private CollisionResolver cResolver;
	
	private final int chunkSize;
	
	public PhysicsWorld(int chunkSize) {
		cDetector = new CollisionDetector();
		mResolver = new MovementResolver();
		cResolver = new CollisionResolver();
		
		this.chunkSize = chunkSize;
	}
	
	/**Adds a rigid body into the physics world.*/
	public void addBody(RigidBody2 body) {
		mResolver.addBody(body);
	}
	
	/**Removes a rigid body from the physics world.*/
	public void removeBody(RigidBody2 body) {
		mResolver.removeBody(body);
	}
	
	public int getChunkSize() {
		return chunkSize;
	}
	
	public void tick(int millis) {
		mResolver.moveBodies(millis);
		boolean isFrameResolved = false;
		while(!isFrameResolved) {
			isFrameResolved = cResolver.resolveCollisions(cDetector.detectCollisions(mResolver.returnBodies()));
			
			Collection<RigidBody2> bodies = mResolver.returnBodies();
			
			for(RigidBody2 b : bodies) {
				b.clearContacts();
			}
		}
	}
}
