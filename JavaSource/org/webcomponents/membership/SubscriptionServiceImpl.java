package org.webcomponents.membership;

import java.io.Writer;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
	
	private static final Logger logger = Logger.getLogger(SubscriptionServiceImpl.class);
	
	private SubscriptionDao subscriptionDao;

	public Object subscribe(Subscription subscription) throws DuplicateMemberException {
		String id = subscription.getId();
		if(id == null) {
			try {
				id = subscriptionDao.insertSubscription(subscription);
			} catch (DataIntegrityViolationException e) {
				logger.info(e.getMessage());
				throw new DuplicateMemberException(e);
			}
		} else {
			if(!subscriptionDao.updateSubscription(subscription)) {
				logger.warn("Unable to update subscription with id " + id);
			}
		}
		return subscriptionDao.getSubscription(id);
	}

	@Required
	public final void setSubscriptionDao(SubscriptionDao subscriptionDao) {
		this.subscriptionDao = subscriptionDao;
	}

	public List<Subscription> list() {
		return subscriptionDao.getSubscriptions();
	}

	public List<Subscription> list(int type) {
		return subscriptionDao.getSubscriptionsByType(type);
	}

	public void export(Writer out) {
		subscriptionDao.exportSubscriptions(out);
	}

	public void export(int type, Writer out) {
		subscriptionDao.exportSubscriptions(type, out);
	}

}
