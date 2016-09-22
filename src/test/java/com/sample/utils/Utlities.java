package com.sample.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Properties;

import com.sample.common_actions.CommonMethods;

public class Utlities extends CommonMethods{
	
	/*
	 * This method will read the properties form property files
	 * @author Raju
	 * @param property
	 * @return Property value
	 */
	public static HashMap<String, String> readProperties(String fileName) {
		prop = new Properties();
		
		try {
			propFis = new FileInputStream(new File(fileName));
			prop.load(propFis);
			propertiesMap=new HashMap<String, String>();
			Enumeration<Object> KeyValues = prop.keys();
			while (KeyValues.hasMoreElements()) {
				String key = (String) KeyValues.nextElement();
				String value = prop.getProperty(key);
				propertiesMap.put(key, value);
				//System.out.println(key + ":- " + value);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return propertiesMap;
	}
}
