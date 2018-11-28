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
import javax.swing.border.EmptyBorder;

import be.gauthier.alexandria.dao.CopyDAO;
import be.gauthier.alexandria.dao.DAO;
import be.gauthier.alexandria.dao.UserDAO;
import be.gauthier.alexandria.pojos.Copy;
import be.gauthier.alexandria.pojos.User;

public class CopiesFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	User currUser;

	public CopiesFrame(User u) {
		currUser=u;
		DAO<Copy> dao=new CopyDAO();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 232, 170));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblVosCopies = new JLabel("Vos copies");
		lblVosCopies.setForeground(new Color(160, 82, 45));
		lblVosCopies.setFont(new Font("Papyrus", Font.PLAIN, 26));
		lblVosCopies.setBounds(226, 11, 140, 56);
		contentPane.add(lblVosCopies);
		
		//On prépare les informations de toutes les copies de l'utilisateur
		DefaultListModel<String> list=new DefaultListModel<>();
		LinkedList<Integer> indexes=new LinkedList<Integer>();
		for(Copy c : currUser.getListOfCopies())
		{
			c.ping();
			String row="Id : "+c.getCopyId()+" / Jeu : "+c.getGameObj().getGameTitle()+" / Console : "+c.getConsoleObj().getShortName()+" / ";
			row+= c.getAvailability()? "Disponible" : "Indisponible";
			list.addElement(row);
			indexes.add(c.getCopyId());
		}
		JList listOfCopies = new JList<>(list);
		
		JButton btnDelete = new JButton("Supprimer la copie s\u00E9lectionn\u00E9e");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!listOfCopies.isSelectionEmpty()&&JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir supprimer cette copie?", "Suppression", JOptionPane.OK_CANCEL_OPTION)==0)
				{
					DefaultListModel model = (DefaultListModel) listOfCopies.getModel();
					int pos=listOfCopies.getSelectedIndex();
					int id=indexes.get(pos);
					Copy copy=dao.find(Integer.toString(id));
					if(copy!=null)
					{
						currUser.removeCopy(copy);
						if(dao.delete(copy))
						{
							model.remove(pos);
							indexes.remove(pos);
							JOptionPane.showMessageDialog(null, "Copie numéro "+id+" supprimée.");
						}
						else
							JOptionPane.showMessageDialog(null, "Suppression impossible. Veuillez recommencer ultérieurement.");
					}
				}
			}
		});
		btnDelete.setBackground(new Color(165, 42, 42));
		btnDelete.setBounds(10, 222, 230, 25);
		contentPane.add(btnDelete);
		
		JButton btnAdd = new JButton("Ajouter une copie");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewCopyFrame nc=new NewCopyFrame(currUser);
				nc.setVisible(true);
				dispose();
			}
		});
		btnAdd.setBackground(new Color(144, 238, 144));
		btnAdd.setBounds(344, 222, 230, 25);
		contentPane.add(btnAdd);
		
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
		
		JScrollPane scrollPane = new JScrollPane(listOfCopies);
		scrollPane.setBounds(10, 61, 564, 150);
		contentPane.add(scrollPane);
		
		JButton btnChange = new JButton("Changer la disponibilit\u00E9");
		btnChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!listOfCopies.isSelectionEmpty())
				{
					
					DefaultListModel model = (DefaultListModel) listOfCopies.getModel();
					int pos=listOfCopies.getSelectedIndex();
					int id=indexes.get(pos);
					Copy copy=dao.find(Integer.toString(id));
					String msg="Etes-vous sûr de vouloir rendre cette copie ";
					msg+= copy.getAvailability()? "indisponible?" : "disponible?";
					if(copy!=null)
					{
						if(JOptionPane.showConfirmDialog(null, msg, "Modification", JOptionPane.OK_CANCEL_OPTION)==0)
						{
							if(copy.getAvailability())
								copy.setAvailability(false);
							else
								copy.setAvailability(true);
							copy.ping();
							String row="Id : "+copy.getCopyId()+" / Jeu : "+copy.getGameObj().getGameTitle()+" / Console : "+copy.getConsoleObj().getShortName()+" / ";
							row+= copy.getAvailability()? "Disponible" : "Indisponible";
							model.set(pos, row);
							dao.update(copy);
							UserDAO udao=new UserDAO();
							currUser=udao.find(currUser.getUserName());
						}
					}
				}
			}
		});
		btnChange.setBackground(Color.ORANGE);
		btnChange.setBounds(10, 258, 230, 25);
		contentPane.add(btnChange);
		
		
	}
}
