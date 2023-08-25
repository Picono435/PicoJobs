package com.gmail.picono435.picojobs.api;

import com.gmail.picono435.picojobs.api.utils.RequiredField;

import java.util.UUID;

public abstract class WorkZoneImplementation {

    protected String requiredPlugin;

    /**
     * Returns a upper case name of economy
     *
     * @return economy name
     */
    public abstract String getName();

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
    public RequiredField<?> getRequiredField() {
        return null;
    };

}
