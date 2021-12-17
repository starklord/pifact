package ts.com.service.util.db;

import java.sql.ResultSet;

public class Query {

    public Select select = new Select(this);
    public final From from = new From();
    public final Join join = new Join();
    public String where = "";
    public String end = "";
    private String query = "";

    public int index = 0;
    public Object[][] resultSet = null;

    public Query() { 
    	this(null);
    }
    
    public Query(String selectBeginning) {
    	if(selectBeginning!=null){
    		this.select = new Select(selectBeginning,this);
    	}
    }

    private void initQuery() {
        query = select.getSelect().toString() + "\n"
                + from.getFrom().toString() + "\n"
                + join.getJoin().toString() + "\n"
                + (where==null?"":where) + "\n"
                + (end==null?"":end);
        System.out.println(query);

    }
    
    public Object[][] initResultSet() throws Exception {
        initQuery();

        ResultSet rs = CConexion.getInstance().select(query);
        boolean isEmpty = !rs.last();
        if (isEmpty) {
            return null;
        }
        int rowCount = rs.getRow();
        int columnCount = rs.getMetaData().getColumnCount();
        rs.beforeFirst();
        Object[][] result = new Object[rowCount][columnCount];
        while (rs.next()) {
            Object[] data = new Object[columnCount];
            for (int i = 0; i < columnCount; i++) {
                data[i] = rs.getObject(i + 1);
            }
            result[rs.getRow() - 1] = data;

        }
        resultSet = result;
        return resultSet;
    }
    
    
    /**
     * calculate the next value of the given sequence
     * @param sequence the name of the sequence
     * @return the next value of the sequence, or 0 if no sequence has found
     */
    public static int getNextValSequence(String sequence) throws Exception {
        int nextVal = 0;
        String select = "SELECT NEXTVAL" + "('" + sequence + "')";
        ResultSet rs = CConexion.getInstance().select(select);
        if (rs.next()) {
            nextVal = rs.getInt(1);
        }
        return nextVal;
    }

    public ObjectDB getNewObjectDB(Class c, String alias, String... fields) throws Exception {
        return new ObjectDB(c, alias, this, fields);
    }

}
