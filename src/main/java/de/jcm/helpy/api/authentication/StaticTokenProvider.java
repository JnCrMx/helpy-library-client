package de.jcm.helpy.api.authentication;

import de.jcm.helpy.api.HelpyApi;

import javax.ws.rs.client.WebTarget;

public class StaticTokenProvider implements TokenProvider
{
	private final String token;

	public StaticTokenProvider(String token)
	{
		this.token = token;
	}

	@Override
	public String provideToken(HelpyApi api, WebTarget baseTarget) throws Exception
	{
		return token;
	}
}
