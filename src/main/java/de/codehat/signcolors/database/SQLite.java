/*
 * Copyright (c) 2017 CodeHat.
 * This file is part of 'SignColors' and is licensed under GPLv3.
 */

package de.codehat.signcolors.database;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Connects to and uses a SQLite database
 *
 * @author tips48
 */
public class SQLite extends Database {
    private final String dbLocation;

    private Connection connection;

    /**
     * Creates a new SQLite instance
     *
     * @param plugin     Plugin instance
     * @param dbLocation Location of the Database (Must end in .db)
     */
    public SQLite(Plugin plugin, String dbLocation) {
        super(plugin);
        this.dbLocation = dbLocation;
    }

    @Override
    public Connection openConnection() {
        File file = new File(plugin.getDataFolder().toPath().toString() + File.separator + dbLocation);
        if (!(file.exists())) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Unable to create database!");
                e.printStackTrace();
            }
        }
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder().toPath().toString() + File.separator + dbLocation);
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not connect to SQLite server! because: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            plugin.getLogger().log(Level.SEVERE, "JDBC Driver not found!");
            e.printStackTrace();
        }
        return connection;
    }

    @Override
    public boolean checkConnection() {
        try {
            return !(connection.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                plugin.getLogger().log(Level.SEVERE, "Error closing the SQLite Connection!");
                e.printStackTrace();
            }
        }
    }

}