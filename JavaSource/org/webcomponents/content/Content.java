package org.webcomponents.content;

import java.util.ArrayList;
import java.util.List;

import org.webcomponents.content.PersistentObject;

public class Content extends PersistentObject {

	private static final long serialVersionUID = -8176732136338806784L;
	private int status = STATUS_WIP;
	private int type;
	private String title;
	private String acl;
	private String parentId;
	private Integer minResourcesCount;
	private Integer maxResourcesCount;
	private List<? extends ResourceMetaData> resourcesMetadata;
	private List<Content> relatedContent;
	
	public static final int STATUS_WIP = 0;
	public static final int STATUS_PUBLISHED = 1;
	public static final int STATUS_UNPUBLISHED = 2;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAcl() {
		return acl;
	}

	public void setAcl(String acl) {
		this.acl = acl;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<Content> getRelatedContent() {
		return relatedContent;
	}

	public void setRelatedContent(List<Content> relatedContent) {
		this.relatedContent = relatedContent;
	}

	public void addRelatedContent(Content content) {
		if(relatedContent == null) {
			relatedContent = new ArrayList<Content>();
		}
		relatedContent.add(content);
	}

	public Integer getMinResourcesCount() {
		return minResourcesCount;
	}

	public void setMinResourcesCount(Integer minResourceCount) {
		this.minResourcesCount = minResourceCount;
	}

	public Integer getMaxResourcesCount() {
		return maxResourcesCount;
	}

	public void setMaxResourcesCount(Integer maxResourceCount) {
		this.maxResourcesCount = maxResourceCount;
	}

	public List<? extends ResourceMetaData> getResourcesMetadata() {
		return resourcesMetadata;
	}

	public void setResourcesMetadata(
			List<? extends ResourceMetaData> resourcesMetadata) {
		this.resourcesMetadata = resourcesMetadata;
	}

}
