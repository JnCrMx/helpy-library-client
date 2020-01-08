package de.jcm.helpy.api;

import de.jcm.helpy.Call;
import de.jcm.helpy.EntityInCallState;
import de.jcm.helpy.GeoLocation;

import javax.ws.rs.client.Entity;
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

	CallEndpoint(HelpyApi api, WebTarget baseTarget)
	{
		this.api = api;
		this.baseTarget = baseTarget;
	}

	public Call byId(int id)
	{
		WebTarget idTarget = baseTarget.path("/call").path(Integer.toString(id));

		Response response = idTarget.request(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer "+api.authToken)
				.get();
		return response.readEntity(Call.class);
	}

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

	public Call[] inRange(GeoLocation location, double range)
	{
		return inRange(location.latitude, location.longitude, range);
	}

	public int[] idsInRange(double latitude, double longitude, double range)
	{
		WebTarget rangeTarget = baseTarget.path("/call/range");

		Response response = rangeTarget.request(MediaType.APPLICATION_JSON)
				.header("Authorization", "Bearer "+api.authToken)
				.post(Entity.form(new Form()
						.param("latitude", Double.toString(latitude))
						.param("longitude", Double.toString(longitude))
						.param("range", Double.toString(range))));
		Map<?, ?> calls = response.readEntity(Map.class);
		return calls.keySet().stream()
				.flatMapToInt(o->IntStream.of(Integer.parseInt((String)o))).toArray();
	}

	public int[] idsInRange(GeoLocation location, double range)
	{
		return idsInRange(location.latitude, location.longitude, range);
	}

	public int create(double latitude, double longitude)
	{
		WebTarget createTarget = baseTarget.path("/call");

		return createTarget.request()
				.header("Authorization", "Bearer "+api.authToken)
				.put(Entity.form(new Form()
					.param("latitude", Double.toString(longitude))
					.param("longitude", Double.toString(longitude))))
				.readEntity(Integer.class);
	}

	public int create(GeoLocation location)
	{
		return create(location.latitude, location.longitude);
	}

	public Call createCall(double latitude, double longitude)
	{
		return byId(create(latitude, longitude));
	}

	public Call createCall(GeoLocation location)
	{
		return byId(create(location));
	}

	public void join(int id, EntityInCallState state)
	{
		WebTarget createTarget = baseTarget.path("/call").path(Integer.toString(id)).path("/join");

		Response response = createTarget.request()
				.header("Authorization", "Bearer "+api.authToken)
				.post(Entity.form(new Form()
					.param("state", state.name())));
		if(response.getStatusInfo().toEnum() != Response.Status.OK)
		{
			throw new RuntimeException("got "+response.getStatus()+" as response");
		}
	}

	public void join(Call call, EntityInCallState state)
	{
		join(call.id, state);
	}
}
