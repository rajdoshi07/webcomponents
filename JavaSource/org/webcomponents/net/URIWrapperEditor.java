package org.webcomponents.net;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.net.URISyntaxException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StringUtils;

public class URIWrapperEditor extends PropertyEditorSupport {

	private final ClassLoader classLoader;

	/**
	 * Create a new URIEditor, converting "classpath:" locations into
	 * standard URIs (not trying to resolve them into physical resources).
	 */
	public URIWrapperEditor() {
		this.classLoader = null;
	}

	/**
	 * Create a new URIEditor, using the given ClassLoader to resolve
	 * "classpath:" locations into physical resource URLs.
	 * @param classLoader the ClassLoader to use for resolving "classpath:" locations
	 * (may be <code>null</code> to indicate the default ClassLoader)
	 */
	public URIWrapperEditor(ClassLoader classLoader) {
		this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
	}


	public void setAsText(String text) throws IllegalArgumentException {
		if (StringUtils.hasText(text)) {
			String uri = text.trim();
			if (this.classLoader != null && uri.startsWith(ResourceUtils.CLASSPATH_URL_PREFIX)) {
				ClassPathResource resource =
						new ClassPathResource(uri.substring(ResourceUtils.CLASSPATH_URL_PREFIX.length()), this.classLoader);
				try {
					String url = resource.getURL().toString();
					setValue(createURI(url));
				}
				catch (IOException ex) {
					throw new IllegalArgumentException("Could not retrieve URI for " + resource + ": " + ex.getMessage());
				}
				catch (URISyntaxException ex) {
					throw new IllegalArgumentException("Invalid URI syntax: " + ex);
				}
			}
			else {
				try {
					setValue(createURI(uri));
				}
				catch (URISyntaxException ex) {
					throw new IllegalArgumentException("Invalid URI syntax: " + ex);
				}
			}
		}
		else {
			setValue(null);
		}
	}

	/**
	 * Create a URI instance for the given (resolved) String value.
	 * <p>The default implementation uses the <code>URI(String)</code>
	 * constructor, replacing spaces with "%20" quotes first.
	 * @param value the value to convert into a URI instance
	 * @return the URI instance
	 * @throws URISyntaxException if URI conversion failed
	 */
	protected URIWrapper createURI(String value) throws URISyntaxException {
		return URIWrapper.create(StringUtils.replace(value, " ", "%20"));
	}


	public String getAsText() {
		URIWrapper value = (URIWrapper) getValue();
		return (value != null ? value.toString() : "");
	}
}
