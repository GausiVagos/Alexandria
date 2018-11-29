package be.gauthier.alexandria;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JOptionPane;

import be.gauthier.alexandria.dao.AlexConn;
import be.gauthier.alexandria.dao.CopyDAO;
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

public class Ptolemy //Couche métier de l'application.
{
	//Fonctions liées User
	private static String adminAnswer="N'est pas mort ce qui à jamais dort";
	private static String modoAnswer="42";
	private static Connection connect=AlexConn.getInstance();
	private static UserDAO udao = new UserDAO();
	private static ReservationDAO rdao=new ReservationDAO();
	private static VersionDAO vdao=new VersionDAO();
	private static CopyDAO cdao=new CopyDAO();
	
	public static User register(String un, String pa, int age, char rank)
	{
		
		User u=new User(un, pa, age, rank);
		if(udao.create(u))
			return udao.find(u.getUserName());//On retourne la version complète du User
		else
			return null;
	}
	
	public static User login(String log, String pass)
	{
		User u=udao.find(log);
		if(u!=null && u.getPassword().equals(pass))
		{
			return u;
		}
		else 
		{
			return null;
		}
	}
	
	public static void massivePing(IPingable[] list)
	{
		for(IPingable p : list)
		{
			p.ping();
		}
	}
	
	public static String getAnswer(char grade)
	{
		if(grade=='a' || grade=='A')
			return adminAnswer;
		else
			return modoAnswer;
	}
	
	public static Set<Copy> getAvailableCopies(User u)
	{
		Set<Copy> list=new HashSet<>();
		if(!u.getListOfCopies().isEmpty())
		{
			for(Copy c : u.getListOfCopies())
			{
				if(c.getAvailability())
					list.add(c);
			}
		}
		
		return list;
	}
	
	public static Set<Loan> getPendingLends(User u)
	{
		Set<Loan> list=new HashSet<>();
		if(!u.getListOfLends().isEmpty())
		{
			for(Loan l : u.getListOfLends())
			{
				if(l.getPending())
					list.add(l);
			}
		}
		
		return list;
	}
	
	public static Set<Loan> getPendingBorrowings(User u)
	{
		Set<Loan> list=new HashSet<>();
		if(!u.getListOfBorrowings().isEmpty())
		{
			for(Loan l : u.getListOfBorrowings())
			{
				if(l.getPending())
					list.add(l);
			}
		}
		
		return list;
	}
	
	public static Set<Reservation> getWaitingReservations(User u)
	{
		Set<Reservation> list=new HashSet<>();
		if(!u.getListOfBorrowings().isEmpty())
		{
			for(Reservation r : u.getListOfReservations())
			{
				if(r.getReservationStatus().equals("En attente"))
					list.add(r);
			}
		}
		
		return list;
	}
	
	public static LinkedList<Console> consolesFromVersions(LinkedList<Version> versions)
	{
		LinkedList<Console> lc=new LinkedList<Console>();
		for(Version v : versions)
		{
			v.ping();
			lc.add(v.getConsoleObj());
		}
		return lc;
	}
	
	public static LinkedList<Game> gamesFromVersions(LinkedList<Version> versions)
	{
		LinkedList<Game> lg=new LinkedList<Game>();
		for(Version v : versions)
		{
			v.ping();
			lg.add(v.getGameObj());
		}
		return lg;
	}
	
