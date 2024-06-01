package consola.auth;

import java.awt.CardLayout;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import usuarios.Usuario;

public class VentanaAuth extends JFrame{
	
	private static final String USERS_FILE = "datos/usuarios.json";
    private boolean autenticado = false;
    private Usuario usuario;
    private CardLayout cardLayout;
    private JPanel mainPanel;
	
	public VentanaAuth() {
		setTitle("Autentificación");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout  = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        JPanel menu = new OpcionesPanel(e -> cardLayout.show(mainPanel, "LoginPanel"), e -> cardLayout.show(mainPanel, "CreatePanel"));
        
        JPanel login = new LoginPanel(e -> cardLayout.show(mainPanel, "OpcionesPanel"));
        
        JPanel create = new CreatePanel(e -> cardLayout.show(mainPanel, "OpcionesPanel"));
        
        mainPanel.add(menu, "OpcionesPanel");
        mainPanel.add(login, "LoginPanel");
        mainPanel.add(create, "CreatePanel");

        add(mainPanel);
        cardLayout.show(mainPanel, "OpcionesPanel");
	}

}
