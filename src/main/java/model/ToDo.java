package model;

// Domänenobjekte / Model

public class ToDo
{
	private final int id;
	private final String description;
	private final String dueDate; // ISO yyyy-mm-dd oder null
	private boolean completed;

	public ToDo(int id, String description, String dueDate, boolean completed)
	{
		this.id = id;
		this.description = description;
		this.dueDate = dueDate;
		this.completed = completed;
	}

	public int getId()
	{
		return id;
	}

	public String getDescription()
	{
		return description;
	}

	public String getDueDate()
	{
		return dueDate;
	}

	public boolean isCompleted()
	{
		return completed;
	}

	public void setCompleted(boolean completed)
	{
		this.completed = completed;
	}
}
