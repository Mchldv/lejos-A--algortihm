import lejos.nxt.*;
import lejos.nxt.Button;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.util.Delay;

public class maze {

	private static DifferentialPilot myPilot;
	private static final double wheelDiam = 2.5f;
	private static final double axleLength = 9.6f;
	private static final double rotateSpeed = 10.0f;
	private static final double travelDist = 10.50f;	
	private static UltrasonicSensor sonic;
	private static int posX;
	private static int posY;
	private static int arah;
	
	public static int map[][];
	
	public void MazeSolver() {
		// Set up motors and sensors
		
	}
	
	public static int checkDis()
	{
		return sonic.getDistance();
	}
	
	public static void rotateLeft()
	{
		myPilot.rotate(50);
	}
	
	public static void rotateRight()
	{
		myPilot.rotate(-50);
	}
	
	public static void updatePos()
	{
		if(arah==0)
		{
			posY--;
		}
		else 
		if(arah==1)
		{
			posX++;
		}
		else
		if(arah==2)
		{
			posY++;
		}
		else
		{
			posX--;
		}
	}
	
	public static void updateMap()
	{
		map[posY][posX]=1;
	}
	
	public static void updateMapBuntu()
	{
		map[posY][posX]=0;
	}
	
	public static boolean adaJalan(int jarak)
	{
		if(jarak>30) return true;
		return false;
	}

	public static void main(String[] args)
	throws Exception{
		// Create and start the solver
		//maze mySolver = new maze();
		myPilot = new DifferentialPilot(wheelDiam, axleLength, Motor.C,
				Motor.B, false);
		sonic = new UltrasonicSensor(SensorPort.S3);
		sonic.continuous();
		// Set rotate speed
		myPilot.setRotateSpeed(rotateSpeed);
		myPilot.setAcceleration(50);
		boolean rotateSonic = false;
		int kiri,kanan;
		posX=0;
		posY=0;
		map[posY][posX]=1;
		arah=2;
		while(!Button.ESCAPE.isDown())
		{
			//sonic.ping();
			LCD.clear();
			LCD.drawInt(sonic.getDistance(), 0, 0);
			if(!rotateSonic) {
				if(sonic.getDistance()>30)
				{
					myPilot.travel(travelDist);
					//myPilot.forward();
					updatePos();
					updateMap();
				}
				else
				{
					myPilot.stop();
					updateMapBuntu();
					rotateSonic=true;
				}
			}
			else
			{
				Motor.A.rotate(90);
				kiri=checkDis();
				Delay.msDelay(1000);
				Motor.A.rotate(-180);
				kanan=checkDis();
				Delay.msDelay(1000);
				Motor.A.rotate(90);
				if(kiri<kanan && adaJalan(kiri))
				{
					rotateRight();
				}
				else
				if(kiri>kanan && adaJalan(kanan))
				{
					rotateLeft();
				}
				else
				{
					
				}
				Thread.sleep(100);
				rotateSonic=false;
			}
		}
		Thread.sleep(2000);
	}
}
