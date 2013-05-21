package org.greg.resteasy.controller.request;

public class Article {

	String	name;
	Integer	id;

	public Article() {

	}

	public Article(int id, String name) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
