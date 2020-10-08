import java.net.*;
import java.io.*; 
import java.util.*;

public class SocketServer extends Thread{
      Private ArrayList<BookSubmission> bookSubmissions;

      private ServerSocket serverSocket;
      private int port;
      private boolean isRunning= false;

    public ServerSocket(int port, ServerSocket serverSocket, ArrayList<BookSubmission> bookSubmissions ){
        this.port = port;
        this.serverSocket = serverSocket;
        this.bookSubmissions = bookSubmissions; 
    }
    
    public synchronized void stopServer(){
        this.isRunning = false;
         try{
        din.close();
        dout.close();
        serverSocket.close(); 
        this.interrupt();
          }
           catch( Exception e){
                  System.out.println("Connection was not properly closed"); 
                  e.printStackTrace();
                  }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(port);
            this.start(); 
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port ", port);
        }
    }

    public void run(){
        synchronized(this){
        }
        openServerSocket();
        this.isRunning = true; 
            try {
                System.out.println("waiting for a connection");
                Socket socket = this.serverSocket.accept();
                startRunning(); 
             } catch (IOException e) {
                e.printStackTrace();
                }
                throw new RuntimeException(
                    "Error accepting client connection", e);
            }
        }
        System.out.println("Server Stopped.") ;
    }

      private String submitRequest(String []requestData){
     
            String serverMessage;
            String submit = "":
            BookSubmission bookSubmission = new BookSubmission();
            for (String line : requestData){
                  String[] data = line.split(" "); 
                  if (data[0].equals("ISBN")){
                        if(Lookup.lookupISBN(bookSubmissions, data[1] != null){
                              serverMessage = "ISBN already exists in directory";
                              return serverMessage;
                        }
                        else{
                        bookSubmission.setISBN(data[1]);
                        }
                    }
                              
                    else if (data[0].equals("TITLE")){
                              submit = line.substring(data[0].length()).trim();
                              bookSubmission.setTitle(submit);

                        }
                    else if (data[0].equals("AUTHOR")){
                              submit = line.substring(data[0].length()).trim();
                              bookSubmission. setAuthor(submit)
                        }
                        
                       else if (data[0].equals("PUBLISHER")){
                             submit = line.substring(data[0].length()).trim();
                             bookSubmission.setPublisher(submit)
                       }
                     else if (data[0].equals("YEAR")){
                               bookSubmission.setYEAR(Integer.parseInt(data[1]));
                     }
                        }
                        }
                            
                     serverMessage = "The entry with ISBN code " + ISBN+" has been added to the bibliography successfully.");
                     bookSubmissions.add(bookSubmission); 
                     return serverMessage;  
                     }
                  
      private String updateRequest(String []requestData){
            String serverMessage = "";
            BookSubmission book = null;
            for (String line : requestData){
                  line = line.trim();
                  String [] data = line.split(" ");
                  String submit = line.substring(data[0].length()).trim();
                  if (data[0].equals("ISBN")){
                        book = Lookup.lookupISBN(bookSubmissions, submit);
                        }
                 
                  if (book!=null){
                        if (data[0].equals("TITLE") && submit.length()>=1){
                              book.setTitle(submit);
                        }
                        else if (data[0].equals("AUTHOR") && submit.length()>=1){
                              book.setAuthor(submit)
                        }
                        
                       else if (data[0].equals("PUBLISHER") && submit.length()>=1){
                             book.setPublisher(submit)
                       }
                      else if (data[0].equals("YEAR") && submit.length()>=1){
                               book.setYEAR(Integer.parseInt(data[1]));
                     }
                        }
                        }
            if(book !=null)
            {
                  serverMessage = "The book was succesfully updated!";
            }
            else{
                  serverMessage = "The book does not exist in the directory";
            }
            return serverMessage;           
      }
                  
      private String getRequest(String []requestData){
      }
      private String removeRequest(String []requestData){
            String serverMessage = "";
            BookSubmission book = null;
            ArrayList<ArrayList<BookSubmission>> bookList = new ArrayList<>();
            for (String line : requestData){
                  line = line.trim();
                  String[] data = line.split(" ");
                  String submit = line.substring(data[0].length()).trim();
                  if(data[0].equals("ISBN") && submit.length >=1){
                         book = Lookup.lookupISBN(bookSubmissions, submit);
                         
                         bookList.add()
                        
                        }          
                  else if (data[0].equals("TITLE")&& submit.length >=1){
                        bookList.add()

                        }
                  else if (data[0].equals("AUTHOR")&& submit.length >=1){
                        bookList.add()

                        }
                        
                   else if (data[0].equals("PUBLISHER")&& submit.length >=1){
                         bookList.add()
                
                       }
                   else if (data[0].equals("YEAR")&& submit.length >=1){
                         bookList.add()

                     }
                        }
            
                if(book == null){
                    serverMessage = "Cannot find book to be removed";
                    return serverMessage; 
                         }
            
                serverMessage = "Book has been removed from the directory"
                returnMessage; 
      }

      public void startRunning()
      {
            try
            {
            System.out.println("Connection Accepted");
                  DataInputStream din = new DataInputStream(socket.getInputStream());
                  DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
                  BufferedReader br = newBufferedReader(new InputStreamReader());
                 
                  dout.println("Enter a Request or "Stop" to end session: ");
                  dout.flush(); 
                  String line = "";
                  String serverMessage = "";
                  String clientMessage = "";
                  line = din.readLine(); 
                        while(line!=null){
                              while(!line.contains("\\EOF")){
                                    clientMessage = clientMessage.concat(line+"\r\n");
                                    line = din.readLine();
                              }
                              
                              String request = clientMessage[0].trim();
                              clientMessage = clientMessage.split(("\n").trim()+"\r\n\\EOF");
                              if(request.equals("SUBMIT"){
                                    serverMessage = submitRequest(clientMessage);

                              }
                              else if(request.equals("UPDATE"){
                                    serverMessage = updateRequest(clientMessage);

                              }
                              else if(request.equals("GET"){
                                    serverMessage = getRequest(clientMessage);
                              }
                              else if(request.equals("REMOVE"){
                                    serverMessage = removeRequest(clientMessage);
                              }
                             else if(request.equals(""){
                                    dout.println("No request identified.")
                                    dout.flush();
                              }
                              else if (request.equals("Stop"){
                                    dout.println("Stopping connection");
                                    dout.flush();
                                    stopServer();
                              else{
                                    dout.println("Request not recognized.");
                                    dout.flush()
                                                 }
                              
                        dout.println(serverMessage);
                        dout.flush(); 
                        dout.println("Enter another Request or "Stop" to end session: ");
                        dout.flush(); 
                        line = in.readLine(); 
                                                 
                        }   
                            }catch (IOException e){
                                  e.printStackTrace();
                                     
                  }
              }

                 
                  
 public class mainServer{
      public static void main(String[] args) throws IOException{
            private boolean run = true; 
            ArrayList<BookSubmission> bookSubmission = new ArrayList<BookSubmission>();
            int port
            if(args[0].length ==0) {
            port =5000;
            }
            else port = Integer.parseInt(arg[0]);
            while (run){ //this part needs editing
           
                  SocketServer socketServer = new SocketServer(Thread.activeCount()+"",clientSocket,bookSubmission);
                  socketServer.start();
                  run(): 
                              }
           }
       }

            
            
                
