package proxy.config;

import java.io.IOException;

public class Mock {
	private String url;
	private String stubFile;
	private Integer responseCode;
	
	public String getUrl() {
		return url;
	}
	
	public String getStubfile() {
		return stubFile;
	}
	
	public String getStubFileContents() throws IOException {
		return Config.readFileAsString(stubFile);
	}

	public Integer getResponseCode() {
		return responseCode;
	}
	
}
