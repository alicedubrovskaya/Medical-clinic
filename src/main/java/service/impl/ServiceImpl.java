package service.impl;

import dao.Transaction;
import service.Service;

abstract public class ServiceImpl implements Service {
	protected Transaction transaction = null;

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}
}
