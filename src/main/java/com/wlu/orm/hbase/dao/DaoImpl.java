package com.wlu.orm.hbase.dao;

import com.wlu.orm.hbase.connection.HBaseConnection;
import com.wlu.orm.hbase.exceptions.HBaseOrmException;
import com.wlu.orm.hbase.schema.DataMapper;
import com.wlu.orm.hbase.schema.DataMapperFacory;
import com.wlu.orm.hbase.schema.value.Value;
import com.wlu.orm.hbase.schema.value.ValueFactory;
import com.wlu.orm.hbase.util.util;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class DaoImpl<T> implements Dao<T> {

    Log LOG = LogFactory.getLog(DaoImpl.class);
    Class<T> dataClass;
    private HBaseConnection hbaseConnection;
    // set constant schemas
    private DataMapperFacory<T> dataMapperFactory = null;

    public DaoImpl(Class<T> dataClass, HBaseConnection connection)
            throws HBaseOrmException {
        this.dataClass = dataClass;
        hbaseConnection = connection;
        dataMapperFactory = new DataMapperFacory<T>(dataClass);
    }

    @Override
    public void createTable() throws IOException {
        if (hbaseConnection.tableExists(dataMapperFactory.tablename)) {
            hbaseConnection.deleteTable(dataMapperFactory.tablename);
        }
        hbaseConnection.createTable(dataMapperFactory.TableCreateDescriptor());
        LOG.info(dataMapperFactory.TableCreateScript());
    }

    @Override
    public void createTableIfNotExist() throws IOException {
        if (hbaseConnection.tableExists(dataMapperFactory.tablename)) {
            LOG.info("The table has already existed, will not recreate it.");
            return;
        }
        hbaseConnection.createTable(dataMapperFactory.TableCreateDescriptor());
        LOG.info(dataMapperFactory.TableCreateScript());
    }

    @Override
    public void insert(T data) throws HBaseOrmException {
        // need to check the type
        if (!data.getClass().equals(dataClass)) {
            LOG.error("Class type of data is not the same as that of the Dao, should be "
                    + dataClass);
            return;
        }
        DataMapper<T> dataMapper = null;
        try {
            dataMapper = dataMapperFactory.Create(data);
            dataMapper.insert(hbaseConnection);
        } catch (Exception e) {
            throw new HBaseOrmException(e);
        }
    }

    @Override
    public void deleteById(Value rowkey) throws HBaseOrmException {
        org.apache.hadoop.hbase.client.Delete delete = new org.apache.hadoop.hbase.client.Delete(
                rowkey.toBytes());
        try {
            hbaseConnection.delete(Bytes.toBytes(dataMapperFactory.tablename),
                    delete);
        } catch (IOException e) {
            throw new HBaseOrmException(e);
        }
    }

    @Override
    public void deleteById(T data) throws HBaseOrmException {
        Value rowkey = null;
        try {
            rowkey = ValueFactory.Create(util.GetFromField(data,
                    dataMapperFactory.rowkeyField));
        } catch (Exception e) {
            throw new HBaseOrmException(e);
        }
        deleteById(rowkey);

    }

    @Override
    /**
     * The qualifier is pretty complicated
     */
    public void delete(T data, String FieldNameOfFamily,
                       String FieldNameOfqualifier) throws HBaseOrmException {
        if (FieldNameOfqualifier == null) {
            delete(data, FieldNameOfFamily);
            return;
        }
        Value rowkey;
        try {

            rowkey = ValueFactory.Create(util.GetFromField(data,
                    dataMapperFactory.rowkeyField));
            org.apache.hadoop.hbase.client.Delete delete = new org.apache.hadoop.hbase.client.Delete(
                    rowkey.toBytes());
            // get family name
            Field familyNameField = data.getClass().getDeclaredField(
                    FieldNameOfFamily);
            byte[] familyname = getFamilyByFieldName(familyNameField,
                    FieldNameOfFamily);
            // get qualifier name
            byte[] qualifiername = getQualiferByFamilyOrSublevelFieldName(
                    familyNameField, FieldNameOfqualifier);

            delete.deleteColumn(familyname, qualifiername);
            hbaseConnection.delete(Bytes.toBytes(dataMapperFactory.tablename),
                    delete);
        } catch (Exception e) {
            throw new HBaseOrmException(e);
        }
    }

    @Override
    public void delete(T data, String familyFieldName) throws HBaseOrmException {
        if (familyFieldName == null) {
            delete(data);
            return;
        }
        Value rowkey;
        try {
            rowkey = ValueFactory.Create(util.GetFromField(data,
                    dataMapperFactory.rowkeyField));
            org.apache.hadoop.hbase.client.Delete delete = new org.apache.hadoop.hbase.client.Delete(
                    rowkey.toBytes());
            // get family name
            Field familyNameField = data.getClass().getDeclaredField(
                    familyFieldName);
            byte[] familyname = getFamilyByFieldName(familyNameField,
                    familyFieldName);
            delete.deleteFamily(familyname);
            hbaseConnection.delete(Bytes.toBytes(dataMapperFactory.tablename),
                    delete);
        } catch (Exception e) {
            throw new HBaseOrmException(e);
        }
    }


    private byte[] getFamilyByFieldName(Field familyNameField,
                                        String familyFieldName) throws SecurityException,
            NoSuchFieldException {
        return dataMapperFactory.fixedSchema.get(familyNameField)
                .getFamily();
    }

    private byte[] getQualiferByFamilyOrSublevelFieldName(
            Field familyNameField, String FieldNameOfQualifier)
            throws HBaseOrmException {
        // if qualifier name is set with family name
        byte[] qualifiername = dataMapperFactory.fixedSchema.get(
                familyNameField).getQualifier();
        // qualifier is not directly set or set with a wrong value
        if (qualifiername == null
                || Bytes.compareTo(qualifiername,
                Bytes.toBytes(FieldNameOfQualifier)) != 0) {
            qualifiername = null;
        }
        if (qualifiername == null) {
            Map<String, byte[]> subFieldToQualifier = dataMapperFactory.fixedSchema
                    .get(familyNameField).getSubFieldToQualifier();
            if (subFieldToQualifier == null) {
                qualifiername = null;
            } else if (subFieldToQualifier.get(FieldNameOfQualifier) != null) {
                qualifiername = subFieldToQualifier.get(FieldNameOfQualifier);
            } else {
                throw new HBaseOrmException("The field '"
                        + FieldNameOfQualifier
                        + "' of sub level family class '"
                        + familyNameField.getName()
                        + "' is not set as qualifier");
            }
            // else qualifier is set with name of the field's name
            if (qualifiername == null) {
                qualifiername = Bytes.toBytes(FieldNameOfQualifier);
            }
        }
        return qualifiername;
    }

    @Override
    public void delete(T data) throws HBaseOrmException {
        deleteById(data);

    }

    @Override
    public void update(T data) throws HBaseOrmException {
        insert(data);

    }

    @Override
    public void update(T data, List<String> familyFieldName) throws HBaseOrmException {
        if (familyFieldName == null) {
            update(data);
            return;
        }
        try {
            DataMapper<T> dm = dataMapperFactory.CreateEmpty(data);
            dm.SetRowKey(data);
            dm.SetFieldValue(data, familyFieldName);
            dm.insert(hbaseConnection);
        } catch (Exception e) {
            throw new HBaseOrmException(e);
        }
    }

    @Override
    public T queryById(Value id) throws HBaseOrmException {
        DataMapper<T> dm = dataMapperFactory.CreateEmpty(dataClass);
        if (dm == null) {
            return null;
        }
        return dm.queryById(id, hbaseConnection);
    }

    @Override
    public List<T> queryWithFilter(String filter, boolean returnWholeObject) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<T> queryWithFilter(String filter) {
        // TODO Auto-generated method stub
        return null;
    }

}
