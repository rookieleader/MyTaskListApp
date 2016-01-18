package com.mycompany.myapp;

public class ItemDTO
{
	private long id;
	private long listeId;
	private String description;
	private boolean done;

	public ItemDTO(long id, long listeId, String description, boolean done)
	{
		this.id = id;
		this.listeId = listeId;
		this.description = description;
		this.done = done;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getId()
	{
		return id;
	}

	public void setListeId(long listeId)
	{
		this.listeId = listeId;
	}

	public long getListeId()
	{
		return listeId;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDone(boolean done)
	{
		this.done = done;
	}

	public boolean isDone()
	{
		return done;
	}
}
