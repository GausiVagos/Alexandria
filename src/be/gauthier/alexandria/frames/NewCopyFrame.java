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

public class NewCopyFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User currUser;
	GameDAO gdao;
	ConsoleDAO cdao;
	VersionDAO vdao;
	CopyDAO copyDao;
	UserDAO udao;
	DefaultListModel<String> listG;
	LinkedList<Integer> indexesG;
	JList listOfGames;
	JScrollPane scrollPaneG;
	
	DefaultListModel<String> listC;
	LinkedList<Integer> indexesC;
	JList listOfConsoles;
	JScrollPane scrollPaneC;
	
	public NewCopyFrame(User u) {
		currUser=u;
		gdao=new GameDAO();
		cdao=new ConsoleDAO();
		vdao=new VersionDAO();

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
		scrollPaneC.setBounds(424, 69, 250, 200);
		contentPane.add(scrollPaneC);
		
		JLabel lblAjoutDeCopie = new JLabel("Ajout de copie");
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
		
		JButton btnAdd = new JButton("Ajouter");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!listOfGames.isSelectionEmpty()&&!listOfConsoles.isSelectionEmpty())
				{
					int game=indexesG.get(listOfGames.getSelectedIndex());
					int console=indexesC.get(listOfConsoles.getSelectedIndex());
					Version v=vdao.find(game+"/"+console);
					if(v!=null)
					{
						v.ping();
						int choice=JOptionPane.showConfirmDialog(null, "Version trouvée : "+v.getGameObj().getGameTitle()+" / "+v.getConsoleObj().getShortName()+System.getProperty("line.separator")+"Voulez vous rendre cette copie déjà disponible?");					
						if(choice!=JOptionPane.CANCEL_OPTION)
						{
							copyDao=new CopyDAO();
							boolean av= choice==JOptionPane.YES_OPTION? true : false;
							Copy newCopy=new Copy(currUser.getUserId(),game,console, av);
							if(copyDao.create(newCopy))
							{
								//On recherche à nouveau le user pour que la nouvelle copie s'affiche (ainsi que son id)
								udao=new UserDAO();
								currUser=udao.find(currUser.getUserName());
								
								if(av)//Si la copie est mise disponible, on regarde si des réservations concernent cette version.
								{
									Reservation prior=Ptolemy.getPriorityReservation(vdao.find(game+"/"+console));
									if(prior!=null)//Si c'est le cas, Ptolemy renverra celle avec la priorité la plus élevée.
									{
										prior.ping();
										
										Copy complete=currUser.getListOfCopies().getLast();
										JOptionPane.showMessageDialog(null, "L'utilisateur "+prior.getApplicantObj().getUserName()+" a réservé cette version! Le prêt commence dès maintenant."+System.getProperty("line.separator")+"(Oui, nous vivons dans un monde utopique sans temps de trajet)");
										//Un nouveau prêt est créé, et certaines valeurs sont ajustées pour correspondre à cette situation.
										Loan l=new Loan(currUser.getUserId(),prior.getApplicant(),true,complete.getCopyId());
										LoanDAO ldao=new LoanDAO();
										ldao.create(l);
										
										complete.setAvailability(false);
										copyDao.update(complete);
										
										ReservationDAO rdao=new ReservationDAO();
										prior.setReservationStatus(3);
										rdao.update(prior);
										
										currUser=udao.find(currUser.getUserName());//On actualise encore pour avoir tous les prêts
									}
								}
								
								
								CopiesFrame c=new CopiesFrame(currUser);
								c.setVisible(true);
								dispose();
							}
							else
							{
								JOptionPane.showMessageDialog(null, "Création impossible, veuillez réessayer plus tard");
							}
						}
						
					}
				}
			}
		});
		btnAdd.setBackground(new Color(143, 188, 143));
		btnAdd.setBounds(270, 246, 144, 23);
		contentPane.add(btnAdd);
		
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
					/*String msg ="";
					for(Game g : Ptolemy.gamesFromVersions(vdao.findFromConsole(Integer.toString(indexesC.get(listOfConsoles.getSelectedIndex())))))
					{
						msg+=g.getGameTitle()+" ";
					}
					JOptionPane.showMessageDialog(null, msg);*/
				}
			}
		});
		btnFiltrer.setBounds(320, 101, 94, 23);
		contentPane.add(btnFiltrer);
		
		JButton button = new JButton("Retour");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CopiesFrame c=new CopiesFrame(currUser);
				c.setVisible(true);
				dispose();
			}
		});
		button.setBounds(10, 277, 89, 23);
		contentPane.add(button);
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
