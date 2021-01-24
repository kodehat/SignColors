/*
 * SignColors is a plug-in for Spigot adding colors and formatting to signs.
 * Copyright (C) 2021 CodeHat
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package de.codehat.signcolors.module;

import dagger.Module;
import dagger.Provides;
import de.codehat.signcolors.SignColors;
import de.codehat.signcolors.dao.ISignLocationDao;
import de.codehat.signcolors.dao.SignLocationDao;
import de.codehat.signcolors.database.IDatabase;
import de.codehat.signcolors.util.SimpleLogger;
import java.io.File;
import java.sql.SQLException;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.inject.Singleton;
import org.bukkit.configuration.file.FileConfiguration;

@Module
public interface SignColorsBukkitModule {

  @Provides
  @Singleton
  static Logger provideLogger(SignColors plugin) {
    return plugin.getLogger();
  }

  @Provides
  @Singleton
  static FileConfiguration provideFileConfiguration(SignColors plugin) {
    return plugin.getConfig();
  }

  @Provides
  @Singleton
  @Named("dataFolder")
  static File provideDataFolder(SimpleLogger logger, SignColors plugin) {
    File dataFolder = plugin.getDataFolder();
    if (dataFolder.mkdirs()) {
      logger.info("Created data folder as it did not exist.");
    }
    return dataFolder;
  }

  @Provides
  @Singleton
  static ISignLocationDao provideSignLocationDao(SimpleLogger logger, IDatabase database) {
    try {
      return new SignLocationDao(database);
    } catch (SQLException e) {
      logger.error("Unable to provide Dao for SignLocation model!", e);
      return null;
    }
  }
}
