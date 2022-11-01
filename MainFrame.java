import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends JFrame { // when you extens from Jframe you can use methods that class
    private JPanel MainPanel;
    private JTable TableOfClasses;
    private JTextField SearchField;
    private JButton SearchButton;
    private JTable TableOfStudents;
    private JButton addStudentButton;
    private JButton removeStudentButton;
    private JButton changeInfoButton;
    private JButton addPointsButton;
    private JButton removePointsButton;
    private JButton countByConditionButton;
    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField studentConditionField;
    private JPanel PanelAddingStudent;
    private JTextField dateOfBirthField;
    private JTextField pointsField;
    private JButton addStudent;
    private JPanel PanelUpdatingStudent;
    private JTextField nameFieldUpdate;
    private JTextField lastNameUpdate;
    private JTextField studentCondtiionUpdate;
    private JTextField dateOfBirthUpdate;
    private JTextField pointsUpdate;
    private JButton updateStudent;
    private JTextField howManyPoints;
    private JPanel PanelToAddPoints;
    private JButton updatePoints;
    private JTextField howManyRemove;
    private JButton RemoveButton;
    private JPanel PanelRemovePoints;
    private JTextField whichTypeField;
    private JButton countButton;
    private JLabel ResultOfCountigByType;
    private JPanel PanelOfCountingCondition;


    public MainFrame(){
        PanelAddingStudent.setVisible(false);
        PanelUpdatingStudent.setVisible(false);
        PanelToAddPoints.setVisible(false);
        PanelRemovePoints.setVisible(false);
        PanelOfCountingCondition.setVisible(false);
        createClassesTable();
//        createStudentsTablev2();
        listenerCLickOnClassTable();
        setContentPane(MainPanel);
        setTitle("school-journal-simulator");
        setSize(500,700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setVisible(true);



    }
    public void listenerCLickOnClassTable(){
            TableOfClasses.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    String whichClass = TableOfClasses.getValueAt(TableOfClasses.getSelectedRow(),0).toString();
                    // TODO I had to update modifying on table here but its not a problem
                    DefaultTableModel modelTabel = (DefaultTableModel) TableOfStudents.getModel();
                    modelTabel.fireTableDataChanged();
                    if(whichClass=="Klasa 1A"){
                        createStudentsTable(whichClass);
                    }else if(whichClass=="Klasa 2B"){
                        createStudentsTable(whichClass);
                    }
                }
            });
    }

    public void removeStudent(JTable TableOfStudents){

        removeStudentButton.addActionListener(new ActionListener() {
            boolean ifDelate = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tableModel =  (DefaultTableModel) TableOfStudents.getModel();

                if(TableOfStudents.getSelectedRow()!=-1){
                    ifDelate=true;
                    tableModel.removeRow(TableOfStudents.getSelectedRow());

                }else if(TableOfStudents.getRowCount()==0) {
                    JOptionPane.showMessageDialog(null,"There is no more student to remove");
                }
            }
        });

    }
    public void addStudentButton(JTable TableOfStudents){
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                PanelAddingStudent.setVisible(true);
                addStudent.addActionListener(new ActionListener() {
                    boolean ifAdded = false;
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if((nameField.getText().equals("") ||lastNameField.getText().equals("") || studentConditionField.getText().equals("") || dateOfBirthField.getText().equals("")|| pointsField.getText().equals(""))){
                        }
                        else{
                            String newRow[]={nameField.getText(),lastNameField.getText(),studentConditionField.getText(),dateOfBirthField.getText(),pointsField.getText()};
                            //Creating currently model of date
                            DefaultTableModel modelTabel = (DefaultTableModel) TableOfStudents.getModel();
                            modelTabel.addRow(newRow);
                            JOptionPane.showMessageDialog(null,"Student added to the group");
                            ifAdded = true;
                            nameField.setText("");
                            lastNameField.setText("");
                            studentConditionField.setText("");
                            dateOfBirthField.setText("");
                            pointsField.setText("");
                        }
                        PanelAddingStudent.setVisible(false);
                    }
                });
            }
        });
    }
    public void changeInfoAboutStudent(JTable TableOfStudends){
        changeInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelUpdatingStudent.setVisible(true);
                TableOfStudents.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        DefaultTableModel modelTable = (DefaultTableModel) TableOfStudents.getModel();
                        String name = modelTable.getValueAt(TableOfStudends.getSelectedRow(),0).toString();
                        String lastName = modelTable.getValueAt(TableOfStudends.getSelectedRow(),1).toString();
                        String studentCondition = modelTable.getValueAt(TableOfStudends.getSelectedRow(),2).toString();
                        String dateOfBirth = modelTable.getValueAt(TableOfStudends.getSelectedRow(),3).toString();
                        String points = modelTable.getValueAt(TableOfStudends.getSelectedRow(),4).toString();

                        //set textField area:

                        nameFieldUpdate.setText(name);
                        lastNameUpdate.setText(lastName);
                        studentCondtiionUpdate.setText(studentCondition);
                        dateOfBirthUpdate.setText(dateOfBirth);
                        pointsUpdate.setText(points);
                        updateStudent.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("ilosc zazanczonych wierszy: " + TableOfStudends.getSelectedRowCount());
                                if (TableOfStudends.getSelectedRowCount() == 1) {
                                    DefaultTableModel tableModel = (DefaultTableModel) TableOfStudends.getModel();
                                    tableModel.setValueAt(nameFieldUpdate.getText(), TableOfStudends.getSelectedRow(), 0);
                                    tableModel.setValueAt(lastNameUpdate.getText(), TableOfStudends.getSelectedRow(), 1);
                                    tableModel.setValueAt(studentCondtiionUpdate.getText(), TableOfStudends.getSelectedRow(), 2);
                                    tableModel.setValueAt(dateOfBirthUpdate.getText(), TableOfStudends.getSelectedRow(), 3);
                                    tableModel.setValueAt(pointsUpdate.getText(), TableOfStudends.getSelectedRow(), 4);
                                    JOptionPane.showMessageDialog(null,"Info about student was updated !!");
                                }
                                PanelUpdatingStudent.setVisible(false);
                            }
                        });
                    }
                });
            }
        });
    }
    public void addPointsToStudents(JTable TableOfStudents){
        addPointsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelToAddPoints.setVisible(true);
                TableOfStudents.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        DefaultTableModel modelTabel = (DefaultTableModel) TableOfStudents.getModel();
                        String stringPoints = modelTabel.getValueAt(TableOfStudents.getSelectedRow(),4).toString();
                        int points = Integer.parseInt(stringPoints);
                        updatePoints.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                DefaultTableModel modelTabelv2 = (DefaultTableModel) TableOfStudents.getModel();
                                int newPoints = points + Integer.parseInt(howManyPoints.getText());
                                if(TableOfStudents.getSelectedRowCount()==1){
                                    modelTabelv2.setValueAt(newPoints,TableOfStudents.getSelectedRow(),4);
                                    JOptionPane.showMessageDialog(null,"Points were changed !!");
                                }
                                howManyPoints.setText("");
                                PanelToAddPoints.setVisible(false);
                            }
                        });

                    }
                });
            }
        });
    }

    public void removePointsToStudents(JTable TableOfStudents){
        removePointsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelRemovePoints.setVisible(true);
                TableOfStudents.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        DefaultTableModel modelTablae = (DefaultTableModel) TableOfStudents.getModel();
                        String stringPoints = modelTablae.getValueAt(TableOfStudents.getSelectedRow(),4).toString();
                        int points = Integer.parseInt(stringPoints);
                        RemoveButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int howPoints = Integer.parseInt(howManyRemove.getText());
                                int allPoints = points - howPoints;
                                DefaultTableModel modelTableToRemove = (DefaultTableModel) TableOfStudents.getModel();
                                modelTableToRemove.setValueAt(allPoints,TableOfStudents.getSelectedRow(),4);
                                JOptionPane.showMessageDialog(null,"Points were removed ");
                                howManyRemove.setText("");
                                PanelRemovePoints.setVisible(false); // it can be wrong
                            }
                        });

                    }
                });
            }
        });
    }


    private void createClassesTable(){
        Object[][]datev1 ={
                {"Klasa 1A",32},
                {"Klasa 2B", 15},
        };
        TableOfClasses.setModel(new DefaultTableModel(
                datev1,
                new String[]{"Name of Group","Size"}
        ));
    }
    private void createStudentsTable(String whichClass){

        if(whichClass=="Klasa 1A") {
            Object[][] date1 = {
                    {"Piotr", "Pogwizd", StudentCondition.ODRABIAJĄCY, "09:08:2000", 620},
                    {"Szymon", "Kowalski", StudentCondition.NIEOBECNY, "01:01:2000", 760},
                    {"Alicja", "Malinowska", StudentCondition.ODRABIAJĄCY, "03:03:1998", 1000},
            };
            DefaultTableModel modelStudent = new DefaultTableModel(date1,new String[]{"Name","LastName","Student Condition","Date Of Birth","Points"});
            TableOfStudents.setModel(modelStudent);
            removeStudent(TableOfStudents);

        } else if (whichClass=="Klasa 2B") {
            Object[][]date1={
                    {"Aneta","Makowska",StudentCondition.CHORY, "04:04:1998",1600},
                    {"Krystian","Ciupaga",StudentCondition.ODRABIAJĄCY,"06:06:2000",1800}
            };
            DefaultTableModel modelStudent = new DefaultTableModel(date1,new String[]{"Name","LastName","Student Condition","Date Of Birth","Points"});
            TableOfStudents.setModel(modelStudent);
            removeStudent(TableOfStudents);
        }

        addStudentButton(TableOfStudents);
        changeInfoAboutStudent(TableOfStudents);
        addPointsToStudents(TableOfStudents);
        removePointsToStudents(TableOfStudents);

        //Sort By Name And Points

        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(TableOfStudents.getModel());
        TableOfStudents.setRowSorter(sorter);
        List<RowSorter.SortKey> sortKeyList = new ArrayList<>(25);
        sortKeyList.add(new RowSorter.SortKey(0,SortOrder.ASCENDING));
//        sortKeyList.add( new RowSorter.SortKey(4,SortOrder.DESCENDING));
        sorter.setSortKeys(sortKeyList);

    }
    public static void main(String[] args) {
        MainFrame myFrame = new MainFrame();
    }


}


//        ListSelectionModel modelClass = TableOfClasses.getSelectionModel();
//        modelClass.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                if( !modelClass.isSelectionEmpty()){
//                    // Get selected row
//                    int indexSelectedClass = modelClass.getMinSelectionIndex();
//                    JOptionPane.showMessageDialog(null,"This is class number" + indexSelectedClass);
//                }
//            }
//        });