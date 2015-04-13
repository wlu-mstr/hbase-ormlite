package com.wlu.orm.hbase.exceptions;

public class HBaseOrmException extends Exception {

    public HBaseOrmException(String msg) {
        super(msg);
    }

    public HBaseOrmException(Exception e) {
        super(e);
    }
}
