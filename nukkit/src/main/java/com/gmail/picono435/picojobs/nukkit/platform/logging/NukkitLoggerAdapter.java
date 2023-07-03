package com.gmail.picono435.picojobs.nukkit.platform.logging;

import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.utils.LogLevel;
import cn.nukkit.utils.MainLogger;
import com.gmail.picono435.picojobs.nukkit.PicoJobsNukkit;
import com.gmail.picono435.picojobs.nukkit.platform.NukkitInventoryAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

public class NukkitLoggerAdapter implements Logger {

    private Logger mainLogger;
    private PluginLogger pluginLogger;

    public NukkitLoggerAdapter(PluginLogger pluginLogger) {
        mainLogger = LoggerFactory.getLogger(MainLogger.class);
        this.pluginLogger = pluginLogger;
    }

    @Override
    public String getName() {
        return PicoJobsNukkit.getInstance().getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return mainLogger.isTraceEnabled();
    }

    @Override
    public void trace(String msg) {
        pluginLogger.log(LogLevel.DEBUG, msg);
    }

    @Override
    public void trace(String format, Object arg) {}

    @Override
    public void trace(String format, Object arg1, Object arg2) {}

    @Override
    public void trace(String format, Object... arguments) {}

    @Override
    public void trace(String msg, Throwable t) {
        pluginLogger.log(LogLevel.DEBUG, msg, t);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return mainLogger.isTraceEnabled(marker);
    }

    @Override
    public void trace(Marker marker, String msg) {}

    @Override
    public void trace(Marker marker, String format, Object arg) {}

    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {}

    @Override
    public void trace(Marker marker, String format, Object... argArray) {}

    @Override
    public void trace(Marker marker, String msg, Throwable t) {}

    @Override
    public boolean isDebugEnabled() {
        return mainLogger.isDebugEnabled();
    }

    @Override
    public void debug(String msg) {
        pluginLogger.log(LogLevel.DEBUG, msg);
    }

    @Override
    public void debug(String format, Object arg) {}

    @Override
    public void debug(String format, Object arg1, Object arg2) {}

    @Override
    public void debug(String format, Object... arguments) {}

    @Override
    public void debug(String msg, Throwable t) {
        pluginLogger.log(LogLevel.DEBUG, msg, t);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return mainLogger.isDebugEnabled(marker);
    }

    @Override
    public void debug(Marker marker, String msg) {}

    @Override
    public void debug(Marker marker, String format, Object arg) {}

    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {}

    @Override
    public void debug(Marker marker, String format, Object... arguments) {}

    @Override
    public void debug(Marker marker, String msg, Throwable t) {}

    @Override
    public boolean isInfoEnabled() {
        return mainLogger.isInfoEnabled();
    }

    @Override
    public void info(String msg) {
        pluginLogger.log(LogLevel.INFO, msg);
    }

    @Override
    public void info(String format, Object arg) {}

    @Override
    public void info(String format, Object arg1, Object arg2) {}

    @Override
    public void info(String format, Object... arguments) {}

    @Override
    public void info(String msg, Throwable t) {
        pluginLogger.log(LogLevel.INFO, msg, t);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return mainLogger.isInfoEnabled(marker);
    }

    @Override
    public void info(Marker marker, String msg) {}

    @Override
    public void info(Marker marker, String format, Object arg) {}

    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {}

    @Override
    public void info(Marker marker, String format, Object... arguments) {}

    @Override
    public void info(Marker marker, String msg, Throwable t) {}

    @Override
    public boolean isWarnEnabled() {
        return mainLogger.isWarnEnabled();
    }

    @Override
    public void warn(String msg) {
        pluginLogger.log(LogLevel.WARNING, msg);
    }

    @Override
    public void warn(String format, Object arg) {}

    @Override
    public void warn(String format, Object... arguments) {}

    @Override
    public void warn(String format, Object arg1, Object arg2) {}

    @Override
    public void warn(String msg, Throwable t) {
        pluginLogger.log(LogLevel.WARNING, msg, t);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return mainLogger.isWarnEnabled(marker);
    }

    @Override
    public void warn(Marker marker, String msg) {}

    @Override
    public void warn(Marker marker, String format, Object arg) {}

    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {}

    @Override
    public void warn(Marker marker, String format, Object... arguments) {}

    @Override
    public void warn(Marker marker, String msg, Throwable t) {}

    @Override
    public boolean isErrorEnabled() {
        return mainLogger.isErrorEnabled();
    }

    @Override
    public void error(String msg) {
        pluginLogger.log(LogLevel.ERROR, msg);
    }

    @Override
    public void error(String format, Object arg) {}

    @Override
    public void error(String format, Object arg1, Object arg2) {}

    @Override
    public void error(String format, Object... arguments) {}

    @Override
    public void error(String msg, Throwable t) {
        pluginLogger.log(LogLevel.ERROR, msg, t);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return mainLogger.isErrorEnabled(marker);
    }

    @Override
    public void error(Marker marker, String msg) {}

    @Override
    public void error(Marker marker, String format, Object arg) {}

    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {}

    @Override
    public void error(Marker marker, String format, Object... arguments) {}

    @Override
    public void error(Marker marker, String msg, Throwable t) {}
}
