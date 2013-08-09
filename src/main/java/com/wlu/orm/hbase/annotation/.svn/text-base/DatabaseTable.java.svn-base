package com.wlu.orm.hbase.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(TYPE)
@Retention(RUNTIME)
public @interface DatabaseTable {
	/**
	 * The name of the column in the database. If not set then the name is taken
	 * from the class name lowercased.
	 */
	String tableName() default "";

	boolean canBeFamily() default false;
}
