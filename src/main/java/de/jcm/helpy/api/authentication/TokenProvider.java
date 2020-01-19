package de.jcm.helpy.api.authentication;

import de.jcm.helpy.api.HelpyApi;
import org.apiguardian.api.API;

import javax.ws.rs.client.WebTarget;

@FunctionalInterface
public interface TokenProvider
{
	@API(status = API.Status.INTERNAL)
	String provideToken(HelpyApi api, WebTarget baseTarget) throws Exception;
}
