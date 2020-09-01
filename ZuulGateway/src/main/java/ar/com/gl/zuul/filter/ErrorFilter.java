package ar.com.gl.zuul.filter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class ErrorFilter extends ZuulFilter {

	protected static final String SEND_ERROR_FILTER_RAN = "sendErrorFilter.ran";

	Logger log = LoggerFactory.getLogger(PreFilter.class);

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		return false;
	}

	@Override
	public Object run() {
		 RequestContext context = RequestContext.getCurrentContext();
	        Throwable throwable = context.getThrowable();
	        log.error("this is a ErrorFilter :{}",throwable.getCause().getMessage());
	        context.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	        context.set("error.message",throwable.getCause().getMessage());
	        return null;
	}

	@Override
	public String filterType() {
		return "error";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
