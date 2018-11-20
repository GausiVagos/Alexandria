package be.gauthier.alexandria;
import be.gauthier.alexandria.dao.*;
import be.gauthier.alexandria.pojos.*;

public class Test {

	public static void main(String[] args) 
	{
		DAO<Game> dao=new GameDAO();
		//Game g1=dao.find("2");
		//System.out.println(g1.getGameTitle()+" : "+g1.getlistOfVersions().size()+" versions");
		Game g2=new Game("TestJeu","Studio",2018);
		System.out.println(dao.create(g2));
	}

}
