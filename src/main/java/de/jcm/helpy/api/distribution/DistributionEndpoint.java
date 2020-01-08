package de.jcm.helpy.api.distribution;

import de.jcm.helpy.api.HelpyApi;

import javax.ws.rs.client.WebTarget;

public class DistributionEndpoint
{
	private final HelpyApi api;
	private final WebTarget baseTarget;

	private final ContentEndpoint contentEndpoint;

	public DistributionEndpoint(HelpyApi api, WebTarget baseTarget)
	{
		this.api = api;
		this.baseTarget = baseTarget.path("/distribution");

		this.contentEndpoint = new ContentEndpoint(this.api, this.baseTarget);;
	}

	public ContentEndpoint content()
	{
		return contentEndpoint;
	}
}
