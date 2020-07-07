/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.util.Queue;
import java.sql.Statement;
import java.util.LinkedList;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * class to manage statement pool
 * @author Elias Wilfinger
 */
public class DB_CachedConnection {
    private Queue<Statement> statementQueue = new LinkedList<>();
    private Connection connection;

    public DB_CachedConnection(Connection connection) {
        this.connection = connection;
    }
    
    public Statement getStatement() throws SQLException{
        if(connection == null){
            throw new RuntimeException("Not connected db.");
        }
        if(!statementQueue.isEmpty()){
            return statementQueue.poll();
        }
        return connection.createStatement();
    }
    /**
     * returns a statement to the queue
     * @param statement 
     */
    public void releaseStatement(Statement statement){
        if(connection == null){
            throw new RuntimeException("Not connected db.");
        }
        statementQueue.offer(statement);
        
    }
}
