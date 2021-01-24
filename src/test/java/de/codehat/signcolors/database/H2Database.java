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
package de.codehat.signcolors.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import java.sql.SQLException;

public class H2Database implements IDatabase {

  public static final String CONFIGURATION_KEY = "h2";

  private static final String H2_CONNECTION_TEMPLATE = "jdbc:h2:mem:sign_colors";

  @Override
  public String getConfigurationKey() {
    return CONFIGURATION_KEY;
  }

  @Override
  public JdbcConnectionSource getConnectionSource() throws SQLException {
    return new JdbcConnectionSource(H2_CONNECTION_TEMPLATE);
  }
}
