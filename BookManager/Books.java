import java.util.HashMap;
import java.util.Scanner;
import ecs100.*;
/**
 * Holds a collection of books in a hashmap
 * Allows a user to add, find, print all 
 * 
 * ??? delete
 * ??? prevent user from adding a duplicate
 *
 * @version 09/04/25
 */
public class Books
{
    // fields
    private HashMap<Long, Book> library; // declaring the hashmap
    private Book currBook; // store the instance of the current book
    private Scanner scanner;
    /**
     * Constructor for objects of class Books
     */
    public Books()
    {
        // initialise instance variables
        library = new HashMap<Long, Book>(); //initialise hashmap
        scanner = new Scanner(System.in); // initialise scanner
        
        // Creating inital books - L converts to long variable class
        Book b1 = new Book(9780141441146L, "Jane Eyre", "Charlotte Bronte", 52);
        Book b2 = new Book(9780141439518L, "Pride and Prejudice", "Jane Austen", 71);
        Book b3 = new Book(9780141321080L, "Little Women", "Louisa May Alcott", 12);
        
        // Add books to collection
        library.put(9780141441146L, b1);
        library.put(9780141439518L, b2);
        library.put(9780141321080L, b3);
    }
    
    /**
     * Add a book to the map
     * @param name, author, qty
     */
    public void addBook(long bookId, String name, String author, int likes){
        this.library.put(bookId, new Book(bookId, name, author, likes));
    }
    
    /**
     * Add a book to the map and display the cover on canva
     * Override the method with different param
     * @param name, author, qty, img
     */
    public void addBook(long bookId, String name, String author, int likes, String img){
        this.library.put(bookId, new Book(bookId, name, author, likes, img));
    }
    
    /**
     * Book Getter
     * @return an instance of BookCollection class
     */
    public Book getBook(){
        return this.currBook;
    }
    
    /**
     * Finds a book based on name
     * Sets the current book instance if found
     * @return boolean false if not found
     */
    
    public boolean findBook(String name){
        //Search for book through hashmap library
        for (long bookId: this.library.keySet()){
            if(this.library.get(bookId).getName().toLowerCase().trim().equals(name.toLowerCase().trim())){
                this.currBook = this.library.get(bookId); //Set the current Book
                return true;
            }
        }
        return false;
    }
    
    /**
     * Print details of all books
     * Console based interaction
     */
    public void printAll(){
        //Traverse Map
        for (long bookId : this.library.keySet()){
            System.out.println(bookId + " Details: ");
            System.out.println(this.library.get(bookId).getName() + " by "
                        +this.library.get(bookId).getAuthor() + " with "
                        +this.library.get(bookId).getLikes() + " likes.");
        }
    }
    
    /**
     * Print all books
     * GUI
     */
    public void printAllBooks(){
        //Traverse Map
        for (long bookId : this.library.keySet()){
            UI.println(bookId + " Details: ");
            UI.println(this.library.get(bookId).getName() + " "
                        +this.library.get(bookId).getAuthor() + " "
                        +this.library.get(bookId).getLikes());
        }
    }
    
    /**
     * Menu to print and call appropriate methods
     * console-based menu
     */
    public void menu(){
        //Print menu and force choice
        String choice;
        do{
            System.out.println("(A)dd a book");
            System.out.println("(F)ind a book");
            System.out.println("(P)rint all");
            System.out.println("(Q)uit");
            
            choice = scanner.nextLine().trim().toUpperCase(); // avoid case-senstivity and whitespace before and after the string
            
            switch(choice){
                case "A": // only allows char "A"
                    System.out.println("\nEnter book title: ");
                    String title = scanner.nextLine();
                    System.out.println("Enter author: ");
                    String author = scanner.nextLine();
                    
                    // Check for existing book
                    if (this.findBook(title)) {  // check if findBook return true
                        System.out.println("Error: A book with that title already exists!");
                        break;
                    }
                    
                    System.out.println("Enter number of likes: ");
                    int likes = Integer.parseInt(scanner.nextLine()); // ?? check how to force an int
                    
                    System.out.println("Enter ISBN number (ID): ");
                    long ID = Long.parseLong(scanner.nextLine()); // ?? check how to force a long
                    
                    // call addBook method with 3 param: title, author, qty
                    this.addBook(ID, title, author, likes);
                    System.out.println("Book added successfully!");
                    break;
                    
                case "F":
                    System.out.print("\nEnter book title to find: ");
                    String searchTitle = scanner.nextLine();
                        
                    if (this.findBook(searchTitle)){
                        System.out.println("\nBook found: ");
                        System.out.println(this.currBook.getName()); // print current book instance's name    
                    }else{
                        System.out.println("Book not found!");
                    }
                    break;
                        
                case "P":
                    printAll();
                    break;
                        
                case "Q":
                    System.out.println("Goodbye");
                    break;
                        
                default:
                    System.out.println("Invalid choice!");
                    } 
        }while (!choice.equals("Q"));   //loop until choice is 'Q'
        scanner.close();
    }
    
    /**
     * main routine
     */
    public static void main(String[] args) {
        new Books().menu();
    }
}