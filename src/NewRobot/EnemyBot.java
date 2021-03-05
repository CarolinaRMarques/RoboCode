package NewRobot;
import robocode.*;

public class EnemyBot {
	private double bearing;
	private double distance;
	private double heading;
	private String name;
	private double velocity;
	private double energy;

	public EnemyBot() {
		reset();
	}
	
	public void reset() {
		setBearing(0.0);
		setDistance(0.0);
		setHeading(0.0);
		setVelocity(0.0);
		setEnergy(0.0);
		setName("");
	}
	
	public void update(ScannedRobotEvent e) {
		
	}
	
	public double getBearing() {
		return bearing;
	}

	public void setBearing(double bearing) {
		this.bearing = bearing;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public double getHeading() {
		return heading;
	}

	public void setHeading(double heading) {
		this.heading = heading;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getVelocity() {
		return velocity;
	}

	public void setVelocity(double velocity) {
		this.velocity = velocity;
	}

	public double getEnergy() {
		return energy;
	}

	public void setEnergy(double energy) {
		this.energy = energy;
	}
}

	

