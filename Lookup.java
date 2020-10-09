import java.util.*;

public class Lookup{

  public static BookSubmission lookupISBN(ArrayList<BookSubmission> bookSubmissions, String ISBN){
    for (BookSubmission bookSubmission : bookSubmissions)
          if(bookSubmission.getISBN().equals(ISBN)){
                return bookSubmission;
            }
     
              return null;
              
             }
    public static ArrayList<BookSubmission> lookup(ArrayList<BookSubmission> bookSubmissions, String value, String submit){
              ArrayList<BookSubmission> books = new ArrayList<>();
              for (BookSubmission bookSubmission : bookSubmissions){
                  if(value.equals("ISBN")){  
                      if(bookSubmission.getISBN().equals(submit)){
                            books.add(bookSubmission);
                        }
                  }
                  else if (value.equals("TITLE")){
                       if(bookSubmission.getTitle().equals(submit)){
                            books.add(bookSubmission);
                        }
                  }
                  else if (value.equals("AUTHOR")){
                       if(bookSubmission.getAuthor().equals(submit)){
                              books.add(bookSubmission);
                        }
                  }
                  else if (value.equals("PUBLISHER")){
                      if(bookSubmission.getPublisher().equals(submit)){
                            books.add(bookSubmission);
                        }
                  }
                  else if (value.equals("YEAR")){
                      if(Integer.toString(bookSubmission.getYear()).equals(submit)){
                            books.add(bookSubmission);
                        }
                  }
                  if(books.size()==0){
                    books = null;
                    }
                  return books; 
             
             }
             return books;
             }
}
     
