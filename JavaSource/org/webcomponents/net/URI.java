package org.webcomponents.net;

public class URI {
	
	private java.net.URI uri;
	
	public void setValue(String s) {
		this.uri = java.net.URI.create(s);
	}
	
	public String getValue() {
		return this.uri == null ? null : this.uri.toString();
	}
	
	public void setUri(java.net.URI uri) {
		this.uri = uri;
	}
	
	public java.net.URI getUri() {
		return this.uri;
	}
	
	public String getPath() {
		return this.uri == null ? null : this.uri.getPath();
	}
	
	public String getHost() {
		return this.uri == null ? null : this.uri.getHost();
	}
	
	public boolean isAbsolute() {
		return this.uri.isAbsolute();
	}
	
	@Override
	public String toString() {
		return this.uri.toString();
	}

	public static URI create(String s) {
		URI rv = new URI();
		rv.setValue(s);
		return rv;
	}

}
