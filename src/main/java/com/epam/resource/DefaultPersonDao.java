package com.epam.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultPersonDao implements PersonDao {

	private Map<String, Person> personRepository = new ConcurrentHashMap<>();

	{
		personRepository.put("Bruce", new Person("Bruce", "London", 55));
		personRepository.put("James", new Person("James", "Seattle", 45));
		personRepository.put("Mille", new Person("Mille", "Berlin", 35));
	}

	@Override
	public Person getPersonByName(String name) {
		return personRepository.get(name);
	}

	@Override
	public List<Person> findAll() {
		return new ArrayList<>(personRepository.values());
	}

	@Override
	public Person updatePerson(Person person) {
		if (personRepository.containsKey(person.getName())) {
			personRepository.put(person.getName(), person);
			return person;
		}
		return null;
	}

	@Override
	public Person addPerson(Person person) {
		if (!personRepository.containsKey(person.getName())) {
			personRepository.put(person.getName(), person);
			return person;
		}
		return null;
	}

	@Override
	public void removePerson(String name) {
		if (personRepository.containsKey(name)) {
			personRepository.remove(name);
		}
	}
}
