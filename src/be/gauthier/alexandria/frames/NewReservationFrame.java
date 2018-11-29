package be.gauthier.alexandria.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
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
import be.gauthier.alexandria.dao.ConsoleDAO;
import be.gauthier.alexandria.dao.CopyDAO;
import be.gauthier.alexandria.dao.GameDAO;
import be.gauthier.alexandria.dao.LoanDAO;
import be.gauthier.alexandria.dao.ReservationDAO;
import be.gauthier.alexandria.dao.UserDAO;
import be.gauthier.alexandria.dao.VersionDAO;
import be.gauthier.alexandria.pojos.Console;
import be.gauthier.alexandria.pojos.Copy;
import be.gauthier.alexandria.pojos.Game;
import be.gauthier.alexandria.pojos.Loan;
import be.gauthier.alexandria.pojos.Reservation;
import be.gauthier.alexandria.pojos.User;
import be.gauthier.alexandria.pojos.Version;

public class NewReservationFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User currUser;
	private UserDAO udao;
	private ReservationDAO rdao;
	private VersionDAO vdao;
	private GameDAO gdao;
	private ConsoleDAO cdao;
	DefaultListModel<String> listG;
	LinkedList<Integer> indexesG;
	JList listOfGames;
	JScrollPane scrollPaneG;
	
	DefaultListModel<String> listC;
	LinkedList<Integer> indexesC;
	JList listOfConsoles;
	JScrollPane scrollPaneC;

	public NewReservationFrame(User u) {
		currUser=u;
		udao=new UserDAO();
		rdao=new ReservationDAO();
		vdao=new VersionDAO();
		gdao=new GameDAO();
		cdao=new ConsoleDAO();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 350);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(210, 180, 140));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		listG=new DefaultListModel<>();
		setListOfGames(gdao.getAll());
		listOfGames = new JList<>(listG);
		scrollPaneG = new JScrollPane(listOfGames);
		scrollPaneG.setBounds(10, 69, 250, 200);
		contentPane.add(scrollPaneG);
		
		listC=new DefaultListModel<>();
		setListOfConsoles(cdao.getAll());
		listOfConsoles = new JList<>(listC);
		scrollPaneC = new JScrollPane(listOfConsoles);
		scrollPaneC.setBounds(424, 67, 250, 200);
		contentPane.add(scrollPaneC);
		
		JLabel lblAjoutDeCopie = new JLabel("Ajout de r\u00E9servation");
		lblAjoutDeCopie.setHorizontalAlignment(SwingConstants.CENTER);
		lblAjoutDeCopie.setFont(new Font("Papyrus", Font.PLAIN, 42));
		lblAjoutDeCopie.setForeground(new Color(139, 69, 19));
		lblAjoutDeCopie.setBounds(139, 11, 406, 55);
		contentPane.add(lblAjoutDeCopie);
		
		JButton btnSearch = new JButton("Filtrer>>");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{
				if(!listOfGames.isSelectionEmpty())
				{
					setListOfConsoles(Ptolemy.consolesFromVersions(vdao.findFromGame(Integer.toString(indexesG.get(listOfGames.getSelectedIndex())))));
				}
					
			}
		});
		btnSearch.setBounds(270, 67, 94, 23);
		contentPane.add(btnSearch);
		
		JButton btnResetG = new JButton("Restaurer");
		btnResetG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setListOfGames(gdao.getAll());
			}
		});
		btnResetG.setBounds(10, 35, 119, 23);
		contentPane.add(btnResetG);
		
		JButton btnResetC = new JButton("Restaurer");
		btnResetC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setListOfConsoles(cdao.getAll());
			}
		});
		btnResetC.setBounds(555, 35, 119, 23);
		contentPane.add(btnResetC);
		
		JButton btnFiltrer = new JButton("<<Filtrer");
		btnFiltrer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!listOfConsoles.isSelectionEmpty())
				{
					setListOfGames(Ptolemy.gamesFromVersions(vdao.findFromConsole(Integer.toString(indexesC.get(listOfConsoles.getSelectedIndex())))));
				}
			}
		});
		btnFiltrer.setBounds(320, 101, 94, 23);
		contentPane.add(btnFiltrer);
		
		JButton button = new JButton("Ajouter");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!listOfGames.isSelectionEmpty()&&!listOfConsoles.isSelectionEmpty())
				{
					int game=indexesG.get(listOfGames.getSelectedIndex());
					int console=indexesC.get(listOfConsoles.getSelectedIndex());
					Version v=vdao.find(game+"/"+console);
					if(v!=null)
					{
						v.ping();
						int choice=JOptionPane.showConfirmDialog(null,"Version trouvée : "+v.getGameObj().getGameTitle()+" / "+v.getConsoleObj().getShortName()+System.getProperty("line.separator")+"Confirmez-vous cette réservation?","Confirmation",JOptionPane.OK_CANCEL_OPTION);					
						if(choice!=JOptionPane.CANCEL_OPTION)
						{
							//int ap, int ga, int co, int stat
							Reservation r=new Reservation(currUser.getUserId(), game, console, 1);
							if(rdao.create(r))
							{
								JOptionPane.showMessageDialog(null, "Réservation "+r.getReservationId()+" réussie");
								//Reservation complete=rdao.find(Integer.toString(r.getReservationId()));
								Copy leastBorrowed=Ptolemy.findLeastBorrowed(v);
								Reservation complete=rdao.find(Integer.toString(r.getReservationId()));
								complete.setReservationStatus(2);
								rdao.update(complete);
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
									
									currUser=udao.find(currUser.getUserName());
								}
								
								ReservationsFrame rf=new ReservationsFrame(currUser);
								rf.setVisible(true);
								dispose();
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Impossible de créer cette réservation, réessayez plus tard");
							}
						}
					}
				}
			}
		});
		button.setBackground(new Color(143, 188, 143));
		button.setBounds(270, 246, 144, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("Retour");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReservationsFrame r=new ReservationsFrame(currUser);
				r.setVisible(true);
				dispose();
			}
		});
		button_1.setBounds(10, 277, 89, 23);
		contentPane.add(button_1);
	}
	private void setListOfGames(LinkedList<Game> games)
	{
        listG.removeAllElements();
		indexesG=new LinkedList<Integer>();
		for(Game g : games)
		{
			String row="Id : "+g.getGameId()+" / Jeu : "+g.getGameTitle();
			listG.addElement(row);
			indexesG.add(g.getGameId());
		}
	}
	private void setListOfConsoles(LinkedList<Console> consoles)
	{
		listC.removeAllElements();
		indexesC=new LinkedList<Integer>();
		for(Console c : consoles)
		{
			String row="Id : "+c.getConsoleId()+" / Console : "+c.getShortName();
			listC.addElement(row);
			indexesC.add(c.getConsoleId());
		}		
	}
}


