package collisiondetection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import bodies.BodyType;
import bodies.RigidBody2;
import vecmath.Vec2;

public class CollisionDetector {
	private Collection<ContactPoint> collisionPoints;
	
	public Collection<ContactPoint> detectCollisions(Collection<RigidBody2> bodies) {
		collisionPoints = new ArrayList<>();
		ArrayList<RigidBody2> b = (ArrayList<RigidBody2>) bodies;
		
		for(int i = 0; i < bodies.size(); i++) {
			RigidBody2 bodyA = b.get(i);
			for(int j = i; j < bodies.size(); j++) {
				RigidBody2 bodyB = b.get(j);
				if(doesRoughCollide(bodyA, bodyB) && !bodyA.equals(bodyB)) {
					generateCollisionPoints(bodyA, bodyB);
				}
			}
		}
		
		return collisionPoints;
	}
	
	public boolean doesRoughCollide(RigidBody2 bodyA, RigidBody2 bodyB) {
		Vec2 posA = new Vec2(bodyA.getPosition());
		Vec2 posB = new Vec2(bodyB.getPosition());
		
		posA.addVec(posB.scale(-1));
		
		if(posA.getX() > 0 && posA.getX() < bodyB.getSizeX() && ) {
			return true;
		}
		
		if(posA.getX() < 0 && posA.getX() > bodyA.getSizeX() * -1) {
			return true;
		}
		
		if(posA.getY() > 0 && posA.getY() < bodyB.getSizeY()) {
			return true;
		}
		
		if(posA.getY() < 0 && posA.getY() > bodyA.getSizeY() * -1) {
			return true;
		}
		
		return false;
	}
	
	public void generateCollisionPoints(RigidBody2 bodyA, RigidBody2 bodyB) {
		if(bodyA.generateContactPoints(bodyB) != null) {
			collisionPoints.add(new ContactPoint(bodyA, bodyB, bodyA.generateContactPoints(bodyB), bodyA.getNormals(bodyB)));
		}
	}
}
