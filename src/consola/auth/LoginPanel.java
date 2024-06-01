package consola.auth;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class LoginPanel extends JPanel{

	public LoginPanel(ActionListener backListener) {
	
	setLayout(new BorderLayout());
    
    JLabel label = new JLabel("Pantalla de Inicio de Sesión");
    label.setHorizontalAlignment(SwingConstants.CENTER);
    add(label, BorderLayout.CENTER);

    JButton backButton = new JButton("Volver");
    backButton.addActionListener(backListener);

    JButton loginButton = new JButton("Autenticar");
    loginButton.addActionListener(e -> authenticateUser());

    add(loginButton, BorderLayout.NORTH);
    add(backButton, BorderLayout.SOUTH);
	
	}

	private void authenticateUser() {
	    boolean authenticated = Math.random() > 0.5; // Simula éxito o fracaso aleatorio
	    SwingUtilities.getWindowAncestor(this).dispose(); // Cierra el JFrame contenedor
	}

}
