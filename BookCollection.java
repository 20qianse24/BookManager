import ecs100.*;
import java.awt.Color;
import java.util.HashMap;
/**
 * Holds a collection of books in a hashmap
 * Allowes a user to add, delete, find, print all, edit likes from a menu and by clicking
 * Prevents user from adding a duplicate by checking existing ISBN numbers
 * 
 * Delete and edit likes is unfinished**
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

    private String searchTitle;
    
    // Constants
    private final int MIN_LIKES = 0;
    private final int MIN_ID = 0;
    
    // x and y coordinates of the string underneath the book
    private int textX = 200;
    private int textY = 450;
    
    /**
     * Constructor for objects of class BooksCollection
     */
    public BookCollection()
    {
        // initialise instance variables
        library = new HashMap<Long, Book>();     // Initialise hashmap
       
        // Creating inital books - L converts to long variable class
        Book b1 = new Book(9780141441146L, "Jane Eyre", "Charlotte Bronte", 52, "jane_eyre.jpg");
        Book b2 = new Book(9780141439518L, "Pride And Prejudice", "Jane Austen", 71, "pride_and_prejudice.jpg");
        Book b3 = new Book(9780141321080L, "Little Women", "Louisa May Alcott", 12, "little_women.jpg");
        
        // Add books to collection
        library.put(9780141441146L, b1);
        library.put(9780141439518L, b2);
        library.put(9780141321080L, b3);
    }
    
    /**
     * Gets book details, validates then adds to the library
     * Need to revise later and seperate error catching loops into seperate methods
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
            if (newBookId <= MIN_ID) {
               UI.println("Invalid ISBN number. Please enter a valid number."); 
            }
        } while (newBookId <= MIN_ID);
        
        // Check for existing book using unique ISBN number
        if (this.findExistingId(newBookId)) {
            UI.println("Error: That book already exists!");
            } else {    // Only proceed if the book does not exist yet
            // Avoid negative likes
            do {
                newLikes = UI.askInt("Number of likes: ");
                if (newLikes < MIN_LIKES) {
                    UI.println("Invalid value. Please enter a positive number");
                }
            } while (newLikes < MIN_LIKES);
            // Validate String inputs
            do {
                // Convert string input to long
                newName = UI.askString("Name of the book: ").trim();
                newAuthor = UI.askString("Author's name: ").trim();
                
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
     * Get string x coordinate
     */
    public int getTextX() {
        return this.textX;
    }
    /**
     * Get string y coordinate
     */
    public int getTextY() {
        return this.textY;
    }
    
    /**
     * Add a book to the collection
     */
    public void addBook(long bookId, String name, String author, int likes, String img) {
        library.put(bookId, new Book(bookId, name, author, likes, img));
    }
    
    /**
     * Finds a book based on the ID, checks if the library contains the given ID as a key
     * @param id the ID to search for
     * @return true if the book exists, false otherwise
     */
    public boolean findExistingId(long id) {
        return library.containsKey(id);
    }
    
    /**
     * Check for an existing book using ISBN
     * Sets the current book instance if found
     * @ return boolean false if not found
     * REMINDER: REPLACE TEXT COORDINATES WITH CONSTANTS RELATING TO BOOK COORDINATES
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
     * Finds and prints book found from title
     * QUESTION: Should I seperate the console and GUI panel printing methods into the GUI class?
     * At the moment this is both printing to the console and the graphics pane
     */
    public void returnBook() {
        searchTitle = UI.askString("\nEnter the title of the book to search: ").trim().toUpperCase();
        findBook(searchTitle);
        if (this.findBook(searchTitle)) {
            UI.clearGraphics();
            library.get(currBook.getId()).displayBook();    // Display the current book
            UI.drawString("Likes: " + currBook.getLikes(), textX, textY);    // Display likes underneath
            
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
     * Returns the searched book title
     */
    public String getTitleSearched() {
        return this.searchTitle;
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
     * Console based menu
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
