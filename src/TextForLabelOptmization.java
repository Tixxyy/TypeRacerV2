import javax.swing.*;
import java.awt.*;

public class TextForLabelOptmization {

    private static final int SIDEPADDING = 8;

    public static String optimizeText(String a, JLabel label, boolean cutOff){ // biggest problem here is the fact that you can't (as far as I know) get the vertical
        Dimension labelDimension = new Dimension(label.getSize());
        FontMetrics metrics = label.getFontMetrics(label.getFont());

        int labelWidth = labelDimension.width-SIDEPADDING;
        int labelHeight = labelDimension.height-SIDEPADDING;

        String foo=a;
        int lastWhiteSpaceWidthPos = 0;

        for (int i = 0; i<foo.length();i++) {
            boolean canCheckNext = i + 1 < foo.length();
            if (foo.charAt(i) == ' ') {
                lastWhiteSpaceWidthPos = metrics.stringWidth(foo);
            }
            if (canCheckNext) {
                if (metrics.stringWidth(foo.substring(0, i + 1)) - lastWhiteSpaceWidthPos > labelWidth) {
                    foo = foo.substring(0, i) + " " + foo.substring(i);
                    lastWhiteSpaceWidthPos = metrics.stringWidth(foo.substring(0, i));
                    i++;
                }
            }
            if (cutOff) {
                if (metrics.stringWidth(foo.substring(0, i)) > (labelHeight / (metrics.getHeight())) * labelWidth) {
                    return foo.substring(0, i);
                }
            }
        }
        return foo;
    }
}
