package com.wlu.orm.hbase.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(FIELD)
@Retention(RUNTIME)
public @interface DatabaseField {
	/**
	 * The name of the column in the database. If not set then the name is taken
	 * from the class name lowercased.
	 */
	String familyName() default "";

	String qualifierName() default "";

	boolean isQualiferList() default false;
	
	boolean isQualifierValueMap() default false;

	boolean id() default false;

}
