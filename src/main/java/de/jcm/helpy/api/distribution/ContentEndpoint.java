package de.jcm.helpy.api.distribution;

import de.jcm.helpy.api.HelpyApi;
import de.jcm.helpy.distribution.VersionInfo;
import org.apache.commons.io.IOUtils;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.*;

public class ContentEndpoint
{
	private final HelpyApi api;
	private final WebTarget baseTarget;

	ContentEndpoint(HelpyApi api, WebTarget baseTarget)
	{
		this.api = api;
		this.baseTarget = baseTarget.path("/content");
	}

	public String[] getBranches()
	{
		WebTarget target = baseTarget.path("/branches");
		return target.request(MediaType.APPLICATION_JSON).get(String[].class);
	}

	public VersionInfo branch(String name)
	{
		WebTarget target = baseTarget.path(name);
		return target.request(MediaType.APPLICATION_JSON).get(VersionInfo.class);
	}

	public InputStream downloadBranch(String name)
	{
		WebTarget target = baseTarget.path(name).path("/download");
		return target.request(MediaType.APPLICATION_OCTET_STREAM).get(InputStream.class);
	}

	public void downloadBranch(String name, File file) throws IOException
	{
		InputStream in = downloadBranch(name);
		OutputStream out = new FileOutputStream(file);

		IOUtils.copy(in, out);

		in.close();
		out.close();
	}
}
