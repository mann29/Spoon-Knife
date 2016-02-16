package com.mitchell.services.core.partialloss.apddelivery.pojo.util;

public class UserData {
    
    private boolean isShopBasic;
    
    private boolean isShopPremium;
    
    // force the data being populated
    // with non-default Constructor
    private UserData() {
        
    }
    
    public UserData(boolean isShopBasic, boolean isShopPremium) {
        this.isShopBasic = isShopBasic;
        this.isShopPremium = isShopPremium;
    }

    public boolean isShopBasic() {
        return isShopBasic;
    }

    public boolean isShopPremium() {
        return isShopPremium;
    }
}
