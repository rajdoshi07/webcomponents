package org.webcomponents.resource;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.jets3t.service.S3Service;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.impl.rest.httpclient.RestS3Service;
import org.jets3t.service.model.S3Bucket;
import org.jets3t.service.model.S3Object;
import org.jets3t.service.security.AWSCredentials;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.StringUtils;

public class S3ResourceDao implements ResourceDao, InitializingBean {
	
	private static final TimeZone TIME_ZONE = TimeZone.getTimeZone("GMT+00");

	private static final Logger logger = Logger.getLogger(S3ResourceDao.class);
	
	private final String awsAccessKey;
	
	private final String awsSecretKey;

	private AWSCredentials awsCredentials;
	
	private S3Service s3Service;
	
	private URI repository;
	
	private String context;
	
	private S3Bucket bucket;
	
	private int accessTime = 60;

	public S3ResourceDao(String awsAccessKey, String awsSecretKey) {
		super();
		this.awsAccessKey = awsAccessKey;
		this.awsSecretKey = awsSecretKey;
	}
	
	@Override
	public void put(String dest, File source) throws IOException {
		try {
			S3Object obj = new S3Object(source);
			String key = context + dest;
			obj.setKey(key);
			obj = s3Service.putObject(bucket, obj);
		} catch (NoSuchAlgorithmException e) {
			logger.error("Uploading of file " + source.getAbsolutePath() + " on S3 location " + dest + "failed. " + e.getMessage());
			throw new IOException(e);
		} catch (S3ServiceException e) {
			logger.error("Uploading of file " + source.getAbsolutePath() + " on S3 location " + dest + "failed. " + e.getMessage());
			throw new IOException(e);
		}
	}
	
	@Required
	public void setRepository(URI uri) {
		this.repository = uri;
	}
	
	private String getBucketName(URI uri) {
		String host = uri.getHost();
		int p = host.indexOf('.');
		return host.substring(0, p);
	}

	@Override
	public String getAccessUrl(String relativeUrl) throws IOException {
		Calendar expiringOn = Calendar.getInstance(TIME_ZONE);
		expiringOn.add(Calendar.MINUTE, accessTime);
		try {
			String rv = S3Service.createSignedGetUrl(bucket.getName(), context + relativeUrl, awsCredentials, expiringOn.getTime());
			return rv;
		} catch (S3ServiceException e) {
			logger.error("Unable to grant an access url to resource " + relativeUrl + ". " + e.getMessage());
			throw new IOException(e);
		}
	}

	@Override
	public void remove(String path) throws IOException {
		try {
			s3Service.deleteObject(bucket, context + path);
		} catch (S3ServiceException e) {
			logger.error("Unable to remove resource " + path + ". " + e.getMessage());
			throw new IOException(e);
		}
	}

	public void setAccessTime(int accessTime) {
		this.accessTime = accessTime;
	}

	@Override
	public URI getAccessUri(URI relativeUrl) throws IOException, URISyntaxException {
		String url = getAccessUrl(relativeUrl.toString());
		return new URI(url);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		String bucketName = getBucketName(repository);
		this.context = this.repository.getPath().substring(1);
		awsCredentials = new AWSCredentials(awsAccessKey, awsSecretKey);
		s3Service = new RestS3Service(awsCredentials);
		bucket = s3Service.getBucket(bucketName);
		logger.debug("Extracted {bucket: " + StringUtils.quote(bucket.getName()) + ", context: " + StringUtils.quote(context) + " from uri " + this.repository);
	}
	
}
