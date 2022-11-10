package com.pearteam.processdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Results {

  private double average;

  private List<Double> values = new ArrayList<>();
  private List<TrxDetails> elements = new ArrayList<>();
  private List<String[]> invalidData = new ArrayList<>();
  //private List
  private Map<String, HoldingParty> holdingPartyMap = new HashMap<>();

  public void addElementByColumns(String[] cols) {
    TrxDetails trxDetails = new TrxDetails();
    trxDetails.setDate(cols[0]);
    trxDetails.setAmount(Double.valueOf(cols[1]));
    trxDetails.setHoldingPartyCountry(cols[2]);
    String holdingPartyId = cols[3];
    trxDetails.setHoldingPartyId(holdingPartyId);
    trxDetails.setHoldingPartyCategory(cols[4]);
    trxDetails.setCounterPartyCountry(cols[5]);
    trxDetails.setCounterPartyId(cols[6]);
    trxDetails.setTxType(cols[7]);
    elements.add(trxDetails);
    getOrCreateHoldingParty(holdingPartyId).addElement(trxDetails);
  }

  public HoldingParty getOrCreateHoldingParty(String id) {
    if (!holdingPartyMap.containsKey(id)) {
      HoldingParty holdingParty = new HoldingParty();
      holdingParty.setId(id);
      holdingPartyMap.put(id, holdingParty);
    }
    return holdingPartyMap.get(id);
  }

  public HoldingParty getHoldingParty(String id) {
    return holdingPartyMap.get(id);
  }


  public void addErrors(String[] cols) {
    invalidData.add(cols);
  }

   public void compute() {
     OptionalDouble optionalDouble = elements.stream().mapToDouble(x -> x.getAmount()).average();
     average =  optionalDouble.isPresent() ? optionalDouble.getAsDouble() : 0;
     holdingPartyMap.forEach((k, v) -> v.compute() );
     elements.forEach(tx -> {
       tx.setA(tx.getAmount() - average);
       tx.setB(tx.getAmount() - getHoldingParty(tx.getHoldingPartyId()).getMedian());
     });
   }
}
