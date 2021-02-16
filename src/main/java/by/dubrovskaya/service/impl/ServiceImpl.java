package by.dubrovskaya.service.impl;

import by.dubrovskaya.dao.Transaction;
import by.dubrovskaya.service.Service;

abstract public class ServiceImpl implements Service {
	protected Transaction transaction = null;

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
}
