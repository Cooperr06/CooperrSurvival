package de.cooperr.cooperrsurvival.util;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import de.cooperr.cooperrsurvival.CooperrSurvival;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Level;

@Getter
public class MySqlConnection {

    private final CooperrSurvival plugin;
    private final String host;
    private final int port;
    private final String database;
    private final String user;
    private final String password;

    private final MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();

    public MySqlConnection(CooperrSurvival plugin, String host, int port, String database, String user, String password) {
        this.plugin = plugin;
        this.host = host;
        this.port = port;
        this.database = database;
        this.user = user;
        this.password = password;

        dataSource.setServerName(host);
        dataSource.setPortNumber(port);
        dataSource.setDatabaseName(database);
        dataSource.setUser(user);
        dataSource.setPassword(password);
    }

    public Optional<Long> getMoney(Player player) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT money FROM players WHERE uuid = '" + player.getUniqueId() + "';"
            );
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return Optional.of(resultSet.getLong("money"));
            }
            return Optional.empty();
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to query database!", e);
            return Optional.empty();
        }
    }

    public boolean updateMoney(Player player, float amount) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE players SET money = money + " + amount + " WHERE uuid = '" + player.getUniqueId() + "';"
            );
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to update money to database!", e);
            return false;
        }
    }

    public void registerNewPlayer(Player player) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO players (uuid, name, money) VALUES (" + player.getUniqueId() + ", " + player.getName() + ");"
            );
            statement.execute();
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to insert into database!", e);
        }
    }

    public boolean isPlayerRegistered(Player player) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT uuid FROM players WHERE uuid = '" + player.getUniqueId() + "';"
            );
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            plugin.getLogger().log(Level.SEVERE, "Failed to query database!", e);
            return false;
        }
    }
}
