package com.gmail.picono435.picojobs.api;

import com.gmail.picono435.picojobs.api.field.RequiredField;

import java.util.UUID;

public abstract class WorkZoneImplementation {

    protected String requiredPlugin = "PicoJobs";

    /**
     * Returns an upper case name of the implementation
     *
     * @return economy name
     */
    public abstract String getName();

    /**
     * This method is automatically called during the implementation registry
     * proccess if the required plugin for this implementation is found and enabled. <br>
     * <br>
     * This should be where you set up the required field for your implementation.
     *
     */
    public void onRegister() {};

    /**
     * Check if a player is located in the work zone
     *
     * @param player player to check if it is in the region
     * @return true if player is in work zone false if not
     */
    public abstract boolean isInWorkZone(UUID player);

    /**
     * Gets the required plugin in order to this implementation be enabled
     *
     * @return required plugin
     */
    public String getRequiredPlugin() {
        return requiredPlugin;
    }

    /**
     * Gets the required field in order to this implementation work correctly
     * Required field is currently only used in PicoJobs editor but should be set for a better user experience
     *
     * @return required field
     */
    public RequiredField<?, ?> getRequiredField() {
        return null;
    };

}
