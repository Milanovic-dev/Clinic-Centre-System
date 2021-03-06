package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity
public class Hall
{
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;

	@JsonIgnore
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "clinic_id")
	private Clinic clinic;
	
	@Column(name= "number",nullable = false)
	private int number; 	
	
	@Column(name= "name",nullable = false)
	private String name;
	
	@Column(name="deleted",nullable = false)
	private Boolean deleted;

	public Hall() {
		super();
		// TODO Auto-generated constructor stub
		deleted = false;
	}
	
	
	public Hall(Clinic clinic, int number,String name) {
		super();
		this.clinic = clinic;
		this.number = number;
		this.name=name;
		deleted = false;
	}

	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Boolean getDeleted() {
		return deleted;
	}


	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}


	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Clinic getClinic() {
		return clinic;
	}
	public void setClinic(Clinic clinic) {
		this.clinic = clinic;
	}


	@Override
	public String toString() {
		return "Hall [hallID=" + id + ", clinic=" + clinic + ", number=" + number + ", scheduleAppointments="
				+ "]";
	}
	
	
}
