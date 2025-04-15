package org.example.restaurantmanagementapplication.common;

import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.stream.Stream;
import org.springframework.beans.BeanWrapperImpl;

public class PropertyUtils {
  public static <T> Stream<String> getPropertiesStream(T source) {
    final BeanWrapperImpl wrappedSource = new BeanWrapperImpl(source);
    return Arrays.stream(wrappedSource.getPropertyDescriptors())
        .map(PropertyDescriptor::getName)
        .filter(name -> wrappedSource.getPropertyValue(name) == null);
  }

  public static <T> String[] getNullPropertyNames(T source) {
    return getPropertiesStream(source).toArray(String[]::new);
  }

  public static <T> long countNullProperties(T source) {
    return getPropertiesStream(source).count();
  }
}
