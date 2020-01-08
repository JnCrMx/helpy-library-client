package de.jcm.helpy.api;

import de.jcm.helpy.User;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class UserEndpoint
{
	private final HelpyApi api;
	private final WebTarget baseTarget;

	UserEndpoint(HelpyApi api, WebTarget baseTarget)
	{
		this.api = api;
		this.baseTarget = baseTarget;
	}

	public User self()
	{
		if(!api.isAuthenticated())
		{
			throw new IllegalStateException("not authenticated");
		}

		WebTarget selfTarget = baseTarget.path("/user/self");
		Response response = selfTarget.request(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer "+api.authToken)
				.get();

		if(response.getStatusInfo().toEnum() != Response.Status.OK)
		{
			throw new RuntimeException("got "+response.getStatus()+" as response");
		}

		return response.readEntity(User.class);
	}
}
