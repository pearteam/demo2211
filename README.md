## Prerequisites:
First build in project folder:
```
$ mvn package
```
## Usage:

Then check if it is working:
```
$ java -jar target/demo-backend-0.0.1-SNAPSHOT.jar input.csv output.csv
```

## Example data

# Input file
```
date,amount,holding_party_country,holding_party_id,holding_party_category,counter_party_country,counter_party_id,tx_type
2019-06-30 08:04:41,104.0,DE,455,87,CH,321875,credit
2019-06-30 08:05:08,30.0,DE,364,110,FR,107470,credit
2019-06-30 06:05:16,5858.52,DE,165,29,AT,224340,credit
2019-06-30 08:07:14,4582.0,DE,363,87,DE,15400,credit
2019-06-30 08:07:19,4582.0,DE,363,87,DE,15400,credit
2019-06-30 08:06:27,331.1,DE,437,87,DE,313236,credit
2019-06-30 08:07:22,263.5,DE,363,87,CH,72583,credit
```

# output file
```
date,amount,holding_party_country,holding_party_id,holding_party_category,counter_party_country,counter_party_id,tx_type,a,b
2019-06-30 08:04:41,104.0,DE,455,87,CH,321875,credit,-2146.1600000000003,0.0
2019-06-30 08:05:08,30.0,DE,364,110,FR,107470,credit,-2220.1600000000003,0.0
2019-06-30 06:05:16,5858.52,DE,165,29,AT,224340,credit,3608.36,0.0
2019-06-30 08:07:14,4582.0,DE,363,87,DE,15400,credit,2331.8399999999997,0.0
2019-06-30 08:07:19,4582.0,DE,363,87,DE,15400,credit,2331.8399999999997,0.0
2019-06-30 08:06:27,331.1,DE,437,87,DE,313236,credit,-1919.0600000000004,0.0
2019-06-30 08:07:22,263.5,DE,363,87,CH,72583,credit,-1986.6600000000003,-4318.5
```

