package tictacrest.core;

import javax.ws.rs.core.Response.Status.Family;
import javax.ws.rs.core.Response.StatusType;

public class CustomErrorStatus implements StatusType {
	private final Family family;
	private final int statusCode;
	private final String reasonPhrase;

	public CustomErrorStatus(final Family family, final int statusCode,
            final String reasonPhrase) {
		super();
	    this.family = family;
	    this.statusCode = statusCode;
	    this.reasonPhrase = reasonPhrase;
	}
	
	public Family getFamily() {
		return family;
	}

	public String getReasonPhrase() {
		return reasonPhrase;
	}

	public int getStatusCode() {
		return statusCode;
	}

}
