package de.jcm.helpy;

import de.jcm.helpy.api.HelpyApi;
import de.jcm.helpy.api.authentication.LoginTokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApiTests
{
	@Test
	void testApi()
	{
		HelpyApi api = new HelpyApi();

		// Check if domain can be found and server is Ready to Serve
		Assertions.assertEquals(404, api.getBaseTarget().request().get().getStatus(),
				"Server did not respond with 404 for base target!");

		// Check if we can authenticate as a demo user specially made for this test
		LoginTokenProvider provider =
				new LoginTokenProvider("testuser", "testpassword");
		Assertions.assertTrue(api.authenticate(provider),
				"Cannot authenticate as \"testuser\" with \"testpassword\".");
	}
}
