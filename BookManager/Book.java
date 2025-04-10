import ecs100.*;
import java.awt.Color;
/**
 * Book support class
 * Has attributes title, author number of likes, and cover image
 *
 * @author Serena.Q
 * @version 07/04/25
 */
public class Book
{
    // instance variables
    private long id;
    private String name;
    private String author;
    private int likes;
    private String image;
    static final String DEFAULT_IMAGE = "book.jpg"; // Set a default image

    /**
     * Constructor for objects of class Book
     */
    public Book(long key, String nm, String auth, int hearts, String img)
    {
        // initialise instance variables
        id = key;
        name = nm;
        author = auth;
        likes = hearts;
        if (img == null) {
            this.image = DEFAULT_IMAGE; // add default img if user clicks cancel
        } else {
            this.image = img;       // else the user inputs an image
        }
    }
    
    /**
     * Constructor for objects of class book
     * @param key, nm, auth, likes
     */
    public Book(long key, String nm, String auth, int hearts)
    {
        this(key, nm, auth, hearts, DEFAULT_IMAGE);
    }
    
    /**
     * Display image on GUI
     */
    public void displayBook() {
        int locX = 100; // image x start position
        int locY = 100; // image y start position
        
        final double WIDTH = 250;
        final double HEIGHT = 300;
        
        UI.drawImage(this.image, locX, locY, WIDTH, HEIGHT);
    }

    /**
     * Get id
     */
    public long getId() {
        return this.id;
    }
    /**
     * Get name
     */
    public String getName() {
        return this.name;
    }
    /**
     * Get author
     */
    public String getAuthor() {
        return this.author;
    }
    /**
     * Get likes
     */
    public int getLikes() {
        return this.likes;
    }
}
