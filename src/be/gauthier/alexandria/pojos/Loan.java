package be.gauthier.alexandria.pojos;
import java.util.*;

public class Loan 
{
	private int loanId;
	private int lender;
	private int borrower;
	private Date startDate;
	private boolean pending;
	private int gameCopy;
	
	private User lenderObj;
	private User borrowerObj;
	private Copy gameCopyObj;
	
	//Accesseurs
	public int getLoanId()
	{
		return loanId;
	}
	public int getLender()
	{
		return lender;
	}
	public int getBorrower()
	{
		return borrower;
	}
	public Date getStartDate()
	{
		return startDate;
	}
	public boolean getPending()
	{
		return pending;
	}
	public int getGameCopy()
	{
		return gameCopy;
	}
	
	public User getLenderObj()
	{
		return lenderObj;
	}
	public User getBorrowerObj()
	{
		return borrowerObj;
	}
	public Copy getGameCopyObj()
	{
		return gameCopyObj;
	}
	
	//Setteurs
	
	public void setLoanId(int i)
	{
		if(loanId==0)
			loanId=i;
		//Attention doublons
	}
	public void setLender(int l)
	{
		lender=l;
	}
	public void setBorrower(int b)
	{
		borrower = b;
	}
	public void setStartDate(Date d)
	{
		startDate=d;
	}
	public void setPending(Boolean p)
	{
		pending = p;
	}
	public void setGameCopy(int gc)
	{
		gameCopy=gc;
	}
	
	
	public void setLenderObj(User l)
	{
		if(lenderObj==null)
		{
			lenderObj=l;
		}
	}
	public void setBorrowerObj(User b)
	{
		if(borrowerObj==null)
		{
			borrowerObj=b;
		}
	}
	public void setGameCopyObj(Copy c)
	{
		if(gameCopyObj==null)
		{
			gameCopyObj=c;
		}
	}
	
	//Constructeurs
	
	public Loan() {}
	
	public Loan(int id, int len, int bor, Date d, boolean pen, int gc)
	{
		loanId=id;
		lender=len;
		borrower=bor;
		startDate=d;
		pending=pen;
		gameCopy=gc;
		//On ne remplit pas les champs références dans le constructeur pour éviter les boucles infinies, mais on peut les remplir en appellannt la fonction ping() de Ptolemy
	}
}
