package service;

import domain.MedicalCard;
import exception.PersistentException;

public interface MedicalCardService extends Service {

	MedicalCard findById(Integer id) throws PersistentException;

	void save(MedicalCard medicalCard) throws PersistentException;

	void delete(Integer id) throws PersistentException;
}
