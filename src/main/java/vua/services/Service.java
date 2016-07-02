package vua.services;

import com.google.inject.ImplementedBy;

@ImplementedBy(ServiceImpl.class)
public interface Service {
    /**
     * Start the application
     */
    void start();

    /**
     * Stop the application
     */
    void stop();

    /**
     * Whether the application is started
     *
     * @return True if the application is started
     */
    boolean isStarted();

    /**
     * Get the state of the service
     *
     * @return The state
     */
    State getState();

    /**
     * Get the time that the service has been up for
     *
     * @return The time that the service has been up for
     */
    long getUpTime();
}