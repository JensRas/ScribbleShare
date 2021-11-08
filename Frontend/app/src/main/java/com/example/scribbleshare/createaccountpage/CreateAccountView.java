package com.example.scribbleshare.createaccountpage;

/**
 *
 */
public interface CreateAccountView {
    //add methods here that the create account view needs to update things in the UI
    //then override them in the Create_Account class and actually implement them

    /**
     *
     * @param message
     */
    void makeToast(String message);

    /**
     *
     * @param c
     */
    void switchView(Class c);
}
