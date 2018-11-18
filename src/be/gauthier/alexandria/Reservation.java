package be.gauthier.alexandria;
import java.util.*;

public class Reservation 
{
	private int reservationId;
	private User applicant;
	private Game game;
	private Console console;
	private String reservationStatus;
	private Date reservationDate;
	
	//Clés étrangères
	private int applicantId;
	private int gameId;
	private int consoleId;
	
	//Accesseurs
	public int getReservationId()
	{
		return reservationId;
	}
	public User getApplicant()
	{
		return applicant;
	}
	public Game getGame()
	{
		return game;
	}
	public Console getConsole()
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
	
	//Setteurs
	public void setReservationId(int i)
	{
		if(reservationId==0)//Seulement pour l'initialisation d'un objet vide
			reservationId=i;
	}
	public void setApplicant(User a)
	{
		if(applicant==null) 
		{
			applicant=a;
			applicantId=a.getUserId();
		}
	}
	public void setGame(Game g)
	{
		if(game==null)
		{
			game=g;
			gameId=g.getGameId();
		}
	}
	public void setConsole(Console c)
	{
		if(console==null)
		{
			console=c;
			consoleId=c.getConsoleId();
		}
	}
	public void setReservationStatus(int stat)
	{
		switch(stat)
		{
			case 1 : reservationStatus="En attente";
				break;
			case 2 : reservationStatus="En cours";
				break;
			default: reservationStatus="Terminée";
				break;
		}
	}
	public void setReservationDate(Date d)
	{
		reservationDate=d;
	}
	
	//Constructeurs
	public Reservation() {}
	
	public Reservation(int id, User ap, Game ga, Console co, int stat, Date d)
	{
		reservationId=id;
		applicant=ap;
		applicantId=ap.getUserId();
		game=ga;
		gameId=ga.getGameId();
		console=co;
		consoleId=co.getConsoleId();
		switch(stat)
		{
			case 1 : reservationStatus="En attente";
				break;
			case 2 : reservationStatus="En cours";
				break;
			default: reservationStatus="Terminée";
				break;
		}
		reservationDate=d;
	}
}
