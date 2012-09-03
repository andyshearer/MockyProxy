package proxy.config;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;

public class Config {
	
	private List<Mock> mocks;
	
	public List<Mock> getMocks() {
		return mocks;
	}
	
	
	public static Config getConfig(String configDir) throws IOException {
		Gson gson = new Gson();
		String jsonString = readFileAsString(configDir);
		return gson.fromJson(jsonString, Config.class);
	}
	
    /** @param filePath the name of the file to open. Not sure if it can accept URLs or just filenames. Path handling could be better, and buffer sizes are hardcoded
     * @throws IOException 
    */ 
    static String readFileAsString(String filePath) throws IOException {
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        return fileData.toString();
    }


}
