package org.webcomponents.content.sqlmap;

import java.io.IOException;
import java.io.Writer;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.stereotype.Repository;
import org.webcomponents.content.Content;
import org.webcomponents.content.ContentDao;

@Repository
public class SqlMapContentDao extends SqlMapClientDaoSupport implements ContentDao {
	
	private String insertContentStatement = "insertContent";
	private String updateContentStatement = "updateContent";
	private String getContentStatement = "getContent";
	private String updateContentStatusStatement = "updateContentStatus";
	private String removeContentStatement = "removeContent";
	private String getContentsStatement = "getContents";
	private String addRelatedContentStatement = "addRelatedContent";
	private String removeRelatedContentsStatement = "removeRelatedContents";
	private String isOwnerStatement = "isOwner";
	private String isEditorStatement = "isEditor";
	private String isViewerStatement = "isViewer";
	private String getContentMetadataStatement = "getContentMetadata";
	private String getContentAuthoritiesStatement = "getContentAuthorities";
	private String putContentAuthorityStatement = "putContentAuthority";
	private String resetContentAuthoritiesStatement = "resetContentAuthorities";
	private String getContentByAuthoritiesStatement = "getContentsByAuthorities";
	private String getContentExportStatement = "getContentExport";

	@Override
	public String insert(Content content) {
		return (String) getSqlMapClientTemplate().insert(insertContentStatement, content);
	}

	@Override
	public boolean update(Content content) {
		return getSqlMapClientTemplate().update(updateContentStatement, content) == 1;
	}
	
	@Override
	public Content get(String id) {
		return (Content) getSqlMapClientTemplate().queryForObject(getContentStatement, id);
	}

	@Override
	public Content getMetadata(String id) {
		return (Content) getSqlMapClientTemplate().queryForObject(getContentMetadataStatement , id);
	}

