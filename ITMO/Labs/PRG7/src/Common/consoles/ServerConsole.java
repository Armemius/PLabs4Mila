package Common.consoles;

public class ServerConsole implements Console {
    private String text = "";

    @Override
    public boolean isNextStr() {
        return false;
    }

    @Override
    public String getNextStr() {
        return null;
    }

    @Override
    public void writeStr(String result) {
        text += result + "\n";
    }

    //get 1 command message and clean "buffer"
    public String getText() {
        String tmp = text;
        text = "";
        return tmp.strip();
    }

}
