
import java.io.*;
import java.net.*;


public class SocketClient
{
	private Socket clientSocket; 
	
	private DataInputStream  din   = null; 
	private DataOutputStream dout     = null; 
	
	public void stopConnection() throws IOException{
		din.close();
		dout.close();
		clientSocket.close(); 
	}
	
		
	public void connect (String location, int port) throws IOException
	{
	 	try
		{
		System.out.println("Conneting to server " +location+ " on port " + port);
		Socket clientSocket = new Socket():
		SocketAddress socketAddress = new InetSocketAddress(location,port);
		clientSocket.connect(socketAddress);
		 din = new DataInputStream(clientSocket.getInputStream());
		 dout = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	} catch(IOException e){
		e.printStackTrace();
		throw new IOException("Connection refused. Enter a new address and Port.");
		}	
	}
	
		
	public String processMessage(Request request, String ISBN, String TITLE, String AUTHOR, String PUBLISHER, int YEAR, boolean all){
		String requestData= request.name() + "\r\n";

		if (all){
			requestData = requestData + "ALL";
		}
		else{
			requestData = requestData + "ISBN: " + ISBN + "\r\n" + "TITLE: " + TITLE + "\r\n" + "AUTHOR: " + AUTHOR + "\r\n" + "PUBLISHER: " + PUBLISHER + "\r\n" + "YEAR: " + YEAR + "\r\n";
			}
		    		

		/*
		String[] split = serverResponse.split("\r\n");
		   for (String data : split) {
			String[] splitLine = x.split(" ");
                    if (splitLine[0].contains("ISBN")) {
                        serverResponse = serverResponse.concat("@BookSubmission{\r\n\tISBN\t= \"" + x.substring(splitLine[0].length()).trim() + "\",\r\n");
                    }
                   else if (splitLine[0].contains("TITLE")) {
                        serverResponse = serverResponse.concat("\tTITLE\t= \"" + x.substring(splitLine[0].length()).trim() + "\",\r\n");
                    }
                   else if (splitLine[0].contains("AUTHOR")) {
                        serverResponse = serverResponse.concat("\tAUTHOR\t= \"" + x.substring(splitLine[0].length()).trim() + "\",\r\n");
                    }
                   else if (splitLine[0].contains("PUBLISHER")) {
                        serverResponse = serverResponse.concat("\tPUBLISHER\t= \"" + x.substring(splitLine[0].length()).trim() + "\",\r\n");
                    }
                    else if (splitLine[0].contains("YEAR")) {
                            serverResponse = serverResponse.concat("\tYEAR\t= \"" + x.substring(splitLine[0].length()).trim() + "\",\r\n");
                    }
                }
		*/
		return requestData;	
        }
	catch(Exception e)
	{
		e.printStackTrace(); 		
        }	
        }
public String sendMessage( Request request, String ISBN, String TITLE, String AUTHOR, String PUBLISHER, int YEAR,
                              boolean all){
	String processedRequest = processRequest(request, ISBN, TITLE, AUTHOR, PUBLISHER, YEAR, all);
	dout.println(processRequest + "\r\n\\EOF");
	String response = getServerResponse();

	return response;
	

}
public String getServerResponse{
	String serverResponse = "";
	String line = din.readLine();
	
	while(line!=null && !line.contains("\\EOF")){
		serverResponse = serverResponse.concat(line + "\r\n");
           	line = din.readLine();
		}
	return serverResponse;

}
	
	
public class mainClient{
	public static void main(String[] args){
		
			
	}
	}
	
	
