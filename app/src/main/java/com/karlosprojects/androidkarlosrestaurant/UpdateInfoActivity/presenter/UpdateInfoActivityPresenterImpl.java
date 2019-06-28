package com.karlosprojects.androidkarlosrestaurant.UpdateInfoActivity.presenter;

import com.facebook.accountkit.Account;
import com.karlosprojects.androidkarlosrestaurant.MainActivity.Model.User;
import com.karlosprojects.androidkarlosrestaurant.UpdateInfoActivity.interactor.UpdateInfoActivityInteractor;
import com.karlosprojects.androidkarlosrestaurant.UpdateInfoActivity.interactor.UpdateInfoActivityInteractorImpl;
import com.karlosprojects.androidkarlosrestaurant.UpdateInfoActivity.view.UpdateInfoActivityView;

public class UpdateInfoActivityPresenterImpl implements UpdateInfoActivityPresenter {

    private UpdateInfoActivityView updateInfoActivityView;
    private UpdateInfoActivityInteractor updateInfoActivityInteractor;

    public UpdateInfoActivityPresenterImpl(UpdateInfoActivityView updateInfoActivityView) {
        this.updateInfoActivityView = updateInfoActivityView;
        updateInfoActivityInteractor = new UpdateInfoActivityInteractorImpl(this);
    }

    @Override
    public void updateUserInfo(Account account, String user_name, String user_address) {
        updateInfoActivityInteractor.updateUserInfo(account, user_name, user_address);
    }

    @Override
    public void goToActivity(User currentUser, Class<?> activityClass) {
        updateInfoActivityView.goToActivity(currentUser, activityClass);
    }

    @Override
    public void detachDisposable() {
        updateInfoActivityInteractor.detachDisposable();
    }

    @Override
    public void showUnsuccessMessage(String message) {
        updateInfoActivityView.showUnsuccessMessage(message);
    }

    @Override
    public void showThrowableMessage(String message) {
        updateInfoActivityView.showThrowableMessage(message);
    }
}
