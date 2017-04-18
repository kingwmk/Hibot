package cn.ybz21.hibotvoice.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.wpi.rail.jrosbridge.Ros;
import edu.wpi.rail.jrosbridge.Topic;
import edu.wpi.rail.jrosbridge.messages.geometry.Twist;

//import edu.wpi.rail.jrosbridge.messages.geometry.Twist;
//import edu.wpi.rail.jrosbridge.messages.geometry.Vector3;

public class VoiceNav {
	private String TOPIC_NAME = "/cmd_vel_mux/input/teleop";
	private String Mes_TYPE = "geometry_msgs/Twist";
	Ros ros= new Ros("192.168.0.102");
//	Ros ros = new Ros("172.20.10.5");

	Topic cmdVelPub = new Topic(ros, TOPIC_NAME, Mes_TYPE);
	Twist twist = new Twist();
			
    int count = 0;
    //boolean stop = false;
	private HashMap<String, String> cmdMap;

	public VoiceNav() {
		ros.connect();
		cmdMap = new HashMap<String, String>();

		/*cmdMap.put( "前进，","forward");
		cmdMap.put( "向前走，","forward");
		cmdMap.put( "钱，","forward");
		cmdMap.put( "钱静，","forward");
		cmdMap.put( "右转，","turnRight");
		cmdMap.put( "左转，","turnLeft");
		cmdMap.put("后退，","backward" );
		cmdMap.put("停止，","stop");*/


	}

	// Attempt to match the recognized word or phrases to the cmdMap dictionary
	// and return the appropriate command.
	public String getCmd(String speech) {
		
		cmdMap = (HashMap<String, String>) CmdText.readTextFile();

		Iterator<Entry<String, String>> it = cmdMap.entrySet().iterator();
		System.out.println(cmdMap.entrySet().size());// 输出5
		// 返回命令
		String cmd = "";
		boolean noCmd = true;
		while (it.hasNext() && noCmd) {
			Map.Entry entry = (Map.Entry) it.next();
			Object obj = entry.getKey();
			if (obj != null && obj.equals(speech)) {
				cmd = (String) entry.getValue();// 搜索命令
				noCmd = false;
			}
		}
		System.out.println("this is the speech command: \t" + cmd);
		return cmd;
	}

	public Twist speechCallback(String speech) {
		// get the motion command from the recognized phrase
		String cmd = getCmd(speech);
		System.out.println("Command:  " + cmd+"\t the count :\t" +count);
		// Twist twist = null;
		// String cmdJson;
		if(cmd =="no1"){
//			ros.disconnect();
			ros=new Ros("192.168.0.102");
			cmdVelPub = new Topic(ros, TOPIC_NAME, Mes_TYPE);
			System.out.println("AAAAAAAAAAAA");
		}
		if(cmd =="no2"){
//			ros.disconnect();
		  	ros=new Ros("192.168.0.103");
			cmdVelPub = new Topic(ros, TOPIC_NAME, Mes_TYPE);
			System.out.println("BBBBBBBBBBB");
		}
		if (cmd == "forward") {
			// 初始化一定要linear和angular都有
			twist = Twist
					.fromJsonString("{\"linear\": {\"x\": 0.1},\"angular\":{\"z\":0}}");
		} else if (cmd == "turnLeft") {
			twist = Twist
					.fromJsonString("{\"linear\": {\"x\": 0.1},\"angular\": {\"z\": 0.3}}");
		} else if (cmd == "turnRight") {
			twist = Twist
					.fromJsonString("{\"linear\": {\"x\": 0.1},\"angular\": {\"z\": -0.3}}");
		} else if (cmd == "backward") {
			twist = Twist
					.fromJsonString("{\"linear\": {\"x\": -0.1},\"angular\":{\"z\":0}}");
		} else if (cmd == "stop") {
			twist = new Twist();
			System.out.println("Turtlebot结束运动。");//////
			//stop = true;
			
		}
		return twist;
	}

	public void move(String speech) {

		twist = speechCallback(speech);
		if(count ==0)
		{
		new Thread() {
			public void run() {
				while ( true)  {
					cmdVelPub.publish(twist);
					System.out.println(twist.getLinear());
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}			
			}
		}.start();
		count =1;
		}
		else{
			twist = speechCallback(speech); 
			}
		
		
		
		// ros.disconnect();
	}
}
