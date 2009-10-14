package org.webcomponents.content.sqlmap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Repository;
import org.webcomponents.content.ResourceMetaData;
import org.webcomponents.content.ResourceMetadataDao;

@Repository
public class SqlMapResourceDao extends SqlMapClientDaoSupport implements ResourceMetadataDao {
	
	private String insertResourceStatement = "insertResource";
	private String updateResourceStatement = "updateResource";
	private String deleteResourceStatement = "deleteResource";
	private String getResourcesStatement = "getResources";
	private String deleteResourcesStatement = "deleteResources";
	private String getResourcesCountStatement = "getResourcesCount";
	private String updateMetadataStatement = "updateMetadata";

	@Override
	public void insert(ResourceMetaData meta) {
		getSqlMapClientTemplate().insert(insertResourceStatement, meta);
	}

	@Override
	public boolean update(ResourceMetaData meta) {
		return getSqlMapClientTemplate().update(updateResourceStatement, meta) == 1;
	}

	@Override
	public int bulkUpdate(List<? extends ResourceMetaData> meta) {
		int rv = 0;
		for(ResourceMetaData metadata: meta) {
			rv += getSqlMapClientTemplate().update(updateMetadataStatement, metadata);
		}
		return rv;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<? extends ResourceMetaData> list(String uri, int offset, int size, String rel) {
		Map<String, Object> model = new HashMap<String, Object>(3);
		model.put("uri", uri);
		if(offset > 0) {			
			model.put("offset", offset);
		}
		if(size > 0) {
			model.put("size", size);
		}
		model.put("rel", rel);
		try {
			List<? extends ResourceMetaData> resources = getSqlMapClientTemplate().queryForList(getResourcesStatement, model);
			return resources;
		} finally {
			model.clear();
		}
	}

	@Override
	public boolean remove(String uri) {
		return getSqlMapClientTemplate().delete(deleteResourceStatement, uri) > 0;
	}
	
	@Override
	public boolean removeAll(String uri) {
		return getSqlMapClientTemplate().delete(deleteResourcesStatement, uri) > 0;
	}

	@Override
	public Long count(String uri) {
		return (Long) getSqlMapClientTemplate().queryForObject(getResourcesCountStatement, uri);
	}

	public void setInsertResourceStatement(String insertResourceStatement) {
		this.insertResourceStatement = insertResourceStatement;
	}

	public void setUpdateResourceStatement(String updateResourceStatement) {
		this.updateResourceStatement = updateResourceStatement;
	}

	public void setDeleteResourceStatement(String deleteResourceStatement) {
		this.deleteResourceStatement = deleteResourceStatement;
	}

	public void setGetResourcesStatement(String getResourcesStatement) {
		this.getResourcesStatement = getResourcesStatement;
	}

	public void setDeleteResourcesStatement(String deleteResourcesStatement) {
		this.deleteResourcesStatement = deleteResourcesStatement;
	}

	public void setGetResourcesCountStatement(String getResourcesCountStatement) {
		this.getResourcesCountStatement = getResourcesCountStatement;
	}

	public void setUpdateMetadataStatement(String updateMetadataStatement) {
		this.updateMetadataStatement = updateMetadataStatement;
	}

}
