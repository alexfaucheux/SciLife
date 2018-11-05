 

public class Book
{
	String author;
	String title;
	int status;
	int imp;
	
	public Book(String title, String author, int status, int imp)
	{
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
}