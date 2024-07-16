import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

//===================== ADD CONTACTS CLASS =======================
class AddContacts extends JFrame {
    private JTextField txtId, txtName, txtPhone, txtCompany, txtSalary, txtBirthday;

    public AddContacts() {
        setTitle("Add New Contact");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(50, 30, 200, 30);
        txtId = new JTextField(20);
        txtId.setBounds(250, 30, 200, 30);
        txtId.setText(ContactsController.generateId());
        txtId.setEditable(false);
        add(lblId);
        add(txtId);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(50, 70, 200, 30);
        txtName = new JTextField(20);
        txtName.setBounds(250, 70, 200, 30);
        add(lblName);
        add(txtName);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(50, 110, 200, 30);
        txtPhone = new JTextField(20);
        txtPhone.setBounds(250, 110, 200, 30);
        txtPhone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                validatePhoneNumber();
            }
        });
        add(lblPhone);
        add(txtPhone);

        JLabel lblCompany = new JLabel("Company:");
        lblCompany.setBounds(50, 150, 200, 30);
        txtCompany = new JTextField(20);
        txtCompany.setBounds(250, 150, 200, 30);
        add(lblCompany);
        add(txtCompany);

        JLabel lblSalary = new JLabel("Salary:");
        lblSalary.setBounds(50, 190, 200, 30);
        txtSalary = new JTextField(20);
        txtSalary.setBounds(250, 190, 200, 30);
        add(lblSalary);
        add(txtSalary);

        JLabel lblBirthday = new JLabel("Birthday:");
        lblBirthday.setBounds(50, 230, 200, 30);
        txtBirthday = new JTextField(20);
        txtBirthday.setBounds(250, 230, 200, 30);
        add(lblBirthday);
        add(txtBirthday);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBounds(100, 280, 100, 30);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addContact();
            }
        });
        add(btnAdd);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(220, 280, 100, 30);
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        add(btnCancel);

        JButton btnBack = new JButton("Back To Homepage");
        btnBack.setBounds(340, 280, 180, 30);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainWindow().setVisible(true);
                dispose();
            }
        });
        add(btnBack);
    }

    private void validatePhoneNumber() {
        String phoneNumber = txtPhone.getText();
        boolean isValidPhone = ContactsController.isValidPhone(phoneNumber);
        if (!isValidPhone) {
            int option = JOptionPane.showConfirmDialog(this,
                    "Invalid Phone Number.... Do you want to input phone number again?");
            if (option == JOptionPane.YES_OPTION) {
                txtPhone.setText("");
                txtPhone.requestFocus();
            } else {
                clearFields();
                dispose();
            }
        }
    }

    private void addContact() {
        String id = txtId.getText();
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String company = txtCompany.getText();
        double salary = Double.parseDouble(txtSalary.getText());
        String birthday = txtBirthday.getText();

        boolean isValidPhoneNumber = ContactsController.isValidPhone(phone);
        boolean isValidSalary = ContactsController.isValidSalary(salary);
        boolean isValidBirthday = ContactsController.isValidBirthday(birthday);

        if (!isValidPhoneNumber) {
            showErrorMessage("Invalid Phone Number");
            return;
        }

        if (!isValidSalary) {
            showErrorMessage("Invalid Salary");
            return;
        }

        if (!isValidBirthday) {
            showErrorMessage("Invalid Birthday");
            return;
        }

        Contact contact = new Contact(id, name, phone, company, salary, birthday);
        ContactsController.addContact(contact);
        int option = JOptionPane.showConfirmDialog(this, "Added Successfully... Do you want to add another contact?");
        if (option == JOptionPane.YES_OPTION) {
            clearFields();
            txtId.setText(ContactsController.generateId());
            txtName.requestFocus();
        } else {
            new MainWindow().setVisible(true);
            dispose();
        }
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void clearFields() {
        txtName.setText("");
        txtPhone.setText("");
        txtCompany.setText("");
        txtSalary.setText("");
        txtBirthday.setText("");
    }
}

