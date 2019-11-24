package de.jcm.helpy;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.jcm.helpy.content.ContentPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.net.URL;

class JsonTests
{
	@ParameterizedTest
	@ValueSource(strings = { "begrüßung", "bewusstsein_prüfen", "stabile_seitenlage" })
	void testContentPageJson(String name) throws IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		URL url  = getClass().getResource("/"+name+".json");

		ContentPage page = mapper.readValue(url, ContentPage.class);
		Assertions.assertEquals("de", page.language);
	}
}
