package com;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Muabe on 2018-05-03.
 */

@Inherited
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface Tester {
}
