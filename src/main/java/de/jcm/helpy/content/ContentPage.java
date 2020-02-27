package de.jcm.helpy.content;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;

public class ContentPage
{
	@JsonAlias("lang")
	public String language;

	public ContentType type;
	public ContentForm form;

	@JsonAlias("short")
	public String shortMessage;
	@JsonAlias("help")
	public String[] helpMessages;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String image;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String video;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public String sound;

	public ContentOption[] options;
}
