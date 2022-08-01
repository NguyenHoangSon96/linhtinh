//@Grab('com.xlson.groovycsv:groovycsv:1.3')
//@GrabConfig(systemClassLoader = true)
//@Grab(group = 'org.postgresql', module = 'postgresql', version = '9.4-1205-jdbc42')
//
//import groovy.sql.Sql
//import com.xlson.groovycsv.*
//
//def connectDB() {
//    def dbUrl = "jdbc:postgresql://127.0.0.1/Rnd4U"
//    def dbUser = ""
//    def dbPassword = ""
//    def dbDriver = "org.postgresql.Driver"
//
//    def sql = Sql.newInstance(dbUrl, dbUser, dbPassword, dbDriver)
//}
//
//def readCsv() {
//    for (line in CsvParser.parseCsv(new FileReader('/Users/sonnguyen/Downloads/Transaction_RM.csv'), separator: ',')) {
//        println "Country=${line['Product Id']}"
//    }
//}
//
//connectDB()