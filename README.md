# PAYMENT-WALLET-APP

## PAYMENT-API-SERVICE
* To make payment.
* Project Path: [payment-wallet-app-api](payment-wallet-app-api)

## EXCHANGE-RATE-PROVIDER-SERVICE
* To make smooth money conversion.
* Project Path: [exchange-rate-provider](exchange-rate-provider)


### Data flow diagram through microservices:

```mermaid
graph TD;
    exchange-rate-provider[["exchange-rate-provider"]]
    payment-wallet-app-api[["payment-wallet-app-api"]]
    DB_Exchange_Rate[("Exchange\n Rate\n DB")]
    
    MailSenderService[["mail-sender-service (HOLD)"]]
    Kafka[("Kafka")]
    Sender[/"👤\n Sender"\]
    Receiver[/"👤\n Receiver"\]

    exchange-rate-provider--Update latest rate-->DB_Exchange_Rate;
    DB_Exchange_Rate--Get latest rate-->payment-wallet-app-api;

    payment-wallet-app-api--Publish notification-->Kafka
    Kafka--Subscribe notification-->MailSenderService
    MailSenderService--Send debit notification-->Sender
    MailSenderService--Send credit notification-->Receiver
```

## References:
* [mermaid](http://mermaid.js.org/intro/)