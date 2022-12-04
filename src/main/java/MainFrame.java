import entity.Groups;
import entity.Students;

import javax.persistence.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.util.List;
import java.util.Objects;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Stream;

public class MainFrame extends JFrame { // when you extens from Jframe you can use methods that class
    private JPanel MainPanel;
    private JTable TableOfClasses;
    private JTextField SearchField;
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
    private JButton searchButtonByName;
    private JButton addGroupButton;
    private JButton removeGroupButton;
    private JPanel PanelToGroup;
    private JTextField fieldToNameGroup;
    private JTextField fieldToSizeGroup;
    private JButton btnToUpdateGroups;
    private JPanel ArreaOfGroups;
    private JTextField groupField;
    private JTextField groupUpdate;


    public MainFrame(){
        // ask how to add this template: ?
        ArreaOfGroups.setVisible(true);
        PanelAddingStudent.setVisible(false);
        PanelUpdatingStudent.setVisible(false);
        PanelToAddPoints.setVisible(false);
        PanelRemovePoints.setVisible(false);
        PanelOfCountingCondition.setVisible(false);
        PanelToGroup.setVisible(false);


        setModelToTableGroup();
        setModelToTableStudent();

        listenerClickOnClassTablev2();
        setContentPane(MainPanel);
        setTitle("school-journal-simulator");
        setSize(600,800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }


    private void setModelToTableGroup(){
        DefaultTableModel model;
        model = new DefaultTableModel(new String[]{"Name of Group","Size"},0);
        TableOfClasses.setModel(model);
        TableOfClasses.setVisible(true);
    }
    private void setModelToTableStudent(){
        DefaultTableModel model;
        model = new DefaultTableModel(new String[]{"Name","LastName","Student Condition","Date Of Birth","Points"},0);
        TableOfStudents.setModel(model);
        TableOfStudents.setVisible(true); // I think this is not necessary
    }
    private void cleanJTable(JTable Table){
        DefaultTableModel modelStudent = (DefaultTableModel) TableOfStudents.getModel();
        modelStudent.setRowCount(0);
    }


    private void showStudentsInGroupByIdGroup(int idGroup, JTable TableOfStudents){
        //todo i can take all students and with params which u sent to funciton i can search ale dshow the m student in table !


        String idGroup_String = Integer.toString(idGroup);
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        DefaultTableModel modelStudent = (DefaultTableModel) TableOfStudents.getModel();
        List<Students> studentsList = entityManager.createQuery("select s from  Students s ").getResultList();

        for( Students student: studentsList){
            if(student.getIdGroup()==idGroup) {
//                System.out.println(student.getName());
                String nameStudent = student.getName();
                String lastName = student.getLastName();
                String SCondition = student.getsCondition();
                String dateOfBirth = student.getDateOfBirth();
                int points = student.getPoints();
                String[] newRow = {nameStudent, lastName, SCondition, dateOfBirth, String.valueOf(points)};
//                System.out.println(newRow.toString());
                modelStudent.addRow(newRow);
                modelStudent.fireTableDataChanged();
            }
        }
//        setModelToTableStudent();
        TableOfStudents.setVisible(true);
        entityManager.close();
        entityManagerFactory.close();
    }
    private void listenerClickOnClassTablev2(){
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        List<Groups>groupsList = entityManager.createQuery("SELECT g FROM Groups g").getResultList();

        DefaultTableModel modelGroup = (DefaultTableModel) TableOfClasses.getModel();
        for(Groups group: groupsList){
            String nameGroup = group.getNameOfGroup();
            int sizeGroup = group.getMaxStudents();
            System.out.println(group.getNameOfGroup());
            String[] newRow = {nameGroup, String.valueOf(sizeGroup)};
            modelGroup.addRow(newRow);
        }
        TableOfClasses.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String whichClass = TableOfClasses.getValueAt(TableOfClasses.getSelectedRow(),0).toString();
                DefaultTableModel modelStudent = (DefaultTableModel) TableOfStudents.getModel();
                for(Groups group : groupsList){
                    if(Objects.equals(group.getNameOfGroup(), whichClass)){
                        cleanJTable(TableOfStudents);
                        showStudentsInGroupByIdGroup(group.getIdGroup(), TableOfStudents);
                        addStudentButton(TableOfStudents);
                        changeInfoAboutStudent(TableOfStudents);
                        addPointsToStudents(TableOfStudents);
                        removePointsToStudents(TableOfStudents);
                        countByTypeOfCondtition(TableOfStudents);
                        addGroup(TableOfClasses);
                        removeGroup(TableOfClasses);
                        removeStudent(TableOfStudents);
                        searchByPartial(TableOfStudents,modelStudent);
                        break;
                    }
                }
            }
        });
    }

    public void addGroup( JTable TableOfClasses){
        addGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelToGroup.setVisible(true);
                btnToUpdateGroups.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(fieldToNameGroup.getText().equals("") || fieldToNameGroup.getText().equals("")){
//                            JOptionPane.showMessageDialog(null,"You have to full all fields!");
                        }else if(!fieldToNameGroup.getText().equals("") && !fieldToSizeGroup.getText().equals("")){
                            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
                            EntityManager entityManager = entityManagerFactory.createEntityManager();
                            EntityTransaction transaction = entityManager.getTransaction();

                            try {
                                transaction.begin();
                                    Groups newGroup = new Groups();
                                    newGroup.setNameOfGroup(fieldToNameGroup.getText());
                                    newGroup.setMaxStudents(Integer.valueOf(fieldToSizeGroup.getText()));
                                    entityManager.persist(newGroup);
                                transaction.commit();
                            } finally {
                                if(transaction.isActive()){
                                    transaction.rollback();
                                }
                                entityManager.close();
                                entityManagerFactory.close();
                            }

                            String nameField = fieldToNameGroup.getText();
                            String sizeField = fieldToSizeGroup.getText();
                            DefaultTableModel tableModel = (DefaultTableModel) TableOfClasses.getModel();
                            String[] newRow = {nameField, sizeField};
                            tableModel.addRow(newRow);
                            fieldToNameGroup.setText("");
                            fieldToSizeGroup.setText("");
                            TableOfClasses.repaint();
                        }
                        //todo after add row student to new created group i have to do sth to display it in Jtable
                        TableOfClasses.repaint();
                        TableOfStudents.repaint();
                        PanelToGroup.setVisible(false);
                    }
                });
            }
        });
    }
    public void removeGroup(JTable TableOfGroups){
        removeGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo i can do it when class is empty, if class willl have a student i should show JOptionPane
                DefaultTableModel tableModel = (DefaultTableModel) TableOfGroups.getModel();
                if(TableOfGroups.getSelectedRow()!=-1) {
                    String nameGroup = tableModel.getValueAt(TableOfClasses.getSelectedRow(),0).toString();
                    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
                    EntityManager entityManager = entityManagerFactory.createEntityManager();
                    EntityTransaction transaction = entityManager.getTransaction();
                    List<Groups>groupsList = entityManager.createQuery("select g from Groups g").getResultList();
                    List<Students>studentsList = entityManager.createQuery("select s from Students s").getResultList();
                    try {
                        //todo zwracac id grupy i sprawdzac w tabeli studencu czy to id wystapilo, jesli nie to usowam grupe jest tak to komunikat
                        transaction.begin();
                        for(Groups group: groupsList){
                            boolean ifExist =false;
                            for(Students student : studentsList){
                                if(group.getIdGroup()==student.getIdGroup()){
                                    ifExist = true;
                                }
                            }
                            if (!ifExist){
                                entityManager.remove(group);
                                tableModel.removeRow(TableOfGroups.getSelectedRow());
                            }
                        }
                        transaction.commit();
                    } finally {
                        if(transaction.isActive()){
                            transaction.rollback();
                        }
                        entityManager.close();
                        entityManagerFactory.close();
                    }
                }else if(TableOfGroups.getSelectedRow()==0){
                    JOptionPane.showMessageDialog(null,"There is no more groups to remove");
                }
                TableOfGroups.repaint();
            }
        });
    }
    public void removeStudent(JTable TableOfStudents){

        removeStudentButton.addActionListener(new ActionListener() {
            boolean ifDelate = false;
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultTableModel tableModel =  (DefaultTableModel) TableOfStudents.getModel();
                String nameStudent = tableModel.getValueAt(TableOfStudents.getSelectedRow(),0).toString();
                String lastName = tableModel.getValueAt(TableOfStudents.getSelectedRow(), 1).toString();
                System.out.println(lastName);
                //Entity manager and others xd
                EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
                EntityManager entityManager = entityManagerFactory.createEntityManager();
                EntityTransaction transaction = entityManager.getTransaction();
                List<Students>studentsList = entityManager.createQuery("select s from Students s").getResultList();

                try {
                    transaction.begin();
                    for( Students student: studentsList){
                        if(student.getName().equals(nameStudent) && student.getLastName().equals(lastName)){
                            entityManager.remove(student);
                        }
                    }
                    transaction.commit();
                } finally {
                    if(transaction.isActive()){
                        transaction.rollback();
                    }
                    entityManager.close();
                    entityManagerFactory.close();
                }

                if(TableOfStudents.getSelectedRow()!=-1){
                    ifDelate=true;
                    tableModel.removeRow(TableOfStudents.getSelectedRow());
                    TableOfStudents.repaint();
                }else if(TableOfStudents.getRowCount()==0) {
                    JOptionPane.showMessageDialog(null,"There is no more student to remove");
                }
//                tableModel.fireTableDataChanged();
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
                        if((nameField.getText().equals("") ||lastNameField.getText().equals("") || studentConditionField.getText().equals("") || dateOfBirthField.getText().equals("")|| pointsField.getText().equals("") || groupField.getText().equals(""))){
                        }
                        else{
                            EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
                            EntityManager entityManager = entityManagerFactory.createEntityManager();
                            EntityTransaction transaction = entityManager.getTransaction();
                            List<Groups>groupsList = entityManager.createQuery("select g from  Groups g").getResultList();
                            String newRow[]={nameField.getText(),lastNameField.getText(),studentConditionField.getText(),dateOfBirthField.getText(),pointsField.getText(), groupField.getText()};
                            DefaultTableModel modelTabel = (DefaultTableModel) TableOfStudents.getModel();
                            modelTabel.addRow(newRow);
                            JOptionPane.showMessageDialog(null,"Student added to the group");
                            TableOfStudents.repaint();
                            try {
                                transaction.begin();
                                    Students newStudent = new Students();
                                    newStudent.setName(nameField.getText());
                                    newStudent.setLastName(lastNameField.getText());
                                    newStudent.setsCondition(studentConditionField.getText());
                                    newStudent.setDateOfBirth(dateOfBirthField.getText());
                                    newStudent.setPoints(Integer.valueOf(pointsField.getText()));
                                    for(Groups group : groupsList){
                                        if(group.getNameOfGroup().equals(groupField.getText())){
                                            newStudent.setGroupsByIdGroup(group);
                                            break;
                                        }
                                    }
                                    entityManager.persist(newStudent);
                                transaction.commit();
                            } finally {
                                if(transaction.isActive()){
                                    transaction.rollback();
                                }
                                entityManager.close();
                                entityManagerFactory.close();
                            }
                            ifAdded = true;
                            nameField.setText("");
                            lastNameField.setText("");
                            studentConditionField.setText("");
                            dateOfBirthField.setText("");
                            pointsField.setText("");
                            groupField.setText("");
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
                        DefaultTableModel groupModel = (DefaultTableModel) TableOfClasses.getModel();
                        String name = modelTable.getValueAt(TableOfStudends.getSelectedRow(),0).toString();
                        String lastName = modelTable.getValueAt(TableOfStudends.getSelectedRow(),1).toString();
                        String studentCondition = modelTable.getValueAt(TableOfStudends.getSelectedRow(),2).toString();
                        String dateOfBirth = modelTable.getValueAt(TableOfStudends.getSelectedRow(),3).toString();
                        String points = modelTable.getValueAt(TableOfStudends.getSelectedRow(),4).toString();
                        String group = groupModel.getValueAt(TableOfClasses.getSelectedRow(),0).toString();
                        //set textField area:

                        nameFieldUpdate.setText(name);
                        lastNameUpdate.setText(lastName);
                        studentCondtiionUpdate.setText(studentCondition);
                        dateOfBirthUpdate.setText(dateOfBirth);
                        pointsUpdate.setText(points);
                        groupUpdate.setText(group);
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

                                    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
                                    EntityManager entityManager = entityManagerFactory.createEntityManager();
                                    EntityTransaction transaction = entityManager.getTransaction();
                                    List<Groups>groupsList = entityManager.createQuery("select g from Groups g").getResultList();
                                    try {
                                        transaction.begin();
                                               List<Students>studentsList = entityManager.createQuery("select s from Students s").getResultList();
                                               for(Students student: studentsList){
                                                   if(student.getName().equals(name) && student.getLastName().equals(lastName)){
                                                       student.setName(nameFieldUpdate.getText());
                                                       student.setLastName(lastNameUpdate.getText());
                                                       student.setsCondition(studentCondtiionUpdate.getText());
                                                       student.setDateOfBirth(dateOfBirthUpdate.getText());
                                                       student.setPoints(Integer.valueOf(pointsUpdate.getText()));
                                                       entityManager.merge(student);
                                                   }
                                               }
                                        transaction.commit();
                                    } finally {
                                        if(transaction.isActive()){
                                            transaction.rollback();
                                        }
                                        entityManager.close();
                                        entityManagerFactory.close();
                                    }

                                    tableModel.fireTableDataChanged();
                                    TableOfStudents.repaint();
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
                                int IntegerPoints = Integer.parseInt(stringPoints);
                                int newPoints = points + Integer.parseInt(howManyPoints.getText());
                                EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
                                EntityManager entityManager = entityManagerFactory.createEntityManager();
                                EntityTransaction transaction = entityManager.getTransaction();
                                List<Students>studentsList = entityManager.createQuery("select s from Students  s").getResultList();
                                try {
                                    transaction.begin();
                                    for(Students student: studentsList){
                                        System.out.println(student.getPoints());
                                        if(student.getPoints()==IntegerPoints){
                                            student.setPoints(newPoints);
                                            entityManager.merge(student);
                                        }
                                    }
                                    transaction.commit();
                                } finally {
                                    if(transaction.isActive()){
                                        transaction.rollback();
                                    }
                                    entityManager.close();
                                    entityManagerFactory.close();
                                }

                                if(TableOfStudents.getSelectedRowCount()==1){
                                    modelTabelv2.setValueAt(newPoints,TableOfStudents.getSelectedRow(),4);
                                    JOptionPane.showMessageDialog(null,"Points were changed !!");
                                }
                                howManyPoints.setText("");
//                                modelTabelv2.fireTableDataChanged();
                                TableOfStudents.repaint();
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
                        int IntegerPoints = Integer.parseInt(stringPoints);
                        int points = Integer.parseInt(stringPoints);
                        RemoveButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                int howPoints = Integer.parseInt(howManyRemove.getText());
                                int allPoints = points - howPoints;
                                DefaultTableModel modelTableToRemove = (DefaultTableModel) TableOfStudents.getModel();
                                if(allPoints<=0){
                                    modelTableToRemove.removeRow(TableOfStudents.getSelectedRow());
                                }else {

                                    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
                                    EntityManager entityManager = entityManagerFactory.createEntityManager();
                                    EntityTransaction transaction = entityManager.getTransaction();
                                    List<Students>studentsList = entityManager.createQuery("select s from Students s").getResultList();
                                    try {
                                        transaction.begin();
                                        for(Students student : studentsList){
                                            if(student.getPoints()==IntegerPoints){
                                                student.setPoints(allPoints);
                                                entityManager.merge(student);
                                            }
                                        }
                                        transaction.commit();
                                    } finally {
                                        if(transaction.isActive()){
                                            transaction.rollback();
                                        }
                                        entityManager.close();
                                        entityManagerFactory.close();
                                    }

                                    modelTableToRemove.setValueAt(allPoints, TableOfStudents.getSelectedRow(), 4);
                                    JOptionPane.showMessageDialog(null, "Points were removed ");
                                    howManyRemove.setText("");
//                                modelTableToRemove.fireTableDataChanged();
                                }
                                TableOfStudents.repaint();
                                PanelRemovePoints.setVisible(false);
                            }
                        });

                    }
                });
            }
        });
    }

    public void countByTypeOfCondtition(JTable TableOfStudents){
        countByConditionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelOfCountingCondition.setVisible(true);
                countButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(!whichTypeField.getText().equals("")) {
                            String whichType = whichTypeField.getText();
                            DefaultTableModel modelTable = (DefaultTableModel) TableOfStudents.getModel();
                            int sum=0;
                            for(int i=0;i<TableOfStudents.getRowCount();i++){
                                if(modelTable.getValueAt(i,2).toString().equals(whichType)){
                                    sum++;
                                }
                            }
                            ResultOfCountigByType.setText(Integer.toString(sum));
                            //TODO has to add in every panel: after click table functionality has to have visible on false mode
                            TableOfStudents.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mouseClicked(MouseEvent e) {
                                    super.mouseClicked(e);
                                    ResultOfCountigByType.setText("");
                                    whichTypeField.setText("");
                                    PanelOfCountingCondition.setVisible(false);
                                }
                            });
                        }else{
                            JOptionPane.showMessageDialog(null,"You have to choose type to search");
                        }
                    }
                });
            }
        });
    }
    public void searchByPartial(JTable TableOfStudents, DefaultTableModel model){

        final TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(model);
        TableOfStudents.setRowSorter(sorter);
        searchButtonByName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchValue = SearchField.getText();
                if(searchValue.length()==0){
                    sorter.setRowFilter(null);
                }else{
                    try{
                        sorter.setRowFilter(RowFilter.regexFilter(searchValue));
                    }catch (PatternSyntaxException pse){
                        System.out.println("Bad regex pattern");
                    }
                }
            }
        });
//       sorter = new TableRowSorter<DefaultTableModel>(tableModel);

    }
    public static void main(String ... args) {
        MainFrame myFrame = new MainFrame();
    }
}
