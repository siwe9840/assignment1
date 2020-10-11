import java.net.*;
import java.io.*; 
import java.util.*;

public class SocketServer extends Thread{
      private final ArrayList<BookSubmission> bookSubmissions;
      private final Socket clientSocket;
      private boolean isRunning= false;
      private BufferedReader din;
      private PrintWriter dout; 

    public SocketServer(Socket clientSocket, ArrayList<BookSubmission> bookSubmissions ){
        this.clientSocket = clientSocket;
        this.bookSubmissions = bookSubmissions; 
    }
    
    public synchronized void stopServer(){
        this.isRunning = false;
         try{
              din.close();
              dout.close();
              clientSocket.close(); 
             this.interrupt();
          }
           catch( Exception e){
                  System.out.println("Connection was not properly closed"); 
                  e.printStackTrace();
                  }
    }

    public synchronized void run() {
       this.isRunning = true; 
      System.out.println("Connection accepted. Thread is running.");
        try {
          dout = new PrintWriter(clientSocket.getOutputStream(), true);
          din = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         System.out.println("before start running");
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
                  line = line.trim(); 
                  String[] data = line.split(" "); 
                  if (data[0].equals("ISBN")){
                        if(Lookup.lookupISBN(bookSubmissions, data[1] )!= null){
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
                     bookSubmissions.add(bookSubmission); 
                            
                     serverMessage = ("The entry has been added to the bibliography successfully.");
                     return serverMessage;  
                     }
                  
      
      private String updateRequest(String[] requestData){
            String serverMessage = "";
            BookSubmission book = null;
            for (String line : requestData){
                  line = line.trim();
                  String[] data = line.split(" ");
                  String submit = line.substring(data[0].length()).trim();
                  if (data[0].equals("ISBN")){
                        book = Lookup.lookupISBN(bookSubmissions, submit);
                        }
                 
                  if (book!=null){
                        if (data[0].equals("TITLE")){
                              book.setTitle(submit);
                        }
                        else if (data[0].equals("AUTHOR")){
                              book.setAuthor(submit);
                        }
                        
                       else if (data[0].equals("PUBLISHER")){
                             book.setPublisher(submit);
                       }
                      else if (data[0].equals("YEAR")){
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
                  String []data = line.split(" ");
                  String submit = line.substring(data[0].length()).trim();
                  if(data[0].equals("ALL")){
                        for(BookSubmission bookSubmission : bookSubmissions){
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
            for(ArrayList<BookSubmission> bookSubmission : intersection){
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
                    for(ArrayList<BookSubmission> bookSubmission : intersection){
                          bookSubmissions.remove(bookSubmission);
                    }
                    }
            
                serverMessage = "The book(s) have been removed from the directory";
                return serverMessage; 
      }

      public void startRunning()
      {
    	  System.out.println("in start rnn");
            dout.println("Enter a request or 'Stop' to end session \\r\\n\\\\EOF ");
                  dout.flush(); 
                  String line = "";
                  String serverMessage = "";
                 String clientMessage = "";
            try
            {
                  System.out.println("Befre read line");
                  dout.flush();
                  System.out.println("after flush");
                  line = din.readLine(); 
                  System.out.println("line (before while) : " + line );
                  System.out.println("BEFRE WHILE LOPP");
                        while(line!=null){
                        	System.out.println(line + "should be submission line ______");
                              while(!line.contains("\\EOF")){
                                    clientMessage = clientMessage.concat(line+"\r\n");
                                    line = din.readLine();
                              }
                              
                              String [] cMessageArray = clientMessage.split(("\n").trim()+"\r\n\\EOF");
                              String request = cMessageArray[0].trim();

                              if(request.equals("SUBMIT")){
                                    serverMessage = submitRequest(cMessageArray);

                              }
                              else if(request.equals("UPDATE")){
                                    serverMessage = updateRequest(cMessageArray);

                              }
                              else if(request.equals("GET")){
                                    serverMessage = getRequest(cMessageArray);
                              }
                              else if(request.equals("REMOVE")){
                                    serverMessage = removeRequest(cMessageArray);
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
                        line = din.readLine(); 
                                                 
                        }   
                            }catch (IOException e){
                                  e.printStackTrace();
                                     
                  }
              }
}

            
                 

            
