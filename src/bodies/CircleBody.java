package bodies;

import java.util.ArrayList;
import java.util.Collection;

import vecmath.Vec2;

/**This body's size is the radius of the circle*/
public class CircleBody extends RigidBody2 {
	
	public CircleBody(Vec2 center, Vec2 com, Vec2 startV, double size, double mass, double restitution, double angularMomentum, double rotationalInertia) {
		super(center, com, startV, size, size, mass, restitution, angularMomentum, rotationalInertia);
		this.bodyType = BodyType.CIRCLE_BODY;
	}

	@Override
	public Collection<Vec2> generateContactPoints(RigidBody2 body) {
		this.clearContacts();
		BodyType collisionType = body.getType();
		
		if(collisionType == BodyType.CIRCLE_BODY) {
			 previousContacts.add(generateCircleContact(body));
			 return previousContacts;
		}
		
		return null;
	}
	
	public Vec2 generateCircleContact(RigidBody2 body) {
		Vec2 bodyPos = new Vec2(body.getPosition());
		bodyPos.addVec(new Vec2(body.getSizeX() / 2, body.getSizeY() / 2));
		
		Vec2 circlePos = new Vec2(this.getPosition());
		circlePos.addVec(new Vec2(this.getSizeX() / 2, this.getSizeY() / 2));
		
		bodyPos.addVec(circlePos.scale(-1));
		
		if(Math.abs(bodyPos.getLength()) <= body.getSizeX() / 2 + this.getSizeX() / 2) {
			bodyPos = bodyPos.scale(0.5);
			bodyPos.addVec(this.getCOM());
			generateNormals(body);
			return bodyPos;
		}
		
		return null;
	}

	private void generateNormals(RigidBody2 body) {
		Vec2 posA = new Vec2(body.getCOM());
		Vec2 posB = new Vec2(this.getCOM());
		
		Vec2 normal = new Vec2(posA.scale(-1));
		normal.addVec(posB);
		previousNormals.add(normal);
	}

	@Override
	public Collection<Vec2> getNormals(RigidBody2 body) {
		return previousNormals;
	}
}
