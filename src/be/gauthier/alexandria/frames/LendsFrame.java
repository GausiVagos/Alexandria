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

import be.gauthier.alexandria.Ptolemy;
import be.gauthier.alexandria.dao.CopyDAO;
import be.gauthier.alexandria.dao.LoanDAO;
import be.gauthier.alexandria.dao.ReservationDAO;
import be.gauthier.alexandria.dao.UserDAO;
import be.gauthier.alexandria.dao.VersionDAO;
import be.gauthier.alexandria.pojos.Copy;
import be.gauthier.alexandria.pojos.Loan;
import be.gauthier.alexandria.pojos.Reservation;
import be.gauthier.alexandria.pojos.User;

public class LendsFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	User currUser;
	LoanDAO ldao;

	public LendsFrame(User u) {
		currUser=u;
		ldao=new LoanDAO();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 232, 170));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblLends = new JLabel("Vos pr\u00EAts");
		lblLends.setHorizontalAlignment(SwingConstants.CENTER);
		lblLends.setForeground(new Color(160, 82, 45));
		lblLends.setFont(new Font("Papyrus", Font.PLAIN, 26));
		lblLends.setBounds(137, 11, 275, 56);
		contentPane.add(lblLends);
		
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
		for(Loan l : currUser.getListOfLends())
		{
			l.ping();
			Copy c=l.getGameCopyObj();
			c.ping();
			String row="Id : "+l.getLoanId()+" / Jeu : "+c.getGameObj().getGameTitle()+" / Console : "+c.getConsoleObj().getShortName()+" / Emprunteur : "+l.getBorrowerObj().getUserName()+" / Date de début : "+l.getStartDate()+" / ";
			row+= !l.getPending()? "Terminé" : l.getCopyState()? "Copie rendue" : "En cours";
			list.addElement(row);
			indexes.add(l.getLoanId());
		}
		JList listOfLends = new JList<>(list);
		
		JScrollPane scrollPane = new JScrollPane(listOfLends);
		scrollPane.setBounds(10, 61, 564, 219);
		contentPane.add(scrollPane);
		
		JButton btnRemove = new JButton("Supprimer le pr\u00EAt");
		btnRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!listOfLends.isSelectionEmpty()&&JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer ce prêt?", "Suppression", JOptionPane.OK_CANCEL_OPTION)==0)
				{
					DefaultListModel model = (DefaultListModel) listOfLends.getModel();
					int pos=listOfLends.getSelectedIndex();
					int id=indexes.get(pos);
					Loan loan=ldao.find(Integer.toString(id));
					if(loan.getPending())
					{
						JOptionPane.showMessageDialog(null, "Impossible de supprimer l'emprunt "+id+" tant qu'il est encore en cours.");
					}
					else if(loan!=null)
					{
						currUser.removeBorrowing(loan);
						if(ldao.delete(loan))
						{
							model.remove(pos);
							indexes.remove(pos);
							JOptionPane.showMessageDialog(null, "Prêt numéro "+id+" supprimé.");
						}
						else
							JOptionPane.showMessageDialog(null, "Suppression impossible. Veuillez recommencer ultérieurement.");
						UserDAO udao=new UserDAO();
						currUser=udao.find(currUser.getUserName());
					}
				}
			}
		});
		btnRemove.setBackground(new Color(165, 42, 42));
		btnRemove.setBounds(344, 291, 230, 25);
		contentPane.add(btnRemove);
		
		JButton btnEnd = new JButton("Mettre fin au prêt");
		btnEnd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!listOfLends.isSelectionEmpty())
				{
					DefaultListModel model = (DefaultListModel) listOfLends.getModel();
					int pos=listOfLends.getSelectedIndex();
					int id=indexes.get(pos);
					Loan loan=ldao.find(Integer.toString(id));
					if(JOptionPane.showConfirmDialog(null, "Confirmez vous avoir récupéré cette copie?", "Fin d'emprunt", JOptionPane.OK_CANCEL_OPTION)==0)
					{
						loan.setPending(false);
						loan.setCopyState(true);
						ldao.update(loan);
						loan.ping();
						Copy c=loan.getGameCopyObj();
						c.ping();
						String row="Id : "+loan.getLoanId()+" / Jeu : "+c.getGameObj().getGameTitle()+" / Console : "+c.getConsoleObj().getShortName()+" / Propriétaire : "+c.getOwnerObj().getUserName()+" / Date de début : "+loan.getStartDate()+" / ";
						row+= !loan.getPending()? "Terminé" : loan.getCopyState()? "Copie rendue" : "En cours";
						model.set(pos, row);
						
						if(JOptionPane.showConfirmDialog(null, "Souhaitez-vous rendre cette copie à nouveau disponible?", "Remise à disposition", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
						{
							VersionDAO vdao=new VersionDAO();
							Reservation prior=Ptolemy.getPriorityReservation(vdao.find(c.getGame()+"/"+c.getConsole()));
							if(prior!=null)
							{
								prior.ping();
								
								JOptionPane.showMessageDialog(null, "L'utilisateur "+prior.getApplicantObj().getUserName()+" a réservé cette version! Le prêt commence dès maintenant."+System.getProperty("line.separator")+"(Oui, nous vivons dans un monde utopique sans temps de trajet)");
								Loan l=new Loan(currUser.getUserId(),prior.getApplicant(),true,c.getCopyId());
								LoanDAO ldao=new LoanDAO();
								ldao.create(l);
								
								ReservationDAO rdao=new ReservationDAO();
								prior.setReservationStatus(3);
								rdao.update(prior);
							}
							else
							{
								c.setAvailability(true);
							}
							CopyDAO cdao=new CopyDAO();
							cdao.update(c);
							UserDAO udao=new UserDAO();
							currUser=udao.find(currUser.getUserName());
						}
					}
				}
			}
		});
		btnEnd.setBackground(Color.ORANGE);
		btnEnd.setBounds(10, 291, 230, 25);
		contentPane.add(btnEnd);
	}

}
