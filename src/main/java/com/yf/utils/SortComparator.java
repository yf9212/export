package com.yf.utils;

import java.lang.reflect.Field;
import java.util.Comparator;

import com.yf.annotation.OrderNumber;

public class SortComparator implements Comparator<Field> {

	public int compare(Field o1, Field o2) {
		OrderNumber on1= o1.getAnnotation(OrderNumber.class);
		OrderNumber on2= o2.getAnnotation(OrderNumber.class);
		if(on1!=null && on2==null){
			return 1;
		}else if(on1==null && on2!=null){
			return -1;
		}else if(on1==null && on2==null){
			return 0;
		}else{
			return on1.value()>on2.value()?1:-1;
		}
	}

}
