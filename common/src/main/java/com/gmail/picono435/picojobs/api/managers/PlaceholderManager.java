package com.gmail.picono435.picojobs.api.managers;

import com.gmail.picono435.picojobs.api.placeholders.JobPlaceholders;
import com.gmail.picono435.picojobs.api.placeholders.JobPlayerPlaceholders;
import com.gmail.picono435.picojobs.api.placeholders.PlaceholderExtension;

public class PlaceholderManager {

    public static String NULL_PLACEHOLDER = "[NULL_PLACEHOLDER]";

    private final JobPlaceholders jobPlaceholders;
    private final JobPlayerPlaceholders jobPlayerPlaceholders;

    public PlaceholderManager() {
        this.jobPlaceholders = new JobPlaceholders();
        this.jobPlayerPlaceholders = new JobPlayerPlaceholders();
    }

    /**
     * Gets the job placeholder extension (prefixed as job).
     *
     * This is a feature that will be added in the future
     * @return the job placeholder extension
     */
    public JobPlaceholders getJobPlaceholders() {
        return jobPlaceholders;
    }

    /**
     * Gets the job placeholder extension (prefixed as jobplayer)
     *
     * @return the jobplayer placeholder extension
     */
    public JobPlayerPlaceholders getJobPlayerPlaceholders() {
        return jobPlayerPlaceholders;
    }

    /**
     * Gets a PicoJobs placeholder extension by ID
     *
     * @param prefix
     * @return the placeholder extension or null if not found
     */
    public PlaceholderExtension getExtensionByPrefix(String prefix) {
        switch (prefix) {
            case JobPlaceholders.PREFIX: {
                return this.jobPlaceholders;
            }
            case JobPlayerPlaceholders.PREFIX: {
                return this.jobPlayerPlaceholders;
            }
        }
        return null;
    }



    public PlaceholderExtension[] getExtensions() {
        return new PlaceholderExtension[]{this.jobPlaceholders, this.jobPlayerPlaceholders};
    }
}
