package com.example.scribbleshare.createaccountpage;

/**
 * Holds methods that create account needs
 */
public interface CreateAccountView {
    //add methods here that the create account view needs to update things in the UI
    //then override them in the Create_Account class and actually implement them

    /**
     * Creates a toast
     * @param message message to be displayed in toast
     */
    void makeToast(String message);

    /**
     * Switches view
     * @param c class of view to switch to
     */
    void switchView(Class c);
}
