/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SNMP;

import javax.swing.JOptionPane;

/**
 *
 * @author realnetwoking
 */
public class Alert {

    private final String minimumMessage = "Limite inferior ultrapassado";
    private final String maximumMessage = "Limite superior ultrapassado";
    private int minimumValue;
    private int maximumValue;
    private static Alert alert = null;

    public static Alert getInstance(){
        alert = alert == null ? new Alert() : alert;
        return alert;
    }
    
    private Alert() {
    }

    public void checkForAlert(int actualValue) {
        if (actualValue < minimumValue) {
            showMinimumAlert();
        } else if (actualValue > maximumValue) {
            showMaximumAlert();
        }

    }

    private void showMinimumAlert() {
        JOptionPane.showMessageDialog(null, minimumMessage, "Limite inferior violado", JOptionPane.WARNING_MESSAGE);
    }

    private void showMaximumAlert() {
        JOptionPane.showMessageDialog(null, maximumMessage, "Limite superior violado", JOptionPane.WARNING_MESSAGE);
    }

    public int getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(int minimumValue) {
//        boolean isLowerThanMaximumValue = isValidSetMinimumValue(minimumValue);
//        if(isLowerThanMaximumValue){
            this.minimumValue = minimumValue;
//        }
        
    }

    public int getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(int maximumValue) {
//        boolean isHigherThanMinimumValue = isValidSetMaximumValue(maximumValue);
//        if(isHigherThanMinimumValue){
            this.maximumValue = maximumValue;
//        }
        
    }
    
    private boolean isValidSetMaximumValue(int value){
        return value > minimumValue;
    }
    
    private boolean isValidSetMinimumValue(int value){
        return value < maximumValue;
    }

    public String getMinimumMessage() {
        return minimumMessage;
    }

    public String getMaximumMessage() {
        return maximumMessage;
    }

}
