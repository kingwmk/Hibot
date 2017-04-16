package cn.ybz21.hibotvoice.action;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class JsonToJava {
	private static Gson gson = new Gson();
	
	public String getJsonToString(String jsonString){
		
		String str = gson.fromJson(jsonString, String.class);
		return str;
	}
	
	public ArrayList<String> getJsonToAList(String jsonString){
		
		ArrayList<String> resultList =
				gson.fromJson(jsonString, new TypeToken<ArrayList<String>>(){}.getType());
		return resultList;
	}
	
	public Map<String,String> getJsonToHashMap(String jsonString){
		
		Type type = new TypeToken<HashMap<String, String>>(){}.getType();
		Map<String, String> map = gson.fromJson(jsonString, type);
		return map;
	}
	
	public Map<String,List<String>> getJsonToMapList(String jsonString){
		
		List<String> list = new ArrayList<String>();
		
		Type type = new TypeToken<Map<String,ArrayList<String>>>(){}.getType();
		Map<String,List<String>> mapList = gson.fromJson(jsonString, type);
		
		return mapList;
	}

}

