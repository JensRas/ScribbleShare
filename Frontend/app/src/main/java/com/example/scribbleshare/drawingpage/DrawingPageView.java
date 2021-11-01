package com.example.scribbleshare.drawingpage;

public interface DrawingPageView {
    public void setDrawingImage(byte[] data);
    public void makeToast(String message);
    void switchView(Class c);
}
