package be.gauthier.alexandria.frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import be.gauthier.alexandria.pojos.User;

public class ModeratorFrame extends JFrame {

	private JPanel contentPane;
	User currUser;

	public ModeratorFrame(User u) {
		currUser=u;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 232, 170));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblOptionsDeModrateur = new JLabel("Options de Mod\u00E9rateur");
		lblOptionsDeModrateur.setBackground(new Color(238, 232, 170));
		lblOptionsDeModrateur.setForeground(new Color(160, 82, 45));
		lblOptionsDeModrateur.setFont(new Font("Papyrus", Font.PLAIN, 36));
		lblOptionsDeModrateur.setHorizontalAlignment(SwingConstants.CENTER);
		lblOptionsDeModrateur.setBounds(10, 11, 414, 40);
		contentPane.add(lblOptionsDeModrateur);
		
		JButton btnChangerLaValeur = new JButton("Changer la valeur d'un jeu");
		btnChangerLaValeur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChangeValueFrame c=new ChangeValueFrame(currUser);
				c.setVisible(true);
				dispose();
			}
		});
		btnChangerLaValeur.setBounds(10, 62, 414, 23);
		contentPane.add(btnChangerLaValeur);
		
		JButton btnAjouterUnJeu = new JButton("Ajouter un jeu");
		btnAjouterUnJeu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewGameFrame ng=new NewGameFrame(currUser);
				ng.setVisible(true);
				dispose();
			}
		});
		btnAjouterUnJeu.setBounds(10, 96, 414, 23);
		contentPane.add(btnAjouterUnJeu);
		
		JButton btnAjouterUneConsole = new JButton("Ajouter une console");
		btnAjouterUneConsole.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewConsoleFrame nc=new NewConsoleFrame(currUser);
				nc.setVisible(true);
				dispose();
			}
		});
		btnAjouterUneConsole.setBounds(10, 130, 414, 23);
		contentPane.add(btnAjouterUneConsole);
		
		JButton btnBack = new JButton("Retour");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomeFrame h=new HomeFrame(currUser);
				h.setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(10, 227, 89, 23);
		contentPane.add(btnBack);
	}

}
