package com.mycompany.myapp;

public class ItemDTO
{
	private int id;
	private int listeId;
	private String description;
	private boolean done;

	public ItemDTO(int id, int listeId, String description, boolean done)
	{
		this.id = id;
		this.listeId = listeId;
		this.description = description;
		this.done = done;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public void setListeId(int listeId)
	{
		this.listeId = listeId;
	}

	public int getListeId()
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
