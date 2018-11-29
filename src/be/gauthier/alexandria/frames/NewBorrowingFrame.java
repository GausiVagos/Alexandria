package be.gauthier.alexandria.frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
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

import be.gauthier.alexandria.CatalogRow;
import be.gauthier.alexandria.Ptolemy;
import be.gauthier.alexandria.dao.CopyDAO;
import be.gauthier.alexandria.dao.LoanDAO;
import be.gauthier.alexandria.dao.UserDAO;
import be.gauthier.alexandria.pojos.Copy;
import be.gauthier.alexandria.pojos.Loan;
import be.gauthier.alexandria.pojos.User;
import be.gauthier.alexandria.pojos.Version;


//Cette frame est un catalogue des copies disponibles
public class NewBorrowingFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	User currUser;
	HashSet<CatalogRow> catalog;

	public NewBorrowingFrame(User u) 
	{
		currUser=u;
		catalog=Ptolemy.getCatalog();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(238, 232, 170));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblCatalogueDesVersions = new JLabel("Catalogue des versions actuellement disponibles");
		lblCatalogueDesVersions.setForeground(new Color(160, 82, 45));
		lblCatalogueDesVersions.setHorizontalAlignment(SwingConstants.CENTER);
		lblCatalogueDesVersions.setFont(new Font("Papyrus", Font.PLAIN, 24));
		lblCatalogueDesVersions.setBounds(10, 11, 564, 40);
		contentPane.add(lblCatalogueDesVersions);
		
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
		LinkedList<Version> indexes=new LinkedList<Version>();
		for(CatalogRow cr : catalog)
		{
			Version v=cr.getVersion();
			v.ping();
			String row="Jeu : "+v.getGameObj().getGameTitle()+" / Console : "+v.getConsoleObj().getConsoleName()+" / Quantité disponible : "+cr.getQuantity();
			list.addElement(row);
			indexes.add(cr.getVersion());
		}
		JList catalogList = new JList<>(list);
		
		JScrollPane scrollPane = new JScrollPane(catalogList);
		scrollPane.setBounds(10, 62, 564, 244);
		contentPane.add(scrollPane);
		
		JButton btnEmprunter = new JButton("Emprunter");
		btnEmprunter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!catalogList.isSelectionEmpty())
				{
					DefaultListModel model = (DefaultListModel) catalogList.getModel();
					int pos=catalogList.getSelectedIndex();
					Version v=indexes.get(pos);
					v.ping();
					if(JOptionPane.showConfirmDialog(null, "Etes-vous sûr de vouloir emprunter "+v.getGameObj().getGameTitle()+" sur "+v.getConsoleObj().getShortName()+"?", "Confirmation", JOptionPane.OK_CANCEL_OPTION)==0)
					{
						Copy leastBorrowed=Ptolemy.findLeastBorrowed(v);
						
						if(leastBorrowed!=null)
						{
							leastBorrowed.ping();
							JOptionPane.showMessageDialog(null, "L'utilisateur "+leastBorrowed.getOwnerObj().getUserName()+" met à votre disposition une copie de "+leastBorrowed.getGameObj().getGameTitle()+" sur "+leastBorrowed.getConsoleObj().getShortName()+" dès maintenant!"
									+System.getProperty("line.separator")+"(Oui, nous vivons dans une utopie sans temps de trajet)");
							
							Loan l=new Loan(leastBorrowed.getOwnerObj().getUserId(),currUser.getUserId(),true,leastBorrowed.getCopyId());
							LoanDAO ldao=new LoanDAO();
							ldao.create(l);
							
							leastBorrowed.setAvailability(false);
							CopyDAO copyDao=new CopyDAO();
							copyDao.update(leastBorrowed);
							
							UserDAO udao=new UserDAO();
							currUser=udao.find(currUser.getUserName());
						}
						
						BorrowingsFrame b=new BorrowingsFrame(currUser);
						b.setVisible(true);
						dispose();
					}
				}
			}
		});
		btnEmprunter.setBackground(new Color(107, 142, 35));
		btnEmprunter.setBounds(424, 327, 150, 23);
		contentPane.add(btnEmprunter);
	}
}
