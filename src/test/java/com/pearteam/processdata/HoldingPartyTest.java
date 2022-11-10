package com.pearteam.processdata;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HoldingPartyTest {

  @ParameterizedTest
  @CsvSource({
      "'1', 1",
      "'1,2', 1.5",
      "'1,2,3', 2",
      "'1,3,2', 2",
      "'1,3,2,4,5', 3",
      "'1,3,2,4,5', 3",
      "'3,5,1,2,4', 3",
      "'1.1,17,1,-15.2,-100',1"
  })
  void checkMedianComputation(String values, double expectedMedian) {
    //given
    HoldingParty holdingParty = new HoldingParty();
    //when
    Arrays.stream(values.split(",")).forEach(x -> {
      TrxDetails trxDetails = new TrxDetails();
      trxDetails.setAmount(Double.parseDouble(x));
      holdingParty.addElement(trxDetails);
    });
    holdingParty.compute();
    //then
    assertEquals(expectedMedian, holdingParty.getMedian());
  }
}