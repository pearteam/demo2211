package com.pearteam.processdata;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ComputeStatsTest {

  @ParameterizedTest
  @CsvSource({
      "'HEADER;2019-06-30 08:29:11,331.5,DE,363,87,CH,21250,credit', 331.5",
      "'HEADER;" +
          "2019-06-30 08:33:22,10.4,DE,364,110,FR,141670,credit;" +
          "2019-06-30 08:34:27,30.4,DE,365,110,HU,217331,credit'," +
          "20.4",
      "'HEADER;" +
          "2019-06-30 08:21:44,-100.0,DE,363,87,FR,75630,credit;" +
          "2019-06-30 08:24:37,200.0,DE,363,87,DE,39413,credit'," +
          "50"
  })
  void doTheJobCheckAverage(String input, double average) {
    //given
    UpdateStats updateStats = new UpdateStats();
    //when
    Results ret = updateStats.doTheJob(Stream.of(input.split(";")));
    //then
    assertEquals(average, ret.getAverage());
  }

  @ParameterizedTest
  @CsvSource({
      "'HEADER;2019-06-30 08:38:57,123.4,DE,777,110,FR,117759,credit', 123.4",
      "'HEADER;" +
          "2019-06-30 06:58:07,10.4,DE,777,84,DE,44955,credit;" +
          "2019-06-30 09:02:12,30.4,DE,777,110,DE,260036,credit'," +
          "20.4",
      "'HEADER;" +
          "2019-06-30 09:03:03,2.0,DE,777,110,FR,140374,credit;" +
          "2019-06-30 09:03:25,3.0,DE,777,110,FR,116352,credit;" +
          "2019-06-30 09:03:55,1.0,DE,777,110,DE,263165,credit'," +
          "2.0"
  })
  void doTheJobCheckMedian(String input, double median) {
    //given
    UpdateStats updateStats = new UpdateStats();
    //when
    Results ret = updateStats.doTheJob(Stream.of(input.split(";")));
    //then
    assertEquals(median, ret.getHoldingParty("777").getMedian());
  }

  @ParameterizedTest
  @CsvSource({
      "'HEADER;2019-06-30 08:26:04,9.0,DE,364,110,FR,145163,credit', 0",
      "'HEADER;" +
          "2019-06-30 08:04:41,10.4,DE,777,87,CH,321875,credit;" +
          "2019-06-30 08:05:08,a0.4,DE,777,110,FR,107470,credit'," +
          "1",
      "'HEADER;" +
          "2019-06-30 08:12:34,70.0,DE,363;" +
          "2019-06-30 08:26:04,3.a,DE,777,110,FR,145163,credit;" +
          "2019-06-30 08:28:23,96.0,DE,777,87,IT,58713,credit'," +
          "2"
  })
  void doTheJobCheckErrors(String input, int errors) {
    //given
    UpdateStats updateStats = new UpdateStats();
    //when
    Results ret = updateStats.doTheJob(Stream.of(input.split(";")));
    //then
    assertEquals(errors, ret.getInvalidData().size());
  }

  @ParameterizedTest
  @CsvSource({
      "'HEADER;" +
          "2019-06-30 09:07:32,2.0,DE,364,110,CH,321364,credit;" +
          "2019-06-30 09:09:58,9.0,DE,364,110,CH,93711,credit;" +
          "2019-06-30 09:12:24,1.0,DE,364,110,CH,157989,credit'," +
          "4.0, 2.0"
  })
  void doTheJobCheckOutputLine(String input, double expectedAverage, double expectedMedian) {
    //given
    UpdateStats updateStats = new UpdateStats();
    //when
    Results ret = updateStats.doTheJob(Stream.of(input.split(";")));
    //then
    assertEquals(expectedAverage, ret.getAverage());
    assertEquals(expectedMedian, ret.getHoldingParty("364").getMedian());
    assertEquals("2019-06-30 09:07:32,2.0,DE,364,110,CH,321364,credit,-2.0,0.0", ret.getElements().get(0).getNewRow());
    assertEquals("2019-06-30 09:09:58,9.0,DE,364,110,CH,93711,credit,5.0,7.0", ret.getElements().get(1).getNewRow());
    assertEquals("2019-06-30 09:12:24,1.0,DE,364,110,CH,157989,credit,-3.0,-1.0", ret.getElements().get(2).getNewRow());
  }
}