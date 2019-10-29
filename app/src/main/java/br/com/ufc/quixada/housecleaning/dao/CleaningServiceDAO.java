package br.com.ufc.quixada.housecleaning.dao;

import java.util.List;

import br.com.ufc.quixada.housecleaning.transactions.CleaningService;

public interface CleaningServiceDAO extends GenericDAO<CleaningService> {

    List<CleaningService> findAllByResponsible(Integer responsibleId);

    List<CleaningService> findAllByRequester(Integer requesterId);

}
