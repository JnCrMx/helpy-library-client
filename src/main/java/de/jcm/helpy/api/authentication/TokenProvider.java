package de.jcm.helpy.api.authentication;

import de.jcm.helpy.api.HelpyApi;

import javax.ws.rs.client.WebTarget;

@FunctionalInterface
public interface TokenProvider
{
	String provideToken(HelpyApi api, WebTarget baseTarget) throws Exception;
}
