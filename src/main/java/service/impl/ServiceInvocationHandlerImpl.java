package service.impl;

import exception.PersistentException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServiceInvocationHandlerImpl implements InvocationHandler {
	private final Logger logger = LogManager.getLogger(getClass().getName());

	private ServiceImpl service;

	public ServiceInvocationHandlerImpl(ServiceImpl service) {
		this.service = service;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments) throws Throwable {
		try {
			Object result = method.invoke(service, arguments);
			service.transaction.commit();
			return result;
		} catch(PersistentException e) {
			rollback(method);
			throw e;
		} catch(InvocationTargetException e) {
			rollback(method);
			throw e.getCause();
		}
	}

	private void rollback(Method method) {
		try {
			service.transaction.rollback();
		} catch(PersistentException e) {
			logger.warn("It is impossible to rollback transaction", e);
		}
	}
}
