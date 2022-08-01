import Pricefx.Constant
import Pricefx.PricefxClient

// https://pricefx.atlassian.net/wiki/spaces/ACC/pages/3854502399/Appendix+Supported+Operators+in+Criteria
PricefxClient.init(Constant.COMPANY_NODE, Constant.USER_NAME, Constant.PARTITION, Constant.PASSWORD, Constant.PRICEFX_KEY)


def uri = '/fetch/PYR'
PricefxClient.get(uri)
// Số RebateRecord, PayoutRecord


