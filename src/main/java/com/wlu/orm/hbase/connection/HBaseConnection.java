package com.wlu.orm.hbase.connection;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.*;

import java.io.IOException;

public class HBaseConnection {

    private HConnection connection;
    private HBaseAdmin admin;

    public HBaseConnection() throws IOException {
        Configuration cfg = HBaseConfiguration.create();
        connection = HConnectionManager.createConnection(cfg);
        admin = new HBaseAdmin(cfg);
    }

    /**
     * insert put to the table with name <code>tablename</code>
     *
     * @param tablename
     * @param put
     * @throws IOException
     */
    public void insert(byte[] tablename, Put put) throws IOException {
        HTableInterface htable = connection.getTable(tablename);
        try {
            htable.put(put);
        } finally {
            htable.close();
        }
    }

    /**
     * Delete the whole row of table with name <code>tablename</code>
     *
     * @param tablename
     * @throws IOException
     */
    public void delete(byte[] tablename,
                       org.apache.hadoop.hbase.client.Delete delete) throws IOException {
        HTableInterface htable = connection.getTable(tablename);
        try {
            htable.delete(delete);
        } finally {
            htable.close();
        }
    }

    public Result query(byte[] tablename, Get get) throws IOException {
        HTableInterface table = connection.getTable(tablename);
        Result result = null;
        try {
            result = table.get(get);

        } finally {
            table.close();
        }
        return result;

    }

    public boolean tableExists(final String tableName) throws IOException {
        return admin.tableExists(tableName);
    }

    public void deleteTable(final String tableName) throws IOException {
        admin.disableTable(tableName);
        admin.deleteTable(tableName);
    }

    public void createTable(HTableDescriptor td) throws IOException {
        admin.createTable(td);
    }

}
