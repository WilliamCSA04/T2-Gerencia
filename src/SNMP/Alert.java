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

    public Alert(int minimumValue, int maximumValue) {
        this.minimumValue = minimumValue;
        this.maximumValue = maximumValue;
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
        this.minimumValue = minimumValue;
    }

    public int getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(int maximumValue) {
        this.maximumValue = maximumValue;
    }

    public String getMinimumMessage() {
        return minimumMessage;
    }

    public String getMaximumMessage() {
        return maximumMessage;
    }

}
