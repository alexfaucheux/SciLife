 
// Getters and setters for all the book objects  
public class Book
{
	String owner;
	String author;
	String title;
	String staff;
	int status;
	int imp;
	
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
		return ("Title:     " + title + "\nAuthor:    " + author + "\nStatus:    " + (status == 1 ? "In" : "Out") + "\nPriority:  " + imp);
	}
	
	
	public String getAuthor()
	{
		return author;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public int getStatus()
	{
		return status;
	}
	
	public void setStatus(int status)
	{
		this.status = status;
	}
	
	public int getImportance()
	{
		return imp;
	}
	
	public String getOwner()
	{
		return owner;
	}
	
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	
	public String getStaff()
	{
		return staff;
	}
	
	public void setStaff(String staff)
	{
		this.staff = staff;
	}
}