package com.sdu.samus.filter;

import com.sdu.samus.Constants;
import com.sdu.samus.enums.ResultCode;
import com.sdu.samus.exception.ServiceException;
import com.sdu.samus.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 	过滤器
 */
public class SessionFilter implements Filter {
	private static final Logger logger= LoggerFactory.getLogger(SessionFilter.class);

	/**
	 * 封装，不需要过滤的list列表
	 */
	protected static List<Pattern> patterns = new ArrayList<Pattern>();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String loginRegex = ".*/login";
		String registerRegex = ".*/register";
		patterns.add(Pattern.compile(loginRegex));
		patterns.add(Pattern.compile(registerRegex));
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException,ServiceException {
		HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
		HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
		logger.info("-----------------进入SessionFilter--------------------");
		String url = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		logger.info("SessionFilter --- [httpRequest.getRequestURI   :]"+httpRequest.getRequestURI());
		logger.info("SessionFilter --- [httpRequest.getContextPath   :]"+httpRequest.getContextPath());
		if (url.startsWith("/") && url.length() > 1) {
			url = url.substring(1);
		}
		logger.info("SessionFilter --- [url  :]"+url);
		if (isInclude(url)){
			chain.doFilter(httpRequest, httpResponse);
		} else {
			HttpSession session = httpRequest.getSession();
			UserInfo user = (UserInfo)session.getAttribute(Constants.USER);

			if (user != null){
				// session存在
				logger.info("SessionFilter --- [user  :]"+user);
				logger.info("SessionFilter --- [session存在]");
				chain.doFilter(httpRequest, httpResponse);
			} else {
				logger.info("SessionFilter --- [session不存在]");
				throw new ServiceException(ResultCode.NOT_LOGIN);
//				// session不存在 准备跳转失败
//                /* RequestDispatcher dispatcher = request.getRequestDispatcher(path);
//                    dispatcher.forward(request, response);或
//                    servletResponse.sendRedirect(PathUtil.getFullPath("user/login"));*/
//				chain.doFilter(httpRequest, httpResponse);
			}
		}
	}

	@Override
	public void destroy() {

	}

	/**
	 * 是否需要过滤
	 * @param url
	 * @return
	 */
	private boolean isInclude(String url) {
		for (Pattern pattern : patterns) {
			Matcher matcher = pattern.matcher(url);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}
}
