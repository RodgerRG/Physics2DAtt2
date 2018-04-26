package bodies;

import java.util.ArrayList;
import java.util.Collection;

import vecmath.Vec2;

public class RectangleBody extends RigidBody2{
	private double height;
	private double width;

	private Collection<Vec2> normals = new ArrayList<>();
	
	public RectangleBody(Vec2 center, Vec2 com, Vec2 startV, double sizeX, double sizeY, double mass,
			double restitution, double angularMomentum, double rotationalInertia) {
		super(center, com, startV, sizeX, sizeY, mass, restitution, angularMomentum, rotationalInertia);
		this.height = sizeY / 2;
		this.width = sizeX / 2;
	}

	@Override
	public Collection<Vec2> generateContactPoints(RigidBody2 body) {
		switch(body.getType()) {
		case CIRCLE_BODY :
			return circleContact(body);
		case RECTANGLE_BODY :
			return rectangleContact(body);
		default :
			return null;
		}
	}

	private Collection<Vec2> circleContact(RigidBody2 body) {
		Vec2 
	}

	private Collection<Vec2> rectangleContact(RigidBody2 body) {
		ArrayList<Vec2> cornersA = (ArrayList<Vec2>) generateCorners();
		ArrayList<Vec2> linesA = (ArrayList<Vec2>) generateLines();
		
		RectangleBody bodyB = (RectangleBody) body;
		ArrayList<Vec2> cornersB = (ArrayList<Vec2>) bodyB.generateCorners();
		ArrayList<Vec2> linesB = (ArrayList<Vec2>) bodyB.generateLines();
		
		//check object A is not in object B.
		
		for(int i = 0; i < cornersA.size(); i++) {
			for(int j = 0; j < cornersB.size(); j++) {
				//check for point - point collisions
				//get the corners from the arraylists.
				Vec2 cornerA = new Vec2(cornersA.get(i));
				Vec2 cornerB = new Vec2(cornersB.get(j));
				
				Vec2 comA = new Vec2(this.getCOM());
				Vec2 comB = new Vec2(bodyB.getCOM());
				
				double scaleB = (comB.getY() + comA.getX() * cornerA.getY() - comA.getY() - comB.getX() * cornerA.getY()) / (cornerA.getY() * cornerB.getX() - cornerB.getY());
				double scaleA = (comB.getY() + cornerB.getY() * scaleB - comA.getX()) / cornerA.getY();
				
				if(scaleA == 1 && scaleB == 1) {
					Vec2 cp = new Vec2(cornerA.scale(scaleA));
					cp.addVec(comA);
					previousContacts.add(cp);
					
					
				}
				
				//check for edge - edge collisions
				Vec2 lineA = new Vec2(linesA.get(i));
				Vec2 lineB = new Vec2(linesB.get(j));
				
			}
		}
		
		
		
	}

	//returns the lines relative to the COM in the order of top left corner going clockwise.
	public Collection<Vec2> generateLines() {
		Collection<Vec2> lines = new ArrayList<>();
		
		ArrayList<Vec2> corners = (ArrayList<Vec2>) generateCorners();
		Vec2 COM = new Vec2(this.getCOM());

		for(int i = 1; i <= corners.size(); i++) {
			if(i == corners.size()) {
				Vec2 lineA = new Vec2(corners.get(0));
				lineA.addVec(new Vec2(corners.get(i - 1)).scale(-1));
				lines.add(lineA);
				break;
			}
			
			Vec2 lineA = new Vec2(corners.get(i));
			lineA.addVec(new Vec2(corners.get(i - 1)).scale(-1));
			lines.add(lineA);
		}

		return lines;
	}

	//returns the corner locations relative to the COM in order of the top left corner going clockwise.
	public Collection<Vec2> generateCorners() {
		Vec2 cornerA = new Vec2(-width, height);
		Vec2 cornerB = new Vec2(width, height);
		Vec2 cornerC = new Vec2(width, -height);
		Vec2 cornerD = new Vec2(-width, -height);

		Collection<Vec2> corners = new ArrayList<>();
		corners.add(cornerA);
		corners.add(cornerB);
		corners.add(cornerC);
		corners.add(cornerD);

		return corners;
	}

	@Override
	public Collection<Vec2> getNormals(RigidBody2 body) {
		return previousNormals;
	}

	@Override
	public void updateRoughCollisionBox() {
		// TODO Auto-generated method stub

	}

}
