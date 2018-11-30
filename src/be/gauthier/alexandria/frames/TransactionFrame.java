package be.gauthier.alexandria.frames;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import be.gauthier.alexandria.Sarapis;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TransactionFrame extends JFrame {

	private JPanel contentPane;
	private JTextField semaines;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransactionFrame frame = new TransactionFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TransactionFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 150);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 230, 140));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEcranDesTransactions = new JLabel("Ecran des transactions");
		lblEcranDesTransactions.setHorizontalAlignment(SwingConstants.CENTER);
		lblEcranDesTransactions.setForeground(new Color(160, 82, 45));
		lblEcranDesTransactions.setFont(new Font("Papyrus", Font.PLAIN, 24));
		lblEcranDesTransactions.setBounds(10, 11, 414, 32);
		contentPane.add(lblEcranDesTransactions);
		
		JButton btnGo = new JButton("Effectuer les transactions");
		btnGo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sem=semaines.getText();
				if(sem.isEmpty()&&Sarapis.start())
					JOptionPane.showMessageDialog(null, "Transactions terminées.");
				else
				{
					try
					{
						int s=Integer.parseInt(sem);
						if(s>0)
						{
							for(int i=0;i<s;i++)
							{
								Sarapis.start();
							}
							JOptionPane.showMessageDialog(null, "Transactions terminées.");
						}
						else
						{
							JOptionPane.showMessageDialog(null, "On s'amuse?");
						}
					}
					catch(NumberFormatException ex)
					{
						JOptionPane.showMessageDialog(null, "NON.");
					}
				}
			}
		});
		btnGo.setBackground(new Color(220, 20, 60));
		btnGo.setBounds(183, 68, 241, 32);
		contentPane.add(btnGo);
		
		semaines = new JTextField();
		semaines.setToolTipText("Optionnel : nombre de cycles à effectuer");
		semaines.setBounds(10, 68, 163, 32);
		contentPane.add(semaines);
		semaines.setColumns(10);
	}
}
