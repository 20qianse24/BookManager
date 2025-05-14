
import ecs100.*;
/**
 * GUI class for book manager.
 * Allows add, print, find books on GUI
 *
 * done - delete
 * done - click the book cover to like the book
 * done - show the total number of likes
 * done - edit number of likes
 *
 * ??? add max and min value constants
 *
 * @author Serena.Q
 * @version 09/04/25
 */
public class GUI {
    // instance variables
    private BookCollection collection;
    private Book book;

    private int newLikes;

    /**
     * Constructor for objects of class GraphicsPane.
     */
    public GUI() {
        // initialise instance variables
        collection = new BookCollection();
        UI.initialise();
        UI.addButton("Print All", collection::printAll);
        UI.addButton("Add", collection::getBookInfo);
        UI.addButton("Find", collection::returnBook);
        UI.addButton("Delete", collection::removeBook);
        UI.addButton("Edit Likes", collection::editLikes);
        UI.addButton("Quit", UI::quit);

        UI.setMouseListener(this::likeBook);
    }

    /**
     * Manages the interaction with the cover image on the pane (edit likes).
     * @param action
     * @param x
     * @param y
     */
    public void likeBook(final String action, final double x, final double y) {
        if (action.equals("clicked")) {
            book = collection.getBook();    // Set the current book instance
            if (book == null) {     // User hasn't searched for a book yet
                return;     // Do nothing
            } else if (collection.getBook().onCover(x, y)) {
                book.addLike();     // Add a like
                // Erase then redraw text
                this.erase();
                UI.drawString("Likes: "
                + book.getLikes(), collection.getTextX(),
                collection.getTextY());
                UI.println("\nBook Liked");
            }
        }
    }

    /**
     * Erase the text already displayed on the pane.
     */
    public void erase() {
        final int BUFFER = 1;
        // CHANGE TO CONSTANTS LATER
        UI.eraseRect(collection.getTextX(), collection.getTextY() - 10,
        90 + BUFFER, 30 + BUFFER);
    }

    /**
     * Main routine.
     * @param args arguments is ignored
     */
    public static void main(final String[] args) {
        new GUI();
    }
}
