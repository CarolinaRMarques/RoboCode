package NewRobot;
import robocode.*;
import robocode.util.Utils;
import standardOdometer.Odometer;


public class MyRobot extends AdvancedRobot{
	private Odometer standardOdometer = new Odometer ("isRacing", this);
	private MyOdometer odometer = new MyOdometer("isRacing",this);
	private boolean start = false;

	double actualY;
	double actualX;

	private boolean scanningObject;
	
	public void run() {
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		
		addCustomEvent(standardOdometer);
		addCustomEvent(this.odometer);

		scanningObject = true;
		goTo(18,18);
	}

	public void onCustomEvent (CustomEvent ev) {
		Condition cd = ev.getCondition();
		if (cd.getName().equals("isRacing"))
			this.odometer.getRaceDistance();
		if (cd.getName().equals("isRacing"))
			this.standardOdometer.getRaceDistance();
	}

	private void goTo(double x, double y){
		x -= getX();
		y -= getY();

		/* Calculate the angle to the target position */
		double angleToTarget = Math.atan2(x, y);

		/* Calculate the turn required get there */
		double targetAngle = Utils.normalRelativeAngle(angleToTarget - getHeadingRadians());

		/*
		 * The Java Hypot method is a quick way of getting the length
		 * of a vector. Which in this case is also the distance between
		 * our robot and the target location.
		 */
		double distance = Math.hypot(x, y);

		/* This is a simple method of performing set front as back */
		double turnAngle = Math.atan(Math.tan(targetAngle));
		setTurnRightRadians(turnAngle);
		
		if(targetAngle == turnAngle) {
			setAhead(distance);
		} else {
			setBack(distance);
		}
	}

	private void findPath() {
		//1º passo : scan por robo no 
		
	}

	@Override
    public void onScannedRobot(ScannedRobotEvent event) {	
        super.onScannedRobot(event);
	    // Assuming radar and gun are aligned...       
	    if (scanningObject)
		    {
				setTurnRadarRight(360);
		    }
	    else
		    {
		        fire(1);
		    }
	}


}
