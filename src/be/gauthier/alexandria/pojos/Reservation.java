package be.gauthier.alexandria.pojos;
import java.sql.Date;

import be.gauthier.alexandria.IPingable;
import be.gauthier.alexandria.dao.*;

public class Reservation implements IPingable
{
	private int reservationId;
	private int applicant;
	private int game;
	private int console;
	private String reservationStatus;
	private Date reservationDate;
	
	//Références
	private User applicantObj=null;
	private Game gameObj=null;
	private Console consoleObj=null;
	
	//Accesseurs
	public int getReservationId()
	{
		return reservationId;
	}
	public int getApplicant()
	{
		return applicant;
	}
	public int getGame()
	{
		return game;
	}
	public int getConsole()
	{
		return console;
	}
	public String getReservationStatus()
	{
		return reservationStatus;
	}
	public Date getReservationDate()
	{
		return reservationDate;
	}
	
	public User getApplicantObj()
	{
		return applicantObj;
	}
	public Game getGameObj()
	{
		return gameObj;
	}
	public Console getConsoleObj()
	{
		return consoleObj;
	}
	
	//Setteurs
	public void setReservationId(int i)
	{
		if(reservationId==0)//Seulement pour l'initialisation d'un objet vide
			reservationId=i;
	}
	public void setApplicant(int a)
	{
		DAO<User> dao=new UserDAO();
		applicant=a;
		applicantObj=dao.find(Integer.toString(a));
	}
	public void setGame(int g)
	{
		DAO<Game>dao=new GameDAO();	
		game=g;
		gameObj=dao.find(Integer.toString(g));
	}
	public void setConsole(int c)
	{
		DAO<Console> dao=new ConsoleDAO();
		console=c;
		consoleObj=dao.find(Integer.toString(c));
	}
	public void setReservationStatus(int stat)
	{
		switch(stat)
		{
			case 1 : reservationStatus="En attente";
				break;
			case 2 : reservationStatus="En cours";
				break;
			default: reservationStatus="Traîtée";
				break;
		}
	}
	public void setReservationDate(Date d)
	{
		reservationDate=d;
	}
	public void setApplicantObj(User a)
	{
		applicantObj=a;
	}
	public void setGameObj(Game g)
	{
		gameObj=g;
	}
	public void setConsoleObj(Console c)
	{
		consoleObj=c;
	}
	
	//Constructeurs
	public Reservation() {}
	
	public Reservation(int ap, int ga, int co, int stat)//Nouveau : raccourci pour le statut
	{
		applicant=ap;
		game=ga;
		console=co;
		switch(stat)
		{
			case 1 : reservationStatus="En attente";
				break;
			case 2 : reservationStatus="En cours";
				break;
			default: reservationStatus="Traitée";
				break;
		}
	}
	public Reservation(int id, int ap, int ga, int co, String stat, Date d)//DB
	{
		reservationId=id;
		applicant=ap;
		game=ga;
		console=co;
		reservationStatus=stat;
		reservationDate=d;
	}
	@Override
	public void ping() 
	{
		DAO<User> udao=new UserDAO();
		DAO<Game> gdao=new GameDAO();
		DAO<Console> cdao=new ConsoleDAO();
		setApplicantObj(udao.find(Integer.toString(applicant)));
		setGameObj(gdao.find(Integer.toString(game)));
		setConsoleObj(cdao.find(Integer.toString(console)));
	}
}
