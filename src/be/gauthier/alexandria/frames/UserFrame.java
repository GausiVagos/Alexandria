package be.gauthier.alexandria.frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import be.gauthier.alexandria.Ptolemy;
import be.gauthier.alexandria.dao.UserDAO;
import be.gauthier.alexandria.pojos.User;

public class UserFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	User currUser;
	UserDAO udao;

	private JPanel contentPane;

	public UserFrame(User u) 
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 232, 170));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		currUser=u;
		udao=new UserDAO();
		
		JLabel lblVosInformations = new JLabel("Vos informations");
		lblVosInformations.setFont(new Font("Papyrus", Font.PLAIN, 18));
		lblVosInformations.setForeground(new Color(139, 69, 19));
		lblVosInformations.setHorizontalAlignment(SwingConstants.CENTER);
		lblVosInformations.setBounds(198, 11, 178, 29);
		contentPane.add(lblVosInformations);
		
		JLabel lblId = new JLabel("Votre identifiant :");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblId.setBounds(10, 48, 125, 20);
		contentPane.add(lblId);
		
		JLabel lblUs = new JLabel("Votre pseudonyme :");
		lblUs.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUs.setBounds(10, 79, 140, 20);
		contentPane.add(lblUs);
		
		JLabel lblAge = new JLabel("Votre age :");
		lblAge.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAge.setBounds(10, 145, 89, 20);
		contentPane.add(lblAge);
		
		JLabel lblVotreGrade = new JLabel("Votre grade :");
		lblVotreGrade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblVotreGrade.setBounds(10, 176, 125, 20);
		contentPane.add(lblVotreGrade);
		
		JLabel lblVotreMotDe = new JLabel("Votre mot de passe :");
		lblVotreMotDe.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblVotreMotDe.setBounds(10, 114, 160, 20);
		contentPane.add(lblVotreMotDe);
		
		JLabel lblDate = new JLabel("Votre date d'inscription :");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDate.setBounds(10, 207, 178, 20);
		contentPane.add(lblDate);
		
		JLabel lblRessources = new JLabel("Ressources");
		lblRessources.setHorizontalAlignment(SwingConstants.CENTER);
		lblRessources.setForeground(new Color(139, 69, 19));
		lblRessources.setFont(new Font("Papyrus", Font.PLAIN, 18));
		lblRessources.setBounds(129, 263, 178, 29);
		contentPane.add(lblRessources);
		
		JLabel lblTokens = new JLabel("Tokens (monnaie fictive utilisée pour les emprunts) : ");
		lblTokens.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTokens.setBounds(10, 303, 356, 20);
		contentPane.add(lblTokens);
		
		JLabel lblGuar = new JLabel("Guarantie (argent r\u00E9el, caution des jeux que vous empruntez) : ");
		lblGuar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblGuar.setBounds(10, 334, 414, 20);
		contentPane.add(lblGuar);
		
		JLabel lblvousPouvez = new JLabel("(Vous pouvez \u00E0 tout moment retirer votre argent du site)");
		lblvousPouvez.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblvousPouvez.setBounds(10, 354, 414, 20);
		contentPane.add(lblvousPouvez);
		
		JLabel id = new JLabel(Integer.toString(currUser.getUserId()));
		id.setFont(new Font("Tahoma", Font.PLAIN, 12));
		id.setBounds(160, 51, 168, 20);
		contentPane.add(id);
		
		JLabel name = new JLabel(currUser.getUserName());
		name.setFont(new Font("Tahoma", Font.PLAIN, 12));
		name.setBounds(160, 79, 168, 20);
		contentPane.add(name);
		
		JLabel pass = new JLabel(currUser.getPassword());
		pass.setFont(new Font("Tahoma", Font.PLAIN, 12));
		pass.setBounds(160, 118, 168, 20);
		contentPane.add(pass);
		
		JLabel age = new JLabel(Integer.toString(currUser.getAge()));
		age.setFont(new Font("Tahoma", Font.PLAIN, 12));
		age.setBounds(160, 145, 168, 20);
		contentPane.add(age);
		
		JLabel rank = new JLabel(currUser.getUserRank());
		rank.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rank.setBounds(160, 176, 168, 20);
		contentPane.add(rank);
		
		JLabel date = new JLabel(currUser.getInscriptionDate().toString());
		date.setFont(new Font("Tahoma", Font.PLAIN, 12));
		date.setBounds(198, 207, 168, 20);
		contentPane.add(date);
		
		JLabel tokens = new JLabel(currUser.getUserTokens()+"C");
		tokens.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tokens.setBounds(406, 303, 168, 20);
		contentPane.add(tokens);
		
		JLabel guar = new JLabel(currUser.getGuarantee()+"€");
		guar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		guar.setBounds(406, 334, 168, 20);
		contentPane.add(guar);
		
		JButton btnPass = new JButton("Modifier la valeur");
		btnPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String newpass=JOptionPane.showInputDialog(null, "Entrez votre nouveau mot de passe :");
				String conf=JOptionPane.showInputDialog(null, "Confirmez :");
				if(newpass.equals(conf))
				{
					currUser.setPassword(newpass);
					udao.update(currUser);
					pass.setText(newpass);
					currUser=udao.find(currUser.getUserName());
				}
			}
		});
		btnPass.setBounds(396, 114, 178, 23);
		contentPane.add(btnPass);
		
		JButton btnAge = new JButton("Modifier la valeur");
		btnAge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input=JOptionPane.showInputDialog(null, "Entrez votre age :");
				try
				{
					int newage=Integer.parseInt(input);
					currUser.setAge(newage);
					udao.update(currUser);
					age.setText(input);
					currUser=udao.find(currUser.getUserName());
				}
				catch(NumberFormatException ex)
				{
					JOptionPane.showMessageDialog(null, "Valeur invalide.");
				}
			}
		});
		btnAge.setBounds(396, 145, 178, 23);
		contentPane.add(btnAge);
		
		JButton btnRank = new JButton("Modifier la valeur");
		btnRank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String input=JOptionPane.showInputDialog(null, "Quel grade souhaitez-vous atteindre? [U],[M],[A]");
				char r=input.toCharArray()[0];
				if(r=='a' || r=='A' || r=='m' || r=='M')
				{
					String answer=JOptionPane.showInputDialog(null, "Quel est le mot de passe de ce rang?");
					if(answer.equals(Ptolemy.getAnswer(r)))
						currUser.setUserRank(r);
				}
				else
				{
					currUser.setUserRank(r);
				}
				udao.update(currUser);
				rank.setText(currUser.getUserRank());
				currUser=udao.find(currUser.getUserName());
			}
		});
		btnRank.setBounds(396, 176, 178, 23);
		contentPane.add(btnRank);
		
		JButton btnBuyTokens = new JButton("Acheter des tokens");
		btnBuyTokens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String res=JOptionPane.showInputDialog("(Permettre d'acheter des tokens serait un moyen de rendre le site rentable)"+System.getProperty("line.separator")
						+ "Combien de crédits voulez vous ajouter sur votre compte?");
				try
				{
					int t=Integer.parseInt(res);
					currUser.setUserTokens(currUser.getUserTokens()+t);
					udao.update(currUser);
					currUser=udao.find(currUser.getUserName());//MAJ on récupère les nouvelles valeurs
					tokens.setText(Integer.toString(currUser.getUserTokens())+"C");
				}
				catch(NumberFormatException ex) {}
			}
		});
		btnBuyTokens.setBounds(374, 390, 200, 25);
		contentPane.add(btnBuyTokens);
		
		JButton btnGuar = new JButton("Modifier votre guarantie");
		btnGuar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String res=JOptionPane.showInputDialog("Entrez le montant à ajouter sur votre compte (négatif pour récupérer)");
				try
				{
					int g=Integer.parseInt(res);
					currUser.setGuarantee(currUser.getGuarantee()+g);
					udao.update(currUser);
					currUser=udao.find(currUser.getUserName());//MAJ on récupère les nouvelles valeurs
					guar.setText(Integer.toString(currUser.getGuarantee())+"€");
				}
				catch(NumberFormatException ex) {}
			}
		});
		btnGuar.setBounds(374, 426, 200, 25);
		contentPane.add(btnGuar);
		
		JButton btnBack = new JButton("Retour");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomeFrame h = new HomeFrame(currUser);
				h.setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(10, 426, 100, 25);
		contentPane.add(btnBack);
	}
}
