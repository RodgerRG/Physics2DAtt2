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

			resolveCollision(bodyA, bodyB, collisionPoints);
		}
	}

	private void resolveCollision(RigidBody2 bodyA, RigidBody2 bodyB, Collection<Vec2> collisionPoints) {

		for(Vec2 c : collisionPoints) {
			//find the collision points in relation to the center of mass of body A and body B.
			Vec2 aPos = new Vec2(bodyA.getPosition());
			Vec2 aRad = new Vec2(c);
			aRad.addVec(aPos.scale(-1));

			Vec2 bPos = new Vec2(bodyB.getPosition());
			Vec2 bRad = new Vec2(c);
			bRad.addVec(bPos.scale(-1));

			//calculate the linear momentum of A and B.
			double aLinMomentum = bodyA.getVelocity().getLength() * bodyA.getMass();
		}
	}
}