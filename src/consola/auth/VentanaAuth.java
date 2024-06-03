package consola.auth;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import usuarios.Usuario;

public class VentanaAuth extends JFrame{
	
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private JPanel menu;
    private JPanel login;
    private JPanel create;
	
	public VentanaAuth() {
		setTitle("Autentificación");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout  = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        menu = new OpcionesPanel(e -> cardLayout.show(mainPanel, "LoginPanel"), e -> cardLayout.show(mainPanel, "CreatePanel"));
        
        login = new LoginPanel(e -> cardLayout.show(mainPanel, "OpcionesPanel"));
        
        create = new CreatePanel(e -> cardLayout.show(mainPanel, "OpcionesPanel"));
        
        mainPanel.add(menu, "OpcionesPanel");
        mainPanel.add(login, "LoginPanel");
        mainPanel.add(create, "CreatePanel");

        add(mainPanel);
        cardLayout.show(mainPanel, "OpcionesPanel");
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);
        
        setVisible(true);
        
	}

}
