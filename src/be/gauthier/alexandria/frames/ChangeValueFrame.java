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

public class ChangeValueFrame extends JFrame {

	private JPanel contentPane;
	User currUser;
	VersionDAO vdao;
	GameDAO gdao;
	ConsoleDAO cdao;
	DefaultListModel<String> listG;
	LinkedList<Integer> indexesG;
	JList listOfGames;
	JScrollPane scrollPaneG;
	
	DefaultListModel<String> listC;
	LinkedList<Integer> indexesC;
	JList listOfConsoles;
	JScrollPane scrollPaneC;


	public ChangeValueFrame(User u) {
		currUser=u;
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
		
		JLabel lblAjoutDeCopie = new JLabel("Modification de valeur");
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
		
		JButton btnSetValue = new JButton("Changer valeur");
		btnSetValue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!listOfGames.isSelectionEmpty()&&!listOfConsoles.isSelectionEmpty())
				{
					int game=indexesG.get(listOfGames.getSelectedIndex());
					int console=indexesC.get(listOfConsoles.getSelectedIndex());
					Version v=vdao.find(game+"/"+console);
					if(v!=null)
					{
						v.ping();
						String input=JOptionPane.showInputDialog(v.getGameObj().getGameTitle()+" / "+v.getConsoleObj().getShortName()+" : "+v.getTokenValue()+System.getProperty("line.separator")+"Nouvelle valeur :");					
						try
						{
							int newValue=Integer.parseInt(input);
							v.setTokenValue(newValue);
							vdao.update(v);
						}
						catch(NumberFormatException ex) {}
					}
				}
			}
		});
		btnSetValue.setBackground(new Color(143, 188, 143));
		btnSetValue.setBounds(270, 246, 144, 23);
		contentPane.add(btnSetValue);
		
		JButton button_1 = new JButton("Retour");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ModeratorFrame m=new ModeratorFrame(currUser);
				m.setVisible(true);
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
