/*
 * package ar.com.gl.zuul.controller;
 * 
 * import java.io.BufferedReader; import java.util.Enumeration;
 * 
 * import javax.servlet.http.HttpServletRequest;
 * 
 * import org.slf4j.Logger; import org.slf4j.LoggerFactory; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RestController;
 * 
 * @RestController("zullController") public class ZuulController {
 * 
 * Logger log = LoggerFactory.getLogger(ZuulController.class);
 * 
 * @RequestMapping(path = "/api") public String test(HttpServletRequest request)
 * { StringBuffer strLog = new StringBuffer();
 * 
 * strLog.append(".....REQUEST RECEIVED EN /api.....\n");
 * strLog.append("Method: " + request.getMethod() + "\n"); strLog.append("URL: "
 * + request.getRequestURL() + "\n"); strLog.append("Remote host: " +
 * request.getRemoteHost() + "\n"); strLog.append("----MAP----\n");
 * request.getParameterMap().forEach((key, value) -> { for (int n = 0; n <
 * value.length; n++) { strLog.append("Clave:" + key + " Valor: " + value[n] +
 * "\n"); } }); strLog.append("\n----- Headers ----\n"); Enumeration<String>
 * nameHeaders = request.getHeaderNames(); while (nameHeaders.hasMoreElements())
 * { String name = nameHeaders.nextElement(); Enumeration<String> valueHeaders =
 * request.getHeaders(name); while (valueHeaders.hasMoreElements()) { String
 * value = valueHeaders.nextElement(); strLog.append("Key:" + name + " Value: "
 * + value + "\n"); } } try { strLog.append("\n----- BODY ----\n");
 * BufferedReader reader = request.getReader(); if (reader != null) { char[]
 * line = new char[100]; int nCaracteres; while ((nCaracteres =
 * reader.read(line, 0, 100)) > 0) { strLog.append(line);
 * 
 * if (nCaracteres != 100) break; } } } catch (Throwable e) {
 * e.printStackTrace(); } log.info(strLog.toString());
 * 
 * return "\n---------- ZUUL ------------\n" + strLog.toString(); } }
 */