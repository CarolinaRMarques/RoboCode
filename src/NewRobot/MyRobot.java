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

    private boolean scanningObject = false;

    public void run() {

        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        addCustomEvent(standardOdometer);
        addCustomEvent(this.odometer);

        //calibrating direction
        if (getHeading() >= 45){
            turnRight(225 - 180 -getHeading());
        }
        else{
            turnRight(225 - 180 -getHeading());
        }

        //Get to starting point
        goTo(18,18);

        //Make time so he gets to the starting point
        for (int i = 0; i < 80; i++) {
            doNothing(); // or perhaps scan();
        }

        while(true){
            this.scanningObject = true;
            setTurnRadarRight(360);

            execute();
        }


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

    public void doScanner(){
        //rodar o radar ja deve detetar
        setTurnRadarRight(360);
    }



    @Override
    public void onScannedRobot(ScannedRobotEvent e) {
        super.onScannedRobot(e);

        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        if (scanningObject){

            double angle = this.getHeading() + e.getBearing();

            double scannedX = getX() + e.getDistance() * Math.sin(Math.toRadians(angle));
            double scannedY = getY() + e.getDistance() * Math.cos(Math.toRadians(angle));

            double angleToEnemy =  Math.abs(e.getHeading());

            // Calculate the coordinates of the robot
            goTo(scannedX,scannedY);
        }

    }



}
