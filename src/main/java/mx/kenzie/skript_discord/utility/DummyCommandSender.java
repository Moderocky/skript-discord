package mx.kenzie.skript_discord.utility;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.UUID;

public interface DummyCommandSender extends CommandSender {

    //<editor-fold desc="Unused command sender methods." defaultstate="collapsed">
    default void sendMessage(@NotNull String message) {
        this.sendMessage(new String[] {message});
    }

    default void sendMessage(@Nullable UUID sender, @NotNull String message) {
        this.sendMessage(message);
    }

    default void sendMessage(@Nullable UUID sender, @NotNull String... messages) {
        this.sendMessage(messages);
    }

    @NotNull
    default Server getServer() {
        return Bukkit.getServer();
    }

    @NotNull
    String getName();

    @NotNull
    default CommandSender.Spigot spigot() {
        return new CommandSender.Spigot();
    }

    @NotNull
    default Component name() {
        return Component.text(this.getName());
    }

    default boolean isPermissionSet(@NotNull String name) {
        return false;
    }

    default boolean isPermissionSet(@NotNull Permission perm) {
        return false;
    }

    default boolean hasPermission(@NotNull String name) {
        return false;
    }

    default boolean hasPermission(@NotNull Permission perm) {
        return false;
    }

    @NotNull
    default PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    default PermissionAttachment addAttachment(@NotNull Plugin plugin) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    default PermissionAttachment addAttachment(@NotNull Plugin plugin, @NotNull String name, boolean value,
                                               int ticks) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    default PermissionAttachment addAttachment(@NotNull Plugin plugin, int ticks) {
        throw new UnsupportedOperationException();
    }

    default void removeAttachment(@NotNull PermissionAttachment attachment) {
        throw new UnsupportedOperationException();
    }

    default void recalculatePermissions() {

    }

    @NotNull
    default Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return Set.of();
    }

    default boolean isOp() {
        return false;
    }

    default void setOp(boolean value) {

    }
    //</editor-fold>

}
