package net.java_school.db.dbpool;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Oracle {
	public boolean value() default true;
}
