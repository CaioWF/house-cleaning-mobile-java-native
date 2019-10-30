package br.com.ufc.quixada.housecleaning.view.eventlistener;

import br.com.ufc.quixada.housecleaning.transactions.CleaningService;

public interface CleaningServiceSolicitationListViewEventListener {

    void onClickAcceptSolicitation(CleaningService cleaningService);

    void onClickRefuseSolicitation(CleaningService cleaningService);

    void onClickFinalizeSolicitation(CleaningService cleaningService);

}
