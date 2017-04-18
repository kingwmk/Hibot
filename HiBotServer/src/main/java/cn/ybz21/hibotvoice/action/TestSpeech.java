package cn.ybz21.hibotvoice.action;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class TestSpeech {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HashMap<String, String> cmdMap =  (HashMap<String, String>) CmdText.readTextFile();
		Iterator<Entry<String, String>> it = cmdMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, String> entry = (Map.Entry<String, String>)it.next();
			System.out.println(entry.getKey()+"==" +entry.getValue());
		}

	}

}
