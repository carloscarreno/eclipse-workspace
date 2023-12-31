package io.redeasy.samples;

import java.util.Optional;

public class Customer {
	private Long id;
	private  String name;
	private String lastname;
	private String alias;
	
	int creditRating;
	int salaryRating;
	
	public Customer(Long id, String name, String lastname, int creditRating) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.creditRating = creditRating;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public  String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public int getCreditRating() {
		return creditRating;
	}
	public void setCreditRating(int creditRating) {
		this.creditRating = creditRating;
	}
	@Override
	public String toString() {
		return "Customer [name=" + name + ", lastname=" + lastname + ", creditRating=" + creditRating + "]";
	}
	public int getSalaryRating() {
		return salaryRating;
	}
	public void setSalaryRating(int salaryRating) {
		this.salaryRating = salaryRating;
	}
	public Customer(Long id, String name, String lastname, int creditRating, int salaryRating) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.creditRating = creditRating;
		this.salaryRating = salaryRating;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}	
	
	public int getAliasLength() {
		return getAlias().length();
	}
	
	public Optional<String> getAliasWithOptional(){
		return Optional.ofNullable(getAlias());
	}
	
	
}
