package com.epam.server;

import io.swagger.annotations.Api;
import com.epam.resource.DefaultPersonDao;
import com.epam.resource.Person;
import com.epam.resource.PersonDao;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Api(value = "person")
@Path("person")
public class PersonService {

	private PersonDao dao = new DefaultPersonDao();

	@GET
	@Path("/{name}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Get Person by name",
			response = Person.class)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid person name supplied."),
			@ApiResponse(code = 404, message = "Person not found.")
	})
	public Response getPersonByName(@ApiParam(value = "Name of person to return", required = true)
									@PathParam("name") String name) {

		if (name == null) {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		Person person = dao.getPersonByName(name);

		if (person == null) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("Person doesn't exist [" + name + "]").build();
		}

		return Response.ok(person).build();
	}

	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@ApiOperation(value = "Get all persons",
			response = Person.class,
			responseContainer = "List")
	public Response findAll() {
		return Response.ok(dao.findAll()).build();
	}

	@PUT
	@Path("/update")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@ApiOperation(
			value = "Update person",
			response = Person.class
	)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid person name."),
			@ApiResponse(code = 404, message = "Person not found."),
			@ApiResponse(code = 200, message = "Person successfully updated")
	})
	public Response updatePerson(@ApiParam(value = "Person to update", required = true) Person person) {
		if (person.getName() == null || person.getName().isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Person doesn't exist [" + person.getName() + "]").build();
		}

		Person personToUpdate = dao.updatePerson(person);
		if (personToUpdate == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Person doesn't exist [" + person.getName() + "]").build();
		}
		return Response.ok(personToUpdate).build();
	}

	@POST
	@Path("/new")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@ApiOperation(
			value = "Add person",
			response = Person.class
	)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid person name or person already exist."),
			@ApiResponse(code = 201, message = "Person successfully created")
	})
	public Response insertPerson(@ApiParam(value = "Person to add", required = true) Person person) throws URISyntaxException {
		if (person.getName() == null || person.getName().isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Person doesn't exist [" + person.getName() + "]").build();
		}

		Person personToInsert = dao.updatePerson(person);
		if (personToInsert == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("Person doesn't exist [" + person.getName() + "]").build();
		}
		return Response.created(new URI("/person/" + person.getName())).entity(personToInsert).build();
	}

	@DELETE
	@Path("/{name}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@ApiOperation(
			value = "Delete person",
			response = Person.class
	)
	@ApiResponses(value = {
			@ApiResponse(code = 400, message = "Invalid person name or empty stock supplied."),
			@ApiResponse(code = 404, message = "Person not found."),
			@ApiResponse(code = 200, message = "Person successfully removed")
	})
	public Response deletePerson(@ApiParam(value = "Name of person to delete", required = true)
								 @PathParam("name") String personName) {
		if (personName == null || personName.isEmpty())
			return Response.status(Response.Status.BAD_REQUEST).build();

		Person existPerson = dao.getPersonByName(personName);
		if (existPerson == null)
			return Response.status(Response.Status.NOT_FOUND)
					.entity("Person does not exist [" + personName + "]")
					.build();

		dao.removePerson(personName);
		return Response.ok().build();
	}
}
