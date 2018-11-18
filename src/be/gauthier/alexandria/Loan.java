package be.gauthier.alexandria;
import java.util.*;

public class Loan 
{
	private int loanId;
	private User lender;
	private User borrower;
	private Date startDate;
	private boolean pending;
	private Copy gameCopy;
	
	private int lenderId;
	private int borrowerId;
	private int gameCopyId;
	
	//Accesseurs
	public int getLoanId()
	{
		return loanId;
	}
	public User getLender()
	{
		return lender;
	}
	public User getBorrower()
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
	public Copy getGameCopy()
	{
		return gameCopy;
	}
	
	public int getLenderId()
	{
		return lenderId;
	}
	public int getBorrowerId()
	{
		return borrowerId;
	}
	public int getGameCopyId()
	{
		return gameCopyId;
	}
	
	//Setteurs
	
	public void setLoanId(int i)
	{
		if(loanId==0)
			loanId=i;
		//Attention doublons
	}
	public void setLender(User l)
	{
		if(lender==null)
		{
			lender=l;
			lenderId=l.getUserId();
		}
	}
	public void setBorrower(User b)
	{
		if(borrower==null)
		{
			borrower=b;
			borrowerId=b.getUserId();
		}
	}
	public void setGameCopy(Copy c)
	{
		if(gameCopy==null)
		{
			gameCopy=c;
			gameCopyId=c.getCopyId();
		}
	}
	
	//Constructeurs
	
	public Loan() {}
	
	public Loan(int id, User len, User bor, Date d, boolean pen, Copy gc)
	{
		loanId=id;
		lender=len;
		lenderId=len.getUserId();
		borrower=bor;
		borrowerId=bor.getUserId();
		startDate=d;
		pending=pen;
		gameCopy=gc;
		gameCopyId=gc.getCopyId();
	}
}
