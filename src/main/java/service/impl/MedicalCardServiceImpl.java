package service.impl;

import dao.MedicalCardDao;
import domain.MedicalCard;
import exception.PersistentException;
import service.MedicalCardService;

import java.util.Collections;
import java.util.List;

public class MedicalCardServiceImpl extends ServiceImpl implements MedicalCardService {
    @Override
    public MedicalCard findById(Integer id) throws PersistentException {
        MedicalCardDao medicalCardDao = transaction.createDao(MedicalCardDao.class);
        MedicalCard medicalCard = medicalCardDao.read(id);
        if (medicalCard != null) {
            buildMedicalCard(Collections.singletonList(medicalCard));
        }
        return medicalCard;
    }

    @Override
    public void save(MedicalCard medicalCard) throws PersistentException {

    }

    @Override
    public void delete(Integer id) throws PersistentException {

    }

    private void buildMedicalCard(List<MedicalCard> medicalCards) throws PersistentException {
        //TODO
//        Map<Integer, Appointment> appointments = new HashMap<>();
//        for (MedicalCard medicalCard : medicalCards) {
//            appointments= AppointmentDao
    }
}