	@Override
	public boolean updateStatus(String id, int status, Principal principal) {
		Map<String, Object> model = new HashMap<String, Object>(2);
		model.put("id", id);
		model.put("status", status);
		model.put("updatedBy", principal.getName());
		try {
			boolean rv = getSqlMapClientTemplate().update(updateContentStatusStatement, model) == 1;
			return rv;
		} finally {
			model.clear();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Content> listMetadata(String username) {
		return getSqlMapClientTemplate().queryForList(getContentsStatement, username);
	}

	@Override
	public boolean remove(String id) {
		return getSqlMapClientTemplate().delete(removeContentStatement, id) == 1;
	}

	@Override
	public void addRelatedContent(String id, String relatedId) {
		Map<String, String> model = new HashMap<String, String>(2);
		try {
			model.put("id", id);
			model.put("relatedId", relatedId);
			getSqlMapClientTemplate().insert(addRelatedContentStatement, model);
		} finally {
			model.clear();
		}		
	}

	@Override
	public boolean isOwner(String id, Authentication authentication) {
		Map<String, Object> model = new HashMap<String, Object>(2);
		try {
			model.put("id", id);
			model.put("authentication", authentication);
			return (Boolean) getSqlMapClientTemplate().queryForObject(isOwnerStatement, model);
		} finally {
			model.clear();
		}
	}

	@Override
	public boolean isEditor(String id, Authentication authentication) {
		Map<String, Object> model = new HashMap<String, Object>(2);
		try {
			model.put("id", id);
			model.put("authentication", authentication);
			return (Boolean) getSqlMapClientTemplate().queryForObject(isEditorStatement, model);
		} finally {
			model.clear();
		}
	}

	@Override
	public boolean isViewer(String id, Authentication authentication) {
		Map<String, Object> model = new HashMap<String, Object>(2);
		try {
			model.put("id", id);
			model.put("authentication", authentication);
			return (Boolean) getSqlMapClientTemplate().queryForObject(isViewerStatement, model);
		} finally {
			model.clear();
		}
	}

	@Override
	public void removeRelatedContent(String id) {
		getSqlMapClientTemplate().delete(removeRelatedContentsStatement , id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GrantedAuthority> getAuthorities(String id) {
		return getSqlMapClientTemplate().queryForList(this.getContentAuthoritiesStatement , id);
	}

	@Override
	public void putAuthority(String id, GrantedAuthority authority) {
		Map<String, Object> model = new HashMap<String, Object>(2);
		try {
			model.put("id", id);
			model.put("authority", authority.getAuthority());
			getSqlMapClientTemplate().insert(this.putContentAuthorityStatement , model);
		} finally {
			model.clear();
		}
	}

	@Override
	public int resetAuthorities(String id) {
		return getSqlMapClientTemplate().delete(this.resetContentAuthoritiesStatement , id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Content> listMetadata(List<GrantedAuthority> authority) {
		return getSqlMapClientTemplate().queryForList(this.getContentByAuthoritiesStatement, authority);
	}

	@Override
	public void export(String id, Writer out) throws IOException {
		Object content = getSqlMapClientTemplate().queryForObject(this.getContentExportStatement, id);
		if(content != null) {
			out.write(content.toString());
		}
	}
	
	public String getInsertContentStatement() {
		return insertContentStatement;
	}

	public void setInsertContentStatement(String insertContentStatement) {
		this.insertContentStatement = insertContentStatement;
	}

	public String getUpdateContentStatement() {
		return updateContentStatement;
	}

	public void setUpdateContentStatement(String updateContentStatement) {
		this.updateContentStatement = updateContentStatement;
	}

	public String getGetContentStatement() {
		return getContentStatement;
	}

	public void setGetContentStatement(String getContentStatement) {
		this.getContentStatement = getContentStatement;
	}

	public String getUpdateContentStatusStatement() {
		return updateContentStatusStatement;
	}

	public void setUpdateContentStatusStatement(String updateContentStatusStatement) {
		this.updateContentStatusStatement = updateContentStatusStatement;
	}

	public String getRemoveContentStatement() {
		return removeContentStatement;
	}

	public void setRemoveContentStatement(String removeContentStatement) {
		this.removeContentStatement = removeContentStatement;
	}

	public String getGetContentsStatement() {
		return getContentsStatement;
	}

	public void setGetContentsStatement(String getContentsStatement) {
		this.getContentsStatement = getContentsStatement;
	}

	public String getIsOwnerStatement() {
		return isOwnerStatement;
	}

	public void setIsOwnerStatement(String isOwnerStatement) {
		this.isOwnerStatement = isOwnerStatement;
	}

	public String getAddRelatedContentStatement() {
		return addRelatedContentStatement;
	}

	public void setAddRelatedContentStatement(String addRelatedContentStatement) {
		this.addRelatedContentStatement = addRelatedContentStatement;
	}

	public String getRemoveRelatedContentsStatement() {
		return removeRelatedContentsStatement;
	}

	public void setRemoveRelatedContentsStatement(String removeRelatedContentsStatement) {
		this.removeRelatedContentsStatement = removeRelatedContentsStatement;
	}

	public String getIsEditorStatement() {
		return isEditorStatement;
	}

	public void setIsEditorStatement(String isEditorStatement) {
		this.isEditorStatement = isEditorStatement;
	}

	public String getIsViewerStatement() {
		return isViewerStatement;
	}

	public void setIsViewerStatement(String isViewerStatement) {
		this.isViewerStatement = isViewerStatement;
	}

	public String getGetContentMetadataStatement() {
		return getContentMetadataStatement;
	}

	public void setGetContentMetadataStatement(String getContentMetadataStatement) {
		this.getContentMetadataStatement = getContentMetadataStatement;
	}

	public void setGetContentAuthoritiesStatement(
			String getContentAuthoritiesStatement) {
		this.getContentAuthoritiesStatement = getContentAuthoritiesStatement;
	}

	public void setPutContentAuthorityStatement(String putContentAuthorityStatement) {
		this.putContentAuthorityStatement = putContentAuthorityStatement;
	}

	public void setResetContentAuthoritiesStatement(
			String resetContentAuthoritiesStatement) {
		this.resetContentAuthoritiesStatement = resetContentAuthoritiesStatement;
	}

	public void setGetContentByAuthoritiesStatement(
			String getContentByAuthorityStatement) {
		this.getContentByAuthoritiesStatement = getContentByAuthorityStatement;
	}

	public void setGetContentExportStatement(String getContentExportStatement) {
		this.getContentExportStatement = getContentExportStatement;
	}
	
}
