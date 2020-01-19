package de.jcm.helpy.api;

import de.jcm.helpy.Box;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apiguardian.api.API;

public class BoxEndpoint
{
	private final HelpyApi api;
	private final WebTarget baseTarget;

	@API(status = API.Status.INTERNAL)
	BoxEndpoint(HelpyApi api, WebTarget baseTarget)
	{
		this.api = api;
		this.baseTarget = baseTarget;
	}

	@API(status = API.Status.MAINTAINED)
	public Box self()
	{
		if(!api.isAuthenticated())
		{
			throw new IllegalStateException("not authenticated");
		}

		WebTarget selfTarget = baseTarget.path("/box/self");
		Response response = selfTarget.request(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer "+api.authToken)
				.get();

		if(response.getStatusInfo().toEnum() != Response.Status.OK)
		{
			throw new RuntimeException("got "+response.getStatus()+" as response");
		}

		return response.readEntity(Box.class);
	}
}
