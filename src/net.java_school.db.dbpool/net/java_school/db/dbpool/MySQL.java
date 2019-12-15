package net.java_school.db.dbpool;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MySQL {
  public boolean value() default true;
}
