package de.jcm.helpy.api.distribution;

import org.apiguardian.api.API;

import javax.ws.rs.client.WebTarget;

public class DistributionEndpoint
{
	private final ContentEndpoint contentEndpoint;

	@API(status = API.Status.INTERNAL)
	public DistributionEndpoint(WebTarget baseTarget)
	{
		WebTarget distTarget = baseTarget.path("/distribution");

		this.contentEndpoint = new ContentEndpoint(distTarget);
	}

	@API(status = API.Status.MAINTAINED)
	public ContentEndpoint content()
	{
		return contentEndpoint;
	}
}
