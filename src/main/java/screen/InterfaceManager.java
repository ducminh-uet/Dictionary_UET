package screen;

import dictionary.Word;

//Singleton
public class InterfaceManager {

    private static InterfaceManager instance;
    private String previousInterface;

    private boolean state;


    private Word selected;

    private InterfaceManager() {
    }

    public static InterfaceManager getInstance() {
        if (instance == null) {
            instance = new InterfaceManager();
        }
        return instance;
    }

    public String getPreviousInterface() {
        return previousInterface;
    }

    public void setPreviousInterface(String previousInterface) {
        this.previousInterface = previousInterface;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Word getSelected() {
        return selected;
    }

    public void setSelected(Word selected) {
        this.selected = selected;
    }



}
