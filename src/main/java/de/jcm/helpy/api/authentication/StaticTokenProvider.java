package de.jcm.helpy.api.authentication;

import de.jcm.helpy.api.HelpyApi;
import org.apiguardian.api.API;

import javax.ws.rs.client.WebTarget;

public class StaticTokenProvider implements TokenProvider
{
	private final String token;

	@API(status = API.Status.MAINTAINED)
	public StaticTokenProvider(String token)
	{
		this.token = token;
	}

	@Override
	@API(status = API.Status.INTERNAL)
	public String provideToken(HelpyApi api, WebTarget baseTarget)
	{
		return token;
	}
}
