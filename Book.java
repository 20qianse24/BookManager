import ecs100.*;
/**
 * Book support class.
 * Has attributes title, author number of likes, and cover image.
 *
 * @author Serena.Q
 * @version 07/04/25
 */

public class Book {
    // instance variables
    private final long id;
    private final String name;
    private final String author;
    private int likes;
    private String image;
    static final String DEFAULT_IMAGE = "book.jpg"; // Set a default image

    private boolean bookClicked = false;    // check if book has been clicked

    // Bounding box of the book
    private double left;
    private double top;
    private double bottom;

    private int locX = 100; // image x start position
    private int locY = 80;  // image y start position

    // Dimensions of book cover
    private final double WIDTH = 250;
    private final double HEIGHT = 350;

    /**
     * Constructor for objects of class Book.
     */
    public Book(final long key, final String nm,
    final String auth, final int hearts, final String img) {
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
     * Constructor for objects of class book.
     * @param key, nm, auth, hearts
     */
    public Book(final long key, final String nm,
    final String auth, final int hearts) {
        this(key, nm, auth, hearts, DEFAULT_IMAGE);
    }

    /**
     * Display image on GUI.
     */
    public void displayBook() {
        UI.drawImage(this.image, locX, locY, WIDTH, HEIGHT);
    }

    /**
     * Reports whether (x,y) is on the book cover.
     * @return boolean clicked on or not
     * @param x, y
     */
    public boolean onCover(final double x, final double y) {
        if ((x >= locX) && (x <= locX + WIDTH)
        &&
            (y >= locY) && (y <= locY + HEIGHT)) {
            bookClicked = true;
        } else {
            bookClicked = false;
        }
        return bookClicked;
    }

    /**
     * Add a like.
     */
    public void addLike() {
        likes += 1;
    }

    /**
     * Change number of likes on a book.
     */
    public void changeLikes(int newLikes) {
        likes = newLikes;
    }

    /**
     * Get id.
     * @return id
     */
    public long getId() {
        return this.id;
    }
    /**
     * Get name.
     * @return name
     */
    public String getName() {
        return this.name;
    }
    /**
     * Get author.
     * @return author
     */
    public String getAuthor() {
        return this.author;
    }
    /**
     * Get likes.
     * @return likes
     */
    public int getLikes() {
        return this.likes;
    }
}
