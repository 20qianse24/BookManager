import ecs100.*;
import java.awt.Color;
import java.util.HashMap;
import java.util.Scanner;
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
    
    private long newBookId;
    private String newName;
    private String newAuthor;
    private int newLikes;
    
    private Scanner scanner;
    private String searchBookName;
    
    /**
     * Constructor for objects of class BooksCollection
     */
    public BookCollection()
    {
        // initialise instance variables
        library = new HashMap<Long, Book>();     // Initialise hashmap
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
     * Gets book details
     */
    public void getBookInfo() {
        newBookId = Long.decode(UI.askString("ISBN number: ").trim());        // Convert string input to long
        newName = UI.askString("Name of the book: ").trim();
        newAuthor = UI.askString("Author's name: ").trim();
        newLikes = UI.askInt("Number of likes: ");
        
        // Capitalise the first letter of the string inputs
        String capName = null;      // To store the capitalised name
        do {
            if (newName != null) {
                capName = newName.substring(0, 1).toUpperCase() + newName.substring(1);
                newName = capName;
            } else {
                UI.println("\nCannot leave null.");
            }
        } while (newName == null || newAuthor == null);
        
        addBook(newBookId, newName, newAuthor, newLikes);       // Adding to the collection
    }
    
    /**
     * Add a book to the collection
     */
    public void addBook(long bookId, String name, String author, int likes) {
        library.put(bookId, new Book(bookId, name, author, likes));
    }
    
    /**
     * Finds a book based on the name
     * Sets the current book instance if found
     * @ return boolean flase if not found
     */
    public boolean findBook(String name) {
        // Search for book
        for (long bookId : library.keySet()) {
            if (library.get(bookId).getName().toLowerCase().equals(name.toLowerCase())) {
                currBook = library.get(bookId);     // Store the instance found book
                return true;
            }
        }
        return false;
    }
    
    /**
     * Gets the current book instance
     */
    public Book getBook() {
        return this.currBook;
    }
    
    /**
     * Prints all books and their details
     * Console based interaction
     */
    public void printAll() {
        // Traverse hashmap
        for (long bookId : library.keySet()) {
            UI.print("ID: " + bookId + " Details: ");
            UI.println(library.get(bookId).getName() + " | "
                        + library.get(bookId).getAuthor() + " | "
                        + library.get(bookId).getLikes() + " likes");
        }
    }
    
    /**
     * Menu to print and call appropriate methods
     */
    public void menu() {
        // Print menu and force choice
        String choice;
        do {
            UI.println("\n(A)dd a book");
            UI.println("(F)ind a book");
            UI.println("(P)print all books");
            UI.println("(Q)uit");
            
            // Avoid case-senstivity and whitespace before and after the string
            choice = UI.askString("Enter a choice: ").trim().toUpperCase();
            
            if (choice.equalsIgnoreCase("A")) {
                getBookInfo();
                addBook(newBookId, newName, newAuthor, newLikes);
            } else if (choice.equalsIgnoreCase("F")) {
                String searchTitle = UI.askString("Enter the title of the book to search: ").trim().toUpperCase();
                findBook(searchTitle);
                if (this.findBook(searchTitle)) {
                    UI.println("\nBook found: ");
                    // print current book instance's details
                    UI.println("\nID: " + this.currBook.getId());
                    UI.println("Title: " + this.currBook.getName());
                    UI.println("Author: " + this.currBook.getAuthor());
                    UI.println("Likes: " + this.currBook.getLikes());
                }else{
                    UI.println("\nBook not found!");
                }
            } else if (choice.equalsIgnoreCase("P")) {
                printAll();
            } else if (choice.equalsIgnoreCase("Q")) {
                UI.println("Closing program");
                break;
            } else {
                UI.println("Not a valid option :C");
            }
        } while (!choice.equalsIgnoreCase("Q"));
    }
    
    /**
     * Main routine
     */
    public static void main(String[] args) {
        new BookCollection().menu();
    }
}
