import java.net.*;
import java.io.*; 
import java.util.*;

public class SocketServer extends Thread{
      private final ArrayList<BookSubmission> bookSubmissions;
      private final ServerSocket serverSocket;
      private boolean isRunning= false;

    public SocketServer(ServerSocket serverSocket, ArrayList<BookSubmission> bookSubmissions ){
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
          DataInputStream din = new DataInputStream(serverSocket.getInputStream());
          DataOutputStream dout = new DataOutputStream(serverSocket.getOutputStream());
          BufferedReader br = newBufferedReader(new InputStreamReader());
          startRunning(); 
              
        } catch (IOException e) {
            e.printStackTrace();
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
            }
        

      private String submitRequest(String[] requestData){
            String serverMessage = "";
            String submit = "";
            BookSubmission bookSubmission = new BookSubmission();
            for (String line : requestData){
                  String[] data = line.split(" "); 
                  if (data[0].equals("ISBN")){
                        if(Lookup.lookupISBN(bookSubmissions, data[1] != null)){
                              serverMessage = ("ISBN already exists in directory");
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
                              bookSubmission.setAuthor(submit);
                        }
                       else if (data[0].equals("PUBLISHER")){
                             submit = line.substring(data[0].length()).trim();
                             bookSubmission.setPublisher(submit);
                       }
                     else if (data[0].equals("YEAR")){
                               bookSubmission.setYEAR(Integer.parseInt(data[1]));
                     }
                        }
                        
                            
                     serverMessage = ("The entry has been added to the bibliography successfully.");
                     bookSubmissions.add(bookSubmission); 
                     return serverMessage;  
                     }
                  
      
      private String updateRequest(String[] requestData){
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
                              book.setAuthor(submit);
                        }
                        
                       else if (data[0].equals("PUBLISHER") && submit.length()>=1){
                             book.setPublisher(submit);
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
                  
      private String getRequest(String[] requestData){
            ArrayList<ArrayList<BookSubmission>> bookList = new ArrayList<>();
            StringBuilder serverMessage = new StringBuilder();
            String message = "";
            for(String line : requestData){
                  line = line.trim();
                  String data = line.split(" ");
                  String submit = line.substring(data[0].length()).trim();
                  if(data[0].equals("ALL")){
                        for(bookSubmission bookSubmission : bookSubmissions){
                              serverMessage.append(bookSubmission.toString());
                              serverMessage.append("\n");
                        }
                        if(serverMessage.length() == 0){
                              message = "No books found";
                              return message; 
                        }
                              
                        return serverMessage.toString();
                  }
                  else if(data[0].equals("ISBN")){
                        bookList.add(Lookup.lookup(bookSubmissions, "ISBN", submit));

                  }
                  else if(data[0].equals("TITLE")){
                        bookList.add(Lookup.lookup(bookSubmissions, "TITLE", submit));
                  }
                  else if(data[0].equals("AUTHOR")){
                        bookList.add(Lookup.lookup(bookSubmissions, "AUTHOR", submit));
                  }
                  else if(data[0].equals("PUBLISHER")){
                        bookList.add(Lookup.lookup(bookSubmissions, "PUBLISHER", submit));
                  }
                  else if(data[0].equals("YEAR")){
                        bookList.add(Lookup.lookup(bookSubmissions, "YEAR", submit));
                  }
            }
            
              ArrayList<ArrayList<BookSubmission>> intersection = new ArrayList<>();
              intersection.addAll(Arrays.asList(bookSubmissions));
              intersection.retainAll(Arrays.asList(bookList));       
              if(intersection == null){
                     message = "No books were found";
                    return message;
              }
            for(BookSubmission bookSubmission : intersection){
                  serverMessage.append(bookSubmission.toString());
                  serverMessage.append("\n");
            }
            return serverMessage.toString();                    
      }
      
      private String removeRequest(String []requestData){
            String serverMessage = "";
            BookSubmission book = null;
            ArrayList<ArrayList<BookSubmission>> bookList = new ArrayList<>();
            for (String line : requestData){
                  line = line.trim();
                  String[] data = line.split(" ");
                  String submit = line.substring(data[0].length()).trim();
                  if(data[0].equals("ISBN") && submit.length() >=1){
                         book = Lookup.lookupISBN(bookSubmissions, submit);
                         
                         bookList.add(Lookup.lookup(bookSubmissions, "ISBN", submit));
                        
                        }          
                  else if (data[0].equals("TITLE")&& submit.length() >=1){
                         bookList.add(Lookup.lookup(bookSubmissions, "TITLE", submit));

                        }
                  else if (data[0].equals("AUTHOR")&& submit.length() >=1){
                         bookList.add(Lookup.lookup(bookSubmissions, "AUTHOR", submit));

                        }
                        
                   else if (data[0].equals("PUBLISHER")&& submit.length() >=1){
                         bookList.add(Lookup.lookup(bookSubmissions, "PUBLISHER", submit));
                
                       }
                   else if (data[0].equals("YEAR")&& submit.length() >=1){
                         bookList.add(Lookup.lookup(bookSubmissions, "YEAR", submit));

                     }
                        }
            
                if(book == null){
                    serverMessage = "Cannot find a book to be removed";
                        return serverMessage; 
                         }
            
                  
              ArrayList<ArrayList<BookSubmission>> intersection = new ArrayList<>();
              intersection.addAll(Arrays.asList(bookSubmissions));
              intersection.retainAll(Arrays.asList(bookList));
              if (intersection !=null)
              {
                    for(BookSubmission bookSubmission : intersection){
                          bookSubmissions.remove(bookSubmission);
                    }
                    }
            
                serverMessage = "The book(s) have been removed from the directory";
                return serverMessage; 
      }

      public void startRunning()
      {
            try
            {
                  dout.println("Enter a request or 'Stop' to end session");
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
                              if(request.equals("SUBMIT")){
                                    serverMessage = submitRequest(clientMessage);

                              }
                              else if(request.equals("UPDATE")){
                                    serverMessage = updateRequest(clientMessage);

                              }
                              else if(request.equals("GET")){
                                    serverMessage = getRequest(clientMessage);
                              }
                              else if(request.equals("REMOVE")){
                                    serverMessage = removeRequest(clientMessage);
                              }
                             else if(request.equals("")){
                                    dout.println("No request identified.");
                                    dout.flush();
                              }
                              else if (request.equals("Stop")){
                                    dout.println("Stopping connection");
                                    dout.flush();
                                    stopServer();
                              }
                              else{
                                    dout.println("Request not recognized.");
                                    dout.flush();
                                                 }
                              
                        dout.println(serverMessage);
                        dout.flush(); 
                        dout.println("Enter another Request or 'Stop' to end session: ");
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

            
            
                
