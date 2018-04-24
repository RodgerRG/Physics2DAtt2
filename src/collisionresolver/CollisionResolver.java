package collisionresolver;

import java.util.Collection;
import java.util.Iterator;

import bodies.RigidBody2;
import collisiondetection.ContactPoint;
import vecmath.Vec2;

public class CollisionResolver {
	public void resolveCollisions(Collection<ContactPoint> contactPoints) {
		for(ContactPoint cp : contactPoints) {
			RigidBody2 bodyA = cp.getBodyA();
			RigidBody2 bodyB = cp.getBodyB();
			Collection<Vec2> collisionPoints = cp.getContactPoints();
			Collection<Vec2> normals = cp.getNormals();

			resolveCollision(bodyA, bodyB, collisionPoints, normals);
		}
	}

	private void resolveCollision(RigidBody2 bodyA, RigidBody2 bodyB, Collection<Vec2> collisionPoints, Collection<Vec2> normals) {
		boolean isSolved = false;
		int count = 0;

		while(!isSolved && count < 100) {
			isSolved = solveCollision(bodyA, bodyB, collisionPoints, normals);
			count++;
		}
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
			
			Vec2 aDeltaV = iLNormal.scale(1 / bodyA.getMass());
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
			
			if(iLNormal.getLength() < 0.001) {
				shouldStop =  true;
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