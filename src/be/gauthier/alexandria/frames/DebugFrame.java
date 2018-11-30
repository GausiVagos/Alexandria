package be.gauthier.alexandria.frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import be.gauthier.alexandria.dao.UserDAO;
import be.gauthier.alexandria.pojos.User;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.Font;

public class DebugFrame extends JFrame {

	private JPanel contentPane;
	UserDAO udao;

	public DebugFrame() {
		udao=new UserDAO();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		DefaultListModel<String> list=new DefaultListModel<>();
		for(User u : udao.getAll())
		{
			String row="Id : "+u.getUserId()+" / UserName : "+u.getUserName()+" / Password : "+u.getPassword();
			list.addElement(row);
		}
		JList listOfUsers = new JList<>(list);
		
		JScrollPane scrollPane = new JScrollPane(listOfUsers);
		scrollPane.setBounds(10, 11, 414, 175);
		contentPane.add(scrollPane);
		
		JTextArea txtrconnectionframeResteOuverte = new JTextArea();
		txtrconnectionframeResteOuverte.setEditable(false);
		txtrconnectionframeResteOuverte.setFont(new Font("Lucida Fax", Font.PLAIN, 10));
		txtrconnectionframeResteOuverte.setLineWrap(true);
		txtrconnectionframeResteOuverte.setText("ConnectionFrame reste ouverte en m\u00EAme temps que cette page.\r\nVous pouvez donc garder cet onglet ouvert en arri\u00E8re-plan tout en utilisant \r\nles autres fonctions de l'application.");
		txtrconnectionframeResteOuverte.setBounds(10, 197, 414, 53);
		contentPane.add(txtrconnectionframeResteOuverte);
	}
}
