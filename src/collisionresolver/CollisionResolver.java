package collisionresolver;

import java.util.Collection;
import java.util.Iterator;

import bodies.RigidBody2;
import collisiondetection.ContactPoint;
import vecmath.Vec2;

public class CollisionResolver {
	//limit on the smallest possible value an impulse can take
	private final double resolution = 1;
	
	public boolean resolveCollisions(Collection<ContactPoint> contactPoints) {
		boolean isSolved = true;
		for(ContactPoint cp : contactPoints) {
			RigidBody2 bodyA = cp.getBodyA();
			RigidBody2 bodyB = cp.getBodyB();
			Collection<Vec2> collisionPoints = cp.getContactPoints();
			Collection<Vec2> normals = cp.getNormals();

			boolean collisionResolved = resolveCollision(bodyA, bodyB, collisionPoints, normals);
			if(!collisionResolved) {
				isSolved = false;
			}
		}
		return isSolved;
	}
	
	//actually implement this method correctly, the current impl. is a stop-gap solution
	private void basicPenetrationResolution(RigidBody2 bodyA, RigidBody2 bodyB) {
		Vec2 comA = new Vec2(bodyA.getCOM());
		Vec2 comB = new Vec2(bodyB.getCOM());
		
		Vec2 deltaAB = new Vec2(comA.scale(-1));
		deltaAB.addVec(comB);
		
		deltaAB.scale(0.5);
		Vec2 deltaBA = new Vec2(deltaAB.scale(-1));
		
		comA.addVec(deltaBA);
		comB.addVec(deltaAB);
	}

	private boolean resolveCollision(RigidBody2 bodyA, RigidBody2 bodyB, Collection<Vec2> collisionPoints, Collection<Vec2> normals) {
		if(!collisionPoints.isEmpty() && !normals.isEmpty()) {
			basicPenetrationResolution(bodyA, bodyB);
			return solveCollision(bodyA, bodyB, collisionPoints, normals);
		}
		//if the contact points don't exist, then the collision didn't happen
		return true;
	}

	private boolean solveCollision(RigidBody2 bodyA, RigidBody2 bodyB, Collection<Vec2> collisionPoints, Collection<Vec2> normals) {
		boolean shouldStop = false;

		//calculate the linear momentum of A and B.
		double aLinMomentum = bodyA.getVelocity().getLength() * bodyA.getMass();

		double bLinMomentum = bodyB.getVelocity().getLength() * bodyA.getMass();
		Iterator<Vec2> nIterator = normals.iterator();

		for(Vec2 c : collisionPoints) {
			Vec2 normal = new Vec2(nIterator.next());
			Vec2 iLNormal = solveImpulse(bodyA, bodyB, normal, c);
			
			Vec2 normalDir = new Vec2(iLNormal);
			normalDir.normalize();
			
			Vec2 bodyDir = new Vec2(bodyB.getCOM());
			bodyDir.addVec(bodyA.getCOM().scale(-1));
			
			if((bodyDir.getX() > 0 && normalDir.getX() < 0) || (bodyDir.getX() < 0 && normalDir.getX() > 0)) {
				iLNormal.addVec(new Vec2(iLNormal.getX() * -1, 0));
			}
			if((bodyDir.getY() > 0 && normalDir.getY() < 0) ||(bodyDir.getY() < 0 && normalDir.getY() > 0)) {
				iLNormal.addVec(new Vec2(0, iLNormal.getY() * -1));
			}

			if(iLNormal.getLength() < resolution) {
				shouldStop =  true;
			} else {
				Vec2 aDeltaV = iLNormal.scale(-1 / bodyA.getMass());
				Vec2 bDeltaV = iLNormal.scale(1 / bodyB.getMass());

				Vec2 radA = new Vec2(bodyA.getCOM().scale(-1));
				radA.addVec(c);
				Vec2 radB = new Vec2(bodyB.getCOM().scale(-1));
				radB.addVec(c);

				Vec2 aDeltaI = new Vec2(radA.tangent().scale(iLNormal.dotProduct(radA.tangent()) / radA.getLength()));
				Vec2 bDeltaI = new Vec2(radB.tangent().scale(iLNormal.dotProduct(radB.tangent()) / radB.getLength()));

				bodyA.getVelocity().addVec(aDeltaV);
				bodyB.getVelocity().addVec(bDeltaV);

				bodyA.addAngularMomentum(aDeltaI.getLength());
				bodyB.addAngularMomentum(bDeltaI.getLength());
			}
		} 
		return shouldStop;
	}

