package world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import bodies.RigidBody2;
import vecmath.Vec2;

public class MovementResolver {
	private Collection<RigidBody2> bodies = new ArrayList<>();
	
	public MovementResolver() {

	}

	public void addBody(RigidBody2 body) {
		bodies.add(body);
	}

	public void removeBody(RigidBody2 body) {
		bodies.remove(body);
	}

	public void moveBodies(int millis) {
		for(RigidBody2 body : bodies) {
			Vec2 currentPos = body.getCOM();
			Vec2 movement = body.getVelocity().scale(((double) millis / (double) 1000));
			resolveRotations(body, millis);
			currentPos.addVec(movement);
			body.getPosition().addVec(movement);
		}
	}
	
	public Collection<RigidBody2> returnBodies() {
		return bodies;
	}
	
	private void resolveRotations(RigidBody2 body, int time) {
		double l = body.getAngularMomentum();
		double i = body.getRotationalIntertia();
		double w = l/i;
		
		double radAngle = w / time;
		body.setAngle(radAngle);
		
	}
}