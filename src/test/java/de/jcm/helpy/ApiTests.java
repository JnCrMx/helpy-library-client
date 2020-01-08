package de.jcm.helpy;

import de.jcm.helpy.api.HelpyApi;
import de.jcm.helpy.api.authentication.LoginTokenProvider;
import de.jcm.helpy.api.authentication.StaticTokenProvider;
import de.jcm.helpy.api.authentication.TokenProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

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
		TokenProvider provider =
				new LoginTokenProvider("testuser", "testpassword");
		Assertions.assertTrue(api.authenticate(provider),
				"Cannot authenticate as \"testuser\" with \"testpassword\".");

		// Check for own user information
		Assertions.assertDoesNotThrow(()->api.users().self(),
				"Cannot access own user information!");

		// Test login with token
		TokenProvider provider2 =
				new StaticTokenProvider("5A6C40C466F58042C045BA7C2FE8C10E");
		Assertions.assertTrue(api.authenticate(provider2));

		// Check if box/self is not found
		Assertions.assertDoesNotThrow(()->api.boxes().self(),
				"Cannot access own box information!");

		Call call = api.calls().byId(6);
		Assertions.assertEquals(6, call.id, "Cannot access calls by id!");

		Call[] calls;
		try
		{
			calls = api.calls().inRange(call.location, 100);
		}
		catch (Throwable t)
		{
			Assertions.fail("Cannot access calls in range!", t);
			calls=new Call[0];
		}
		Assertions.assertTrue(Stream.of(calls).anyMatch(c->c.id==call.id),
				"Calls in range do not match calls by id!");
	}
}
