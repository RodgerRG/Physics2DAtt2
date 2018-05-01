package world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import bodies.RigidBody2;
import vecmath.Vec2;

public class MovementResolver {
	private Collection<RigidBody2> bodies = new ArrayList<>();
	private double gravConst = 9.81;
	private boolean isGrav = false;
	
	public MovementResolver() {

	}
	
	public void toggleGrav() {
		isGrav = !isGrav;
	}
	
	public void setGrav(double newConst) {
		gravConst = newConst;
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
			if(isGrav) {
				body.getVelocity().addVec(new Vec2(0, gravConst * (double) millis / (double) 1000));
			}
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
		body.addAngle(radAngle);
		
	}
}