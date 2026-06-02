import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;

public class StudentManagementApp {

    static boolean darkMode = false;

    public static void main(String[] args) {

        JFrame f = new JFrame("Student Management System");
        f.setSize(800, 550);
        f.setLocationRelativeTo(null);
        f.setLayout(null);

        // ===== HEADER =====
        JPanel header = new JPanel(null);
        header.setBackground(new Color(0, 102, 204));
        header.setBounds(0, 0, 800, 50);

        JLabel appTitle = new JLabel("🎓 Student Management System");
        appTitle.setForeground(Color.WHITE);
        appTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        appTitle.setBounds(20, 10, 400, 30);

        // 🌙 DARK MODE BUTTON
        JButton darkBtn = new JButton("🌙");
        darkBtn.setBounds(720, 10, 50, 30);

        header.add(appTitle);
        header.add(darkBtn);
        f.add(header);

        JTabbedPane tab = new JTabbedPane();
        tab.setBounds(0, 50, 800, 500);

        JPanel p1 = new JPanel(null);
        JPanel p2 = new JPanel(null);
        JPanel p3 = new JPanel(null);
        JPanel p4 = new JPanel(null);

        // ===== REGISTER =====
        JLabel title = new JLabel("Student Registration");
        title.setBounds(250, 10, 300, 30);

        JLabel nameLbl = new JLabel("Name:");
        JLabel genderLbl = new JLabel("Gender:");
        JLabel skillsLbl = new JLabel("Skills:");
        JLabel cityLbl = new JLabel("City:");

        JTextField nameField = new JTextField();

        JRadioButton male = new JRadioButton("Male");
        JRadioButton female = new JRadioButton("Female");

        ButtonGroup bg = new ButtonGroup();
        bg.add(male); bg.add(female);

        JCheckBox java = new JCheckBox("Java");
        JCheckBox python = new JCheckBox("Python");
        JCheckBox csharp = new JCheckBox("C#");

        JComboBox<String> cityBox = new JComboBox<>(new String[]{"Select","Mumbai","Pune","Delhi"});

        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton reset = new JButton("Reset");

        nameLbl.setBounds(120,80,100,30);
        nameField.setBounds(250,80,200,30);

        genderLbl.setBounds(120,130,100,30);
        male.setBounds(250,130,80,30);
        female.setBounds(330,130,100,30);

        skillsLbl.setBounds(120,180,100,30);
        java.setBounds(250,180,80,30);
        python.setBounds(330,180,80,30);
        csharp.setBounds(410,180,80,30);

        cityLbl.setBounds(120,230,100,30);
        cityBox.setBounds(250,230,200,30);

        addBtn.setBounds(200,300,100,40);
        updateBtn.setBounds(320,300,100,40);
        reset.setBounds(440,300,100,40);

        p1.add(title); p1.add(nameLbl); p1.add(nameField);
        p1.add(genderLbl); p1.add(male); p1.add(female);
        p1.add(skillsLbl); p1.add(java); p1.add(python); p1.add(csharp);
        p1.add(cityLbl); p1.add(cityBox);
        p1.add(addBtn); p1.add(updateBtn); p1.add(reset);

        // ===== DASHBOARD =====
        String[] cols = {"Name","Gender","Skills","City"};
        DefaultTableModel model = new DefaultTableModel(cols,0);

        JTable table = new JTable(model);
        table.setRowHeight(25);
        table.getTableHeader().setBackground(new Color(0,123,255));
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(100,60,600,250);

        JTextField searchField = new JTextField();
        searchField.setBounds(100,330,200,30);

        JButton searchBtn = new JButton("Search");
        JButton deleteBtn = new JButton("Delete");
        JButton clearAllBtn = new JButton("Clear All");
        JButton saveBtn = new JButton("Save");
        JButton loadBtn = new JButton("Load");

        searchBtn.setBounds(320,330,100,30);
        deleteBtn.setBounds(430,330,100,30);
        clearAllBtn.setBounds(540,330,100,30);

        saveBtn.setBounds(320,370,100,30);
        loadBtn.setBounds(430,370,100,30);

        p2.add(sp);
        p2.add(searchField); p2.add(searchBtn);
        p2.add(deleteBtn); p2.add(clearAllBtn);
        p2.add(saveBtn); p2.add(loadBtn);

        // ===== ABOUT =====
        JLabel about = new JLabel("Student Management System");
        about.setBounds(200,100,400,30);

        JLabel dev = new JLabel("Developed by Vijay Prajapati 🚀");
        dev.setBounds(250,150,300,30);

        p3.add(about); p3.add(dev);

        // ===== STATISTICS =====
        JLabel totalLbl = new JLabel("Total Students: 0");
        JLabel maleLbl = new JLabel("Male: 0");
        JLabel femaleLbl = new JLabel("Female: 0");

        totalLbl.setBounds(250,100,200,30);
        maleLbl.setBounds(250,150,200,30);
        femaleLbl.setBounds(250,200,200,30);

        p4.add(totalLbl); p4.add(maleLbl); p4.add(femaleLbl);

        Runnable updateStats = () -> {
            int total = model.getRowCount();
            int maleCount = 0, femaleCount = 0;

            for(int i=0;i<total;i++){
                String g = model.getValueAt(i,1).toString();
                if(g.equals("Male")) maleCount++;
                else femaleCount++;
            }

            totalLbl.setText("Total Students: " + total);
            maleLbl.setText("Male: " + maleCount);
            femaleLbl.setText("Female: " + femaleCount);
        };

        // ===== ADD =====
        addBtn.addActionListener(e -> {
            String name = nameField.getText();
            String gender = male.isSelected()?"Male":female.isSelected()?"Female":"";

            String skills="";
            if(java.isSelected()) skills+="Java ";
            if(python.isSelected()) skills+="Python ";
            if(csharp.isSelected()) skills+="C# ";

            String city = cityBox.getSelectedItem().toString();

            if(name.isEmpty()||gender.isEmpty()||skills.isEmpty()||city.equals("Select")){
                JOptionPane.showMessageDialog(f,"Fill all fields!");
            } else {
                model.addRow(new Object[]{name,gender,skills,city});
                updateStats.run();
                tab.setSelectedIndex(1);
            }
        });

        // ===== UPDATE FIX =====
        updateBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row != -1){
                model.setValueAt(nameField.getText(), row, 0);
                model.setValueAt(male.isSelected()?"Male":"Female", row, 1);

                String skills="";
                if(java.isSelected()) skills+="Java ";
                if(python.isSelected()) skills+="Python ";
                if(csharp.isSelected()) skills+="C# ";

                model.setValueAt(skills, row, 2);
                model.setValueAt(cityBox.getSelectedItem(), row, 3);
            }
        });

        // ===== DELETE =====
        deleteBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if(row!=-1){
                model.removeRow(row);
                updateStats.run();
            }
        });

        // ===== SEARCH =====
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);

        searchBtn.addActionListener(e -> {
            sorter.setRowFilter(RowFilter.regexFilter(searchField.getText()));
        });

        // ===== SAVE FILE =====
        saveBtn.addActionListener(e -> {
            try(PrintWriter pw = new PrintWriter("data.txt")){
                for(int i=0;i<model.getRowCount();i++){
                    pw.println(model.getValueAt(i,0)+","+
                               model.getValueAt(i,1)+","+
                               model.getValueAt(i,2)+","+
                               model.getValueAt(i,3));
                }
                JOptionPane.showMessageDialog(f,"Saved!");
            }catch(Exception ex){}
        });

        // ===== LOAD FILE =====
        loadBtn.addActionListener(e -> {
            try(BufferedReader br = new BufferedReader(new FileReader("data.txt"))){
                model.setRowCount(0);
                String line;
                while((line=br.readLine())!=null){
                    model.addRow(line.split(","));
                }
                updateStats.run();
            }catch(Exception ex){}
        });

        darkBtn.addActionListener(e -> {

darkMode = !darkMode;

Color backgroundColor = darkMode
        ? new Color(30,30,30)
        : Color.WHITE;

Color fg = darkMode
        ? Color.WHITE
        : Color.BLACK;

p1.setBackground(backgroundColor);
p2.setBackground(backgroundColor);
p3.setBackground(backgroundColor);
p4.setBackground(backgroundColor);

// Labels
title.setForeground(fg);
nameLbl.setForeground(fg);
genderLbl.setForeground(fg);
skillsLbl.setForeground(fg);
cityLbl.setForeground(fg);

about.setForeground(fg);
dev.setForeground(fg);

totalLbl.setForeground(fg);
maleLbl.setForeground(fg);
femaleLbl.setForeground(fg);

// TextFields
nameField.setBackground(
        darkMode ? new Color(60,60,60) : Color.WHITE);
nameField.setForeground(fg);

searchField.setBackground(
        darkMode ? new Color(60,60,60) : Color.WHITE);
searchField.setForeground(fg);

// Radio Buttons
male.setForeground(fg);
female.setForeground(fg);

male.setBackground(backgroundColor);
female.setBackground(backgroundColor);

// CheckBoxes
java.setForeground(fg);
python.setForeground(fg);
csharp.setForeground(fg);

java.setBackground(backgroundColor);
python.setBackground(backgroundColor);
csharp.setBackground(backgroundColor);

// ComboBox
cityBox.setBackground(
        darkMode ? new Color(60,60,60) : Color.WHITE);
cityBox.setForeground(fg);

cityBox.setRenderer(new DefaultListCellRenderer() {
    @Override
    public Component getListCellRendererComponent(
            JList<?> list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        Component c =
            super.getListCellRendererComponent(
                    list,
                    value,
                    index,
                    isSelected,
                    cellHasFocus);

        c.setBackground(
            darkMode
            ? new Color(60,60,60)
            : Color.WHITE);

        c.setForeground(
            darkMode
            ? Color.WHITE
            : Color.BLACK);

        return c;
    }
});

// Buttons
JButton[] buttons = {
    addBtn, updateBtn, reset,
    searchBtn, deleteBtn,
    clearAllBtn, saveBtn, loadBtn
};

for(JButton btn : buttons){
    btn.setBackground(
        darkMode
        ? new Color(80,80,80)
        : UIManager.getColor("Button.background"));

    btn.setForeground(fg);
}

// Table
table.setBackground(
    darkMode
    ? new Color(50,50,50)
    : Color.WHITE);

table.setForeground(fg);

table.setGridColor(
    darkMode
    ? Color.GRAY
    : Color.LIGHT_GRAY);

table.getTableHeader().setBackground(
    darkMode
    ? new Color(40,40,40)
    : new Color(0,123,255));

table.getTableHeader().setForeground(Color.WHITE);

sp.getViewport().setBackground(
    darkMode
    ? new Color(50,50,50)
    : Color.WHITE);


});


        // ===== RESET =====
        reset.addActionListener(e -> {
            nameField.setText("");
            bg.clearSelection();
            java.setSelected(false);
            python.setSelected(false);
            csharp.setSelected(false);
            cityBox.setSelectedIndex(0);
        });

        tab.add("Register", p1);
        tab.add("Dashboard", p2);
        tab.add("About", p3);
        tab.add("Statistics", p4);

        f.add(tab);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}