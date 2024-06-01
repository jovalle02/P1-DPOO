package consola.auth;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class CreatePanel extends JPanel{

	private JTextField usernameField;
    private JTextField passwordField;
    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField emailField;
    private JTextField maxPurchaseField;
    private JTextField birthDateField;

    public CreatePanel(ActionListener backListener) {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JTextField(20);
        JLabel firstNameLabel = new JLabel("First Name:");
        firstNameField = new JTextField(20);
        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameField = new JTextField(20);
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(20);
        JLabel maxPurchaseLabel = new JLabel("Max Purchase:");
        maxPurchaseField = new JTextField(20);
        JLabel birthDateLabel = new JLabel("Birth Date (dd/mm/yyyy):");
        birthDateField = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(firstNameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(firstNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(lastNameLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(lastNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        formPanel.add(emailLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        formPanel.add(maxPurchaseLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(maxPurchaseField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        formPanel.add(birthDateLabel, gbc);

        gbc.gridx = 1;
        formPanel.add(birthDateField, gbc);

        JButton createButton = new JButton("Crear Usuario");
        createButton.addActionListener(e -> createUser());

        JButton backButton = new JButton("Volver");
        backButton.addActionListener(backListener);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(createButton, BorderLayout.NORTH);
        buttonPanel.add(backButton, BorderLayout.SOUTH);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createUser() {
        // Obtener los valores ingresados por el usuario
        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String email = emailField.getText();
        double maxPurchase = Double.parseDouble(maxPurchaseField.getText());
        String birthDate = birthDateField.getText();
    }
}
