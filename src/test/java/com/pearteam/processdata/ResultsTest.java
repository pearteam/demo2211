package com.pearteam.processdata;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ResultsTest {

  @ParameterizedTest
  @CsvSource({
      "'1', 1",
      "'1,2', 1.5",
      "'1,2,3', 2",
      "'1,1,2,6', 2.5",
      "'1,3,2,4,5', 3",
      "'1,3,2,4,5', 3",
      "'3,5,1,2,4', 3",
      "'1.1,17,1,-15.2,-100',-19.22"
  })
  void checkAverageComputation(String values, double expectedMedian) {
    //given
    Results results = new Results();
    //when
    Arrays.stream(values.split(",")).forEach(x -> {
      String[] array = {"CONTENT1", x, "CONTENT3", "CONTENT4", "CONTENT5", "CONTENT6", "CONTENT7", "CONTENT8"};
      results.addElementByColumns(array);
    });
    results.compute();
    //then
    assertEquals(expectedMedian, results.getAverage());
  }
}