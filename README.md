# PAYMENT-WALLET-APP

## PAYMENT-API-SERVICE
* To make payment.
* Project Path: [payment-wallet-app-api](payment-wallet-app-api)

## EXCHANGE-RATE-PROVIDER-SERVICE
* To make smooth money conversion.
* Project Path: [exchange-rate-provider](exchange-rate-provider)


### Here is the relation diagram between both service:

```mermaid
graph TD;
    exchange-rate-provider[["exchange-rate-provider"]]
    payment-wallet-app-api[["payment-wallet-app-api"]]
    DB_Exchange_Rate[("Exchange_Rate\nDB")]

    exchange-rate-provider--cron Job-->DB_Exchange_Rate;
    DB_Exchange_Rate--Get latest rate-->payment-wallet-app-api;
```

## References:
* [mermaid](http://mermaid.js.org/intro/)