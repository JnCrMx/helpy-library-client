package de.jcm.helpy.api.authentication;

import de.jcm.helpy.api.HelpyApi;
import org.apiguardian.api.API;

import javax.naming.AuthenticationException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class LoginTokenProvider implements TokenProvider
{
	private final String username;
	private final String password;

	@API(status = API.Status.MAINTAINED)
	public LoginTokenProvider(String username, String password)
	{
		this.username = username;
		this.password = password;
	}

	@Override
	@API(status = API.Status.INTERNAL)
	public String provideToken(HelpyApi api, WebTarget baseTarget) throws Exception
	{
		WebTarget userAuth = baseTarget.path("/authentication/user");

		Response response = userAuth.request(MediaType.TEXT_PLAIN)
				.post(Entity.form(new Form().param("username", username).param("password", password)));

		if(response.getStatusInfo().toEnum() != Response.Status.OK)
			throw new AuthenticationException("got status "+response.getStatus());
		String responseLine = response.readEntity(String.class);
		if(responseLine.isBlank())
			throw new AuthenticationException("got blank response");

		return responseLine;
	}
}
