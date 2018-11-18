package be.gauthier.alexandria;
import java.util.*;

public class User 
{
	//Attributs
	private int userId;
	private String userName;
	private int age;
	private float rating;
	private String userRank;
	private int userTokens;
	private int guarantee;
	private Date inscriptionDate;
	
	//Liaisons
	private Set<Loan> listOfLends=new HashSet<>();
	private Set<Loan> listOfBorrowings=new HashSet<>();
	private Set<Reservation> listOfReservations=new HashSet<>();
	private Set<Copy> listOfCopies=new HashSet<>();
	
	//Accesseurs
	public int getUserId()
	{
		return userId;
	}
	public String getUserName()
	{
		return userName;
	}
	public int getAge()
	{
		return age;
	}
	public float getRating()
	{
		return rating;
	}
	public String getUserRank()
	{
		return userRank;
	}
	public int getUserTokens()
	{
		return userTokens;
	}
	public int getGuarantee()
	{
		return guarantee;
	}
	public Date getInscriptionDate()
	{
		return inscriptionDate;
	}
	
	public Set<Loan> getListOfLends()
	{
		return listOfLends;
	}
	public Set<Loan> getListOfBorrowings()
	{
		return listOfBorrowings;
	}
	public Set<Reservation> getListOfReservation()
	{
		return listOfReservations;
	}
	public Set<Copy> getListOfCopies()
	{
		return listOfCopies;
	}
	
	//Setteurs
	public void setUserId(int i)
	{
		if(userId==0)
			userId=i;
	}
	public void setUserName(String u)
	{
		userName=u;
	}
	public void setAge(int a)
	{
		age=a;
	}
	public void setRating(float r)
	{
		rating=r;
	}
	public void setUserRank(char rank)
	{
		switch(rank)
		{
			case 'A':
			case 'a':	userRank="Administrateur";
				break;
			case 'M':
			case 'm':	userRank="Modérateur";
				break;
				
			default : 	userRank="Utilisateur";
				break;
		}
	}
	public void setUserTokens(int t)
	{
		userTokens=t;
	}
	public void setGuarantee(int g)
	{
		guarantee=g;
	}
	public void setInscriptionDate(Date d)
	{
		inscriptionDate=d;
	}
	
	public void addLend(Loan l)
	{
		if(!listOfLends.contains(l))
			listOfLends.add(l);
		//Faire une manip de l'autre côté?
	}
	public void addBorrowing(Loan l)
	{
		if(!listOfBorrowings.contains(l))
			listOfBorrowings.add(l);
	}
	public void addReservation(Reservation r)
	{
		if(!listOfReservations.contains(r))
			listOfReservations.add(r);
	}
	public void addCopy(Copy c)
	{
		if(!listOfCopies.contains(c))
			listOfCopies.add(c);
	}
	
	public void removeLend(Loan l)
	{
		if(listOfLends.contains(l))
			listOfLends.remove(l);
	}
	public void removeBorrowing(Loan l)
	{
		if(listOfBorrowings.contains(l))
			listOfBorrowings.remove(l);
	}
	public void removeReservation(Reservation r)
	{
		if(listOfReservations.contains(r))
			listOfReservations.remove(r);
	}
	public void removeCopy(Copy c)
	{
		if(listOfCopies.contains(c))
			listOfCopies.remove(c);
	}
	
	//Constructeurs
	public User() {}
	
	public User(int id, String un, int a, char rank, int g, Date d)
	{
		userId=id;
		userName=un;
		age=a;
		rating=0;
		guarantee=g;
		inscriptionDate=d;
		switch(rank)
		{
			case 'A':
			case 'a':	userRank="Administrateur";
				break;
			case 'M':
			case 'm':	userRank="Modérateur";
				break;
				
			default : 	userRank="Utilisateur";
				break;
		}
	}
}
