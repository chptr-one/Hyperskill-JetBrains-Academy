package banking.account;

import org.jetbrains.annotations.NotNull;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class AccountRepository {

    private static AccountRepository instance;
    private Connection connection = null;

    public static AccountRepository getInstance(String url) {
        if (instance == null) {
            instance = new AccountRepository(url);
        }
        return instance;
    }

    private AccountRepository(String url) {
        String databaseUrl = "jdbc:sqlite:" + url;
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(databaseUrl);

        try {
            connection = dataSource.getConnection();
            var statement = connection.createStatement();
            statement.executeUpdate(
                    "CREATE TABLE IF NOT EXISTS card (" +
                            "id INTEGER PRIMARY KEY, " +
                            "number TEXT UNIQUE, " +
                            "pin TEXT, " +
                            "balance INTEGER DEFAULT 0" +
                            ");"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void add(@NotNull Account account) {
        String sql = String.format("INSERT INTO card (number, pin, balance)" +
                "VALUES (%s, %s, %d);", account.getNumber(), account.getPin(), account.getBalance());
        executeUpdateSql(sql);
    }

    public Optional<Account> getByNumber(long number) {
        String sql = String.format("SELECT * FROM card WHERE number = %s", number);
        Account account = null;
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sql);
            if (!result.isClosed()) {
                account = new Account(result.getLong("number"), result.getInt("pin"));
                account.setBalance(result.getInt("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(account);
    }

    public void update(@NotNull Account... accounts) {
        for (var account : accounts) {
            String sql = String.format("UPDATE card SET balance = %d WHERE number = %s",
                    account.getBalance(),
                    account.getNumber()
            );
            executeUpdateSql(sql);
        }
    }

    public void delete(@NotNull Account account) {
        String sql = String.format("DELETE FROM card WHERE number = %s",
                account.getNumber()
        );
        executeUpdateSql(sql);
    }

    private void executeUpdateSql(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
