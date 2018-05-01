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
			for(int j = i + 1; j < bodies.size(); j++) {
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
		
		//vector from B to A
		Vec2 diffVec = new Vec2(bodyA.getPosition());
		diffVec.addVec(posB.scale(-1));
		
		//B is to the top left of A
		if(diffVec.getX() >= 0 && diffVec.getY() >= 0) {
			if(diffVec.getX() <= bodyB.getSizeX() && diffVec.getY() <= bodyB.getSizeY()) {
				return true;
			}
		}
		
		//B is to the top right of A
		if(diffVec.getX() <= 0 && diffVec.getY() >= 0) {
			if(diffVec.getX() * -1 <= bodyA.getSizeX() && diffVec.getY() <= bodyB.getSizeY()) {
				return true;
			}
		}
		
		//B is to the bottom right of A
		if(diffVec.getX() >= 0 && diffVec.getY() <= 0) {
			if(diffVec.getX() <= bodyB.getSizeX() && diffVec.getY() * -1 <= bodyA.getSizeY()) {
				return true;
			}
		}
		
		//B is to the bottom left of A
		if(diffVec.getX() <=0 && diffVec.getY() <= 0) {
			if(diffVec.getX() * -1 <= bodyA.getSizeX() && diffVec.getY() * -1 <= bodyA.getSizeY()) {
				return true;
			}
		}
		
		return false;
	}
	
	public void generateCollisionPoints(RigidBody2 bodyA, RigidBody2 bodyB) {
		if(!bodyA.generateContactPoints(bodyB).isEmpty()) {
			collisionPoints.add(new ContactPoint(bodyA, bodyB, bodyA.generateContactPoints(bodyB), bodyA.getNormals(bodyB)));
		}
	}
}
