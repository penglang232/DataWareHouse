package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class ConfigManager {
	private Map<String,String> configMap = new HashMap<String,String>();
	private static ConfigManager instance = null;
	
	private ConfigManager() {
		initConfig();
	}

	public static ConfigManager getInstance(){
		if(instance == null){
			synchronized (ConfigManager.class) {
				instance = new ConfigManager();
			}
		}
		return instance;
	}
	
	public void initConfig() {
		File file = new File("conf/config.ini");
		if(!file.exists()){
			PrintWriter pw = null;
			try {
				file.createNewFile();
				pw = new PrintWriter(file);
				pw.println("database=sqlite");
				pw.println("sqlurl=jdbc:sqlite:db/dataWareHouse.db");
				pw.println("username=dev");
				pw.println("password=fleety");
				pw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				pw.close();
			}
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(file));
			String str = null;
		    while((str = br.readLine()) != null){
		    	if(!str.contains("=")){
		    		continue;
		    	}
		    	if(str.contains("=")){
		    		int index = str.indexOf("=");
		    		configMap.put(str.substring(0, index),str.substring(index+1, str.length()));
		    	}
		    }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void refreshMap(Map<String, String> configMap){
		if(configMap == null){
			configMap = this.configMap;
		}
		File file = new File("conf/config.ini");
		if(file.exists()){
			file.delete();
		}
		PrintWriter pw = null;
		try {
			file.createNewFile();
			pw = new PrintWriter(file);
			for(String key:configMap.keySet()){
				pw.println(key + "=" + configMap.get(key));
			}
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			pw.close();
		}
	}

	public void setConfigMap(Map<String, String> configMap) {
		this.configMap = configMap;
	}

	public Map<String, String> getConfigMap() {
		return configMap;
	}
}
