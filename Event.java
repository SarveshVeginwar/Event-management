import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class EventManagementApp extends JFrame {
private Connection conn;
private JTextField eventNameField;
private JTextField eventDateField;
private JTextField eventVenueField;
public EventManagementApp() {
super("Event Management System");
setSize(400, 400);
setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

connectToDatabase();

JLayeredPane layeredPane = new JLayeredPane();
getContentPane().add(layeredPane);
JLabel eventNameLabel = new JLabel("Event Name:");
eventNameField = new JTextField(20);
JLabel eventDateLabel = new JLabel("Event Date (YYYY-MM-DD):");
eventDateField = new JTextField(20);
JLabel eventVenueLabel = new JLabel("Event Venue:");
eventVenueField = new JTextField(20);

eventNameLabel.setBounds(20, 20, 150, 25);
eventNameField.setBounds(180, 20, 180, 25);
eventDateLabel.setBounds(20, 50, 150, 25);
eventDateField.setBounds(180, 50, 180, 25);
eventVenueLabel.setBounds(20, 80, 150, 25);
eventVenueField.setBounds(180, 80, 180, 25);

JButton addEventButton = new JButton("Add Event");
JButton deleteEventButton = new JButton("Delete Event");

addEventButton.setBounds(20, 130, 160, 30);
deleteEventButton.setBounds(200, 130, 160, 30);

addEventButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
String eventName = eventNameField.getText();
String eventDate = eventDateField.getText();
String eventVenue = eventVenueField.getText();
if (eventName.isEmpty() || eventDate.isEmpty() || eventVenue.isEmpty()) {
JOptionPane.showMessageDialog(EventManagementApp.this, "Please enter event name, date, and

venue.");
} else {
try {
PreparedStatement stmt = conn.prepareStatement("INSERT INTO events (name, date, venue) VALUES
(?, ?, ?)");
stmt.setString(1, eventName);
stmt.setString(2, eventDate);
stmt.setString(3, eventVenue);
int rowsAffected = stmt.executeUpdate();
if (rowsAffected > 0) {
JOptionPane.showMessageDialog(EventManagementApp.this, "Event added successfully!");
// Clear text fields after adding event
eventNameField.setText("");
eventDateField.setText("");
eventVenueField.setText("");
} else {
JOptionPane.showMessageDialog(EventManagementApp.this, "Failed to add event.");
}
} catch (SQLException ex) {
JOptionPane.showMessageDialog(EventManagementApp.this, "Error: " + ex.getMessage());
}
}
}
});
deleteEventButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
// Get event name from text field
String eventName = eventNameField.getText();
if (eventName.isEmpty()) {
JOptionPane.showMessageDialog(EventManagementApp.this, "Please enter event name to delete.");
} else {
try {
PreparedStatement stmt = conn.prepareStatement("DELETE FROM events WHERE name = ?");
stmt.setString(1, eventName);
int rowsAffected = stmt.executeUpdate();
if (rowsAffected > 0) {
JOptionPane.showMessageDialog(EventManagementApp.this, "Event deleted successfully!");
// Clear text field after deleting event
eventNameField.setText("");
} else {
JOptionPane.showMessageDialog(EventManagementApp.this, "Event not found.");
}
} catch (SQLException ex) {
JOptionPane.showMessageDialog(EventManagementApp.this, "Error: " + ex.getMessage());
}
}
}
});
// Add components to the layered pane
layeredPane.add(eventNameLabel, JLayeredPane.PALETTE_LAYER);
layeredPane.add(eventNameField, JLayeredPane.PALETTE_LAYER);
layeredPane.add(eventDateLabel, JLayeredPane.PALETTE_LAYER);
layeredPane.add(eventDateField, JLayeredPane.PALETTE_LAYER);
layeredPane.add(eventVenueLabel, JLayeredPane.PALETTE_LAYER);
layeredPane.add(eventVenueField, JLayeredPane.PALETTE_LAYER);
layeredPane.add(addEventButton, JLayeredPane.PALETTE_LAYER);
layeredPane.add(deleteEventButton, JLayeredPane.PALETTE_LAYER);

setVisible(true);
}
private void connectToDatabase() {
try {
// Provide database credentials
String url = "jdbc:mysql://localhost:3306/event_management";
String user = "root";
String password = "ganarush";
// Establish connection
conn = DriverManager.getConnection(url, user, password);
if (conn != null) {
System.out.println("Connected to the database!");
}
} catch (SQLException e) {
System.out.println("Connection failed!");
e.printStackTrace();
}
}
public static void main(String[] args) {
new EventManagementApp();
}
}