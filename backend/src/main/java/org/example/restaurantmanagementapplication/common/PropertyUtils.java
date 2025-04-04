package org.example.restaurantmanagementapplication.common;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import org.springframework.beans.BeanWrapperImpl;

public class PropertyUtils {
  public static <T> String[] getNullPropertyNames(T source) {
    final BeanWrapperImpl wrappedSource = new BeanWrapperImpl(source);
    return Arrays.stream(wrappedSource.getPropertyDescriptors())
        .map(PropertyDescriptor::getName)
        .filter(name -> wrappedSource.getPropertyValue(name) == null)
        .toArray(String[]::new);
  }

  public static <T> long countNullProperties(T source) {
    final BeanWrapperImpl wrappedSource = new BeanWrapperImpl(source);
    return Arrays.stream(wrappedSource.getPropertyDescriptors())
        .map(PropertyDescriptor::getName)
        .filter(name -> wrappedSource.getPropertyValue(name) == null)
        .count();
  }
}
