package de.jcm.helpy.api;

import de.jcm.helpy.api.authentication.TokenProvider;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class HelpyApi
{
	public static final String baseUrl = "https://jserver.kwgivnecuiphlqnj.myfritz.net/api/helpy/";

	protected final Client client;

	public Client getClient()
	{
		return client;
	}

	public WebTarget getBaseTarget()
	{
		return baseTarget;
	}

	protected final WebTarget baseTarget;

	protected String authToken = null;

	public HelpyApi()
	{
		client = ClientBuilder.newClient();

		baseTarget = client.target(baseUrl);
	}

	public boolean authenticate(TokenProvider provider)
	{
		String token;
		try
		{
			token = provider.provideToken(this, baseTarget);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return false;
		}
		if(token == null)
			return false;
		authToken = token;
		return true;
	}
}
