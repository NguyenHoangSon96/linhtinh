package Pricefx

@Grab('com.github.groovy-wslite:groovy-wslite:1.1.2')

import wslite.rest.RESTClient

class PricefxClient {
    static private companynode
    static private userName
    static private partition
    static private password
    static private priceFxKey
    static private credential
    static private client

    PricefxClient(companynode, userName, partition, password, priceFxKey) {
//        this.companynode = companynode
//        this.userName = userName
//        this.partition = partition
//        this.password = password
//        this.priceFxKey = priceFxKey
//        this.credential = Base64.getEncoder().encodeToString(("$partition:$password").getBytes())
//        this.client = new RESTClient("https://${companynode}.pricefx.eu/pricefx/${partition}")
    }

    static void init(companynode, userName, partition, password, priceFxKey) {
        PricefxClient.companynode = companynode
        PricefxClient.userName = userName
        PricefxClient.partition = partition
        PricefxClient.password = password
        PricefxClient.priceFxKey = priceFxKey
        credential = Base64.getEncoder().encodeToString(("$partition:$password").getBytes())
        client = new RESTClient("https://${companynode}.pricefx.eu/pricefx/${partition}")
    }

    static void get(uri) {
        def response = client.get([
                headers        : [
                        'Content-Type' : 'application/json',
                        "Authorization": "Basic $credential",
                        'Pricefx-Key'  : priceFxKey
                ],
                connectTimeout : 5000,
                readTimeout    : 10000,
                followRedirects: true,
                useCaches      : false,
        ])

        println(response.json as String)
    }


}
