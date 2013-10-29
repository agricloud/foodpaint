package foodpaint;

import java.sql.SQLException;
import java.sql.Types;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Hibernate;
import org.hibernate.JDBCException;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.SQLExceptionConverter;
import org.hibernate.exception.SQLStateConverter;
import org.hibernate.exception.ViolatedConstraintNameExtracter;

/**
 * This is just a minimal implementation which
 * <ul>
 * <li>Allows Hibernate to read NVARCHAR... columns from SQL Server JDBC 4 (ad-hoc) result sets. This is <b>not</b>
 * required for neither the BootStrap nor the integration test contained within this demo project, but might prevent
 * exceptions within your application when querying information schema data from Java.</li>
 * <li>Maps MS SQL Server unique index violations to unique constraint violations.</li>
 * </ul>
 * 
 * An enhanced dialect supporting Unicode strings and taking advantage of xxx(MAX) column types will be presented in
 * another <a href="http://blog.saddey.net">Blog post</a>.
 * 
 * @author Reiner Saddey - this code donated to public domain
 * @see <a
 *      href="http://blog.saddey.net/2011/12/15/grails-and-microsoft-sql-server-not-so-painless-when-using-unique-constraints-a-pragmatic-work-around/">
 *      Grails and Microsoft SQL Server - Not so painless when using Unique Constraints - A Pragmatic Work-Around</a>
 */
public class MinimalSQLServer2008Dialect extends SQLServerDialect {

    public MinimalSQLServer2008Dialect() {
        super();

        registerHibernateType(Types.NCHAR, Hibernate.CHARACTER.getName());
        registerColumnType(Types.NCHAR, "nchar(1)");

        registerHibernateType(Types.NVARCHAR, Hibernate.STRING.getName());
        registerColumnType(Types.VARCHAR, "varchar($l)");

        registerHibernateType(Types.NCLOB, Hibernate.CLOB.getName());
        registerColumnType(Types.NCLOB, "ntext");
    }

    /**
     * Provides a {@link ViolatedConstraintNameExtracter} that tries to return the name of the violated constraint (or
     * unique index). Note: The default (super) implementation always returns null.
     */
    private static final ViolatedConstraintNameExtracter MS_SQL_EXTRACTER = new ViolatedConstraintNameExtracter() {
        @Override
        public String extractConstraintName(final SQLException sqle) {
            final String detailMessage = sqle.getMessage();
            final Matcher matcher = Pattern.compile(".+(constraint|unique index) '([^']+)'\\..*")
                    .matcher(detailMessage);
            if (matcher.matches()) {
                final String violName = matcher.group(2);
                return violName;
            }
            return null;
        }
    };

    /**
     * Provides a {@link ViolatedConstraintNameExtracter} that tries to return the name of the violated constraint (or
     * unique index). Note: The default (super) implementation always returns null.
     */
    @Override
    public ViolatedConstraintNameExtracter getViolatedConstraintNameExtracter() {
        return MS_SQL_EXTRACTER;
    }

    /**
     * According to and confirmed by tests, MS SQL Server returns invalid sql states on unique index violations. We'll
     * map unique <b>index</b> viols to {@link ConstraintViolationException}s just as though they were unique
     * <b>constraint</b> viols. Although according to MS KB 2294405 this prb should have been fixed it still does occur
     * with MS JDBC 3.0.1301.202.
     * 
     * @see <a href="http://support.microsoft.com/kb/2294405">MS KB 2294405 - FIX: The "getSQLState" method returns an
     *      incorrect SQL state code value of an exception in Microsoft SQL Server JDBC Driver 3.0</a>
     */
    private static final SQLExceptionConverter MS_SQL_STATE_CONVERTER = new SQLStateConverter(MS_SQL_EXTRACTER) {
        @Override
        public JDBCException convert(final SQLException sqlException, final String message, final String sql) {
            final String sqlState = sqlException.getSQLState();
            if ("S0001".equals(sqlState)) { // S... means severe in MS speak
                final int vendorErrorCode = sqlException.getErrorCode();
                if (vendorErrorCode == 2601) { // 2601 means unique index viol
                    final String constraintName = MS_SQL_EXTRACTER.extractConstraintName(sqlException);
                    return new ConstraintViolationException(message, sqlException, sql, constraintName);
                }
            }
            // Fall through/back to default handling
            return super.convert(sqlException, message, sql);
        }
    };

    /**
     * Provides a {@link SQLExceptionConverter} that maps MS SQL unique index viols to
     * {@link ConstraintViolationException}s.
     * 
     * @see MsSqlServerSQLStateConverter
     */
    @Override
    public SQLExceptionConverter buildSQLExceptionConverter() {
        return MS_SQL_STATE_CONVERTER;
    }

}
