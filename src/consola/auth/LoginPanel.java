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

public class LoginPanel extends JPanel{
	
	private JTextField usernameField;
    private JTextField passwordField;

	public LoginPanel(ActionListener backListener) {
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JTextField(20);

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

        JButton loginButton = new JButton("Autenticar");
        loginButton.addActionListener(e -> authenticateUser());

        JButton backButton = new JButton("Volver");
        backButton.addActionListener(backListener);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(loginButton, BorderLayout.NORTH);
        buttonPanel.add(backButton, BorderLayout.SOUTH);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

	private void authenticateUser() {
		String username = usernameField.getText();
        String password = passwordField.getText();
	    boolean authenticated = Math.random() > 0.5; // Simula éxito o fracaso aleatorio
	    SwingUtilities.getWindowAncestor(this).dispose(); // Cierra el JFrame contenedor
	}

}
