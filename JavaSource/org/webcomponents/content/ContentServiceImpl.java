package org.webcomponents.content;

import java.io.Writer;
import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
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
		if(publishing.getRelatedContent() != null) {
			for(Content related: publishing.getRelatedContent()) {
				this.contentDao.addRelatedContent(publishing.getId(), related.getId());
			}
		}
		Content rv = retrieve(id);
		return rv;
	}

	@Override
	public void export(String id, Writer out) throws ContentNotFoundException {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Content> listOwnedContentMetadata(String username) {
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
				boolean rv = contentDao.isEditor(content.getId(), principal);
				return rv;
			}
			return true;
		}
		return contentDao.isEditor(obj.toString(), principal);
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
				boolean rv = contentDao.isOwner(content.getId(), principal);
				return rv;
			}
			return true;
		}
		return contentDao.isOwner(obj.toString(), principal);
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
				boolean rv = contentDao.isViewer(content.getId(), principal);
				return rv;
			}
			return true;
		}
		return contentDao.isViewer(obj.toString(), principal);
	}

	@Override
	public void putAuthorities(String id, List<GrantedAuthority> authorities) {
		this.contentDao.resetAuthorities(id);
		if(authorities == null) {
			return;
		}
		for(GrantedAuthority authority: authorities) {
			this.contentDao.putAuthority(id, authority);
		}
	}

	@Override
	public List<GrantedAuthority> listAuthorities(String id) {
		return this.contentDao.getAuthorities(id);
	}

	protected ContentDao getContentDao() {
		return contentDao;
	}

	@Override
	public List<Content> listOwnedContentMetadata() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return contentDao.listMetadata(authentication.getName());
	}

	@Override
	public List<Content> listVisibleContentMetadata() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(!authentication.isAuthenticated() || authentication.getAuthorities() == null) {
			return Collections.emptyList();
		}
		return this.contentDao.listMetadata(Arrays.asList(authentication.getAuthorities()));
	}

	@Override
	public List<Content> listVisibleContentMetadata(List<GrantedAuthority> authority) {
		if(CollectionUtils.isEmpty(authority)) {
			return Collections.emptyList();
		}
		return this.contentDao.listMetadata(authority);
	}

}
