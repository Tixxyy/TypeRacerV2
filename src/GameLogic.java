import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameLogic {

    private JTextField field;
    private JLabel label;

    private boolean mode;
    private ArrayList<String> sentences;
    private boolean random;
    private boolean charBased;
    private int charBasedTextLength;

    private String text;
    private int currentSentence=0;

    private int posOffset;
    private int subPos;
    private static final int MAXMISTAKES = 7;
    private boolean backspace;

    private long startime;
    private long endTime;

    public GameLogic (JTextField field, JLabel label){
        this.field=field;
        this.label=label;
    }

    public void init(boolean mode, ArrayList<String> sentences, boolean random, boolean charBased, String k){
        this.mode=mode;
        if (mode){
            this.sentences=sentences;
            this.random=random;
            this.charBased=charBased;
            this.charBasedTextLength=getCharBasedTextLength(k);
            createText();

            posOffset=0;
            subPos=0;
            backspace=false;

            startCountdown();
        }else deInit();
    }
    private void init(){
        if (mode){
            createText();
            posOffset=0;
            subPos=0;
            backspace=false;

            startCountdown();
        }
        else deInit();
    }

    private void deInit(){
        field.setText("");
        field.setEnabled(false);
        label.setText("<html></html>");
    }

    private void createText() {
        if (!random && !charBased) {
            text = sentences.get(currentSentence);
            increaseCurrentSentencePos();
        } else if (random && !charBased) {
            text = sentences.get((int) (Math.random() * sentences.size()));
        }
        else if (!random && charBased) {
            String buffer = sentences.get(currentSentence);
            increaseCurrentSentencePos();
            getCharBasedText(buffer);
        }
        else if (random && charBased) { // redundant if's
            String buffer = sentences.get((int) (Math.random() * sentences.size()));
            getCharBasedText(buffer);
        }
        if (Main.wantOptimize()) text = TextForLabelOptmization.optimizeText(text,label,Main.wantCutOffText());
        label.setText("<html>"+text+"</html>");
    }

    private void startCountdown(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            int i = 3;
            @Override
            public void run() {
                if  (!mode){ // user has stopped the game during countdown
                    timer.cancel();
                    deInit();
                }
                else if (i==0){
                    timer.cancel();
                    field.setText("");
                    field.setEnabled(true);
                    field.grabFocus();
                    field.requestFocus();
                    startime = System.currentTimeMillis();
                }
                else {
                    field.setText(Integer.toString(i));
                    i--;
                }
            }
        },0,1000);
    }

    public KeyListener listener(){
        KeyListener listen = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (mode) {
                    String fullField = field.getText() + e.getKeyChar();
                    int fullFieldLength;
                    if (backspace) fullFieldLength = fullField.length() - 1 + subPos; // otherwise the size will just stay the same
                    else fullFieldLength = fullField.length() + subPos;

                    if (fullFieldLength<=text.length()){
                        String subText = text.substring(subPos, fullFieldLength);
                        if (fullField.equals(subText)) {
                            if (e.getKeyChar() == ' ') {
                                subPos = fullFieldLength;
                                field.setText("");
                                e.consume();
                            } else if (fullFieldLength == text.length()) { // end
                                changeColour(fullFieldLength, posOffset);
                                endTime = System.currentTimeMillis();
                                field.setEnabled(false);
                                int time = (int) ((text.length() / 5.0) * 60.0 / ((endTime - startime) / 1000.0));
                                field.setText("WPM: " + time);
                                java.util.Timer timer = new Timer();
                                timer.schedule(new TimerTask() { // idk sloppy
                                    @Override
                                    public void run() {
                                        if (mode) init();
                                    }
                                }, 3000);
                            } else {
                                changeColour(fullFieldLength, posOffset);
                            }
                        }
                        else if (!backspace) {
                            if (posOffset+1<=MAXMISTAKES) {
                                posOffset++;
                                changeColour(fullFieldLength, posOffset);
                            } else e.consume();

                        } else if (backspace){
                            changeColour(fullFieldLength, posOffset);
                        }
                    } else if (!backspace) { // when fullFieldLength goes past text.length
                        if (posOffset+1<=MAXMISTAKES){
                            posOffset++;
                        }
                        else e.consume();
                    }
                    backspace=false;
                }
                else {
                    deInit();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 8) { // backspace
                    if (!field.getText().isEmpty()){
                        if (posOffset>0) posOffset--;
                    }
                    backspace=true;
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {

            }
        };
        return listen;
    }

    // helper methods

    private void increaseCurrentSentencePos(){
        if (currentSentence+1 < sentences.size()){
            currentSentence++;
        }
        else currentSentence=0;
    }
    private void getCharBasedText(String buffer){
        int n;
        if (charBasedTextLength<=0) n = buffer.length();
        else n=charBasedTextLength;

        String foo = "";
        for (int i = 0; i<n;i++){
            foo+=buffer.charAt((int) (Math.random()* buffer.length()));
        }
        text=foo.trim().replaceAll("\\s+", " ");
    }

    private int getCharBasedTextLength(String a){
        try{
            return Integer.parseInt(a);
        }
        catch (Exception e){
            return 0;
        }
    }

    private void changeColour(int pos, int offPos){
        if (offPos!=0) label.setText("<html><font color='green'>"+text.substring(0,pos-offPos)+"</font><font color='red'>"+text.substring(pos-offPos,pos)+"</font><font color='black'>"+text.substring(pos)+"</html>");
        else label.setText("<html><font color='green'>"+text.substring(0,pos)+"</font><font color='black'>"+text.substring(pos)+"</html>");
    }

}
