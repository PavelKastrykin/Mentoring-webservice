package com.epam.resource;

import java.util.Objects;

public class Person {

	private String name;
	private String address;
	private int age;

	public Person(){}

	public Person(String name, String address, int age) {
		this.name = name;
		this.address = address;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Person person = (Person) o;
		return age == person.age &&
				Objects.equals(name, person.name) &&
				Objects.equals(address, person.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, address, age);
	}
}
