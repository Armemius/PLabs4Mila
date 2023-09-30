package Common.consoles;

public interface Console {
    boolean isNextStr(); // Possibility of getting text line checking

    String getNextStr(); // Next line getter

    void writeStr(String text); // Line writing

    String getText();
}
