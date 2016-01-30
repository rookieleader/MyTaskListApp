package com.mycompany.myapp;

import java.util.*;

public class ListeDTO
{
	private int id;
	private String description;
	private Collection<ItemDTO> listItem;

	public ListeDTO(int id, String description)
	{
		this.id = id;
		this.description = description;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getId()
	{
		return id;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	public void setListItem(Collection<ItemDTO> listItem)
	{
		this.listItem = listItem;
	}

	public Collection<ItemDTO> getListItem()
	{
		return listItem;
	}
}
