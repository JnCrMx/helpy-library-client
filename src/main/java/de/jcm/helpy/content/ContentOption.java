package de.jcm.helpy.content;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContentOption
{
	public int id;
	public String[] answers;
	public String target;
	public String message;
}
