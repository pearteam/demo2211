package com.pearteam.processdata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UpdateStats {

  private static final boolean HEADER_PRESENT = true;
  public static final String AMOUNT_FORMAT = "\\-??\\d+\\.\\d+";
  public static final String NEW_HEADER = "date,amount,holding_party_country,holding_party_id,holding_party_category," +
      "counter_party_country,counter_party_id,tx_type,a,b";

  public static void main(String[] args) throws IOException {
    if (args.length < 2) {
      System.out.println("Missing two arguments input and output file name with paths");
      return;
    }
    Stream<String> rows;
    try {
      rows = Files.lines(Paths.get(args[0]));
    } catch (NoSuchFileException ex) {
      System.out.println("Input file not found");
      return;
    }

    UpdateStats updateStats = new UpdateStats();
    Results results = updateStats.doTheJob(rows);
    rows.close();

    try {
      Files.write(Paths.get(args[1]), List.of(NEW_HEADER));
          Files.write(Paths.get(args[1]), results.getElements().stream().map(tx -> tx.getNewRow()).collect(Collectors.toList()), StandardOpenOption.APPEND);
    } catch (IOException e) {
      System.out.println("Can't write output file");
    }

    if (!results.getInvalidData().isEmpty()) {
      System.out.println("Below invalid rows");
      results.getInvalidData().forEach(x -> System.out.println(String.join("'", x)));
    }
  }

  public Results doTheJob(Stream<String> rows) {
    int skipRows = HEADER_PRESENT ? 1 : 0;

    Results results = new Results();
    rows.skip(skipRows).map(line -> line.split(","))
        .forEach(cols -> {
          if (cols.length == 8 && cols[1].matches(AMOUNT_FORMAT)) {
            results.addElementByColumns(cols);
          } else {
            results.addErrors(cols);
          }
        });

    results.compute();
    return results;
  }
}
