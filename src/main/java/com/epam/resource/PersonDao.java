package com.epam.resource;

import java.util.List;

public interface PersonDao {

	Person getPersonByName(String name);

	List<Person> findAll();

	Person updatePerson(Person person);

	Person addPerson(Person person);

	void removePerson(String name);
}