// ================================================================

// ==================== CONTACT CLASS =============================
class Contact {
    private String id;
    private String name;
    private String phoneNumber;
    private String companyName;
    private double salary;
    private String birthday;

    public Contact(String id, String name, String phoneNumber, String companyName, double salary, String birthday) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.salary = salary;
        this.birthday = birthday;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
}

// ================================================================

// =============== CONTACT LIST CLASS =============================
class ContactList {
    private static Node start;

    public Node getStart() {
        return start;
    }

    public static int getSize() {
        Node temp = start;
        int count = 0;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    private boolean isEmpty() {
        return start == null;
    }

    public Contact[] toArray() {
        Contact[] contactArray = new Contact[getSize()];
        Node temp = start;
        for (int i = 0; temp != null; i++) {
            contactArray[i] = temp.getContact();
            temp = temp.getNext();
        }
        return contactArray;
    }

    public int searchByNameOrPhoneNumber(String nameOrPhone) {
        Node temp = start;
        int index = 0;
        while (temp != null) {
            if (temp.getContact().getName().equals(nameOrPhone)
                    || temp.getContact().getPhoneNumber().equals(nameOrPhone)) {
                return index;
            }
            temp = temp.getNext();
            index++;
        }
        return -1;
    }

    public Contact get(int index) {
        if (isValidIndex(index)) {
            Node temp = start;
            for (int i = 0; i < index; i++) {
                temp = temp.getNext();
            }
            return temp.getContact();
        }
        return null;
    }

    boolean isValidIndex(int index) {
        return index >= 0 && index < getSize();
    }

    public boolean remove(int index) {
        if (!isValidIndex(index)) {
            System.out.println("Invalid index.");
            return false;
        }
        if (index == 0) {
            start = start.getNext();
            return true;
        }
        Node prev = null;
        Node current = start;
        for (int i = 0; current != null && i < index; i++) {
            prev = current;
            current = current.getNext();
        }
        if (current == null) {
            System.out.println("Invalid index.");
            return false;
        }
        prev.setNext(current.getNext());
        return true;
    }

    public void add(Contact contact) {
        Node newNode = new Node(contact);
        if (isEmpty()) {
            start = newNode;
        } else {
            Node lastNode = start;
            while (lastNode.getNext() != null) {
                lastNode = lastNode.getNext();
            }
            lastNode.setNext(newNode);
        }
    }

    public void updatePhoneNumber(int index, String phoneNumber) {
        get(index).setPhoneNumber(phoneNumber);
    }

    public void updateSalary(int index, double salary) {
        get(index).setSalary(salary);
    }

    public void updateCompanyName(int index, String companyName) {
        get(index).setCompanyName(companyName);
    }

    public void updateName(int index, String name) {
        get(index).setName(name);
    }

    public void updateBirthday(int index, String birthday) {
        get(index).setBirthday(birthday);
    }

    public void sortingBySalary() {
        sortContacts((c1, c2) -> Double.compare(c1.getSalary(), c2.getSalary()));
    }

    public void sortingByBirthday() {
        sortContacts((c1, c2) -> c1.getBirthday().compareTo(c2.getBirthday()));
    }

    public static void sortingByName() {
        sortContacts((c1, c2) -> c1.getName().compareTo(c2.getName()));
    }

    private static void sortContacts(Comparator<Contact> comparator) {
        int size = getSize();
        if (size < 2)
            return;

        for (int i = 0; i < size - 1; i++) {
            Node current = start;
            Node next = start.getNext();
            for (int j = 0; j < size - 1 - i; j++) {
                if (comparator.compare(current.getContact(), next.getContact()) > 0) {
                    Contact temp = current.getContact();
                    current.setContact(next.getContact());
                    next.setContact(temp);
                }
                current = next;
                next = next.getNext();
            }
        }
    }

    public void delete(int index) {
        if (!isValidIndex(index)) {
            System.out.println("Invalid index.");
            return;
        }
        if (index == 0) {
            start = start.next;
            return;
        }
        Node prev = null;
        Node current = start;
        for (int i = 0; current != null && i < index; i++) {
            prev = current;
            current = current.getNext();
        }
        if (current == null) {
            System.out.println("Invalid index.");
            return;
        }
        prev.next = current.next;
    }
}

// ================================================================

// ============== CONTACTS CONTROLLER CLASS =======================
class ContactsController {
    private static List<Contact> contacts = new ArrayList<>();
    private static int currentId = 1;
    public static Object contactList;

