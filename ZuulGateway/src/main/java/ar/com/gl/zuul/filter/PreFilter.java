package ar.com.gl.zuul.filter;

import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class PreFilter extends ZuulFilter {

	Logger log = LoggerFactory.getLogger(PreFilter.class);

	@Override
	public boolean shouldFilter() { // ejecuta la funcion run
		return true;
	}

	@Override
	public Object run() throws ZuulException { // Se ejecuta en cada peticion
		RequestContext ctx = RequestContext.getCurrentContext();
		StringBuffer strLog = new StringBuffer();
		strLog.append("\n----- NEW REQUEST -----\n");
		strLog.append(String.format("Server: %s Method: %s Path %s \n", ctx.getRequest().getServerName(),
				ctx.getRequest().getMethod(), ctx.getRequest().getRequestURI()));
		Enumeration<String> enume = ctx.getRequest().getHeaderNames();
		String header;
		while (enume.hasMoreElements()) {
			header = enume.nextElement();
			strLog.append(String.format("Headers: %s = %s \n", header, ctx.getRequest().getHeader(header)));
		}
		;
		log.info(strLog.toString());
		return null;
	}

	@Override
	public String filterType() { // Se ejecuta antes de haber realizado la direccion - antes de llamar al
									// servidor final
		return "pre";
	}

	@Override
	public int filterOrder() { // Devuelve cuando se ejecuta este filtro
		return FilterConstants.SEND_RESPONSE_FILTER_ORDER;
	}

}
