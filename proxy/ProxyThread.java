package proxy;

import java.net.*;
import java.io.*;
import java.util.*;

import proxy.config.Config;
import proxy.config.Mock;

public class ProxyThread extends Thread {
    private Socket socket = null;
    private static final int BUFFER_SIZE = 32768;
    public ProxyThread(Socket socket) {
        super("ProxyThread");
        this.socket = socket;
    }

    public void run() {
        //get input from user
        //send request to server
        //get response from server
        //send response to user
    	
   
    	
    	
    	

        try {
        	
        	// get config
        	Config config = Config.getConfig();
        	System.out.println("Config loaded. We have  " + config.getMocks().size() + " mocks");
        	
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String inputLine;
            int cnt = 0;
            String urlToCall = "";

            while ((inputLine = in.readLine()) != null) {
                try {
                    StringTokenizer tok = new StringTokenizer(inputLine);
                    tok.nextToken();
                } catch (Exception e) {
                    break;
                }
                //parse the first line of the request to find the url
                if (cnt == 0) {
                    String[] tokens = inputLine.split(" ");
                    urlToCall = tokens[1];
                    //can redirect this to output log
                    System.out.println("Request for : " + urlToCall);
                }

                cnt++;
            }


        	BufferedReader rd = null;
            //begin send request to server, get response from server
            URL url = new URL(urlToCall);
            URLConnection conn = url.openConnection();
            conn.setDoInput(true);
            //not doing HTTP posts
            conn.setDoOutput(false);

            // Gsset the response
            InputStream is = null;

            String stubbedContents = null;
           for (Mock mock : config.getMocks()) {
        	   if (urlToCall.equals(mock.getUrl())) {
        		   stubbedContents = mock.getStubFileContents();
        	   }
           }
			
            try {
				if (stubbedContents != null) {
					System.out.println("Getting mock");
					is = new ByteArrayInputStream(stubbedContents.getBytes());
				} else {
					System.out.println("Getting real content");
					System.out.println("Getting real content!");
					conn.connect();
					is = conn.getInputStream();
					System.out.println("End real content");
				}
				rd = new BufferedReader(new InputStreamReader(is));
            } catch (IOException ioe) {
                System.out.println(
		"********* IO EXCEPTION **********: " + ioe);
            }
                
           
            byte by[] = new byte[ BUFFER_SIZE ];
            int index = is.read( by, 0, BUFFER_SIZE );
            while ( index != -1 )
            {
              out.write( by, 0, index );
              index = is.read( by, 0, BUFFER_SIZE );
            }
            out.flush();


            //close out all resources
            if (rd != null) {
                rd.close();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            if (socket != null) {
                socket.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