    public static String generateId() {
        return String.format("C%04d", currentId++);
    }

    public static boolean isValidPhone(String phone) {
        return phone.matches("^0[0-9]{9}$");
    }

    public static boolean isValidSalary(double salary) {
        return salary >= 0;
    }

    public static boolean isValidBirthday(String birthday) {
        return birthday.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }

    public static void addContact(Contact contact) {
        contacts.add(contact);
    }

    public static Contact searchByNameOrPhoneNumber(String search) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(search) || contact.getPhoneNumber().equals(search)) {
                return contact;
            }
        }
        return null;
    }

    public static boolean deleteContact(String search) {
        Contact contact = searchByNameOrPhoneNumber(search);
        if (contact != null) {
            contacts.remove(contact);
            return true;
        }
        return false;
    }

    public static void updateContact(Contact updatedContact) {
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            if (contact.getId().equals(updatedContact.getId())) {
                contacts.set(i, updatedContact);
                break;
            }
        }
    }

    public static String listContactsByName() {
        contacts.sort(Comparator.comparing(Contact::getName));
        return formatContactList(contacts);
    }

    public static String listContactsByBirthday() {
        contacts.sort(Comparator.comparing(Contact::getBirthday));
        return formatContactList(contacts);
    }

    public static String listContactsBySalary() {
        contacts.sort(Comparator.comparingDouble(Contact::getSalary));
        return formatContactList(contacts);
    }

    private static String formatContactList(List<Contact> contacts) {
        StringBuilder sb = new StringBuilder();
        for (Contact contact : contacts) {
            sb.append(contact.toString()).append("\n");
        }
        return sb.toString();
    }

    public static ArrayList<Contact> getContactsSortedByName() {
        ArrayList<Contact> sortedList = new ArrayList<>(contacts);
        Collections.sort(sortedList, Comparator.comparing(Contact::getName));
        return sortedList;
    }

    public static ArrayList<Contact> getContactsSortedBySalary() {
        ArrayList<Contact> sortedList = new ArrayList<>(contacts);
        Collections.sort(sortedList, Comparator.comparingDouble(Contact::getSalary));
        return sortedList;
    }

    public static ArrayList<Contact> getContactsSortedByBirthday() {
        ArrayList<Contact> sortedList = new ArrayList<>(contacts);
        Collections.sort(sortedList, Comparator.comparing(Contact::getBirthday));
        return sortedList;
    }
}

// ==============================================================

// ============== DBCONNECTION CLASS ============================
class DBConnection {
    private ContactList contactList;
    private static DBConnection dbConnection;

    private DBConnection() {
        contactList = new ContactList();
    }

    public static DBConnection getInstance() {
        if (dbConnection == null) {
            dbConnection = new DBConnection();

        }
        return dbConnection;
    }

    public ContactList getContactList() {
        return contactList;
    }
}

// ==============================================================

// ============== DELETE CONTACTS CLASS ============================
class DeleteContacts extends JFrame {
    private JTextField txtSearch;

