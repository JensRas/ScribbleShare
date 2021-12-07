package com.example.scribbleshare;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * A singleton class used to hold static information to be globally accessable to the app.
 */
public class MySingleton {

    /**
     * The instance of the singleton for the application
     */
    private static MySingleton instance;

    /**
     * The network request queue for the application
     */
    private RequestQueue requestQueue;

    /**
     * The context of the singleton/application
     */
    private static Context ctx;

    /**
     * The currently logged in user
     */
    private User user;

    public void setApplicationUser(User user){
        this.user = user;
    }

    public User getApplicationUser() {
        return user;
    }

    /**
     * Create a singleton for the scribbleshare application
     * @param context the context of the singleton for the app
     */
    private MySingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
        requestQueue.start();
    }

    /**
     * Get the current instance of the singleton. If it doesn't exist, create a new instance.
     * @param context The context of the application
     * @return The existing/new instance
     */
    public static synchronized MySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new MySingleton(context);
        }
        return instance;
    }

    /**
     * Get the request queue. If it doesn't exist, create a new one
     * @return The existing/new request queue
     */
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    /**
     * Add a new request to the request queue
     * @param req The request to add
     * @param <T> The type of request
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
