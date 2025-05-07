import ecs100.*;
import java.util.HashMap;
/**
 * Holds a collection of books in a hashmap.
 * Allowes a user to add, delete, find, print all,
 * edit likes from a menu and by clicking
 * Prevents user from adding a duplicate by checking existing ISBN numbers
 * 
 * Notes to self for internal:
 * Create new function to ask for author name + title to avoid duplicates
 * Call this function instead of asking again, and change findBook to use both author & title every time
 * Use (for : ) to loop and check auth+title and return current book
 *
 * @author Serena.Q
 * @version 07/04/25
 */
public class BookCollection {
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
     * Constructor for objects of class BooksCollection.
     */
    public BookCollection() {
        // initialise instance variables
        library = new HashMap<Long, Book>();     // Initialise hashmap

        // Creating inital books - L converts to long variable class
        Book b1 = new Book(9780141441146L, "Jane Eyre",
        "Charlotte Bronte", 52, "jane_eyre.jpg");
        Book b2 = new Book(9780141439518L, "Pride And Prejudice",
        "Jane Austen", 71, "pride_and_prejudice.jpg");
        Book b3 = new Book(9780141321080L, "Little Women",
        "Louisa May Alcott", 12, "little_women.jpg");

        // Add books to collection
        library.put(9780141441146L, b1);
        library.put(9780141439518L, b2);
        library.put(9780141321080L, b3);
    }

    /**
     * Gets book details, validates then adds to the library.
     * Need to revise later & seperate error catching loops
     * into seperate methods
     */
    public void getBookInfo() {
        String capName = null;      // To store the capitalised name
        String capAuthor = null;    // To store the capitalised Author name

        // Validate the input for newBookId
        do {
            try {
                // Convert to long
                newBookId = Long.decode(UI.askString("ISBN number: ").trim());
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
            // Ask for and validate likes
            newLikes = validateLikes();

            // Ask for and validate String inputs
            newName = validateString("\nEnter Title: ");
            newAuthor = validateString("\nEnter Author: ");

            // add books with images
            imgFileName = UIFileChooser.open("Choose Image File: ");

            // Adding to the collection
            addBook(newBookId, newName, newAuthor, newLikes, imgFileName);
            UI.print("\nBook successfully added");
        }
    }

    /**
     * Ask for and validate number of likes.
     * @return likes
     */
    public int validateLikes() {
        int likes;
        do {
            likes = UI.askInt("Number of likes: ");
            if (likes < MIN_LIKES) {
                UI.println("Invalid value. Please enter a positive number");
            }
        } while (likes < MIN_LIKES);
        return likes;
    }

    /**
     * Ask for and validate a string input.
     * @param prompt
     * @return newString
     */
    public String validateString(final String prompt) {
        String newString;
        do {
                // Convert string input to long
                newString = UI.askString(prompt).trim();

                if (newString.isEmpty()) {
                    UI.println("\nCannot leave null.");
                } else {
                    // Setting the first letter to capital
                    String capString = newString.substring(0, 1).toUpperCase()
                    + newString.substring(1);
                    newString = capString;
                }
                // Repeat until no input is left empty
        } while (newString.isEmpty());
        return newString;
    }

    /**
     * Get string x coordinate.
     * @return textX
     */
    public int getTextX() {
        return this.textX;
    }
    /**
     * Get string y coordinate.
     * @return textY
     */
    public int getTextY() {
        return this.textY;
    }

    /**
     * Add a book to the collection.
     * @param bookId
     * @param name
     * @param author
     * @param likes
     * @param img
     */
    public void addBook(final long bookId, final String name,
    final String author, final int likes, final String img) {
        library.put(bookId, new Book(bookId, name, author, likes, img));
    }

    /**
     * Finds a book based on the ID.
     * Checks if the library contains the given ID as a key
     * @param id the ID to search for
     * @return true if the book exists, false otherwise
     */
    public boolean findExistingId(final long id) {
        return library.containsKey(id);
    }

    /**
     * Check for an existing book using ISBN.
     * Sets the current book instance if found
     * @param name
     * @return boolean false if not found
     *
     * REMINDER: REPLACE TEXT COORDINATES WITH CONSTANTS
     * RELATING TO BOOK COORDINATES
     */
    public boolean findBook(final String name) {
        //Search for book through hashmap library
        for (long bookId: this.library.keySet()) {
            if (this.library.get(bookId).getName().toLowerCase()
            .trim().equals(name.toLowerCase().trim())) {
                this.currBook = this.library.get(bookId); //Set the current Book
                return true;
            }
        }
        return false;
    }

    /**
     * Finds and prints book found from title.
     */
    public void returnBook() {
        searchTitle = validateString(
        "\nEnter the title of the book to search: ")
        .trim().toUpperCase();
        findBook(searchTitle);
        if (this.findBook(searchTitle)) {
            UI.clearGraphics();
            // Display the current book
            library.get(currBook.getId()).displayBook();
            // Display likes underneath
            UI.drawString("Likes: " + currBook.getLikes(), textX, textY);

            UI.println("\nBook found: ");
            // print current book instance's details
            UI.println("\nID: " + this.currBook.getId());
            UI.println("Title: " + this.currBook.getName());
            UI.println("Author: " + this.currBook.getAuthor());
            UI.println("Likes: " + this.currBook.getLikes());
        } else {
            UI.println("\nBook not found!");
        }
    }

    /**
     * Edit number of likes on a book.
     */
    public void editLikes() {
        searchTitle = validateString(
        "\nEnter the title of the book to search: ")
        .trim().toUpperCase();
        findBook(searchTitle);
        if (this.findBook(searchTitle)) {
            // Get new number of likes and verify
            do {
                newLikes = UI.askInt("Enter new number of likes: ");
                if (newLikes < MIN_LIKES) {
                    UI.println("Invalid value. Please enter a positive number");
                } else if (newLikes == this.currBook.getLikes()) {
                    UI.println(
                "Edit likes means to change it to a new number knucklehead.");
                    UI.println("\nTry again.");
                }
            } while (newLikes < MIN_LIKES
            || newLikes == this.currBook.getLikes());

            this.currBook.changeLikes(newLikes);
            UI.println("Likes successfully updated!");
        } else {
            UI.println("\nBook not found!");
        }
    }

    /**
     * Gets the current book instance.
     * @return currBook
     */
    public Book getBook() {
        return this.currBook;
    }

    /**
     * Prints all books and their details.
     * Add if statement for when library is empty
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
     * Gets book info and finds book before deleting from library.
     */
    public void removeBook() {
        searchTitle = validateString(
        "\nEnter the title of the book to search: ")
        .trim().toUpperCase();
        findBook(searchTitle);  // Check if the book actually exists
        if (this.findBook(searchTitle)) {
            library.remove(this.currBook.getId());     // Remove the book
            UI.println("\nBook deleted from library.");
        } else {
            UI.println("\nBook not found!");
        }
    }

    /**
     * Menu to print and call appropriate methods.
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
     * Main routine.
     * Console based
     * @param args arguments is ignored
     */
    public static void main(final String[] args) {
        new BookCollection().menu();
    }
}