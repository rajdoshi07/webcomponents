package org.webcomponents.content;

import java.io.Serializable;
import java.security.Principal;
import java.util.Date;

import org.springframework.util.StringUtils;

public class PersistentObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5100687576748395370L;
	
	private String id;
	private Date createdAt;
	private Principal createdBy;
	private Date updatedAt;
	private Principal updatedBy;
	
	public PersistentObject() {
		super();
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = StringUtils.hasText(id) ? id : null;
	}

	/**
	 * @return the createdAt
	 */
	public final Date getCreatedAt() {
		if(createdAt == null) {
			createdAt = new Date();
		}
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public final void setCreatedAt(Date insertedAt) {
		this.createdAt = insertedAt;
	}

	/**
	 * @return the createdBy
	 */
	public Principal getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Principal insertedBy) {
		this.createdBy = insertedBy;
	}

	/**
	 * @return the updatedAt
	 */
	public final Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * @param updatedAt the updatedAt to set
	 */
	public final void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * @return the updatedBy
	 */
	public final Principal getUpdatedBy() {
		return updatedBy;
	}

	/**
	 * @param updatedBy the updatedBy to set
	 */
	public final void setUpdatedBy(Principal updatedBy) {
		this.updatedBy = updatedBy;
	}

	public void reset() {
		this.id = null;
		this.createdAt = null;
		this.createdBy = null;
		this.updatedAt = null;
		this.updatedBy = null;
	}
}