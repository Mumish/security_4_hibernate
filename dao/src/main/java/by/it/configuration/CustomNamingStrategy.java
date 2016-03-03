package by.it.configuration;

import org.hibernate.cfg.DefaultNamingStrategy;

/**
 *
 * @author Mumish
 */
public class CustomNamingStrategy extends DefaultNamingStrategy {

    @Override
    public String classToTableName(String className) {
        return "T_" + super.classToTableName(className).toUpperCase();
    }

    @Override
    public String propertyToColumnName(String propName) {
        return "F_" + super.propertyToColumnName(propName);
    }

    /**
     *
     * @param columnName
     * @return
     */
    @Override
    public String columnName(String columnName) {
        return columnName;
    }

    @Override
    public String tableName(String tableName) {
        return tableName;
    }
}
