package com.sdu.samus.filter;

import com.sdu.samus.AppContext;
import com.sdu.samus.Constants;
import com.sdu.samus.model.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AppContextFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(AppContextFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		logger.info("-----------------进入AppContextFilter--------------------");

		logger.info("AppContextFilter --- [request.getContextPath()]     :"+request.getContextPath());

		AppContext.setContextPath(request.getContextPath());

		AppContext appContext = AppContext.getContext();

		HttpSession session = request.getSession();

		logger.info("AppContextFilter --- [session.getId()]     :"+session.getId());

		UserInfo user = (UserInfo)session.getAttribute(Constants.USER);

		logger.info("AppContextFilter --- [UserInfo]     :"+user);

		appContext.addObject(Constants.APP_CONTEXT_USER, user);
		appContext.addObject(Constants.APP_CONTEXT_SESSION, session);

		try{
			filterChain.doFilter(request,response);
		}finally {
			appContext.clear();
		}
	}

	@Override
	public void destroy() {

	}
}
