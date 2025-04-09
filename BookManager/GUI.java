import ecs100.*;
/**
 * GUI class for book manager
 * Allows add, print, find books on GUI
 *
 * @author Serena.Q
 * @version 09/04/25
 */
public class GUI
{
    // instance variables
    private BookCollection books;
    private Book book;

    /**
     * Constructor for objects of class GraphicsPane
     */
    public GUI()
    {
        // initialise instance variables
        books = new BookCollection();
        UI.initialise();
        UI.addButton("Print All", books::printAll);
        UI.addButton("Add", books::getBookInfo);
        UI.addButton("Find", books::findBook);
        UI.addButton("Quit", UI::quit);
    }

    /**
     * Add a book to the collection
     */
    
}
