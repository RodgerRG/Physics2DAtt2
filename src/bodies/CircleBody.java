package bodies;

import java.util.ArrayList;
import java.util.Collection;

import vecmath.Vec2;

/**This body's size is the radius of the circle*/
public class CircleBody extends RigidBody2 {
	
	
	public CircleBody(Vec2 center, Vec2 com, Vec2 startV, double size, double mass, double restitution, double angularMomentum, double rotationalInertia) {
		super(center, com, startV, size, size, mass, restitution, angularMomentum, rotationalInertia);
	}

	@Override
	public Collection<Vec2> generateContactPoints(RigidBody2 body) {
		BodyType collisionType = body.getType();
		Collection<Vec2> contactPoints = new ArrayList<>();
		
		if(collisionType == BodyType.CIRCLE_BODY) {
			 contactPoints.add(circleContact(body));
			 return contactPoints;
		}
		
		return null;
	}
	
	public Vec2 circleContact(RigidBody2 body) {
		Vec2 bodyPos = new Vec2(body.getPosition());
		bodyPos.addVec(new Vec2(body.getSizeX() / 2, body.getSizeY() / 2));
		
		Vec2 circlePos = new Vec2(this.getPosition());
		circlePos.addVec(new Vec2(this.getSizeX() / 2, this.getSizeY() / 2));
		
		bodyPos.addVec(circlePos.scale(-1));
		
		if(Math.abs(bodyPos.getLength()) <= body.getSizeX() / 2 + this.getSizeX() / 2) {
			bodyPos.scale(0.5);
			bodyPos.addVec(body.getPosition());
			return bodyPos;
		}
		
		return null;
	}
}
