package com.se.thymeleafdemo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;

import org.hibernate.annotations.NamedQuery;

@Entity
@Table(name="tinnhan")
public class TinNhan {

    // define fields

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="username")
    private String username;

    @Column(name="noidung")
    private String noidung;

    @Column(name="type")
    private String type;
    
    @Column(name="date")
    private String date;
    
    @Column(name="brower")
    private String brower;

    public TinNhan() {
		// TODO Auto-generated constructor stub
	}


	public TinNhan(int id, String username, String noidung, String type, String date, String brower) {
		super();
		this.id = id;
		this.username = username;
		this.noidung = noidung;
		this.type = type;
		this.date = date;
		this.brower = brower;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNoidung() {
		return noidung;
	}

	public void setNoidung(String noidung) {
		this.noidung = noidung;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getBrower() {
		return brower;
	}

	public void setBrower(String brower) {
		this.brower = brower;
	}

	@Override
	public String toString() {
		return "TinNhan [id=" + id + ", username=" + username + ", noidung=" + noidung + ", type=" + type + ", date="
				+ date + ", brower=" + brower + "]";
	}
    

}











