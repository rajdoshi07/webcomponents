package org.webcomponents.content;

import java.io.Writer;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

public class ContentServiceImpl implements ContentService {

	private ContentDao contentDao;

	@Required
	public void setContentDao(ContentDao contentDao) {
		this.contentDao = contentDao;
	}

	@Override
	public Content clone(String parentId) throws ContentNotFoundException {
		Content rv = retrieve(parentId);
		if(rv == null) {
			throw new ContentNotFoundException(parentId);
		}
		rv.reset();
		rv.setParentId(parentId);
		Authentication principal = SecurityContextHolder.getContext().getAuthentication();
		rv.setCreatedBy(principal);
		return rv;
	}

	@Override
	public Content edit(Content publishing) throws ContentNotFoundException {
		Authentication principal = SecurityContextHolder.getContext().getAuthentication();
		publishing.setUpdatedBy(principal);
		
		String id = publishing.getId();
		if(StringUtils.hasText(id)) {
			if(!this.contentDao.update(publishing)) {
				throw new ContentNotFoundException(publishing.getId());
			}
		} else {
			id = this.contentDao.insert(publishing);
		}
		this.contentDao.removeRelatedContent(id);
		for(Content related: publishing.getRelatedContent()) {
			this.contentDao.addRelatedContent(publishing.getId(), related.getId());
		}
		Content rv = retrieve(id);
		return rv;
	}

	@Override
	public void export(String id, Writer out) throws ContentNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Content> listMetadata(String username) {
		return contentDao.listMetadata(username);
	}

	@Override
	public void publish(String id) throws ContentNotFoundException {
		Content pub = retrieve(id);
		if(pub == null) {
			throw new ContentNotFoundException(id);
		}
		if(pub.getStatus() == Content.STATUS_PUBLISHED) {
			return;
		}
		// Pubblicare sul negozio la pubblicazione
		Authentication principal = SecurityContextHolder.getContext().getAuthentication();
		contentDao.updateStatus(id, Content.STATUS_PUBLISHED, principal);
	}

	@Override
	public void remove(String id) throws ContentNotFoundException, IllegalStateException {
		Content content = this.retrieve(id);
		if(content == null) {
			throw new ContentNotFoundException(id);
		}
		if(content.getStatus() == Content.STATUS_PUBLISHED) {
			throw new IllegalStateException("Cannot remove publishing " + id + ": it's published");
		}
		contentDao.remove(id);
	}

	@Override
	public Content retrieve(String id) {
		Content rv = contentDao.get(id);
		return rv;
	}

	@Override
	public Content retrieveMetadata(String id) {
		Content rv = contentDao.getMetadata(id);
		return rv;
	}
	
	@Override
	public boolean isContentEditor(Object obj, Principal principal) {
		if(obj == null) {
			return true;
		}
		if(principal == null) {
			return false;
		}
		if(obj instanceof Content) {
			Content content = (Content) obj;
			if(StringUtils.hasText(content.getId())) {
				boolean rv = contentDao.isContentEditor(content.getId(), principal);
				return rv;
			}
			return true;
		}
		return contentDao.isContentEditor(obj.toString(), principal);
	}

	@Override
	public boolean isContentOwner(Object obj, Principal principal) {
		if(obj == null) {
			return true;
		}
		if(principal == null) {
			return false;
		}
		if(obj instanceof Content) {
			Content content = (Content) obj;
			if(StringUtils.hasText(content.getId())) {
				boolean rv = contentDao.isContentOwner(content.getId(), principal);
				return rv;
			}
			return true;
		}
		return contentDao.isContentOwner(obj.toString(), principal);
	}

	@Override
	public boolean isContentViewer(Object obj, Principal principal) {
		if(obj == null) {
			return true;
		}
		if(principal == null) {
			return false;
		}
		if(obj instanceof Content) {
			Content content = (Content) obj;
			if(StringUtils.hasText(content.getId())) {
				boolean rv = contentDao.isContentViewer(content.getId(), principal);
				return rv;
			}
			return true;
		}
		return contentDao.isContentViewer(obj.toString(), principal);
	}

	protected ContentDao getContentDao() {
		return contentDao;
	}

}
