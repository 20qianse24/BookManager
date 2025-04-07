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

    /**
     * Constructor for objects of class Book
     */
    public Book(long key, String nm, String auth, int hearts)
    {
        // initialise instance variables
        id = key;
        name = nm;
        author = auth;
        likes = hearts;
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
