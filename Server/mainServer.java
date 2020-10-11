 public class mainServer{
       
      public static void main(String[] args) throws IOException{
            ArrayList<BookSubmission> bookSubmissions = new ArrayList<BookSubmission>();

            int port;
            if(args[0].length() ==0) {
            port =5000;
            }
            else{
                  port = Integer.parseInt(args[0]);
            }          
       ServerSocket serverSocket = new ServerSocket(port);

           while(true){
            start(serverSocket, bookSubmissions);
            }
      }
   
       
      public static void start(ServerSocket serverSocket, ArrayList<BookSubmission>bookSubmissions){

            try{
                  System.out.println("Waiting to be connected");
                  Socket clientSocket = serverSocket.accept();
                  SocketServer socketServer = new SocketServer(clientSocket, bookSubmissions);
                  socketServer.start();
            
      }catch(IOException e){
                  e.printStackTrace();
 }
      }
 }
