package com.demo.common.util;

import com.demo.common.dto.Header;

/**
 */
public class HeaderFactory {

    public static Header getHeader() {
        Header requestHeader = new Header();
        // TODO
        requestHeader.setVersion(HeaderConstant.VERSION);
        requestHeader.setCompanyId(HeaderConstant.PORTAL_TYPE);
        requestHeader.setProductLine(HeaderConstant.PRODUCT_LINE);
        requestHeader.setPlatform(HeaderConstant.PORTAL_TYPE);
        requestHeader.setAppid(HeaderConstant.TO_BE_DETEMINED);
        requestHeader.setImei(HeaderConstant.TO_BE_DETEMINED);
        requestHeader.setImsi(HeaderConstant.TO_BE_DETEMINED);
        requestHeader.setClientVer(HeaderConstant.TO_BE_DETEMINED);
        requestHeader.setAppid(HeaderConstant.TO_BE_DETEMINED);
        requestHeader.setAppName(HeaderConstant.TO_BE_DETEMINED);
        requestHeader.setAccessInfo(HeaderConstant.TO_BE_DETEMINED);
        requestHeader.setPortalType(HeaderConstant.PORTAL_TYPE);
        
        return requestHeader;
    }
}
