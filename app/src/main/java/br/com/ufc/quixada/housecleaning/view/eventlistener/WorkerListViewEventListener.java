package br.com.ufc.quixada.housecleaning.view.eventlistener;

import br.com.ufc.quixada.housecleaning.transactions.User;

public interface WorkerListViewEventListener {

    void onClickHireButton(User worker);

    void onClickViewDetailsButton(User worker);

}
