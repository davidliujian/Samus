package com.sdu.samus;

import java.util.HashMap;

public class AppContext {
	private static final ThreadLocal<AppContext> appContext = new ThreadLocal<AppContext>();
	private final HashMap<String,Object> values = new HashMap<String, Object>();
	private static String contextPath;

	public static String getContextPath(){
		return contextPath;
	}

	public static void setContextPath(String contextPath){
		if(contextPath == null){
			AppContext.contextPath = contextPath;
		}
	}

	/**
	 *
	 * @return	返回当前线程的AppContext
	 */
	public static AppContext getContext(){
		AppContext context = appContext.get();
		if(context == null){
			context = new AppContext();
			appContext.set(context);
		}
		return context;
	}

	/**
	 * 清空values
	 */
	public void clear(){
		AppContext context = appContext.get();
		if(context != null){
			context.values.clear();
		}
		context = null;
	}

	/**
	 * 	向values添加数据
	 * @param key
	 * @param value
	 */
	public void addObject(String key , Object value){
		values.put(key, value);
	}

	/**
	 * 	获得values中对用key的对象
	 * @param key
	 * @return
	 */
	public Object getObject(String key){
		return values.get(key);
	}

	/**
	 *  清除当前key值对应的对象
	 * @param key
	 */
	public void removeObject(String key){
		values.remove(key);
	}
}
