package proxy.config;

import java.io.IOException;

public class Mock {
	private String url;
	private String stubFile;
	
	public String getUrl() {
		return url;
	}
	
	public String getStubfile() {
		return stubFile;
	}
	
	public String getStubFileContents() throws IOException {
		return Config.readFileAsString(stubFile);
	}
	
}
