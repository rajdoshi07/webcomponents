package org.webcomponents.competition;

public class VoteSummary {

	private Object object;
	
	private long count;
	
	private long global;

	public VoteSummary() {
		super();
		// TODO Auto-generated constructor stub
	}

	public VoteSummary(Object object, long count) {
		super();
		this.object = object;
		this.count = count;
	}

	public final Object getObject() {
		return object;
	}

	public final void setObject(Object object) {
		this.object = object;
	}

	public final long getCount() {
		return count;
	}

	public final void setCount(long count) {
		this.count = count;
	}

	public final long getGlobal() {
		return global;
	}

	public final void setGlobal(long global) {
		this.global = global;
	}
	
	public float getPercentage() {
		return count / (float) global;
	}
}
