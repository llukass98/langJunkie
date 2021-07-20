// HttpRequest class, makes requests and returns responses
package ru.lukas.langjunkie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/* Singleton */
class HttpClient {

    private static HttpClient instance;
    private String responseBody;
    private int statusCode;
    private HashMap<String,String> headers;
    public static final String HTTP_GET = "GET";
    public static final String HTTP_POST = "POST";  
    
    private HttpClient () {}
    
    private void makeGetRequest(String url) throws IOException {
	URL uri = new URL(url);
	HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
	BufferedReader in = null;
	StringBuilder body = new StringBuilder();
	String line;
	
	headers = new HashMap<String,String>();
	conn.setRequestMethod(this.HTTP_GET);
	conn.setReadTimeout(15*1000);
	conn.setFollowRedirects(true);
	conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:89.0) Gecko/20100101 Firefox/89.0");
	conn.setRequestProperty("Referer", url.split("\\?")[0]);
	conn.connect();

	statusCode = conn.getResponseCode();
	setHeaders(conn);
	
	in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	
	while ((line = in.readLine()) != null) {
	    body.append(line + "\n");
	}
	
	responseBody = body.toString();
	in.close();    
    }
    
    private void setHeaders(HttpURLConnection conn) {
	for (int i = 1; i <= 50; i++) {
	    String key, value;
	    
	    key = conn.getHeaderFieldKey(i);
	    value = conn.getHeaderField(i);      
      
	    if (key == null || value == null) {
		break;
	    }
      
	    headers.put(key, value);
	}
    }

    public void makeRequest(String method, String url) throws IOException {
	if (method == this.HTTP_GET) {
	    makeGetRequest(url);
	}
    }
    
    // Getters
    public static HttpClient getInstance() {
	if (instance == null) {
	    instance = new HttpClient();
	}
	
	return instance;
    }
    
    public int getStatusCode() {
	return statusCode;
    }
    
    public String getResponseBody() {
	return responseBody;
    }
    
    public HashMap<String,String> getHeaders() {
	return headers;
    }
    
}
