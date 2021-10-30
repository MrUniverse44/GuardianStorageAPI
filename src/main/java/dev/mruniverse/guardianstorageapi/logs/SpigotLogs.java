package dev.mruniverse.guardianstorageapi.logs;

import dev.mruniverse.guardianstorageapi.interfaces.GLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class SpigotLogs implements GLogger {
    private final String hidePackage;
    private String pluginName = "GuardianStorageAPI";
    private String containIdentifier = "mruniverse";

    /**
     * Call the External Logger
     *
     * @param pluginName this is the name of your plugin.
     * @param hidePackage hide package example: dev.mruniverse.guardianrftb.
     */
    public SpigotLogs(String pluginName,String hidePackage) {
        this.hidePackage = hidePackage;
        if(pluginName != null) this.pluginName = pluginName;
    }

    /**
     * Call the External Logger
     *
     * @param pluginName this is the name of your plugin.
     * @param hidePackage hide package example: dev.mruniverse.guardianrftb.
     * @param containIdentifier when a package contain this word this package will show in Internal - StackTrace
     */
    public SpigotLogs(String pluginName,String hidePackage, String containIdentifier) {
        this.hidePackage = hidePackage;
        if(pluginName != null) this.pluginName = pluginName;
        if(containIdentifier == null) return;
        this.containIdentifier = containIdentifier;
    }

    /**
     * Colorize a string provided to method
     *
     * @param message Message to transform.
     * @return transformed message with colors.
     */
    public String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Send a error message to console.
     * @param message message to send.
     */
    public void error(String message) {
        sendMessage("&f[&cERROR &7| &f" + pluginName + "] " + message);
    }
    /**
     * Send a error message to console.
     * @param throwable throwable to send.
     */
    public void error(Throwable throwable) {
        String location = throwable.getClass().getName();
        String error = throwable.getClass().getSimpleName();
        sendMessage("&f[&cERROR &7| &f" + pluginName + "] -------------------------");
        sendMessage("&f[&cERROR &7| &f" + pluginName + "] &aLocation: " + location.replace("." + error,""));
        sendMessage("&f[&cERROR &7| &f" + pluginName + "] &aError: " + error);
        if(throwable.getStackTrace() != null) {
            sendMessage("&f[&cERROR &7| &f" + pluginName + "] &6Internal - StackTrace: ");
            List<StackTraceElement> other = new ArrayList<>();
            for(StackTraceElement line : throwable.getStackTrace()) {
                if(line.toString().contains(containIdentifier)) {
                    sendMessage("&f[&cERROR &7| &f" + pluginName + "] &b(Line: " + line.getLineNumber() + ") " + line.toString().replace("(" + line.getFileName() + ":" + line.getLineNumber() + ")","").replace(hidePackage,""));
                } else {
                    other.add(line);
                }
            }
            sendMessage("&f[&cERROR &7| &f" + pluginName + "]  -------------------------");
            sendMessage("&f[&cERROR &7| &f" + pluginName + "] &6External - StackTrace: ");
            for(StackTraceElement line : other) {
                sendMessage("&f[&cERROR &7| &f" + pluginName + "] &b(Line: " + line.getLineNumber() + ") (Class: " + line.getFileName() + ") (Method: " + line.getMethodName() + ")".replace(".java",""));
            }

        }
        sendMessage("&f[&cERROR &7| &f" + pluginName + "]  -------------------------");
    }

    /**
     * Send a warn message to console.
     * @param message message to send.
     */
    public void warn(String message) {
        sendMessage("&f[&eWARN &7| &f" + pluginName + "] " + message);
    }

    /**
     * Send a debug message to console.
     * @param message message to send.
     */
    public void debug(String message) {
        sendMessage("&f[&9DEBUG &7| &f" + pluginName + "] " + message);
    }

    /**
     * Send a info message to console.
     * @param message message to send.
     */
    public void info(String message) {
        sendMessage("&f[&bINFO &7| &f" + pluginName + "] " + message);
    }

    /**
     * Sends a message to a Bukkit command sender.
     *
     * @param sender Bukkit CommandSender
     * @param message Message to send.
     */
    public void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(color(message));
    }


    /**
     * Used to other methods and prevent this copy pasta
     * to those methods.
     *
     * @param message Provided message
     */
    public void sendMessage(String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(color(message));
    }
}
