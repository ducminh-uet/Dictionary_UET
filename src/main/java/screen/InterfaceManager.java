package screen;

import dictionary.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.*;

//Singleton
public class InterfaceManager {

    private static InterfaceManager instance;
    private String previousInterface;

    private boolean state;


    private List<Word> selected = new ArrayList<>();
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

    public List<Word> getSelected() {
        Set<Word> temp = new LinkedHashSet<>(selected);
        selected.clear();
        selected.addAll(temp);
        Collections.reverse(selected);
        return new ArrayList<>(selected);
    }

    public void setSelected(List<Word> selected) {
        this.selected = selected;
    }
    public void removeSavedWord(Word word) {
        selected.remove(word);
    }
    public void addSavedWord(Word word) {
        selected.add(word);
    }
    public ObservableList<Word> getSelectedObservableList() {
        return FXCollections.observableArrayList(selected);
    }
}
