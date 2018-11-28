package be.gauthier.alexandria.frames;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import be.gauthier.alexandria.Ptolemy;
import be.gauthier.alexandria.pojos.User;

public class RegisterFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField log;
	private JPasswordField pass;
	private JPasswordField conf;
	private JTextField age;

	public RegisterFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		ButtonGroup bgp=new ButtonGroup();
		
		JLabel lblVeuillezEntrezVos = new JLabel("Veuillez entrez vos informations :");
		lblVeuillezEntrezVos.setBounds(10, 11, 202, 14);
		contentPane.add(lblVeuillezEntrezVos);
		
		JLabel lblVotrePseudonyme = new JLabel("Votre pseudonyme");
		lblVotrePseudonyme.setBounds(10, 65, 128, 14);
		contentPane.add(lblVotrePseudonyme);
		
		JLabel lblVotreMotDe = new JLabel("Votre mot de passe");
		lblVotreMotDe.setBounds(10, 90, 159, 14);
		contentPane.add(lblVotreMotDe);
		
		JLabel lblConfirmationMotDe = new JLabel("Confirmation mot de passe");
		lblConfirmationMotDe.setBounds(10, 115, 214, 14);
		contentPane.add(lblConfirmationMotDe);
		
		log = new JTextField();
		log.setBounds(190, 62, 168, 20);
		contentPane.add(log);
		log.setColumns(10);
		
		pass = new JPasswordField();
		pass.setBounds(190, 87, 168, 20);
		contentPane.add(pass);
		
		conf = new JPasswordField();
		conf.setBounds(190, 112, 168, 20);
		contentPane.add(conf);
		
		JLabel lblVotregeActuel = new JLabel("Votre \u00E2ge actuel");
		lblVotregeActuel.setBounds(10, 140, 128, 14);
		contentPane.add(lblVotregeActuel);
		
		age = new JTextField();
		age.setBounds(190, 137, 86, 20);
		contentPane.add(age);
		age.setColumns(10);
		
		JLabel lblVotreGrade = new JLabel("Votre grade");
		lblVotreGrade.setBounds(10, 165, 86, 14);
		contentPane.add(lblVotreGrade);
		
		JRadioButton rdU = new JRadioButton("Utilisateur");
		rdU.setSelected(true);
		rdU.setBounds(190, 164, 109, 23);
		contentPane.add(rdU);
		bgp.add(rdU);
		
		JRadioButton rdM = new JRadioButton("Mod\u00E9rateur");
		rdM.setBounds(190, 190, 109, 23);
		contentPane.add(rdM);
		bgp.add(rdM);
		
		JRadioButton rdA = new JRadioButton("Administrateur");
		rdA.setBounds(190, 216, 109, 23);
		contentPane.add(rdA);
		bgp.add(rdA);
		
		JButton btnSinscrire = new JButton("S'inscrire");
		btnSinscrire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				String login=log.getText().toString();
				@SuppressWarnings("deprecation")
				String password=pass.getText().toString();
				@SuppressWarnings("deprecation")
				String confirm=conf.getText().toString();
				String sAge=age.getText().toString();
				if(!login.isEmpty()&&!password.isEmpty()&&!confirm.isEmpty()&&!sAge.isEmpty()&&password.equals(confirm))
				{
					try
					{
						int age=Integer.parseInt(sAge);
						if(age<=10)
						{
							JOptionPane.showMessageDialog(null,"Veuillez entrer un age valide, SVP.");
						}
						
						else
						{
							char rank = rdA.isSelected()? 'a' : rdM.isSelected()? 'm' : 'u';
							if(rank!='u')
							{
								String sRank= rank=='a'? "administrateur" : "modérateur";
								String res=JOptionPane.showInputDialog("Vous vous apprêtez à créér un "+sRank+", veuillez entrer le mot de passe correspondant à ce rang :");
								if(!res.equals(Ptolemy.getAnswer(rank)))
									rank='u';
								User newUser=Ptolemy.register(login, password, age, rank);
								if(newUser!=null)
								{
									HomeFrame h=new HomeFrame(newUser);
									h.setVisible(true);
									dispose();
								}
								else
								{
									JOptionPane.showMessageDialog(null, "Enregistrement impossible. Veuillez essayer avec un autre pseudonyme, SVP");
								}
							}
						}
					}
					catch(NumberFormatException ex)
					{
						JOptionPane.showMessageDialog(null,"Veuillez entrer une valeur numérique dans le champ Age.");
					}
				}			
			}
		});
		btnSinscrire.setForeground(new Color(34, 139, 34));
		btnSinscrire.setBounds(335, 190, 89, 23);
		contentPane.add(btnSinscrire);
		
		JButton btnRetour = new JButton("Retour");
		btnRetour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConnectionFrame c=new ConnectionFrame();
				c.setVisible(true);
				dispose();
			}
		});
		btnRetour.setBounds(10, 227, 89, 23);
		contentPane.add(btnRetour);
	}
}
