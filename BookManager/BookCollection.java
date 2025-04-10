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
    private String imgFileName;
    
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
        Book b2 = new Book(9780141439518L, "Pride And Prejudice", "Jane Austen", 71);
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
        String capName = null;      // To store the capitalised name
        String capAuthor = null;    // To store the capitalised Author name

        // Validate the input for newBookId
        do { 
            try {
                newBookId = Long.decode(UI.askString("ISBN number: ").trim());;  // Convert to long
            } catch (NumberFormatException e) {
                newBookId = 0;
            }
            if (newBookId <= 0) {
               UI.println("Invalid ISBN number. Please enter a valid number."); 
            }
        } while (newBookId <= 0);
        
        // Check for existing book using unique ISBN number
        if (this.findExistingId(newBookId)) {
            UI.println("Error: That book already exists!");
            } else {    // Only proceed if the book does not exist yet
            // Avoid negative likes
            do {
                newLikes = UI.askInt("Number of likes: ");
                if (newLikes < 0) {
                    UI.println("Invalid value. Please enter a positive nmber");
                }
            } while (newLikes < 0);
            // Validate String inputs
            do {
                // Convert string input to long
                newName = UI.askString("Name of the book: ").trim();
                newAuthor = UI.askString("Author's name: ").trim();
                String imgFileName = UIFileChooser.open("Choose Image File: ");
        
                if (newName.isEmpty() || newAuthor.isEmpty()) {
                    UI.println("\nCannot leave either null.");
                }else {
                    // Setting the first letter to capital
                    capName = newName.substring(0, 1).toUpperCase() + newName.substring(1);
                    newName = capName;
                    capAuthor = newAuthor.substring(0, 1).toUpperCase() + newAuthor.substring(1);
                    newAuthor = capAuthor;
                }
            } while (newName.isEmpty() || newAuthor.isEmpty());     // Repeat until no inputs are left empty
            
            // add books with images
            String imgFileName = UIFileChooser.open("Choose Image File: ");
            // add books with images
            addBook(newBookId, newName, newAuthor, newLikes, imgFileName);       // Adding to the collection
            UI.print("\nBook successfully added");
        }
    }
    
    /**
     * Add a book to the collection
     */
    public void addBook(long bookId, String name, String author, int likes, String img) {
        library.put(bookId, new Book(bookId, name, author, likes, img));
    }
    
    /**
     * Finds a book based on the ID
     * @param id The ID to search for
     * @return true if the book exists, false otherwise
     */
    public boolean findExistingId(long id) {
        // Check if the library contains the given ID as a key
        return library.containsKey(id);
    }
    
    /**
     * Check for an existing book using ISBN
     * Sets the current book instance if found
     * @ return boolean false if not found
     */
    public boolean findBook(String name) {
        // Search for Book
        for (long bookId : library.keySet()) {
            if (library.get(name).getName().toLowerCase().equals(name.toLowerCase())) {
                currBook = library.get(bookId);     // Store the instance found book
                return true;
            }
        }
        return false;
    }
    
    /**
     * Return found book
     */
    public void returnBook() {
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
            UI.print("\nID: " + bookId + "\nDetails: ");
            UI.println(library.get(bookId).getName() + " | "
                        + library.get(bookId).getAuthor() + " | "
                        + library.get(bookId).getLikes() + " likes");
        }
    }
    
    /**
     * Delete a book
     *
    public void deleteBook() {
        String searchTitle = UI.askString("Enter the title of the book to delete: ").trim().toUpperCase();
        findBook(searchTitle);
        if (this.findBook(searchTitle)) {
            // Delete book
        }else{
            UI.println("\nBook not found!");
        }
    } */
    
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
                addBook(newBookId, newName, newAuthor, newLikes, imgFileName);
            } else if (choice.equalsIgnoreCase("F")) {
                returnBook();
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
     * Console based
     */
    public static void main(String[] args) {
        new BookCollection().menu();
    }
}
