package Users;

/**
 *
 * @author MAI_PHUONG
 */
import com.mongodb.*;


public class DBConn {
    private static final String HOST = "localhost";
    private static final int PORT = 27017;
    private static final String DATABASE_NAME = "my_database";
    
    private static MongoClient mongo;
    private static DB db;
    
    public static DB getConn() {
        if (db == null) {
            try {
                mongo = new MongoClient(HOST, PORT);
                db = mongo.getDB(DATABASE_NAME);
            }
            catch(Exception e) {
                System.out.println("ERROR: " + e);
            }
        }
        return db;
    }
    
    public static void closeConn() {
        if(mongo != null) {
            mongo.close();
        }
    }
}
