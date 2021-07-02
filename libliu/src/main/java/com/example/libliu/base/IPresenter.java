package com.example.libliu.base;

public interface IPresenter {
    void attachView(IView view);
    void detachView();
}
