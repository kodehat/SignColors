/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.database;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Abstract Database class, serves as a base for any connection method (MySQL, SQLite, etc.)
 *
 * @author -_Husky_-
 * @author tips48
 */
public abstract class Database {

    /**
     * Plugin instance, use for plugin.getDataFolder() and plugin.getLogger()
     */
    protected Plugin plugin;

    /**
     * Creates a new Database
     *
     * @param plugin Plugin instance
     */
    protected Database(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Opens a connection with the database
     *
     * @return Connection opened
     */
    public abstract Connection openConnection();

    /**
     * Checks if a connection is open with the database
     *
     * @return true if a connection is open
     */
    public abstract boolean checkConnection();

    /**
     * Gets the connection with the database
     *
     * @return Connection with the database, null if none
     */
    public abstract Connection getConnection();

    /**
     * Closes the connection with the database
     */
    public abstract void closeConnection();

    /**
     * Adds a location of a sign to the database.
     *
     * @param location Location of the sign (world, x, y, z).
     */
    public void addSign(Location location) {
        try {
            PreparedStatement ps = this.getConnection().prepareStatement("INSERT INTO " + this.plugin.getConfig().getString("mysql.table_prefix")
                    + "sign_locations (world, x, y, z) VALUES (?, ?, ?, ?)");
            ps.setString(1, location.getWorld().getName());
            ps.setInt(2, location.getBlockX());
            ps.setInt(3, location.getBlockY());
            ps.setInt(4, location.getBlockZ());
            ps.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * Checks if a location of a sign is stored in the database.
     *
     * @param location Location of the sign (world,x,y,z).
     * @return True if stored, false if not found.
     */
    public boolean checkSign(Location location) {
        try {
            PreparedStatement ps = this.getConnection().prepareStatement("SELECT * FROM " + this.plugin.getConfig().getString("mysql.table_prefix")
                    + "sign_locations WHERE world = ? AND x = ? AND y = ? AND z = ?");
            ps.setString(1, location.getWorld().getName());
            ps.setInt(2, location.getBlockX());
            ps.setInt(3, location.getBlockY());
            ps.setInt(4, location.getBlockZ());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    /**
     * Deletes a location of a sign from the database.
     *
     * @param location Location of the sign (world,x,y,z).
     */
    public void deleteSign(Location location) {
        try {
            PreparedStatement ps = this.getConnection().prepareStatement("DELETE FROM " + this.plugin.getConfig().getString("mysql.table_prefix")
                    + "sign_locations WHERE world = ? AND x = ? AND y = ? AND z = ?");
            ps.setString(1, location.getWorld().getName());
            ps.setInt(2, location.getBlockX());
            ps.setInt(3, location.getBlockY());
            ps.setInt(4, location.getBlockZ());
            ps.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}