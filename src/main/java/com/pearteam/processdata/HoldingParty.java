package com.pearteam.processdata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HoldingParty {
  private String id;
  private double median;
  private List<TrxDetails> elements = new ArrayList<>();

  public void addElement(TrxDetails trxDetails) {
    elements.add(trxDetails);
  }
  public void compute() {
    int size = elements.size();
    DoubleStream sortedAges = elements.stream().mapToDouble(d -> d.getAmount()).sorted();
    median =  size % 2 == 0 ?
        sortedAges.skip(size / 2 - 1).limit(2).average().getAsDouble() :
        sortedAges.skip(size / 2).findFirst().getAsDouble();
  }
}
