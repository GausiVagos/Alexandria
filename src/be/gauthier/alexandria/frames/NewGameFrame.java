package be.gauthier.alexandria.frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import be.gauthier.alexandria.dao.GameDAO;
import be.gauthier.alexandria.pojos.Game;
import be.gauthier.alexandria.pojos.User;

public class NewGameFrame extends JFrame {

	User currUser;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField title;
	private JTextField pub;
	private JTextField year;

	public NewGameFrame(User u) {
		currUser=u;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 358, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNouveauJeu = new JLabel("Nouveau jeu");
		lblNouveauJeu.setForeground(new Color(160, 82, 45));
		lblNouveauJeu.setHorizontalAlignment(SwingConstants.CENTER);
		lblNouveauJeu.setFont(new Font("Papyrus", Font.PLAIN, 24));
		lblNouveauJeu.setBounds(10, 11, 322, 39);
		contentPane.add(lblNouveauJeu);
		
		JLabel lblTitre = new JLabel("Titre");
		lblTitre.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTitre.setBounds(10, 79, 100, 25);
		contentPane.add(lblTitre);
		
		JLabel lblDveloppeur = new JLabel("D\u00E9veloppeur");
		lblDveloppeur.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDveloppeur.setBounds(10, 115, 100, 25);
		contentPane.add(lblDveloppeur);
		
		JLabel lblAnneDeSortie = new JLabel("Ann\u00E9e");
		lblAnneDeSortie.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAnneDeSortie.setBounds(10, 151, 100, 25);
		contentPane.add(lblAnneDeSortie);
		
		title = new JTextField();
		title.setBounds(132, 81, 200, 25);
		contentPane.add(title);
		title.setColumns(10);
		
		pub = new JTextField();
		pub.setColumns(10);
		pub.setBounds(132, 117, 200, 25);
		contentPane.add(pub);
		
		year = new JTextField();
		year.setColumns(10);
		year.setBounds(132, 153, 200, 25);
		contentPane.add(year);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String t=title.getText();
				String p=pub.getText();
				String y=year.getText();
				if(!t.isEmpty()&&!p.isEmpty()&&!y.isEmpty())
				{
					try
					{
						int year=Integer.parseInt(y);
						Game g=new Game(t,p,year);
						GameDAO gdao=new GameDAO();
						gdao.create(g);
						ModeratorFrame mf=new ModeratorFrame(currUser);
						mf.setVisible(true);
						dispose();
					}
					catch(NumberFormatException ex) {}
				}
			}
		});
		btnAjouter.setBounds(132, 227, 89, 23);
		contentPane.add(btnAjouter);
		
		JButton button_1 = new JButton("Retour");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModeratorFrame m=new ModeratorFrame(currUser);
				m.setVisible(true);
				dispose();
			}
		});
		button_1.setBounds(10, 227, 89, 23);
		contentPane.add(button_1);
	}

}
