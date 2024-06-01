package consola.auth;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class OpcionesPanel extends JPanel{

	public OpcionesPanel(ActionListener loginListener, ActionListener createUserListener) {
		
		setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton loginButton = new JButton("Iniciar Sesión");
        JButton createUserButton = new JButton("Crear Usuario");
        loginButton.addActionListener(loginListener);
        createUserButton.addActionListener(createUserListener);

        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createUserButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        buttonPanel.add(loginButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre botones
        buttonPanel.add(createUserButton);

        add(buttonPanel, BorderLayout.CENTER);
		
        
        
	}
}
