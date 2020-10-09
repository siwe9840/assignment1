
import java.io.*;
import java.net.*;

public class SocketClient {
	private Socket clientSocket; 
	private DataInputStream  din   = null; 
	private DataOutputStream dout     = null; 
	
	public void stopConnection() throws IOException{
		din.close();
		dout.close();
		clientSocket.close(); 
	}
	
		
	public void connect(String location, int port){
	 	try
		{
		System.out.println("Conneting to server " +location+ " on port " + port);
		Socket clientSocket = new Socket();
		SocketAddress socketAddress = new InetSocketAddress(location,port);
		clientSocket.connect(socketAddress);
		 din = new DataInputStream(clientSocket.getInputStream());
		 dout = new DataOutputStream(clientSocket.getOutputStream());
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	} catch(IOException e){
		e.printStackTrace();
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
		return requestData;	
  	
	}
        

public String sendMessage(Request request, String ISBN, String TITLE, String AUTHOR, String PUBLISHER, int YEAR,
                              boolean all){
	String processedMessage = processMessage(request, ISBN, TITLE, AUTHOR, PUBLISHER, YEAR, all);
	dout.println(processedMessage + "\r\n\\EOF");
	String response = getServerResponse();

	return response;
}
	
public String getServerResponse(){
	String serverResponse = "";
	String line = din.readLine();
	
	while(line!=null && !line.contains("\\EOF")){
		serverResponse = serverResponse.concat(line + "\r\n");
           	line = din.readLine();
		}
	return serverResponse;

}
}

	
