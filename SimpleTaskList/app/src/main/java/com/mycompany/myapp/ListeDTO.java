package com.mycompany.myapp;

import java.util.*;

public class ListeDTO
{
	private long id;
	private String description;
	private Collection<ItemDTO> listItem;

	public ListeDTO(long id, String description)
	{
		this.id = id;
		this.description = description;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getId()
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
