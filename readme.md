ORM-HBase

This is simple orm for HBase, the project includes several parts:

1. DataMapper

According to PoEAA, domain object knows nothing about Data Mapper, but mapper knows the domain object. Actually, data mapper is designed for specific objet including functions to access databases.

Different from PoEAA, the ORM-HBase here is designed based on the model, but make it more general. We specify the mapping from object’s fields to HBase schema through annotation, and design a general DataMapper? based on generic data type (with annotations).

2. Object Beans: Make sure the object has an empty construction function with no parameters and getter&setter functions for each member variable;

3. Dao: User does not need to call Create() function of DataMapperFactory nor need to know anything about DataMapper?’s functions. Only need to use Dao to do CRUD.