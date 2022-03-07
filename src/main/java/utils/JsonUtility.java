package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonUtility {

	public String getData(String key) throws FileNotFoundException, IOException, ParseException{

		String value;
		JSONParser parser = new JSONParser();
	    Object obj = parser.parse(new FileReader("./resources/testData.json"));
	    JSONObject jsonObject = (JSONObject)obj;
	    value = (String)jsonObject.get(key);
	    
	    return value;
	}
	
	
}
