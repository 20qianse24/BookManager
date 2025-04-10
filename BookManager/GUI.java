import ecs100.*;
/**
 * GUI class for book manager
 * Allows add, print, find books on GUI
 * 
 * ??? delete
 * ??? click the book cover to like the book
 * ??? show the total number of likes
 * ??? edit number of likes
 *
 * @author Serena.Q
 * @version 09/04/25
 */
public class GUI
{
    // instance variables
    private BookCollection collection;
    private Book book;

    /**
     * Constructor for objects of class GraphicsPane
     */
    public GUI()
    {
        // initialise instance variables
        collection = new BookCollection();
        UI.initialise();
        UI.addButton("Print All", collection::printAll);
        //UI.addButton("Display All", collection::displayAll);
        UI.addButton("Add", collection::getBookInfo);
        UI.addButton("Find", collection::returnBook);
        UI.addButton("Quit", UI::quit);
    }
    
    /**
     * Main routine
     */
    public static void main(String args[]) {
        new GUI();
    }
}
