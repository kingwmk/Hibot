package cn.ybz21.hibotvoice.action;

import edu.wpi.rail.jrosbridge.Ros;
import edu.wpi.rail.jrosbridge.Topic;
import edu.wpi.rail.jrosbridge.messages.Message;
import edu.wpi.rail.jrosbridge.messages.geometry.Twist;

//向前走一米。
public class Forward {

	//How fast will we update the robot's movement?
	private static int RATE = 25 ;
	
	//Set the travel distance to 1.0 meter
	private static double L_DISTANCE = 1.0 ;
	
	//Set the forward linear speed to 0.2 meters per second.
	private static double L_SPEED = 0.2 ;
	
	//move forward for a time to go 1 meter
	private static double TICKS =  RATE *L_DISTANCE/L_SPEED ;//RATE *L_DISTANCE/L_SPEED ;

//	private static Topic cmdVel;
	//private static Ros ros;

	public static void main(String[] args) throws InterruptedException {
		Ros ros = new Ros();
		ros.connect();
		
		 Topic  cmdVel = new Topic(ros, "/cmd_vel_mux/input/teleop", "geometry_msgs/Twist");
			//Message msg = new Message("{\"linear\": {\"x\": 0.2, \"y\" : 0.0, \"z\": 0.0}, \"angular\" : {\"x\": 0.0, \"y\": 0.0, \"z\": 0}}");
			//Twist twist = Twist.fromMessage(msg);
		 Twist twist = Twist.fromJsonString("{\"linear\": {\"x\": 0.2, \"y\" : 0.0, \"z\": 0.0}, \"angular\" : {\"x\": 0.0, \"y\": 0.0, \"z\": 0}}");
			
			for(int i =0; i<TICKS; i++)
			{
				cmdVel.publish(twist);
				System.out.println("send message:");
				System.out.println(twist.getLinear());
				try {
					//We publish message every 1/rate seconds for appropriate duration.
					Thread.sleep(1000/RATE);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			//Publish empty message to stop the robot.
			  cmdVel.publish(new Twist());
			
		}
	}