    public DeleteContacts() {
        setTitle("Delete Contacts");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(0, 0, 700, 300);
        panel1.setBackground(new Color(248, 205, 248));

        JLabel titleLabel = new JLabel("Delete Contacts", SwingConstants.CENTER);
        titleLabel.setBounds(0, 10, 700, 40);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 24));
        titleLabel.setForeground(new Color(71, 39, 71));
        panel1.add(titleLabel);

        JLabel lblSearch = new JLabel("Enter Name or Phone:");
        lblSearch.setBounds(50, 30, 200, 30);
        txtSearch = new JTextField(20);
        txtSearch.setBounds(250, 30, 200, 30);
        add(lblSearch);
        add(txtSearch);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(460, 30, 100, 30);
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchContact();
            }
        });
        add(btnSearch);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(340, 80, 100, 30);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteContact();
            }
        });
        add(btnDelete);

        JButton btnBack = new JButton("Back To Homepage");
        btnBack.setBounds(460, 80, 180, 30);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainWindow().setVisible(true);
                dispose();
            }
        });
        add(btnBack);
    }

    private void searchContact() {
        String search = txtSearch.getText();
        Contact contact = ContactsController.searchByNameOrPhoneNumber(search);
        if (contact != null) {
            String contactInfo = String.format(
                    "ID: %s\nName: %s\nPhone Number: %s\nCompany Name: %s\nSalary: %s\nBirthday: %s",
                    contact.getId(),
                    contact.getName(),
                    contact.getPhoneNumber(),
                    contact.getCompanyName(),
                    String.valueOf(contact.getSalary()),
                    contact.getBirthday());
            JOptionPane.showMessageDialog(this, contactInfo, "Contact Found", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteContact() {
        String search = txtSearch.getText();
        boolean isDeleted = ContactsController.deleteContact(search);
        if (isDeleted) {
            JOptionPane.showMessageDialog(this, "Contact deleted successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found!");
        }
    }
}

// ==============================================================

// ============== LIST BY BIRTHDAY CLASS ============================
class ListContactByBirthday extends JFrame {
    private JLabel titleLabel;

    private JTable contactsTable;
    private DefaultTableModel dtm;

    private JButton backToMenu;
    private JButton btnReload;

    public ListContactByBirthday() {
        setSize(800, 500);
        setTitle("List Contacts By Birthday");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        JPanel panel = new JPanel();

        JPanel panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBounds(30, 55, 800, 300);

        String[] columnNames = { "Id", "Name", "Phone Number", "Company Name", "Salary", "Birthday" };
        dtm = new DefaultTableModel(columnNames, 0);
        contactsTable = new JTable(dtm);

        JScrollPane tablePane = new JScrollPane(contactsTable);
        tablePane.setBounds(0, 30, 700, 300);
        panel2.add(tablePane);

        JButton btnReload = new JButton("Reload");
        btnReload.setBounds(220, 280, 100, 30);
        btnReload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reloadContacts();
            }
        });
        add(btnReload);

        JButton btnBack = new JButton("Back To Homepage");
        btnBack.setBounds(340, 280, 180, 30);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainWindow().setVisible(true);
                dispose();
            }
        });
        add(btnBack);

        add(panel);
        add(panel2);

        reloadContacts();
    }

    private void reloadContacts() {
        List<Contact> contactList = ContactsController.getContactsSortedByBirthday();
        dtm.setRowCount(0);
        for (Contact contact : contactList) {
            Object[] rowData = {
                    contact.getId(),
                    contact.getName(),
                    contact.getPhoneNumber(),
                    contact.getCompanyName(),
                    contact.getSalary(),
                    contact.getBirthday()
            };
            dtm.addRow(rowData);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ListContactByBirthday().setVisible(true);
            }
        });
    }
}

// ==============================================================

// ============== LIST BY NAME CLASS ============================

class ListContactByName extends JFrame {
    private JLabel titleLabel;

    private JTable contactsTable;
    private DefaultTableModel dtm;

    private JButton backToMenu;
    private JButton btnReload;

