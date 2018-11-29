package be.gauthier.alexandria.frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import be.gauthier.alexandria.dao.ReservationDAO;
import be.gauthier.alexandria.pojos.Reservation;
import be.gauthier.alexandria.pojos.User;

public class ReservationsFrame extends JFrame {

	private JPanel contentPane;
	private User currUser;
	private ReservationDAO rdao=new ReservationDAO();
	
	public ReservationsFrame(User u) {
		currUser=u;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 232, 170));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblReserv = new JLabel("Vos réservations");
		lblReserv.setHorizontalAlignment(SwingConstants.CENTER);
		lblReserv.setForeground(new Color(160, 82, 45));
		lblReserv.setFont(new Font("Papyrus", Font.PLAIN, 26));
		lblReserv.setBounds(151, 11, 275, 56);
		contentPane.add(lblReserv);
		
		JButton btnBack = new JButton("Retour");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				HomeFrame h=new HomeFrame(currUser);
				h.setVisible(true);
				dispose();
			}
		});
		btnBack.setBounds(10, 327, 89, 23);
		contentPane.add(btnBack);
		
		DefaultListModel<String> list=new DefaultListModel<>();
		LinkedList<Integer> indexes=new LinkedList<Integer>();
		for(Reservation r : currUser.getListOfReservations())
		{
			r.ping();
			String row="Id : "+r.getReservationId()+" / Jeu : "+r.getGameObj().getGameTitle()+" / Console : "+r.getConsoleObj().getShortName()+" / Date : "+r.getReservationDate()+" / "+r.getReservationStatus();
			list.addElement(row);
			indexes.add(r.getReservationId());
		}
		JList listOfReservations = new JList<>(list);
		
		JButton btnAdd = new JButton("Ajouter une réservation");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewReservationFrame nr=new NewReservationFrame(currUser);
				nr.setVisible(true);
				dispose();
			}
		});
		btnAdd.setBackground(new Color(144, 238, 144));
		btnAdd.setBounds(344, 222, 230, 25);
		contentPane.add(btnAdd);
		
		JScrollPane scrollPane = new JScrollPane(listOfReservations);
		scrollPane.setBounds(10, 61, 564, 150);
		contentPane.add(scrollPane);
		
		JButton btnRemove = new JButton("Supprimer la r\u00E9servation");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!listOfReservations.isSelectionEmpty()&&JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer cette réservation?", "Suppression", JOptionPane.OK_CANCEL_OPTION)==0)
				{
					DefaultListModel model = (DefaultListModel) listOfReservations.getModel();
					int pos=listOfReservations.getSelectedIndex();
					int id=indexes.get(pos);
					Reservation reserv=rdao.find(Integer.toString(id));
					if(reserv!=null)
					{
						currUser.removeReservation(reserv);
						if(rdao.delete(reserv))
						{
							model.remove(pos);
							indexes.remove(pos);
							JOptionPane.showMessageDialog(null, "Réservation numéro "+id+" supprimée.");
						}
						else
							JOptionPane.showMessageDialog(null, "Suppression impossible. Veuillez recommencer ultérieurement.");
					}
				}
			}
		});
		btnRemove.setBackground(new Color(165, 42, 42));
		btnRemove.setBounds(10, 222, 230, 25);
		contentPane.add(btnRemove);
	}
}
