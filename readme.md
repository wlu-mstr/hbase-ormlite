ORM-HBase
=======================
This is simple **ORM** for HBase, the project includes several parts:

1. DataMapper
----------------

  According to PoEAA [http://www.amazon.com/Patterns-Enterprise-Application-Architecture-Martin/dp/0321127420], domain object (POJO) knows nothing about Data Mapper, but mapper knows the domain object. Actually, data mapper is designed for specific objet including functions to access databases.

  Different from PoEAA, this ORM-HBase here is designed based on the model, but we make it more general. We specify the mapping from object’s fields to HBase schema (id/rowkey, family, qualifier) through Java annotation, and design a general DataMapper based on generic data type (with annotations).
  
    So, user of this module do not need to care about DataMapper logic at all(!)

2. Object Beans
-------------------
  Make sure the object has **an empty construction function** with no parameters and **getter&setter functions** for each member variable;

3. Dao
-------------------
  User does not need to call Create() function of DataMapperFactory nor need to know anything about DataMapper’s logic. Only need to use **Dao<ConcretClass>** to do CRUD.
