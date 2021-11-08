package com.example.scribbleshare.signinpage;

/**
 * Holds methods that sign in needs
 */
public interface SignInView {
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
