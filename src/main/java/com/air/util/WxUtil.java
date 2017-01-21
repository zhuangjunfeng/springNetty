/**
 * 
 */
package com.air.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.apache.log4j.Logger;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Map;

/**
 * @author grossopaforever@gmail.com
 * @version Jul 28, 2013
 * 
 */
public class WxUtil {
	private static Logger logger = Logger.getLogger(WxUtil.class);


	private WxUtil() {
	}

	public static final Long currentTimeInSec() {
		return Long.valueOf(new Date().getTime() / 1000);
	}

	@SuppressWarnings("unchecked")
	public static final <T> T sendRequest(String url, HttpMethod method,
			Map<String, String> params, HttpEntity requestEntity,
			Class<T> resultClass) {
		HttpClient client = HttpClientBuilder.create().build();
		HttpRequestBase request = null;

		try {
			if (HttpMethod.GET.equals(method)) {
				request = new HttpGet();
			} else if (HttpMethod.POST.equals(method)) {
				request = new HttpPost();
				if (requestEntity != null) {
					((HttpPost) request).setEntity(requestEntity);
				}
			}
			URIBuilder builder = new URIBuilder(url);

			if (params != null) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					builder.addParameter(entry.getKey(), entry.getValue());
				}
			}
			request.setURI(builder.build());

			HttpResponse response = client.execute(request);
			HttpEntity entity = response.getEntity();
			String respBody = EntityUtils.toString(entity);
			if (entity != null) {
				EntityUtils.consume(entity);
			}

			if (String.class.isAssignableFrom(resultClass)) {
				return (T) respBody;
			} else {
				ObjectMapper mapper = new ObjectMapper();
				T result = mapper.readValue(respBody, resultClass);
				return result;
			}

		} catch (IOException e) {
			logger.error(e);
		} catch (URISyntaxException e) {
			logger.error(e);
		}
		return null;
	}



}
