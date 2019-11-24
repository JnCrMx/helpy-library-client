package de.jcm.helpy.content;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.File;

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
	public File image;
	@JsonInclude(JsonInclude.Include.NON_NULL)
	public File video;

	public ContentOption[] options;
}
