package de.jcm.helpy.api;

import de.jcm.helpy.Call;
import de.jcm.helpy.CallInteraction;
import de.jcm.helpy.EntityInCallState;
import de.jcm.helpy.GeoLocation;
import de.jcm.helpy.content.ContentOption;
import de.jcm.helpy.content.ContentPage;
import org.apiguardian.api.API;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.stream.IntStream;

public class CallEndpoint
{
	private final HelpyApi api;
	private final WebTarget baseTarget;

	@API(status = API.Status.INTERNAL)
	CallEndpoint(HelpyApi api, WebTarget baseTarget)
	{
		this.api = api;
		this.baseTarget = baseTarget;
	}

	@API(status = API.Status.MAINTAINED)
	public Call byId(int callId)
	{
		WebTarget idTarget = baseTarget.path("/call").path(Integer.toString(callId));

		Invocation.Builder builder = idTarget.request(MediaType.APPLICATION_JSON);
		if(api.isAuthenticated())
		{
			builder = builder.header("Authorization", "Bearer " + api.authToken);
		}
		return builder.get(Call.class);
	}

	@API(status = API.Status.DEPRECATED)
	public Call[] inRange(double latitude, double longitude, double range)
	{
		int[] ids = idsInRange(latitude, longitude, range);
		Call[] calls = new Call[ids.length];

		for(int i=0; i<ids.length; i++)
		{
			calls[i] = byId(ids[i]);
		}

		return calls;
	}

	@API(status = API.Status.MAINTAINED)
	public Call[] inRange(GeoLocation location, double range)
	{
		return inRange(location.latitude, location.longitude, range);
	}

	@API(status = API.Status.DEPRECATED)
	public int[] idsInRange(double latitude, double longitude, double range)
	{
		WebTarget rangeTarget = baseTarget.path("/call/range");

		Invocation.Builder builder = rangeTarget.request(MediaType.APPLICATION_JSON);
		if(api.isAuthenticated())
		{
			builder = builder.header("Authorization", "Bearer " + api.authToken);
		}
		Response response = builder.post(Entity.form(new Form()
						.param("latitude", Double.toString(latitude))
						.param("longitude", Double.toString(longitude))
						.param("range", Double.toString(range))));
		Map<?, ?> calls = response.readEntity(Map.class);
		return calls.keySet().stream()
				.flatMapToInt(o->IntStream.of(Integer.parseInt((String)o))).toArray();
	}

	@API(status = API.Status.MAINTAINED)
	public int[] idsInRange(GeoLocation location, double range)
	{
		return idsInRange(location.latitude, location.longitude, range);
	}

	@API(status = API.Status.DEPRECATED)
	public int create(double latitude, double longitude)
	{
		if(!api.isAuthenticated())
		{
			throw new IllegalStateException("not authenticated");
		}

		WebTarget createTarget = baseTarget.path("/call");

		return createTarget.request()
				.header("Authorization", "Bearer "+api.authToken)
				.put(Entity.form(new Form()
					.param("latitude", Double.toString(latitude))
					.param("longitude", Double.toString(longitude))))
				.readEntity(Integer.class);
	}

	@API(status = API.Status.MAINTAINED)
	public int create(GeoLocation location)
	{
		return create(location.latitude, location.longitude);
	}

	@API(status = API.Status.DEPRECATED)
	public Call createCall(double latitude, double longitude)
	{
		return byId(create(latitude, longitude));
	}

	@API(status = API.Status.MAINTAINED)
	public Call createCall(GeoLocation location)
	{
		return byId(create(location));
	}

	@API(status = API.Status.MAINTAINED)
	public void join(int callId, EntityInCallState state)
	{
		if(!api.isAuthenticated())
		{
			throw new IllegalStateException("not authenticated");
		}

		WebTarget createTarget = baseTarget.path("/call").path(Integer.toString(callId)).path("/join");

		Response response = createTarget.request()
				.header("Authorization", "Bearer "+api.authToken)
				.post(Entity.form(new Form()
					.param("state", state.name())));
		if(response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL)
		{
			throw new RuntimeException("got "+response.getStatus()+" as response");
		}
	}

	@API(status = API.Status.MAINTAINED)
	public void join(Call call, EntityInCallState state)
	{
		join(call.id, state);
	}

	@API(status = API.Status.MAINTAINED)
	public void addCallInteraction(int callId, String language,
	                                  String contentPath, int chosenOption)
	{
		if(!api.isAuthenticated())
		{
			throw new IllegalStateException("not authenticated");
		}

		WebTarget target = baseTarget.path("/call").path(Integer.toString(callId))
				.path("/interactions");

		Response response = target.request()
				.header("Authorization", "Bearer " + api.authToken)
				.put(Entity.form(new Form()
						.param("language", language)
						.param("content_path", contentPath)
						.param("chosen_option", Integer.toString(chosenOption))));

		if(response.getStatusInfo().getFamily() != Response.Status.Family.SUCCESSFUL)
		{
			throw new RuntimeException("got "+response.getStatus()+" as response");
		}
	}

	@API(status = API.Status.MAINTAINED)
	public void addCallInteraction(Call call, ContentPage page,
	                               String contentPath, ContentOption option)
	{
		addCallInteraction(call.id, page.language, contentPath, option.id);
	}

	@API(status = API.Status.MAINTAINED)
	public void addCallInteraction(Call call, String language,
	                               String contentPath, int chosenOption)
	{
		addCallInteraction(call.id, language, contentPath, chosenOption);
	}

	@API(status = API.Status.MAINTAINED)
	public CallInteraction[] getCallInteractions(int callId)
	{
		if(!api.isAuthenticated())
		{
			throw new IllegalStateException("not authenticated");
		}

		WebTarget target = baseTarget.path("/call")
				.path(Integer.toString(callId))
				.path("/interactions");

		return target.request(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + api.authToken)
				.get(CallInteraction[].class);
	}

	@API(status = API.Status.MAINTAINED)
	public CallInteraction[] getCallInteractions(Call call)
	{
		return getCallInteractions(call.id);
	}

	@API(status = API.Status.MAINTAINED)
	public CallInteraction getLastCallInteraction(int callId)
	{
		if(!api.isAuthenticated())
		{
			throw new IllegalStateException("not authenticated");
		}

		WebTarget target = baseTarget.path("/call")
				.path(Integer.toString(callId))
				.path("/interactions/last");

		return target.request(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer " + api.authToken)
				.get(CallInteraction.class);
	}

	@API(status = API.Status.MAINTAINED)
	public CallInteraction getLastCallInteraction(Call call)
	{
		return getLastCallInteraction(call.id);
	}
}
