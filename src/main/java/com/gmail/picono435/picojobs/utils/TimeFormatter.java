package com.gmail.picono435.picojobs.utils;

import java.util.concurrent.TimeUnit;

import com.gmail.picono435.picojobs.api.managers.LanguageManager;

public class TimeFormatter {
	
	public static String formatTimeInRealLife(long time) {
		if (time == 0) {
			return "now";
		}

		long days = TimeUnit.MILLISECONDS.toDays(time);
		long hours = TimeUnit.MILLISECONDS.toHours(time) - (days * 24);
		long minutes = TimeUnit.MILLISECONDS.toMinutes(time) - (TimeUnit.MILLISECONDS.toHours(time) * 60);
		long seconds = TimeUnit.MILLISECONDS.toSeconds(time) - (TimeUnit.MILLISECONDS.toMinutes(time) * 60);
		
		StringBuilder sb = new StringBuilder();
		
		if (days > 0)
			sb.append(days + (days == 1 ? " " + LanguageManager.getTimeFormat("day") : " " + LanguageManager.getTimeFormat("days")));
		
		if (hours > 0)
			sb.append(days > 0 ? (minutes > 0 ? ", " : " " + LanguageManager.getTimeFormat("and") + " ") : "").append(hours + (hours == 1 ? " " + LanguageManager.getTimeFormat("hour") : " "  + LanguageManager.getTimeFormat("hours")));
		
		if (minutes > 0)
			sb.append(days > 0 || hours > 0 ? (seconds > 0 ? ", " : " " + LanguageManager.getTimeFormat("and") + " ") : "").append(minutes + (minutes == 1 ? " "  + LanguageManager.getTimeFormat("minute") : " "  + LanguageManager.getTimeFormat("minutes")));
		
		if (seconds > 0)
			sb.append(days > 0 || hours > 0 || minutes > 0 ? " " + LanguageManager.getTimeFormat("and") + " " : (sb.length() > 0 ? ", " : "")).append(seconds + (seconds == 1 ? " "  + LanguageManager.getTimeFormat("second") : " "  + LanguageManager.getTimeFormat("seconds")));
		
		String s = sb.toString();
		return s.isEmpty() ? "0 " + LanguageManager.getTimeFormat("seconds") : s;
	}
	
	public static String formatTimeInMinecraft(long time) {
		if (time == 0) {
			return "now";
		}

		long secondsReal = time / 1000;
		long days = toMinecraftDays(secondsReal);
		long hours = toMinecraftHours(secondsReal) - (days * 24);
		long minutes = toMinecraftMinutes(secondsReal) - (toMinecraftHours(secondsReal) * 60);
		long seconds = toMinecraftSeconds(secondsReal) - (toMinecraftMinutes(secondsReal) * 60);
		
		int max = 0;
		
		StringBuilder sb = new StringBuilder();
		
		if (days > 0 && max <= 1) {
			sb.append(days + (days == 1 && max > 1 ? " minecraft " + LanguageManager.getTimeFormat("day") : " minecraft " + LanguageManager.getTimeFormat("days")));
			max++;
		}
		
		if (hours > 0 && max <= 1) {
			sb.append(days > 0 ? (minutes > 0 && max > 1 ? ", " : " " + LanguageManager.getTimeFormat("and") + " ") : "").append(hours + (hours == 1 ? " minecraft " + LanguageManager.getTimeFormat("hour") : " minecraft "  + LanguageManager.getTimeFormat("hours")));
			max++;
		}
		
		if (minutes > 0 && max <= 1) {
			sb.append(days > 0 || hours > 0 && max > 1 ? (seconds > 0 ? ", " : " " + LanguageManager.getTimeFormat("and") + " ") : "").append(minutes + (minutes == 1 ? " minecraft "  + LanguageManager.getTimeFormat("minute") : " minecraft "  + LanguageManager.getTimeFormat("minutes")));
			max++;
		}
		
		if (seconds > 0 && max <= 1) {
			sb.append(days > 0 || hours > 0 && max > 1 || minutes > 0 ? " " + LanguageManager.getTimeFormat("and") + " " : (sb.length() > 0 ? ", " : "")).append(seconds + (seconds == 1 ? " minecraft "  + LanguageManager.getTimeFormat("second") : " minecraft "  + LanguageManager.getTimeFormat("seconds")));
			max++;
		}
		
		String s = sb.toString();
		return s.isEmpty() ? "0 minecraft " + LanguageManager.getTimeFormat("seconds") : s;
	}
	
	private static long toMinecraftDays(long seconds) {
		return seconds * 72 / 60 / 60 / 24;
	}
	
	private static long toMinecraftHours(long seconds) {
		return seconds * 72 / 60 / 60;
	}
	
	private static long toMinecraftMinutes(long seconds) {
		return seconds * 72 / 60;
	}
	
	private static long toMinecraftSeconds(long seconds) {
		return seconds * 72;
	}
	
}