	private Vec2 solveImpulse(RigidBody2 bodyA, RigidBody2 bodyB, Vec2 normal, Vec2 collisionPoint) {	
		Vec2 relativeLinV = solveLinearVelocity(bodyA, bodyB, normal);
		Vec2 relativeRotV = solveRotationalVelocity(bodyA, bodyB, normal, collisionPoint);

		Vec2 totalRelativeV = new Vec2(relativeLinV);
		totalRelativeV.addVec(relativeRotV);

		double inverseMasses = -(1 + bodyA.getRestitution()) / (1.0 / bodyA.getMass() + 1.0 / bodyB.getMass());
		double rotImpulseScalar = solverotImpulseScalar(bodyA, bodyB, normal, collisionPoint);

		Vec2 iNormal = new Vec2(totalRelativeV.scale(inverseMasses + rotImpulseScalar));

		return iNormal;
	}

	private double solverotImpulseScalar(RigidBody2 bodyA, RigidBody2 bodyB, Vec2 normal, Vec2 collisionPoint) {
		Vec2 cp = new Vec2(collisionPoint);
		cp.addVec(new Vec2(bodyA.getPosition()).scale(-1));

		Vec2 radA = new Vec2(bodyA.getCOM().scale(-1));
		radA.addVec(collisionPoint);

		Vec2 radB = new Vec2(bodyB.getCOM().scale(-1));
		radB.addVec(collisionPoint);

		Vec2 tangentA = radA.tangent();
		Vec2 tangentB = radB.tangent();

		double torqueA = tangentA.dotProduct(normal);
		double torqueB = tangentB.dotProduct(normal);

		return torqueA * torqueA / bodyA.getRotationalIntertia() + torqueB * torqueB / bodyB.getRotationalIntertia();	
	}

	private Vec2 solveLinearVelocity(RigidBody2 bodyA, RigidBody2 bodyB, Vec2 normal) {
		Vec2 aVelocity = new Vec2(bodyA.getVelocity());
		Vec2 aNormalV = new Vec2(normal.scale(aVelocity.dotProduct(normal) / normal.getLength()));

		Vec2 bVelocity = new Vec2(bodyB.getVelocity());
		Vec2 bNormalV = new Vec2(normal.scale(bVelocity.dotProduct(normal) / normal.getLength()));

		Vec2 rNormalV = new Vec2(aNormalV.scale(-1));
		rNormalV.addVec(bNormalV);

		return rNormalV;
	}

	private Vec2 solveRotationalVelocity(RigidBody2 bodyA, RigidBody2 bodyB, Vec2 normal, Vec2 collisionPoint) {
		Vec2 cp = new Vec2(collisionPoint);
		cp.addVec(new Vec2(bodyA.getPosition()).scale(-1));

		Vec2 radA = new Vec2(bodyA.getCOM().scale(-1));
		radA.addVec(cp);

		Vec2 radB = new Vec2(bodyB.getCOM().scale(-1));
		radB.addVec(cp);

		Vec2 velocityA = new Vec2(radA.scale(bodyA.getAngularMomentum() / bodyA.getRotationalIntertia()));
		Vec2 velocityB = new Vec2(radB.scale(bodyB.getAngularMomentum() / bodyB.getRotationalIntertia()));

		Vec2 rVelocity = new Vec2(velocityA.scale(-1));
		rVelocity.addVec(velocityB);

		return rVelocity;
	}
}