package consola.auth;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class CreatePanel extends JPanel{

	public CreatePanel(ActionListener backListener) {
		setLayout(new BorderLayout());
        
        JLabel label = new JLabel("Pantalla de Crear Cuenta");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        JButton backButton = new JButton("Volver");
        backButton.addActionListener(backListener);

        add(backButton, BorderLayout.SOUTH);
	
	
	}

}
