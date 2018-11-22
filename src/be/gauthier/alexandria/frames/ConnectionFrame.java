package be.gauthier.alexandria.frames;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import be.gauthier.alexandria.Ptolemy;
import be.gauthier.alexandria.dao.DAO;
import be.gauthier.alexandria.dao.UserDAO;
import be.gauthier.alexandria.pojos.User;

public class ConnectionFrame extends JFrame {

	private JPanel contentPane;
	private JPasswordField pass;
	private JTextField log;
	int attempts=5;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectionFrame frame = new ConnectionFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void securityCheck()
	{
		attempts--;
		if(attempts<=0)
		{
			JOptionPane.showMessageDialog(null,"Nombre d'essais maximum atteint. Get Rekt.");
			System.exit(1);
		}
		else
		{
			JOptionPane.showMessageDialog(null,"Mauvaise combinaison login/mot de passe. "+attempts+" tentatives restantes.");
		}
	}

	/**
	 * Create the frame.
	 */
	public ConnectionFrame() {
		DAO<User> dao=new UserDAO();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 222, 173));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		
		JLabel lblBienvenueDansLa = new JLabel("Bienvenue dans la Ludoth\u00E8que d'Alexandrie!");
		lblBienvenueDansLa.setFont(new Font("Papyrus", Font.PLAIN, 20));
		lblBienvenueDansLa.setBackground(new Color(0, 0, 0));
		lblBienvenueDansLa.setForeground(new Color(139, 69, 19));
		lblBienvenueDansLa.setBounds(10, 11, 414, 29);
		contentPane.add(lblBienvenueDansLa);
		
		JLabel lblVotrePseudonymeOu = new JLabel("Votre pseudonyme (ou id)");
		lblVotrePseudonymeOu.setBounds(10, 75, 163, 17);
		contentPane.add(lblVotrePseudonymeOu);
		
		JLabel lblVotreMotDe = new JLabel("Votre mot de passe");
		lblVotreMotDe.setBounds(10, 103, 130, 20);
		contentPane.add(lblVotreMotDe);
		
		pass = new JPasswordField();
		pass.setBounds(261, 103, 163, 20);
		contentPane.add(pass);
		
		log = new JTextField();
		log.setBounds(261, 72, 163, 20);
		contentPane.add(log);
		log.setColumns(10);
		
		JButton btnConnect = new JButton("Se connecter");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String login=log.getText().toString();
				String password=pass.getText().toString();
				if(!login.isEmpty()&&!password.isEmpty())
				{
					User connected=Ptolemy.login(login, password);
					if(connected!=null)
					{
						HomeFrame h=new HomeFrame(connected);
						h.setVisible(true);
						dispose();
					}
					securityCheck();
				}
			}
		});
		btnConnect.setBackground(new Color(60, 179, 113));
		btnConnect.setForeground(new Color(47, 79, 79));
		btnConnect.setBounds(261, 134, 163, 23);
		contentPane.add(btnConnect);
		
		JButton btnIn = new JButton("S'inscrire");
		btnIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegisterFrame r=new RegisterFrame();
				r.setVisible(true);
				dispose();
			}
		});
		btnIn.setBackground(new Color(192, 192, 192));
		btnIn.setBounds(10, 227, 130, 23);
		contentPane.add(btnIn);
		
		JLabel lblNonveau = new JLabel("Pas encore inscrit?");
		lblNonveau.setBounds(10, 202, 130, 14);
		contentPane.add(lblNonveau);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, new Color(210, 105, 30), new Color(210, 105, 30), new Color(160, 82, 45), new Color(160, 82, 45)));
		panel.setBackground(new Color(222, 184, 135));
		panel.setBounds(246, 191, 178, 59);
		contentPane.add(panel);
		
		JButton btnLogs = new JButton("Obtenir les logins");
		panel.add(btnLogs);
		btnLogs.setBackground(new Color(169, 169, 169));
		btnLogs.setForeground(new Color(210, 105, 30));
		
		JLabel lblALattentionDes = new JLabel("(A l'attention des testeurs)");
		panel.add(lblALattentionDes);
	}
}
