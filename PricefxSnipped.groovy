//// [MASTER DATA] Working with stream
//def resultIterator = api.stream('PR', null, ['lineId', 'sourceId', 'sku', 'customerId'])
//resultIterator.each {
//    api.trace(it)
//}
//
//// [PARAMETER] findLookupTableValues
//def targetDate = api.targetDate().format('yyyy-MM')
//def PriceFxClient = api.findLookupTableValues('ExchangeRatePerMonth', ['key1', 'key2', 'attribute1'], '-key2', *[
//    Filter.equal('key1', 'USD'),
//    Filter.lessOrEqual('key2', targetDate)
//])
//if (PriceFxClient) [
//    PriceFxClient[0]
//]
//
//// [PARAMETER] vLookup
//api.vLookup("MarginAdj", 'Lamb') = 0.18 // Simple
//api.vLookup('Region', 'Currency', ['name': 'America'])// Matrix
//
//// [PRODUCT EXTENDSION]
//def targetDate = new Date(2022-1900, Calendar.OCTOBER, 1)
//def currency = 'EUR'
//def sku = api.product('sku')
//def productCostAvg = api.find("PX", 0, 1, '-attribute1', ['sku', 'attribute1', 'attribute2'], *[
//        Filter.equal("name", 'ProductCost'),
//        Filter.equal("sku", sku),
//        Filter.equal("attribute3", currency),
//        Filter.lessOrEqual("attribute1", targetDate)
//])?.getAt(0)?.attribute2 as BigDecimal
//productCostAvg
//
//
//// [QUOTE, REBATE HEADER] Create input
//if (quoteProcessor.isPostPhase()) return
//def deliveryType = api.inputBuilderFactory()
//        .createOptionEntry("DeliveryType")
//        .setLabel("Delivery Type")
//        .setRequired(true)
//        .setOptions(["Express", "Standard"])
//        .setLabels(["Express": "Express Delivery", "Standard": "Standard Delivery"])
//        .buildMap()
//quoteProcessor.addOrUpdateInput(deliveryType)
//
//// [CHART] Create Select
//def ctx = api.getDatamartContext()
//def dm = ctx.getDatamart("Transaction")
//def column = dm.getColumn("InvoiceDateYear")
//ctx.dimFilterEntry("Year", column)?.getValue()
//
//// [LINE ITEM] Create input
//def country = api.inputBuilderFactory()
//        .createOptionEntry("Country")
//        .setLabel("Select Country")
//        .setOptions(["CZ", "DE", "UK"])
//        // .buildContextParameter() for Configurators;
//        // .getInput() Only use for line item
//
//
//// [DMDS] Working with stream
//def target = api.getDatamartRowSet('target')
//if (!target) return
//def ctx = api.getDatamartContext()
//def table = ctx.getDataSource('Transaction')
//def query = ctx.newQuery(table, false).selectAll()
//def rowStream = ctx.streamQuery(query)
//def rows = []
//while (rowStream.next()) {
//    rows << rowStream.get()
//}
//rowStream.close()
//def targetRow = [:]
//rows.each { row ->
//    row.each {
//        targetRow[it.key.replace(" ", "") as String] = it.value
//    }
//    target.addRow(targetRow)
//    targetRow = [:]
//}
//
//// [QUOTE HEADER] Calculate total
//if (quoteProcessor.isPostPhase()) {
//    def quoteView = quoteProcessor.getQuoteView()
//
//    def quoteTotalInvoicePrice = 0
//    quoteView?.lineItems?.each { lineItem ->
//        if (!lineItem.folder) {
//            quoteTotalInvoicePrice += lineItem.outputs.find { it.resultName == 'TotalInvoicePrice' }?.result
//        }
//    }
//}
//
//// [DASHBOARD] Create years input
//def ctx = api.getDatamartContext()
//def dm = ctx.getDatamart("Transaction")
//def column = dm.getColumn("InvoiceDateYear")
//ctx.dimFilterEntry("Year", column)?.getValue()
//
//
//// [NONAME]
//def dmCtx = api.getDatamartContext()
//def dmTable = dmCtx.getDatamart("Transaction")
//def customerGroup = ["customerFieldLabel": "CustomerGroup", "customerFieldName": "attribute1", "customerFieldValue": "APPO AG"] as net.pricefx.domain.CustomerGroup
//def productGroup = ["productFieldLabel": "ProductGroup", "productFieldName": "attribute1", "productFieldValue": "Beef"] as net.pricefx.domain.ProductGroup
//def query = dmCtx.newQuery(dmTable, true)
//        .select("CustomerId", "CustomerId")
//        .select("SUM(InvoicePrice)", "TotalRevenue")
//        .where(customerGroup)
//        .where(productGroup)
//        .where(
//                Filter.greaterOrEqual("InvoiceDate", out.StartDate),
//                Filter.lessOrEqual("InvoiceDate", out.EndDateOrToday)
//        )
//
//def result = dmCtx.executeQuery(query)
//def r = result?.data?.collectEntries { row ->
//    [(row.CustomerId): row.TotalRevenue]
//}
//api.trace(r)
//
//// [WORKFLOW] Build workflow
//workflow.addApprovalStep('SalesManager')
//        .withApprovers("john.cena")
//        .withMinApprovalsNeeded(1)
//        .withReasons("SalesManager needs to approve this shit")
//
//// ExchangeMoney for exam partition
///**
// * Only work with EUR
// * @param moneyAmount How much money to convert
// * @param fromCurrency In which currency is the provided moneyAmount
// * @param toCurrency In which currency would you like the result
// * @return Amount of Money in the toCurrency, or null, if error occurred
// */
//BigDecimal convertMoney(BigDecimal moneyAmount, String fromCurrency, String toCurrency, Date conversionDate = api.targetDate()) {
//    if (moneyAmount == null || moneyAmount < 0) {
//        api.addWarning("Money amount is empty or less than 0")
//        return
//    }
//
//    if (fromCurrency != toCurrency) {
//        def filters = [
//                "key1": fromCurrency != 'EUR' ? fromCurrency : toCurrency,
//                "key2": conversionDate?.format("yyyy-MM")
//        ]
//        BigDecimal exchangeRate = api.vLookup('ExchangeRatePerMonth', 'InEuros', filters) as BigDecimal
//
//        if (exchangeRate != null) {
//            exchangeRate = fromCurrency != 'EUR' ? exchangeRate : (1 / exchangeRate)
//            return moneyAmount * exchangeRate
//        } else {
//            api.addWarning("Cannot convert money because exchange rate is empty")
//            return
//        }
//    } else {
//        return moneyAmount
//    }
//}
//
//def deliveryType = api.inputBuilderFactory()     // (2)
//        .createOptionEntry("SalesDiscountPct")
//        .setLabel("Sales Discount %")
//        .setFrom(0)
//        .setValue(0)
//        .setLabels(["Express": "Express Delivery", "Standard": "Standard Delivery"])
//        .buildMap()
//
//
