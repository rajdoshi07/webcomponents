package org.webcomponents.net;

import org.codehaus.jackson.annotate.JsonIgnore;

public class URIWrapper {
	
	private java.net.URI uri;
	
	public void setValue(String s) {
		this.uri = java.net.URI.create(s);
	}
	
	public String getValue() {
		return this.uri == null ? null : this.uri.toString();
	}
	
	@JsonIgnore
	public void setUri(java.net.URI uri) {
		this.uri = uri;
	}
	
	@JsonIgnore
	public java.net.URI getUri() {
		return this.uri;
	}
	
	@JsonIgnore
	public String getPath() {
		return this.uri == null ? null : this.uri.getPath();
	}
	
	@JsonIgnore
	public String getHost() {
		return this.uri == null ? null : this.uri.getHost();
	}
	
	@JsonIgnore
	public boolean isAbsolute() {
		return this.uri.isAbsolute();
	}
	
	@Override
	public String toString() {
		return this.uri.toString();
	}

	public static URIWrapper create(String s) {
		URIWrapper rv = new URIWrapper();
		rv.setValue(s);
		return rv;
	}

}
