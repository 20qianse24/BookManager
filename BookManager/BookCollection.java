import ecs100.*;
import java.awt.Color;
import java.util.HashMap;
/**
 * Holds a collection of books in a hashmap
 * Allowes a user to add, delete, find, print all, edit likes from a menu
 * prevent user from adding a duplicate?
 *
 * @author Serena.Q
 * @version 07/04/25
 */
public class BookCollection
{
    // instance variables
    private HashMap<Long, Book> library;     // Declaring the hashmap library
    private int currBookId;     // Store the current id of the book being added
    private Book currBook;      // Store the instance of the current book
    
    /**
     * Constructor for objects of class BooksCollection
     */
    public BookCollection()
    {
        // initialise instance variables
        library = new HashMap<Long, Book>();     // Initialise hashmap
        
        // Creating inital books - L converts to long variable class
        Book b1 = new Book(‎9780141441146L, "Jane Eyre", "Charlotte Bronte", 52);
        Book b2 = new Book(9780141439518L, "Pride and Prejudice", "Jane Austen", 71);
        Book b3 = new Book(9780141321080L, "Little Women", "Louisa May Alcott", 12);
        
        // Add books to collection
        library.put(‎9780141441146L, b1);
        library.put(9780141439518L, b2);
        library.put(9780141321080L, b3);
        
        this.currBookId = 3;        // Store the current book id
    }
    
    /**
     * Menu to print and call appropriate methods
     */
    public void menu() {
        // Print menu and force choice
        String choice;
        do {
            UI.println("(A)dd a book");
            UI.println("(F)ind a book");
            UI.println("(P)print all books");
            UI.println("(Q)uit");
            
            choice = UI.askString("Enter a choice: ");
            
            if (choice.equalsIgnoreCase("A")) {
                // add book
            } else if (choice.equalsIgnoreCase("F")) {
                // find a book
            } else if (choice.equalsIgnoreCase("P")) {
                // print all books
            } else if (choice.equalsIgnoreCase("Q")) {
                UI.println("Closing program");
                UI.quit();
            } else {
                UI.println("Not a valid option :C");
            }
        } while (!choice.equalsIgnoreCase("Q"));
    }
}
