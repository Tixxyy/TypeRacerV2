import javax.swing.*;
import java.awt.*;

public class Main {
    // options
    private static final int WIDTH = 500; // default starting width of the frame;
    private static final int HEIGHT = 400; // default starting height of the frame;

    private static final Font font = new Font("Times New Roman", Font.PLAIN, 23); // Font, feel free to make adjustments (text optimization/text cutoff should still work)

    private static final boolean optimizeText = true; // text optimization - basically ensures text wrapping
    private static final boolean cutOffLongText = true; // cuts off the text if it is larger than JLabel (An alternate option is just to resize the Frame itself if the text does not fit)

    // end of options

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            var frame = new UserInterface(font);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setSize(WIDTH,HEIGHT);
            frame.setVisible(true);
    });
}
    public static boolean wantOptimize(){
        return optimizeText;
    }
    public static boolean wantCutOffText(){
        return cutOffLongText;
    }
}
