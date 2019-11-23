 
// Getters and setters for all the book objects	 
public class Book
{
	private String owner;
	private String author;
	private String title;
	private String staff;
	private int status;
	private int imp;
	
	public Book(String title, String author, int status, int imp, String owner, String staff)
	{
		this.staff = staff;
		this.owner = owner;
		this.author = author;
		this.title = title;
		this.status = status;
		this.imp = imp;
	}
	
	public String toString()
	{
		return ("Title:		" + title + "\nAuthor:	  " + author + "\nStatus:	 " + (status == 1 ? "In" : "Out") + "\nPriority:  " + imp);
	}
	
	
	String getAuthor()
	{
		return author;
	}
	
	String getTitle()
	{
		return title;
	}
	
	int getStatus()
	{
		return status;
	}
	
	void setStatus(int status)
	{
		this.status = status;
	}

	int getImportance()
	{
		return imp;
	}
	
	String getOwner()
	{
		return owner;
	}
	
	void setOwner(String owner)
	{
		this.owner = owner;
	}
	
	String getStaff()
	{
		return staff;
	}
	
	void setStaff(String staff)
	{
		this.staff = staff;
	}
}