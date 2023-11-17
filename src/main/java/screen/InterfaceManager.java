package screen;
//Singleton
public class InterfaceManager {

    private static InterfaceManager instance;
    private String previousInterface;

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
}