    public ListContactByName() {
        setSize(800, 500);
        setTitle("List Contacts By Name");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        JPanel panel = new JPanel();

        JPanel panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBounds(30, 55, 800, 300);

        String[] columnNames = { "Id", "Name", "Phone Number", "Company Name", "Salary", "Birthday" };
        dtm = new DefaultTableModel(columnNames, 0);
        contactsTable = new JTable(dtm);

        JScrollPane tablePane = new JScrollPane(contactsTable);
        tablePane.setBounds(0, 30, 700, 300);
        panel2.add(tablePane);

        JButton btnReload = new JButton("Reload");
        btnReload.setBounds(220, 280, 100, 30);
        btnReload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reloadContacts();
            }
        });
        add(btnReload);

        JButton btnBack = new JButton("Back To Homepage");
        btnBack.setBounds(340, 280, 180, 30);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainWindow().setVisible(true);
                dispose();
            }
        });
        add(btnBack);

        add(panel);
        add(panel2);

        reloadContacts();
    }

    private void reloadContacts() {
        List<Contact> contactList = ContactsController.getContactsSortedByName();
        dtm.setRowCount(0);
        for (Contact contact : contactList) {
            Object[] rowData = {
                    contact.getId(),
                    contact.getName(),
                    contact.getPhoneNumber(),
                    contact.getCompanyName(),
                    contact.getSalary(),
                    contact.getBirthday()
            };
            dtm.addRow(rowData);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ListContactByName().setVisible(true);
            }
        });
    }
}

// ==============================================================

// ============== LIST BY SALARY CLASS ============================
class ListContactBySalary extends JFrame {
    private JLabel titleLabel;

    private JTable contactsTable;
    private DefaultTableModel dtm;

    private JButton backToMenu;
    private JButton btnReload;

    public ListContactBySalary() {
        setSize(800, 500);
        setTitle("List Contacts By Salary");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        JPanel panel = new JPanel();

        JPanel panel2 = new JPanel();
        panel2.setLayout(null);
        panel2.setBounds(30, 55, 800, 300);

        String[] columnNames = { "Id", "Name", "Phone Number", "Company Name", "Salary", "Birthday" };
        dtm = new DefaultTableModel(columnNames, 0);
        contactsTable = new JTable(dtm);

        JScrollPane tablePane = new JScrollPane(contactsTable);
        tablePane.setBounds(0, 30, 700, 300);
        panel2.add(tablePane);

        JButton btnReload = new JButton("Reload");
        btnReload.setBounds(220, 280, 100, 30);
        btnReload.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reloadContacts();
            }
        });
        add(btnReload);

        JButton btnBack = new JButton("Back To Homepage");
        btnBack.setBounds(340, 280, 180, 30);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainWindow().setVisible(true);
                dispose();
            }
        });
        add(btnBack);

        add(panel);
        add(panel2);

        reloadContacts();
    }

    private void reloadContacts() {
        List<Contact> contactList = ContactsController.getContactsSortedBySalary();
        dtm.setRowCount(0);
        for (Contact contact : contactList) {
            Object[] rowData = {
                    contact.getId(),
                    contact.getName(),
                    contact.getPhoneNumber(),
                    contact.getCompanyName(),
                    contact.getSalary(),
                    contact.getBirthday()
            };
            dtm.addRow(rowData);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ListContactBySalary().setVisible(true);
            }
        });
    }
}

// ==============================================================

// ============== NODE CLASS ============================
class Node {
    private Contact contact;
    Node next;
    public Object contacts;

    public Node(Contact contact) {
        this.contact = contact;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }
}

// ==============================================================

// ============== SEARCH CONTACTS CLASS ============================
class SearchContacts extends JFrame {
    private JTextField txtSearch;

