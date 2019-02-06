import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class UserInterface extends JFrame {

    private JLabel label;
    private JTextField field;

    private JButton startStop;
    private boolean startStopBoolean;
    private JRadioButton random;
    private JRadioButton charBased;
    private JTextField charBasedLength;
    private JButton getTxtFile;

    private ArrayList<String> sentences;
    private GameLogic logic;

    private final Font font;

    public UserInterface(Font font){
        this.font = font;
        magager();
    }

    private void magager(){
        createComponents();
        logic = new GameLogic(field,label);

        setEnabledBegTxt(false);
        addActionListeners();
    }

    private void createComponents(){
        this.setLayout(new BorderLayout());

        this.label = new JLabel("<html></html>");
        label.setVerticalAlignment(JLabel.TOP);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setFont(font);

        this.field = new JTextField("");
        field.setFont(font);
        field.setEnabled(false);
        field.setDisabledTextColor(Color.BLACK);

        JPanel panel = createBottomPanel();

        this.getContentPane().add(label, BorderLayout.CENTER);
        this.getContentPane().add(field, BorderLayout.SOUTH);
        this.getContentPane().add(panel,BorderLayout.NORTH);
    }

    private JPanel createBottomPanel(){
        GridLayout layoutBottom = new GridLayout(1,5);
        JPanel panel = new JPanel();
        panel.setLayout(layoutBottom);

        startStop = new JButton("Start/Stop");
        this.startStopBoolean=false;
        random = new JRadioButton("Random");
        charBased = new JRadioButton("CharBased");
        charBasedLength = new JTextField("0");
        getTxtFile = new JButton("GetText");

        panel.add(startStop);
        panel.add(getTxtFile);
        panel.add(random);
        panel.add(charBased);
        panel.add(charBasedLength);

        return panel;
    }

    private void addActionListeners(){
        startStop.addActionListener(event ->{
            if (startStopBoolean){
                startStopBoolean=false;
            }
            else startStopBoolean=true;
            innerInit();
        });
        charBased.addActionListener(event ->{
            charBasedLength.setEnabled(charBased.isSelected());
        });

        getTxtFile.addActionListener(event->{
            do{
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getDefaultDirectory());
                fileChooser.setDialogTitle("Choose a .txt file");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT", "txt", "text");
                fileChooser.setFileFilter(filter);

                int returnVal = fileChooser.showOpenDialog(null);

                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();

                    if (file != null) {
                        var tempList = ReadSentences.getSentenceArray(file);
                        if (tempList.isEmpty()) { // no text in txt file or txt file not found
                            JOptionPane.showConfirmDialog(null, "Not unicode | File is empty", "", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
                        } else {
                            sentences = tempList;
                            setEnabledBegTxt(true);
                        }
                    }
                }
                else break; // if user decides to cancel/exit
            } while (sentences == null);
        });

        field.addKeyListener(logic.listener());
    }
    private void setEnabledBegTxt(boolean a){
        startStop.setEnabled(a);
        random.setEnabled(a);
        charBased.setEnabled(a);
        charBasedLength.setEnabled(charBased.isSelected());
    }

    private void innerInit(){
        random.setEnabled(!startStopBoolean);
        charBased.setEnabled(!startStopBoolean);
        getTxtFile.setEnabled(!startStopBoolean);
        logic.init(startStopBoolean,sentences,random.isSelected(),charBased.isSelected(),charBasedLength.getText());
    }
}
