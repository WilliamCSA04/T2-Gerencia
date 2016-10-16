package Initializer;
import Windows.MainWindow;
import SNMP.Connection;

public class Main {
    
    public static void main(String[] args) {
        for (String description : Connection.getAllSystemDescription()) {
            System.out.println(description);
        }
        
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }
    
}
