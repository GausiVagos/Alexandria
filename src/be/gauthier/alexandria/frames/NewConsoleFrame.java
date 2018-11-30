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

import be.gauthier.alexandria.dao.ConsoleDAO;
import be.gauthier.alexandria.pojos.Console;
import be.gauthier.alexandria.pojos.User;

public class NewConsoleFrame extends JFrame {

	User currUser;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField name;
	private JTextField comp;
	private JTextField sh;

	public NewConsoleFrame(User u) {
		currUser=u;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 358, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNouveauJeu = new JLabel("Nouvelle console");
		lblNouveauJeu.setForeground(new Color(160, 82, 45));
		lblNouveauJeu.setHorizontalAlignment(SwingConstants.CENTER);
		lblNouveauJeu.setFont(new Font("Papyrus", Font.PLAIN, 24));
		lblNouveauJeu.setBounds(10, 11, 322, 39);
		contentPane.add(lblNouveauJeu);
		
		JLabel lblName = new JLabel("Nom complet");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblName.setBounds(10, 79, 100, 25);
		contentPane.add(lblName);
		
		JLabel lblComp = new JLabel("Entreprise");
		lblComp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblComp.setBounds(10, 115, 100, 25);
		contentPane.add(lblComp);
		
		JLabel lblShort = new JLabel("Nom court");
		lblShort.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblShort.setBounds(10, 151, 100, 25);
		contentPane.add(lblShort);
		
		JButton btnAjouter = new JButton("Ajouter");
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String n=name.getText();
				String c=comp.getText();
				String s=sh.getText();
				if(!n.isEmpty()&&!c.isEmpty()&&!s.isEmpty())
				{
					try
					{
						Console con=new Console(n,c,s);
						ConsoleDAO cdao=new ConsoleDAO();
						cdao.create(con);
						ModeratorFrame mf=new ModeratorFrame(currUser);
						mf.setVisible(true);
						dispose();
					}
					catch(Exception ex) {}
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
		
		name = new JTextField();
		name.setColumns(10);
		name.setBounds(120, 81, 200, 25);
		contentPane.add(name);
		
		comp = new JTextField();
		comp.setColumns(10);
		comp.setBounds(120, 115, 200, 25);
		contentPane.add(comp);
		
		sh = new JTextField();
		sh.setColumns(10);
		sh.setBounds(120, 151, 200, 25);
		contentPane.add(sh);
	}

}
