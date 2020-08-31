package ar.com.gl.zuul.filter;

import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.CharStreams;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class PostFilter extends ZuulFilter {

	Logger log = LoggerFactory.getLogger(PreFilter.class);

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {

		RequestContext context = RequestContext.getCurrentContext();
		try (final InputStream responseDataStream = context.getResponseDataStream()) {

			if (responseDataStream == null) {
				log.info("BODY: {}", "");
				return null;
			}
			log.info("\n----- RESPONSE-----\n");
			String responseData = CharStreams.toString(new InputStreamReader(responseDataStream, "UTF-8"));
			log.info("\nBODY: \n{} ",  responseData+"\n");

			context.setResponseBody(responseData);
		} catch (Exception e) {
			log.error(e.getMessage());
			//throw new ZuulException(e, 500, e.getMessage());
		}

		return null;

	}

	@Override
	public String filterType() {
		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
