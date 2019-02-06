# A basic TypeRacer
A TypeRacer with custom text support.

##How to use
1. Provide a .txt file that contains text (unicode)

    **A thing to note about the .txt file** - it reads the LINES provided, not the sentences. It's your own responsibility to 'Guess' the appropriate text line length
    when adding text to the .txt file (or dont, since you can resize the GUI to your preferred size), or you can just enable cutoffLongText in the Main.java file (it is enabled by default)

2. Start the game.

Normal settings - Provides the given text line one by one.

Random - If checked, will pick a random text line out of the given .txt file.

**Charbased mode** 
If the CharBased is enabled, the characters from the given line of text are going to be scrambled.
You can also optimize how many characters do you want in the box to the right (0 = more or less the same amount of characters as the length of the text line 
(can be smaller since 2+= whitespace characters are reduced do 1))

If you set the char amount to be reasonably high, it may go out of bounds.

Normal + CharBased + N characters (0= the same length as the given text line (if whitespaces are provided the number will most likely be smaller))

Random + CharBased + N - Picks a random text line + scrambles up the characters 



## Limitations/concerns
1. The calculations for WPM could easily be wrong if the author understood the way of calculating it incorrectly.
2. **There still may be issues (rarely, mostly with CharBased) with text going out of bounds ("out" of the GUI) therefore it is recommended to keep up with the length of the given text lines yourself (or just resize the GUI)** 
3. Supports only .txt files and unicode character set.
4. Java jdk-11.
