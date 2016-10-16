package Initializer;
import Graphics.MainWindow;
import SNMP.Connection;

public class Main {
    
    public static void main(String[] args) {
        System.out.println(Connection.getSystemDescription());
        MainWindow mainWindow = new MainWindow();
        mainWindow.setVisible(true);
    }
    
}
