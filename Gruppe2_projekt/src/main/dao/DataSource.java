package dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {

    //TODO: Gustav forklar hvad der foregår her. Er det done?

    /**
     * Meget simpel klasse, bruger Hikaris data pooling framework til at lave noget connection pooling.
     */

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl( "jdbc:mysql://ec2-52-30-211-3.eu-west-1.compute.amazonaws.com/s185031?" );
        config.setUsername( "s185031" );
        config.setPassword( "UfudYEA2p7RmipWZXxT2R" );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource(config);
    }

    private DataSource() {}

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
