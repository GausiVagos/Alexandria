package be.gauthier.alexandria.frames;

import java.awt.Color;
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

import be.gauthier.alexandria.Ptolemy;
import be.gauthier.alexandria.dao.ConsoleDAO;
import be.gauthier.alexandria.dao.GameDAO;
import be.gauthier.alexandria.dao.VersionDAO;
import be.gauthier.alexandria.pojos.Console;
import be.gauthier.alexandria.pojos.Game;
import be.gauthier.alexandria.pojos.User;
import be.gauthier.alexandria.pojos.Version;

public class NewCopyFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private User currUser;
	GameDAO gdao;
	ConsoleDAO cdao;
	VersionDAO vdao;
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
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		listG=new DefaultListModel<>();
		setListOfGames(gdao.getAll());
		scrollPaneG = new JScrollPane(listOfGames);
		scrollPaneG.setBounds(10, 90, 250, 210);
		contentPane.add(scrollPaneG);
		
		listC=new DefaultListModel<>();
		setListOfConsoles(cdao.getAll());
		scrollPaneC = new JScrollPane(listOfConsoles);
		scrollPaneC.setBounds(424, 90, 250, 210);
		contentPane.add(scrollPaneC);
		
		JLabel lblAjoutDeCopie = new JLabel("Ajout de copie");
		lblAjoutDeCopie.setBounds(295, 15, 119, 14);
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
		btnSearch.setBounds(270, 88, 94, 23);
		contentPane.add(btnSearch);
		
		JButton btnAdd = new JButton("Ajouter");
		btnAdd.setBackground(new Color(143, 188, 143));
		btnAdd.setBounds(270, 277, 144, 23);
		contentPane.add(btnAdd);
		
		JButton btnResetG = new JButton("Restaurer");
		btnResetG.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setListOfGames(gdao.getAll());
			}
		});
		btnResetG.setBounds(10, 56, 119, 23);
		contentPane.add(btnResetG);
		
		JButton btnResetC = new JButton("Restaurer");
		btnResetC.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setListOfConsoles(cdao.getAll());
			}
		});
		btnResetC.setBounds(555, 56, 119, 23);
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
		btnFiltrer.setBounds(320, 122, 94, 23);
		contentPane.add(btnFiltrer);
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
		listOfGames = new JList<>(listG);
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
		listOfConsoles = new JList<>(listC);		
	}
}
