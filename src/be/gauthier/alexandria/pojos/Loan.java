package be.gauthier.alexandria.pojos;
import java.sql.*;
import be.gauthier.alexandria.IPingable;
import be.gauthier.alexandria.dao.*;

public class Loan implements IPingable
{
	private int loanId;
	private int lender;
	private int borrower;
	private Date startDate;
	private boolean copyHasBeenReturned;
	private boolean pending;
	private int gameCopy;
	
	private User lenderObj=null;
	private User borrowerObj=null;
	private Copy gameCopyObj=null;
	
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
	public boolean getCopyState()
	{
		return copyHasBeenReturned;
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
		DAO<User> dao=new UserDAO();
		lender=l;
		lenderObj=dao.find(Integer.toString(lender));
	}
	public void setBorrower(int b)
	{
		DAO<User> dao=new UserDAO();
		borrower = b;
		borrowerObj=dao.find(Integer.toString(borrower));
	}
	public void setStartDate(Date d)
	{
		startDate=d;
	}
	public void setCopyState(Boolean c)
	{
		copyHasBeenReturned=c;
	}
	public void setPending(Boolean p)
	{
		pending = p;
	}
	public void setGameCopy(int gc)
	{
		DAO<Copy> dao=new CopyDAO();
		gameCopy=gc;
		gameCopyObj=dao.find(Integer.toString(gameCopy));
	}
	
	
	public void setLenderObj(User l)
	{
		lenderObj=l;
	}
	public void setBorrowerObj(User b)
	{
		borrowerObj=b;
	}
	public void setGameCopyObj(Copy c)
	{
		gameCopyObj=c;
	}
	
	//Constructeurs
	
	public Loan() {}
	
	public Loan(int len, int bor, boolean pen, int gc)//Nouveau
	{
		lender=len;
		borrower=bor;
		pending=pen;
		gameCopy=gc;
	}
	
	public Loan(int id, int len, int bor, Date d, boolean cstat, boolean pen, int gc)
	{
		this(len,bor,pen,gc);
		loanId=id;
		startDate=d;
		copyHasBeenReturned=cstat;
	}
	
	@Override
	public void ping() 
	{
		DAO<User> udao=new UserDAO();
		DAO<Copy> cdao=new CopyDAO();
		setLenderObj(udao.find(Integer.toString(lender)));
		setBorrowerObj(udao.find(Integer.toString(borrower)));
		setGameCopyObj(cdao.find(Integer.toString(gameCopy)));
		
	}
}
