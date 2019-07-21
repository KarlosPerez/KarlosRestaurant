package com.karlosprojects.androidkarlosrestaurant.activitiy.UpdateInfoActivity.presenter;

import com.facebook.accountkit.Account;
import com.karlosprojects.androidkarlosrestaurant.activitiy.MainActivity.Model.User;
import com.karlosprojects.androidkarlosrestaurant.activitiy.UpdateInfoActivity.interactor.UpdateInfoActivityInteractor;
import com.karlosprojects.androidkarlosrestaurant.activitiy.UpdateInfoActivity.interactor.UpdateInfoActivityInteractorImpl;
import com.karlosprojects.androidkarlosrestaurant.activitiy.UpdateInfoActivity.view.UpdateInfoActivityView;

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
        if(isProgressDialogShowing()) updateInfoActivityView.hideProgressDialog();
        updateInfoActivityView.goToActivity(currentUser, activityClass);
    }

    @Override
    public void detachDisposable() {
        updateInfoActivityInteractor.detachDisposable();
    }

    @Override
    public void showUnsuccessMessage(String message) {
        if(isProgressDialogShowing()) updateInfoActivityView.hideProgressDialog();
        updateInfoActivityView.showUnsuccessMessage(message);
    }

    @Override
    public void showThrowableMessage(String message) {
        if(isProgressDialogShowing()) updateInfoActivityView.hideProgressDialog();
        updateInfoActivityView.showThrowableMessage(message);
    }

    @Override
    public void showProgressDialog() {
        updateInfoActivityView.showProgressDialog();
    }

    @Override
    public void hideProgressDialog() {
        updateInfoActivityView.hideProgressDialog();
    }

    @Override
    public boolean isProgressDialogShowing() {
        return updateInfoActivityView.isProgressDialogShowing();
    }
}
