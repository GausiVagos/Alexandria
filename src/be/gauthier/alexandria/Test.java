package be.gauthier.alexandria;

public class Test {

	public static void main(String[] args) 
	{
		DAO<User> dao=new UserDAO();
		User u1=dao.find("1");
		System.out.println("User numéro 1 : "+u1.getUserName());
		User u2=dao.find("Vagos");
		System.out.println("User Vagos : "+u2.getUserId());
		System.out.println(Ptolemy.login("Vagos", "Wololo"));
		System.out.println(Ptolemy.login("Vagos", "alexandria"));
	}

}
