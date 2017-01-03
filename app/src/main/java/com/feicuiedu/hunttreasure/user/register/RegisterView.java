package com.feicuiedu.hunttreasure.user.register;

/**
 * Created by admin on 2017-01-03.
 */

/**
 * 试图接口
 */
public interface RegisterView {
    void showProgress();
    void hideProgress();
    void showMessage(String msg);
    void navigationToHome();
}
