package de.jcm.helpy.api;

import de.jcm.helpy.api.authentication.TokenProvider;
import de.jcm.helpy.api.distribution.DistributionEndpoint;
import org.apiguardian.api.API;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class HelpyApi
{
	public static final String BASE_URL = "https://jserver.kwgivnecuiphlqnj.myfritz.net/api/helpy/";

	protected final Client client;
	protected final WebTarget baseTarget;

	protected String authToken = null;

	protected UserEndpoint userEndpoint;
	protected BoxEndpoint boxEndpoint;
	protected CallEndpoint callEndpoint;
	protected DistributionEndpoint distributionEndpoint;

	@API(status = API.Status.MAINTAINED)
	public HelpyApi(String baseUrl)
	{
		client = ClientBuilder.newClient();

		baseTarget = client.target(baseUrl);

		userEndpoint = new UserEndpoint(this, baseTarget);
		boxEndpoint = new BoxEndpoint(this, baseTarget);
		callEndpoint = new CallEndpoint(this, baseTarget);
		distributionEndpoint = new DistributionEndpoint(baseTarget);
	}

	@API(status = API.Status.MAINTAINED)
	public HelpyApi()
	{
		this(BASE_URL);
	}

	@API(status = API.Status.MAINTAINED)
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

	@API(status = API.Status.MAINTAINED)
	public boolean isAuthenticated()
	{
		return authToken != null;
	}

	@API(status = API.Status.MAINTAINED)
	public UserEndpoint users()
	{
		return userEndpoint;
	}
	@API(status = API.Status.MAINTAINED)
	public BoxEndpoint boxes()
	{
		return boxEndpoint;
	}
	@API(status = API.Status.MAINTAINED)
	public CallEndpoint calls()
	{
		return callEndpoint;
	}
	@API(status = API.Status.MAINTAINED)
	public DistributionEndpoint distributions()
	{
		return distributionEndpoint;
	}
}
