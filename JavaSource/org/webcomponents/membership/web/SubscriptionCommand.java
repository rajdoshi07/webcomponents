package org.webcomponents.membership.web;

import org.webcomponents.membership.Subscription;

public class SubscriptionCommand extends Subscription {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1913096220647149485L;
	private String humanTest;

	public SubscriptionCommand() {
		super();
	}

	public String getHumanTest() {
		return humanTest;
	}

	public void setHumanTest(String humanTest) {
		this.humanTest = humanTest;
	}

}