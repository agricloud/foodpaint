dataSource {
    pooled = true
    driverClassName = "org.h2.Driver"
    username = "sa"
    password = ""
}

hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = false
    cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory' // Hibernate 3
    // cache.region.factory_class = 'org.hibernate.cache.ehcache.EhCacheRegionFactory' // Hibernate 4
}
// environment specific settings
environments {
    development {
        // 標準
        dataSource {
            dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
            url = "jdbc:h2:mem:devDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
        dataSource_erp {
            pooled = true
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = ""
            dbCreate = "create-drop"
            url = "jdbc:h2:mem:devErpDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
    }
    test {

        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }
        dataSource_erp {

            pooled = true
            driverClassName = "org.h2.Driver"
            username = "sa"
            password = ""
            dbCreate = "update"
            url = "jdbc:h2:mem:devErpDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        }        
    }
    production {
        dataSource {
            dbCreate = "update"
            pooled = true
            driverClassName = "com.mysql.jdbc.Driver"
            dialect = org.hibernate.dialect.MySQL5InnoDBDialect
            //dialect = org.hibernate.dialect.MySQLMyISAMDialect
            // logSql = true
            username = "foodpaint"
            password = "foodpaint"
            url = "jdbc:mysql://localhost/foodpaint?useUnicode=true&characterEncoding=UTF8&zeroDateTimeBehavior=convertToNull"
            properties {
                minEvictableIdleTimeMillis=1800000
                timeBetweenEvictionRunsMillis=1800000
                numTestsPerEvictionRun=3
                testOnBorrow=true
                testWhileIdle=true
                testOnReturn=true
                validationQuery="SELECT 1"
            }
        }
        dataSource_erp {
            pooled = true
            driverClassName = "net.sourceforge.jtds.jdbc.Driver"
            dialect = "org.hibernate.dialect.SQLServer2008Dialect"
            username = "sa"
            password = "dsc"
            dbCreate = "validate"
            url= "jdbc:jtds:sqlserver://192.168.1.15:1433;databaseName=Leader"
        }
    }
}
