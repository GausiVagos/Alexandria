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

import be.gauthier.alexandria.dao.LoanDAO;
import be.gauthier.alexandria.dao.UserDAO;
import be.gauthier.alexandria.pojos.Copy;
import be.gauthier.alexandria.pojos.Loan;
import be.gauthier.alexandria.pojos.Reservation;
import be.gauthier.alexandria.pojos.User;

public class BorrowingsFrame extends JFrame {

	User currUser;
	LoanDAO ldao;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;


	public BorrowingsFrame(User u) {
		currUser=u;
		ldao=new LoanDAO();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 232, 170));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblReserv = new JLabel("Vos emprunts");
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
		for(Loan l : currUser.getListOfBorrowings())
		{
			l.ping();
			Copy c=l.getGameCopyObj();
			c.ping();
			String row="Id : "+l.getLoanId()+" / Jeu : "+c.getGameObj().getGameTitle()+" / Console : "+c.getConsoleObj().getShortName()+" / Propriétaire : "+c.getOwnerObj().getUserName()+" / Date de début : "+l.getStartDate()+" / ";
			row+= !l.getPending()? "Terminé" : l.getCopyState()? "Copie rendue" : "En cours";
			list.addElement(row);
			indexes.add(l.getLoanId());
		}
		JList listOfBorrowings = new JList<>(list);
		
		JButton btnAdd = new JButton("Emprunter un jeu");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(currUser.getUserTokens()>0)
				{
					NewBorrowingFrame nr=new NewBorrowingFrame(currUser);
					nr.setVisible(true);
					dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Votre solde de tokens est négatif! Prêtez des jeux pour repasser dans le positif.");
				}
			}
		});
		btnAdd.setBackground(new Color(144, 238, 144));
		btnAdd.setBounds(344, 222, 230, 25);
		contentPane.add(btnAdd);
		
		JScrollPane scrollPane = new JScrollPane(listOfBorrowings);
		scrollPane.setBounds(10, 61, 564, 150);
		contentPane.add(scrollPane);
		
		JButton btnRemove = new JButton("Supprimer l'emprunt");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!listOfBorrowings.isSelectionEmpty()&&JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer cet emprunt?", "Suppression", JOptionPane.OK_CANCEL_OPTION)==0)
				{
					DefaultListModel model = (DefaultListModel) listOfBorrowings.getModel();
					int pos=listOfBorrowings.getSelectedIndex();
					int id=indexes.get(pos);
					Loan borr=ldao.find(Integer.toString(id));
					if(borr.getPending())
					{
						JOptionPane.showMessageDialog(null, "Impossible de supprimer l'emprunt "+id+" tant qu'il est encore en cours.");
					}
					else if(borr!=null)
					{
						currUser.removeBorrowing(borr);
						if(ldao.delete(borr))
						{
							model.remove(pos);
							indexes.remove(pos);
							JOptionPane.showMessageDialog(null, "Emprunt numéro "+id+" supprimé.");
						}
						else
							JOptionPane.showMessageDialog(null, "Suppression impossible. Veuillez recommencer ultérieurement.");
					}
				}
			}
		});
		btnRemove.setBackground(new Color(165, 42, 42));
		btnRemove.setBounds(10, 265, 230, 25);
		contentPane.add(btnRemove);
		
		JButton btnEnd = new JButton("Mettre fin à l'emprunt");
		btnEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!listOfBorrowings.isSelectionEmpty())
				{
					DefaultListModel model = (DefaultListModel) listOfBorrowings.getModel();
					int pos=listOfBorrowings.getSelectedIndex();
					int id=indexes.get(pos);
					Loan borr=ldao.find(Integer.toString(id));
					if(!borr.getCopyState()&&JOptionPane.showConfirmDialog(null, "Confirmez vous avoir rendu cette copie?", "Fin d'emprunt", JOptionPane.OK_CANCEL_OPTION)==0)
					{
						borr.setCopyState(true);
						ldao.update(borr);
						borr.ping();
						Copy c=borr.getGameCopyObj();
						c.ping();
						String row="Id : "+borr.getLoanId()+" / Jeu : "+c.getGameObj().getGameTitle()+" / Console : "+c.getConsoleObj().getShortName()+" / Propriétaire : "+c.getOwnerObj().getUserName()+" / Date de début : "+borr.getStartDate()+" / ";
						row+= !borr.getPending()? "Terminé" : borr.getCopyState()? "Copie rendue" : "En cours";
						model.set(pos, row);
						UserDAO udao=new UserDAO();
						currUser=udao.find(currUser.getUserName());
					}
				}
			}
		});
		btnEnd.setBackground(Color.ORANGE);
		btnEnd.setBounds(10, 222, 230, 25);
		contentPane.add(btnEnd);
	}
}
