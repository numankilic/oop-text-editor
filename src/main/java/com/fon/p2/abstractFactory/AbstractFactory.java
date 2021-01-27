package com.fon.p2.abstractFactory;

import com.fon.p2.abstractFactory.informationFactory.Information;
import com.fon.p2.abstractFactory.errorFactory.Error;


public abstract class AbstractFactory {
    
    public abstract Error getAlert(String errorType);
    public abstract Information getInformation(String infoType);
    
}