    public SearchContacts() {
        setTitle("Search Contacts");
        setSize(700, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 700, 300);
        panel.setBackground(new Color(2, 205, 248));

        JLabel lblSearch = new JLabel("Enter Name or Phone:");
        lblSearch.setBounds(50, 30, 200, 30);
        txtSearch = new JTextField(20);
        txtSearch.setBounds(250, 30, 200, 30);
        add(lblSearch);
        add(txtSearch);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(460, 30, 100, 30);
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchContact();
            }
        });
        add(btnSearch);

        JButton btnBack = new JButton("Back To Homepage");
        btnBack.setBounds(250, 80, 180, 30);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainWindow().setVisible(true);
                dispose();
            }
        });
        add(btnBack);
    }

    private void searchContact() {
        String search = txtSearch.getText();
        Contact contact = ContactsController.searchByNameOrPhoneNumber(search);
        if (contact != null) {
            String contactInfo = String.format(
                    "ID: %s\nName: %s\nPhone Number: %s\nCompany Name: %s\nSalary: %s\nBirthday: %s",
                    contact.getId(),
                    contact.getName(),
                    contact.getPhoneNumber(),
                    contact.getCompanyName(),
                    String.valueOf(contact.getSalary()),
                    contact.getBirthday());
            JOptionPane.showMessageDialog(this, contactInfo, "Contact Found", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
// ==============================================================

// ============== UPDATE CONTACTS CLASS ============================
class UpdateContacts extends JFrame {
    private JTextField txtSearch, txtId, txtName, txtPhone, txtCompany, txtSalary, txtBirthday;

    public UpdateContacts() {
        setTitle("Update Contact");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        JLabel lblSearch = new JLabel("Enter Name or Phone:");
        lblSearch.setBounds(50, 30, 200, 30);
        txtSearch = new JTextField(20);
        txtSearch.setBounds(250, 30, 200, 30);
        add(lblSearch);
        add(txtSearch);

        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(460, 30, 100, 30);
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchContact();
            }
        });
        add(btnSearch);

        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(50, 70, 200, 30);
        txtId = new JTextField(20);
        txtId.setBounds(250, 70, 200, 30);
        txtId.setEditable(false);
        add(lblId);
        add(txtId);

        JLabel lblName = new JLabel("Name:");
        lblName.setBounds(50, 110, 200, 30);
        txtName = new JTextField(20);
        txtName.setBounds(250, 110, 200, 30);
        add(lblName);
        add(txtName);

        JLabel lblPhone = new JLabel("Phone:");
        lblPhone.setBounds(50, 150, 200, 30);
        txtPhone = new JTextField(20);
        txtPhone.setBounds(250, 150, 200, 30);
        txtPhone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                validatePhoneNumber();
            }
        });
        add(lblPhone);
        add(txtPhone);

        JLabel lblCompany = new JLabel("Company:");
        lblCompany.setBounds(50, 190, 200, 30);
        txtCompany = new JTextField(20);
        txtCompany.setBounds(250, 190, 200, 30);
        add(lblCompany);
        add(txtCompany);

        JLabel lblSalary = new JLabel("Salary:");
        lblSalary.setBounds(50, 230, 200, 30);
        txtSalary = new JTextField(20);
        txtSalary.setBounds(250, 230, 200, 30);
        add(lblSalary);
        add(txtSalary);

        JLabel lblBirthday = new JLabel("Birthday:");
        lblBirthday.setBounds(50, 270, 200, 30);
        txtBirthday = new JTextField(20);
        txtBirthday.setBounds(250, 270, 200, 30);
        add(lblBirthday);
        add(txtBirthday);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(100, 320, 100, 30);
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateContact();
            }
        });
        add(btnUpdate);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBounds(220, 320, 100, 30);
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
        add(btnCancel);

        JButton btnBack = new JButton("Back To Homepage");
        btnBack.setBounds(340, 320, 180, 30);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainWindow().setVisible(true);
                dispose();
            }
        });
        add(btnBack);
    }

    private void validatePhoneNumber() {
        String phoneNumber = txtPhone.getText();
        boolean isValidPhone = ContactsController.isValidPhone(phoneNumber);
        if (!isValidPhone) {
            int option = JOptionPane.showConfirmDialog(this,
                    "Invalid Phone Number.... Do you want to input phone number again?");
            if (option == JOptionPane.YES_OPTION) {
                txtPhone.setText("");
                txtPhone.requestFocus();
            } else {
                clearFields();
                dispose();
            }
        }
    }

    private void searchContact() {
        String search = txtSearch.getText();
        Contact contact = ContactsController.searchByNameOrPhoneNumber(search);
        if (contact != null) {
            txtId.setText(contact.getId());
            txtName.setText(contact.getName());
            txtPhone.setText(contact.getPhoneNumber());
            txtCompany.setText(contact.getCompanyName());
            txtSalary.setText(String.valueOf(contact.getSalary()));
            txtBirthday.setText(contact.getBirthday());
        } else {
            JOptionPane.showMessageDialog(this, "Contact not found!");
        }
    }

    private void updateContact() {
        String id = txtId.getText();
        String name = txtName.getText();
        String phone = txtPhone.getText();
        String company = txtCompany.getText();
        double salary = Double.parseDouble(txtSalary.getText());
        String birthday = txtBirthday.getText();

        boolean isValidPhoneNumber = ContactsController.isValidPhone(phone);
        boolean isValidSalary = ContactsController.isValidSalary(salary);
        boolean isValidBirthday = ContactsController.isValidBirthday(birthday);

        if (!isValidPhoneNumber) {
            showErrorMessage("Invalid Phone Number");
            return;
        }

        if (!isValidSalary) {
            showErrorMessage("Invalid Salary");
            return;
        }

        if (!isValidBirthday) {
            showErrorMessage("Invalid Birthday");
            return;
        }

        Contact contact = new Contact(id, name, phone, company, salary, birthday);
        ContactsController.updateContact(contact);
        JOptionPane.showMessageDialog(this, "Contact updated successfully!");
        new MainWindow().setVisible(true);
        dispose();
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private void clearFields() {
        txtSearch.setText("");
        txtId.setText("");
        txtName.setText("");
        txtPhone.setText("");
        txtCompany.setText("");
        txtSalary.setText("");
        txtBirthday.setText("");
    }
}

