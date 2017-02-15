/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ChatForm.java
 *
 * Created on 13.11.2009, 18:20:21
 */



/**
 *
 * @author 4ekist_5-45
 */
import javax.swing.*;
import java.lang.*;
import java.util.*;

public class ChatForm extends javax.swing.JFrame {


    DataLinkLayer DlLayer;
    DefaultListModel model;
    private boolean isConnect = false;


    public ChatForm() {
        this.DlLayer = new DataLinkLayer(this);
        this.model = new DefaultListModel();
        initComponents();
        this.ResetAllButtonsToDefault();
        initList(this.model);
    }


    private void initList(DefaultListModel mdl)
    {
        this.listAreaContacts.setModel(mdl);

    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnExit = new javax.swing.JButton();
        btnSend = new javax.swing.JButton();
        btnHistory = new javax.swing.JButton();
        btnConnect = new javax.swing.JButton();
        btnDisconnect = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAreaChat = new javax.swing.JTextArea();
        txtFld = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        listAreaContacts = new javax.swing.JList();
        inputPortCombobox = new javax.swing.JComboBox();
        inputRateCombobox = new javax.swing.JComboBox();
        nameTxtField = new javax.swing.JTextField();
        outputPortCombobox = new javax.swing.JComboBox();
        outputRateCombobox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnExit.setText("Exit");
        btnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExitMouseClicked(evt);
            }
        });

        btnSend.setText("Send");
        btnSend.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSendMouseClicked(evt);
            }
        });

        btnHistory.setText("Ready to Chat");
        btnHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnHistoryMouseClicked(evt);
            }
        });

        btnConnect.setText("Connect");
        btnConnect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnConnectMouseClicked(evt);
            }
        });

        btnDisconnect.setText("Disconnect");
        btnDisconnect.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDisconnectMouseClicked(evt);
            }
        });

        txtAreaChat.setColumns(20);
        txtAreaChat.setRows(5);
        jScrollPane1.setViewportView(txtAreaChat);

        jScrollPane2.setViewportView(listAreaContacts);

        inputPortCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "COM1", "COM2" }));

        inputRateCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "9600", "14400", "19200", "38400", "57600", "115600" }));

        nameTxtField.setText(" ");

        outputPortCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "COM2", "COM1" }));

        outputRateCombobox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "9600", "14400", "19200", "38400", "57600", "115600" }));

        jLabel1.setText("Nickname");

        jLabel2.setText("inPort");

        jLabel3.setText("inRate");

        jLabel4.setText("outPort");

        jLabel5.setText("outRate");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnDisconnect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnConnect)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHistory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSend)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addComponent(btnExit))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtFld, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(inputPortCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(inputRateCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(outputPortCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(outputRateCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(nameTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(95, 95, 95)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(inputPortCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inputRateCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputPortCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(outputRateCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(nameTxtField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(txtFld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnExit)
                    .addComponent(btnDisconnect)
                    .addComponent(btnConnect)
                    .addComponent(btnHistory)
                    .addComponent(btnSend))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnConnectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConnectMouseClicked

            this.btnConnect.setEnabled(false);
            this.btnDisconnect.setEnabled(true);
            this.btnHistory.setEnabled(true);
            if ((String)(inputPortCombobox.getSelectedItem()) ==(String)(outputPortCombobox.getSelectedItem()))
                  {
                          JOptionPane.showMessageDialog(btnConnect, "Невозможно выбрать один и тот же порт");
                  }
                  else
                  {
                       String inPort, outPort;
                          int inRate, outRate;
                          inPort = (String)(inputPortCombobox.getSelectedItem());
                          outPort = (String)(outputPortCombobox.getSelectedItem());
                          inRate = Integer.parseInt((String)inputRateCombobox.getSelectedItem());
                          outRate = Integer.parseInt((String)outputRateCombobox.getSelectedItem());

                          this.DlLayer.ConnectToCOMPort(nameTxtField.getText(), inPort, inRate);
                          this.txtAreaChat.setText("");

                     
                  }
    }//GEN-LAST:event_btnConnectMouseClicked

    private void btnSendMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSendMouseClicked
                 String message = this.txtFld.getText();
		 this.txtAreaChat.append(this.DlLayer.networkName + ">> " + message + '\n');
		 this.DlLayer.ReceiveDataFromAppLayer(message);
                 this.txtFld.setText("");
    }//GEN-LAST:event_btnSendMouseClicked

    private void btnHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHistoryMouseClicked
        this.btnHistory.setEnabled(false);
        this.btnSend.setEnabled(true);
        this.DlLayer.CheckConnection();
    }//GEN-LAST:event_btnHistoryMouseClicked

    private void btnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExitMouseClicked
        System.exit(0);
    }//GEN-LAST:event_btnExitMouseClicked

    private void btnDisconnectMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDisconnectMouseClicked
        this.DlLayer.Disconnect();
    }//GEN-LAST:event_btnDisconnectMouseClicked


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatForm().setVisible(true);
            }
        });
    }

    public void DisableRTCButton()
    {
        this.btnHistory.disable();
    }
    public void SetTextAreaChatText(String text)
    {
        this.txtAreaChat.setText(text);
    }

    public void AppendTextAreaChatText(String text)
    {
        this.txtAreaChat.append(text);
    }

    public void ResetAllButtonsToDefault()
    {
        this.btnConnect.setEnabled(true);
        this.btnDisconnect.setEnabled(false);
        this.btnHistory.setEnabled(false);
        this.btnSend.setEnabled(false);
    }
    public String GetTextField()
    {
        return this.txtAreaChat.getText();
    }

    public void AddListItem(String text)
    {
        this.model.addElement(text);
    }

    public void InitList(Vector<DataLinkLayer.UserId> UserIDVector)
    {
        this.listAreaContacts.setListData(UserIDVector);
    }

    public void ClearAll()
    {
        this.txtAreaChat.setText("");
        this.txtFld.setText("");
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnDisconnect;
    private javax.swing.JButton btnExit;
    private javax.swing.JButton btnHistory;
    private javax.swing.JButton btnSend;
    private javax.swing.JComboBox inputPortCombobox;
    private javax.swing.JComboBox inputRateCombobox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList listAreaContacts;
    private javax.swing.JTextField nameTxtField;
    private javax.swing.JComboBox outputPortCombobox;
    private javax.swing.JComboBox outputRateCombobox;
    private javax.swing.JTextArea txtAreaChat;
    private javax.swing.JTextField txtFld;
    // End of variables declaration//GEN-END:variables

}
