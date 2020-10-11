import java.io.*;
import java.net.*;

public class SocketClient {
	private Socket clientSocket; 
	private BufferedReader br; 
	private PrintWriter dout; 
	
	public void stopConnection() throws IOException{
		br.close();
		dout.close();
		clientSocket.close(); 
	}
	
		
	public void connect(String location, int port){
	 	try
		{
		System.out.println("Conneting to server " +location+ " on port " + port);
		 clientSocket = new Socket();
		System.out.println("AFter clientsocket - new socket ");
		SocketAddress socketAddress = new InetSocketAddress(location,port);
		System.out.println("AFter socketAddress = new InetSocketAddress(location,port)");

		clientSocket.connect(socketAddress);
		System.out.println(" AFTER clientSocket.connect(socketAddress)");
		 //din = new DataInputStream(clientSocket.getInputStream());
		 dout = new PrintWriter(clientSocket.getOutputStream());
		 System.out.println("AFTER DOUT SET");
		 br = new BufferedReader( new InputStreamReader(clientSocket.getInputStream()));
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
                              boolean all) throws IOException{
	String processedMessage = processMessage(request, ISBN, TITLE, AUTHOR, PUBLISHER, YEAR, all);
	System.out.println(processedMessage + "\n message proccesed");
	dout.println(processedMessage + "\r\n\\EOF");
	String response = getServerResponse();
System.out.println("--------------------------------");	
System.out.println(response); 
System.out.println(":  returning that response");
	return response;
}
	
public String getServerResponse() throws IOException{
	String serverResponse = "";
	String line = br.readLine();
	System.out.println(line + "line = br.readline");
	while(line!=null && !line.contains("\\EOF")){
		System.out.println("possible infinit loop");
		serverResponse = serverResponse.concat(line + "\r\n");
		System.out.println("ServerResponse: " + serverResponse);
           	line = br.readLine();
        System.out.println("end of loop");
		}
	System.out.println(serverResponse);
	System.out.println("Exited the  while loop ");
	return serverResponse;

}
}
