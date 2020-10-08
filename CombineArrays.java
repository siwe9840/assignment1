import java.util.*

public class CombineArrays{

  public static ArrayList<BookSubmission> combine(ArrayList<ArrayList<BookSubmission>> bookList){
        ArrayList<BookSubmission> combined = null;
        combined = ArrayList.union(bookSubmissions, bookList);
        return combined; 
      }

}
