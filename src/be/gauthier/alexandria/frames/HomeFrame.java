package be.gauthier.alexandria.frames;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import be.gauthier.alexandria.Ptolemy;
import be.gauthier.alexandria.dao.DAO;
import be.gauthier.alexandria.dao.UserDAO;
import be.gauthier.alexandria.pojos.User;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HomeFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User currUser;

	public HomeFrame(User u) {
		currUser=u;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(245, 245, 220));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblBienvenue = new JLabel("Bienvenue, "+currUser.getUserName());
		lblBienvenue.setBounds(10, 11, 279, 14);
		contentPane.add(lblBienvenue);
		
		JLabel grade = new JLabel(currUser.getUserRank());
		grade.setFont(new Font("Papyrus", Font.PLAIN, 12));
		grade.setBounds(10, 36, 137, 19);
		contentPane.add(grade);
		
		JButton btnDisconnect = new JButton("Se déconnecter");
		btnDisconnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ConnectionFrame c=new ConnectionFrame();
				c.setVisible(true);
				dispose();
			}
		});
		btnDisconnect.setBounds(10, 227, 126, 23);
		contentPane.add(btnDisconnect);
		
		JButton btnAccderVos = new JButton("Accéder à vos données personnelles");
		btnAccderVos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserFrame u =new UserFrame(currUser);
				u.setVisible(true);
				dispose();
			}
		});
		btnAccderVos.setBounds(245, 36, 279, 23);
		contentPane.add(btnAccderVos);
		
		JLabel lblCopies = new JLabel("Vous avez enregistré "+currUser.getListOfCopies().size()+" copie(s), dont "+Ptolemy.getAvailableCopies(currUser).size()+" disponible(s)");
		lblCopies.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblCopies.setBounds(10, 66, 321, 20);
		contentPane.add(lblCopies);
		
		JButton btnCopies = new JButton("Voir vos copies");
		btnCopies.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CopiesFrame c=new CopiesFrame(currUser);
				c.setVisible(true);
				dispose();
			}
		});
		btnCopies.setBounds(349, 66, 175, 23);
		contentPane.add(btnCopies);
		
		JLabel lblLends = new JLabel("Vous avez effectué "+currUser.getListOfLends().size()+" prêt(s), dont "+Ptolemy.getPendingLends(currUser).size()+" en cours");
		lblLends.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblLends.setBounds(10, 97, 279, 20);
		contentPane.add(lblLends);
		
		JButton btnLends = new JButton("Voir vos pr\u00EAts");
		btnLends.setBounds(349, 97, 175, 23);
		contentPane.add(btnLends);
		
		JLabel lblBor = new JLabel("Vous avez effectué "+currUser.getListOfBorrowings().size()+" emprunt(s), dont "+Ptolemy.getPendingBorrowings(currUser).size()+" en cours");
		lblBor.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblBor.setBounds(10, 128, 279, 20);
		contentPane.add(lblBor);
		
		JButton btnVoirVosEmprunts = new JButton("Voir vos emprunts");
		btnVoirVosEmprunts.setBounds(349, 128, 175, 23);
		contentPane.add(btnVoirVosEmprunts);
		
		JLabel lblReserv = new JLabel("Vous avez effectué "+currUser.getListOfReservations().size()+" réservation(s), dont "+Ptolemy.getWaitingReservations(currUser).size()+" en attente");
		lblReserv.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblReserv.setBounds(10, 159, 321, 20);
		contentPane.add(lblReserv);
		
		JButton btnReserv = new JButton("Voir vos r\u00E9servations");
		btnReserv.setBounds(349, 159, 175, 23);
		contentPane.add(btnReserv);
	}
}
