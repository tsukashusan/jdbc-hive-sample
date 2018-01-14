package example

import java.sql.{DriverManager, Connection, Statement, ResultSet}
import org.apache.hive.jdbc.HiveDriver


object Utility{
  def using[A <: {def close()},B](resource:A)(func:A => B):Unit = {
    try{
      func(resource)  //execute function
    }catch{
      case e:Exception => 
          e.printStackTrace
    }finally{
      resource.close
      println("resource was closed.")
    }
  }
}


object Hello extends Greeting with App {
    val url: String = "jdbc:hive2://zk0-hdinsi.oe0ytkozqirexjg014nqso2jch.lx.internal.cloudapp.net:2181,zk1-hdinsi.oe0ytkozqirexjg014nqso2jch.lx.internal.cloudapp.net:2181,zk2-hdinsi.oe0ytkozqirexjg014nqso2jch.lx.internal.cloudapp.net:2181/;serviceDiscoveryMode=zooKeeper;zooKeeperNamespace=hiveserver2-hive2"
   
    def getConnection(): Connection = {
        return DriverManager.getConnection(url, "hive", "hive")
    }

    def selectPrint(con: Connection, sql: String) :Unit = {
        Utility.using(con.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)){ stmt =>
            Utility.using(stmt.executeQuery(sql)){ rset =>
                while(rset.next) {
                    // get data
                    val r = for(n <- 1 to rset.getMetaData.getColumnCount) yield rset.getObject(n)
                    println(r)
                }       
            }
        }
    }
    println("getConnection Start!")
    Utility.using(getConnection){ con =>
        println("show tables")
        selectPrint(con, "show tables")
        
        println("select * from hivesampletable limit 10")
        selectPrint(con, "select * from hivesampletable limit 10")
    
        println("""select deviceplatform, count(*) as total_count
from hivesampletable
group by deviceplatform
limit 100""")
        selectPrint(con, """select deviceplatform, count(*) as total_count
                            from hivesampletable
                            group by deviceplatform
                            limit 100""")
    
        println("select deviceplatform, querydwelltime from hivesampletable ")
        selectPrint(con, "select deviceplatform, querydwelltime from hivesampletable ")
        
    }

    println("Successful")
    println(greeting)
}

trait Greeting {
    lazy val greeting: String = "hello"
}