// ==============================================================

// ============== VIEW CONTACTS CLASS ============================
class ViewContacts extends JFrame {
    public ViewContacts() {
        setTitle("View Contacts");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        JButton btnListByName = new JButton("List by Name");
        btnListByName.setBounds(50, 30, 200, 30);
        btnListByName.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ListContactByName().setVisible(true);
            }
        });
        add(btnListByName);

        JButton btnListByBirthday = new JButton("List by Birthday");
        btnListByBirthday.setBounds(50, 80, 200, 30);
        btnListByBirthday.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ListContactByBirthday().setVisible(true);
            }
        });
        add(btnListByBirthday);

        JButton btnListBySalary = new JButton("List by Salary");
        btnListBySalary.setBounds(50, 130, 200, 30);
        btnListBySalary.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ListContactBySalary().setVisible(true);
            }
        });
        add(btnListBySalary);

        JButton btnBack = new JButton("Back To Homepage");
        btnBack.setBounds(50, 180, 200, 30);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new MainWindow().setVisible(true);
                dispose();
            }
        });
        add(btnBack);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ViewContacts().setVisible(true);
            }
        });
    }
}

// ==============================================================

// ============== MAIN WINDOW CLASS ===========================
public class MainWindow extends JFrame {
    private JLabel titleLabel;
    private JButton btnAddContacts;
    private JButton btnUpdateContacts;
    private JButton btnSearchContacts;
    private JButton btnDeleteContacts;
    private JButton btnViewContacts;
    private JButton btnExit;
    private JLabel imageLabel;

    private AddContacts addContacts;
    private UpdateContacts updateContacts;
    private SearchContacts searchContacts;
    private DeleteContacts deleteContacts;
    private ViewContacts viewContacts;

