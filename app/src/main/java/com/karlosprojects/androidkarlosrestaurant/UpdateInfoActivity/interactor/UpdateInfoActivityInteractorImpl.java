package com.karlosprojects.androidkarlosrestaurant.UpdateInfoActivity.interactor;

import com.facebook.accountkit.Account;
import com.karlosprojects.androidkarlosrestaurant.UpdateInfoActivity.presenter.UpdateInfoActivityPresenter;
import com.karlosprojects.androidkarlosrestaurant.UpdateInfoActivity.repository.UpdateInfoActivityRepository;
import com.karlosprojects.androidkarlosrestaurant.UpdateInfoActivity.repository.UpdateInfoActivityRepositoryImpl;

public class UpdateInfoActivityInteractorImpl implements UpdateInfoActivityInteractor{

    private UpdateInfoActivityRepository updateInfoActivityRepository;
    private UpdateInfoActivityPresenter updateInfoActivityPresenter;

    public UpdateInfoActivityInteractorImpl(UpdateInfoActivityPresenter updateInfoActivityPresenter) {
        this.updateInfoActivityPresenter = updateInfoActivityPresenter;
        updateInfoActivityRepository = new UpdateInfoActivityRepositoryImpl(updateInfoActivityPresenter);
    }

    @Override
    public void updateUserInfo(Account account, String user_name, String user_address) {
        updateInfoActivityRepository.updateUserInfo(account, user_name, user_address);
    }

    @Override
    public void detachDisposable() {
        updateInfoActivityRepository.detachDisposable();
    }
}
