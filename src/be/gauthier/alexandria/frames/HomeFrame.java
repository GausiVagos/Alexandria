package be.gauthier.alexandria.frames;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import be.gauthier.alexandria.dao.DAO;
import be.gauthier.alexandria.dao.UserDAO;
import be.gauthier.alexandria.pojos.User;

public class HomeFrame extends JFrame {

	private JPanel contentPane;
	private User currUser;
	DAO<User> dao=new UserDAO();

	public HomeFrame(User u) {
		//Si on arrive ici depuis Register, l'objet User est incomplet(c'est la version pré-intégration à la db qui est retournée par Ptolemy.register())
		currUser= u.getUserId()!=0? u : dao.find(u.getUserName()); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBienvenue = new JLabel("Bienvenue, "+currUser.getUserName());
		lblBienvenue.setBounds(10, 11, 414, 14);
		contentPane.add(lblBienvenue);
	}

}