    public MainWindow() {
        setSize(700, 500);
        setTitle("iFriend Contact Organizer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);

        JPanel panel1 = new JPanel();
        panel1.setLayout(null);
        panel1.setBounds(0, 0, 700, 500);
        panel1.setBackground(new Color(52, 91, 181));

        titleLabel = new JLabel("HOME PAGE", SwingConstants.CENTER);
        titleLabel.setBounds(200, 10, 300, 60);
        titleLabel.setFont(new Font("Monospaced", Font.BOLD, 44));
        titleLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        titleLabel.setForeground(new Color(5, 26, 27));
        panel1.add(titleLabel);

        imageLabel = new JLabel(new ImageIcon("img/pic2.jpg"));
        imageLabel.setBounds(320, 90, 350, 355);
        panel1.add(imageLabel);

        btnAddContacts = new JButton("Add New Contacts");
        btnAddContacts.setFont(new Font("DialogInput", Font.BOLD, 20));
        btnAddContacts.setBounds(50, 90, 250, 35);
        btnAddContacts.setBackground(new Color(248, 205, 248));
        btnAddContacts.setForeground(new Color(71, 39, 71));
        btnAddContacts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (addContacts == null) {
                    addContacts = new AddContacts();
                }
                addContacts.setVisible(true);
            }
        });
        panel1.add(btnAddContacts);

        btnUpdateContacts = new JButton("Update Contacts");
        btnUpdateContacts.setFont(new Font("DialogInput", Font.BOLD, 20));
        btnUpdateContacts.setBounds(50, 150, 250, 35);
        btnUpdateContacts.setBackground(new Color(248, 205, 248));
        btnUpdateContacts.setForeground(new Color(71, 39, 71));
        btnUpdateContacts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (updateContacts == null) {
                    updateContacts = new UpdateContacts();
                }
                updateContacts.setVisible(true);
            }
        });
        panel1.add(btnUpdateContacts);

        btnSearchContacts = new JButton("Search Contacts");
        btnSearchContacts.setFont(new Font("DialogInput", Font.BOLD, 20));
        btnSearchContacts.setBounds(50, 210, 250, 35);
        btnSearchContacts.setBackground(new Color(248, 205, 248));
        btnSearchContacts.setForeground(new Color(71, 39, 71));
        btnSearchContacts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (searchContacts == null) {
                    searchContacts = new SearchContacts();
                }
                searchContacts.setVisible(true);
            }
        });
        panel1.add(btnSearchContacts);

        btnDeleteContacts = new JButton("Delete Contacts");
        btnDeleteContacts.setFont(new Font("DialogInput", Font.BOLD, 20));
        btnDeleteContacts.setBounds(50, 270, 250, 35);
        btnDeleteContacts.setBackground(new Color(248, 205, 248));
        btnDeleteContacts.setForeground(new Color(71, 39, 71));
        btnDeleteContacts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (deleteContacts == null) {
                    deleteContacts = new DeleteContacts();
                }
                deleteContacts.setVisible(true);
            }
        });
        panel1.add(btnDeleteContacts);

        btnViewContacts = new JButton("View Contacts");
        btnViewContacts.setFont(new Font("DialogInput", Font.BOLD, 20));
        btnViewContacts.setBounds(50, 330, 250, 35);
        btnViewContacts.setBackground(new Color(248, 205, 248));
        btnViewContacts.setForeground(new Color(71, 39, 71));
        btnViewContacts.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (viewContacts == null) {
                    viewContacts = new ViewContacts();
                }
                viewContacts.setVisible(true);
            }
        });
        panel1.add(btnViewContacts);

        btnExit = new JButton("Exit");
        btnExit.setFont(new Font("DialogInput", Font.BOLD, 20));
        btnExit.setBounds(50, 390, 250, 35);
        btnExit.setBackground(new Color(248, 205, 248));
        btnExit.setForeground(new Color(71, 39, 71));
        btnExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        });
        panel1.add(btnExit);

        add(panel1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
}

// ====================== END =================================================
