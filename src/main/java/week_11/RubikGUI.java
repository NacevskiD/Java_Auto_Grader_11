package week_11;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

public class RubikGUI extends JFrame {
    
    private JPanel mainPanel;
    private JTable solversTable;
    private JTextField newCubeSolverNameText;
    private JTextField newCubeSolverTimeText;
    private JButton addNewSolverButton;
    private JLabel updateSolverNameLabel;
    private JTextField updateTimeText;
    private JButton updateTimeButton;
    private JButton deleteSolverButton;
    private DBConfig dbConfig;
    DefaultTableModel tableModel;
    DefaultListModel<RubrikObject> defaultListModel;


    // TODO configure and implement functionality in your GUI.
    
    
    
    public RubikGUI(Rubik rubikProgram) {
        dbConfig = new DBConfig();
        dbConfig.addData("test",10.0);
        setContentPane(mainPanel);
        pack();
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel();
        defaultListModel = new DefaultListModel<>();
        // adding the columns
        tableModel.addColumn("id");
        tableModel.addColumn("solver_name");
        tableModel.addColumn("time_seconds");
        setListData();
        solversTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        solversTable.setModel(tableModel);


        
        // TODO configure and implement functionality in your GUI.

        addNewSolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // checking if there is any data in the fields
                if (newCubeSolverNameText.getText().length() > 0 && (Double.parseDouble(newCubeSolverTimeText.getText()) > 0)){
                    // if there is, add it to the DB and clear the fields
                    dbConfig.addData(newCubeSolverNameText.getText(),Double.parseDouble(newCubeSolverTimeText.getText()));
                    newCubeSolverNameText.setText("");
                    newCubeSolverTimeText.setText("");
                    // update list
                    setListData();
                }else {
                    showAlertDialog("Incorrect data, try again.");
                }
            }
        });
        updateTimeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // get the selected row, search based on the selected name in the DB to update
                int a = solversTable.getSelectedRow();
                String name = solversTable.getModel().getValueAt(a, 1).toString();
                // checking if the new time is bigger than 0 and then updating
                double time = Double.parseDouble(updateTimeText.getText());
                if (time>0){
                    dbConfig.updateData(name,time);
                    solversTable.getModel().setValueAt(time,a,2);
                }else {
                    showAlertDialog("Enter a valid number");
                }


            }
        });


        solversTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                //selected row shows up in the update section
                int a = solversTable.getSelectedRow();
                if (a >= 0) {
                    String name = solversTable.getModel().getValueAt(a, 1).toString();
                    String time = solversTable.getModel().getValueAt(a, 2).toString();


                    updateSolverNameLabel.setText(name);
                    updateTimeText.setText(time);
                }
            }
        });

        deleteSolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel model = (DefaultTableModel) solversTable.getModel();
                try{
                    if (showYesNoDialog("Are you sure you want to delete this item?") == JOptionPane.YES_OPTION) {
                        // deleting the selected row and clearing the selection
                        int a = solversTable.getSelectedRow();


                        dbConfig.deleteItem(solversTable.getModel().getValueAt(a, 1).toString());
                        model.removeRow(a);
                        updateSolverNameLabel.setText("");
                        updateTimeText.setText("");
                    }
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(null,ex);
                }



            }
        });
    }

    void setListData(){
        // loading data from db
        tableModel.setRowCount(0);
        ArrayList<RubrikObject> data = dbConfig.getAll();

        if (data.size() != 0) {

            for (RubrikObject rubrikObject : data) {
                tableModel.addRow(new String[]{Integer.toString(rubrikObject.getId()), rubrikObject.getName(), Double.toString(rubrikObject.getTime())});
            }
        }

    }

    void addData(){


    }
    
    
    
    
    
    // Use these methods to show dialogs. Or your tests may time out.
    
    
    /* This will return
    *
    * JOptionPanel.YES_OPTION if the user clicks 'yes'
    * JOptionPanel.NO_OPTION if the user clicks 'no'.
    *
    *
    * You can call this method and check for the return type, e.g.
    *
    * if (showYesNoDialog("Delete this solver?") == JOptionPane.YES_OPTION) {
    *     // User clicked yes, so go ahead and delete
    * } else {
    *     // User did not click yes.
    * }
    * */
    protected int showYesNoDialog(String message) {
        return JOptionPane.showConfirmDialog(this, message, null, JOptionPane.YES_NO_OPTION);
    }
    
    
    
    /*
    *  Call this method to show an alert/message dialog with the message provided.
    * */
    protected void showAlertDialog(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
    
    
}
