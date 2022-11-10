package com.pearteam.processdata;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrxDetails {
  private String date;
  private double amount;
  private String holdingPartyCountry;
  private String holdingPartyId;
  private String holdingPartyCategory;
  private String counterPartyCountry;
  private String counterPartyId;
  private String txType;
  private double a;
  private double b;

  String getNewRow() {
    return String.join(",", date, Double.toString(amount), holdingPartyCountry, holdingPartyId, holdingPartyCategory,
        counterPartyCountry, counterPartyId, txType, Double.toString(a), Double.toString(b));
  }
}
