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

import be.gauthier.alexandria.Ptolemy;
import be.gauthier.alexandria.pojos.User;

public class AdminFrame extends JFrame {

	private JPanel contentPane;
	private JTextField modo;
	User currUser;

	public AdminFrame(User u) {
		currUser=u;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 200);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 222, 179));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblOptionsDadministrateur = new JLabel("Options d'administrateur");
		lblOptionsDadministrateur.setForeground(new Color(184, 134, 11));
		lblOptionsDadministrateur.setHorizontalAlignment(SwingConstants.CENTER);
		lblOptionsDadministrateur.setFont(new Font("Papyrus", Font.PLAIN, 34));
		lblOptionsDadministrateur.setBounds(10, 11, 414, 56);
		contentPane.add(lblOptionsDadministrateur);
		
		modo = new JTextField();
		modo.setBounds(10, 78, 200, 25);
		contentPane.add(modo);
		modo.setColumns(10);
		
		JButton btnChange = new JButton("Changer le MDP mod\u00E9rateur");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String mdp=modo.getText();
				if(!mdp.isEmpty())
				{
					Ptolemy.setAnswer(mdp);
				}
			}
		});
		btnChange.setBounds(220, 78, 204, 25);
		contentPane.add(btnChange);
		
		JButton button_1 = new JButton("Retour");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModeratorFrame m=new ModeratorFrame(currUser);
				m.setVisible(true);
				dispose();
			}
		});
		button_1.setBounds(10, 127, 89, 23);
		contentPane.add(button_1);
	}

}
