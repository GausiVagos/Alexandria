package be.gauthier.alexandria.frames;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import be.gauthier.alexandria.pojos.User;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	User currUser;

	private JPanel contentPane;

	public UserFrame(User u) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 232, 170));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		currUser=u;
		
		JLabel lblVosInformations = new JLabel("Vos informations");
		lblVosInformations.setFont(new Font("Papyrus", Font.PLAIN, 18));
		lblVosInformations.setForeground(new Color(139, 69, 19));
		lblVosInformations.setHorizontalAlignment(SwingConstants.CENTER);
		lblVosInformations.setBounds(129, 11, 178, 29);
		contentPane.add(lblVosInformations);
		
		JLabel lblId = new JLabel("Votre identifiant :");
		lblId.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblId.setBounds(10, 48, 280, 20);
		contentPane.add(lblId);
		
		JLabel lblUs = new JLabel("Votre pseudonyme :");
		lblUs.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblUs.setBounds(10, 79, 280, 20);
		contentPane.add(lblUs);
		
		JLabel lblAge = new JLabel("Votre age :");
		lblAge.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAge.setBounds(10, 145, 280, 20);
		contentPane.add(lblAge);
		
		JButton btnPass = new JButton("Modifier la valeur");
		btnPass.setBounds(303, 110, 121, 23);
		contentPane.add(btnPass);
		
		JLabel lblVotreGrade = new JLabel("Votre grade :");
		lblVotreGrade.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblVotreGrade.setBounds(10, 176, 280, 20);
		contentPane.add(lblVotreGrade);
		
		JLabel lblVotreMotDe = new JLabel("Votre mot de passe :");
		lblVotreMotDe.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblVotreMotDe.setBounds(10, 114, 280, 20);
		contentPane.add(lblVotreMotDe);
		
		JButton btnAge = new JButton("Modifier la valeur");
		btnAge.setBounds(303, 145, 121, 23);
		contentPane.add(btnAge);
		
		JButton btnRank = new JButton("Modifier la valeur");
		btnRank.setBounds(303, 176, 121, 23);
		contentPane.add(btnRank);
		
		JLabel lblDate = new JLabel("Votre date d'inscription :");
		lblDate.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblDate.setBounds(10, 207, 280, 20);
		contentPane.add(lblDate);
		
		JLabel lblRessources = new JLabel("Ressources");
		lblRessources.setHorizontalAlignment(SwingConstants.CENTER);
		lblRessources.setForeground(new Color(139, 69, 19));
		lblRessources.setFont(new Font("Papyrus", Font.PLAIN, 18));
		lblRessources.setBounds(129, 263, 178, 29);
		contentPane.add(lblRessources);
		
		JLabel lblTokens = new JLabel("Tokens (monnaie fictive utilisée pour les emprunts) : "+currUser.getUserTokens()+"C");
		lblTokens.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTokens.setBounds(10, 303, 414, 20);
		contentPane.add(lblTokens);
		
		JLabel lblGuar = new JLabel("Guarantie (argent r\u00E9el, caution des jeux que vous empruntez) : "+currUser.getGuarantee()+"€");
		lblGuar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblGuar.setBounds(10, 334, 414, 20);
		contentPane.add(lblGuar);
		
		JLabel lblvousPouvez = new JLabel("(Vous pouvez \u00E0 tout moment retirer votre argent du site)");
		lblvousPouvez.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblvousPouvez.setBounds(10, 354, 414, 20);
		contentPane.add(lblvousPouvez);
		
		JButton btnBuyTokens = new JButton("Acheter des tokens");
		btnBuyTokens.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String res=JOptionPane.showInputDialog("(Permettre d'acheter des tokens serait un moyen de rendre le site rentable) \n"
						+ "Combien de crédits voulez vous ajouter sur votre compte?");
				try
				{
					int t=Integer.parseInt(res);
					currUser.setUserTokens(+t);
					
				}
				catch(NumberFormatException ex) {}
			}
		});
		btnBuyTokens.setBounds(10, 426, 200, 25);
		contentPane.add(btnBuyTokens);
		
		JButton btnGuar = new JButton("Modifier votre guarantie");
		btnGuar.setBounds(224, 426, 200, 25);
		contentPane.add(btnGuar);
	}
}