	public static Reservation getPriorityReservation(Version v)
	{
		Reservation prio=null;
		LinkedList<Reservation> list=rdao.findForAVersion(v);
		if(!list.isEmpty())//Pas de réservation concernant cette version
		{
			if(list.size()==1)
				prio=list.getFirst();
			else
			{
				//On départage selon le nombre de crédits
				LinkedList<Reservation> basedOnTokens=new LinkedList<Reservation>();
				
				Reservation max=list.getFirst();
				max.ping();
				basedOnTokens.add(max);
				for(Reservation r : list)
				{
					r.ping();
					if(r.getApplicantObj().getUserTokens()>max.getApplicantObj().getUserTokens())
					{
						basedOnTokens.clear();
						max=r;
						basedOnTokens.add(max);
					}
					else if(r.getApplicantObj().getUserTokens()==max.getApplicantObj().getUserTokens() && r.getApplicantObj().getUserId()!=max.getApplicantObj().getUserId())
						basedOnTokens.add(r);
				}
				if(basedOnTokens.size()==1)
					prio=basedOnTokens.getFirst();
				else
				{
					//On départage selon la plus ancienne réservation
					LinkedList<Reservation> basedOnDate=new LinkedList<Reservation>();
					Reservation oldest=basedOnTokens.getFirst();
					basedOnDate.add(oldest);
					
					for(Reservation r : basedOnTokens)
					{
						if(r.getReservationDate().before(oldest.getReservationDate()))
						{
							basedOnDate.clear();
							oldest=r;
							basedOnDate.add(oldest);
						}
						else if(r.getReservationDate().equals(oldest.getReservationDate()) && r.getReservationId()!=oldest.getReservationId())
							basedOnDate.add(r);
					}
					if(basedOnDate.size()==1)
						prio=basedOnDate.getFirst();
					else
					{
						//On départage par l'ancienneté des utilisateurs
						LinkedList<Reservation> basedOnSeniority=new LinkedList<Reservation>();
						Reservation senior=basedOnDate.getFirst();
						basedOnSeniority.add(senior);
						
						for(Reservation r : basedOnDate)
						{
							if(r.getApplicantObj().getInscriptionDate().before(senior.getReservationDate()))
							{
								basedOnSeniority.clear();
								senior=r;
								basedOnSeniority.add(senior);
							}
							else if(r.getApplicantObj().getInscriptionDate().equals(senior.getReservationDate()))
								basedOnSeniority.add(r);
						}
						if(basedOnSeniority.size()==1)
							prio=basedOnSeniority.getFirst();
						else
						{
							//On départage selon l'âge
							LinkedList<Reservation> basedOnAge=new LinkedList<Reservation>();
							senior=basedOnSeniority.getFirst();
							basedOnAge.add(senior);
							
							for(Reservation r : basedOnSeniority)
							{
								if(r.getApplicantObj().getAge()>senior.getApplicantObj().getAge())
								{
									basedOnAge.clear();
									senior=r;
									basedOnAge.add(senior);
								}
								else if(r.getApplicantObj().getAge()==senior.getApplicantObj().getAge() && r.getApplicantObj().getUserId()!=senior.getApplicantObj().getUserId())
									basedOnAge.add(r);
							}
							if(basedOnAge.size()==1)
								prio=basedOnAge.getFirst();
							else
							{
								//Dernier recours : l'aléatoire
								prio=basedOnAge.get((int) (Math.random() * (basedOnAge.size())));
							}
						}
					}
				}
			}
		}
		
		return prio;
	}
	
	public static HashSet<CatalogRow> getCatalog()
	{
		HashSet<CatalogRow> catalog=new HashSet<>();
		Statement stmt=null;
		ResultSet res=null;
		String sql="Select game, console, count(*) from Copy where available=true group by game, console";
		try
		{
			stmt=connect.createStatement();
			res=stmt.executeQuery(sql);
			
			while(res.next())
			{
				Version v=vdao.find(res.getInt(1)+"/"+res.getInt(2));
				if(v!=null)
				{
					CatalogRow cr=new CatalogRow(v,res.getInt(3));
					catalog.add(cr);
				}
			}
		}
		catch(SQLException ex) {}
		finally
		{
			try
			{
				if(res!=null)
					res.close();
				if(stmt!=null)
					stmt.close();
			}
			catch(SQLException ex){}
		}
		
		return catalog;
	}
	
	public LinkedList<Copy> findAvailableCopies(Version v)
	{
		LinkedList<Copy> list=new LinkedList<Copy>();
		Statement stmt=null;
		ResultSet res=null;
		String sql="select * from Copy where game="+v.getGame()+" and console="+v.getConsole()+" and available='true';";
		
		try
		{
			stmt=connect.createStatement();
			res=stmt.executeQuery(sql);
			
			while(res.next())
			{
				list.add(new Copy(res.getInt(1),res.getInt(2),res.getInt(3),res.getInt(4),res.getBoolean(5)));
			}
		}
		catch(SQLException ex)
		{
			JOptionPane.showMessageDialog(null, "Erreur de requête");
		}
		finally
		{
			try
			{
				if(res!=null)
					res.close();
				if(stmt!=null)
					stmt.close();
			}
			catch(SQLException ex)
			{
				ex.printStackTrace();
			}
		}
		
		return list;
	}
	
	public static Copy findLeastBorrowed(Version v)
	{
		Copy c=null;
		Statement stmt=null;
		ResultSet res=null;
		String sql1="select copyId from Copy where game="+v.getGame()+" and console="+v.getConsole()+" and available=true and copyId not in (select copyId from Copy inner join Loan on Copy.copyId=Loan.gameCopy)";
		String sql2="select copyId, count(*) from Copy inner join Loan on Copy.copyId=Loan.gameCopy where game="+v.getGame()+" and console="+v.getConsole()+" and available=true group by copyId order by count(*) ASC";
		
		try
		{
			stmt=connect.createStatement();
			res=stmt.executeQuery(sql1);

			if(res.next())
			{
				c=cdao.find(Integer.toString(res.getInt(1)));
			}
			else
			{
				res=stmt.executeQuery(sql2);
				if(res.next())
				{
					c=cdao.find(Integer.toString(res.getInt(1)));
				}
			}
		}
		catch(SQLException ex)
		{
			JOptionPane.showMessageDialog(null, "Erreur de requête");
		}
		finally
		{
			try
			{
				if(res!=null)
					res.close();
				if(stmt!=null)
					stmt.close();
			}
			catch(SQLException ex)
			{
				ex.printStackTrace();
			}
		}
		
		return c;
	}
}
