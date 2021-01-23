/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fon.p1.abstractFactory;

import com.fon.p1.abstractFactory.informationFactory.Information;
import com.fon.p1.abstractFactory.errorFactory.Error;

/**
 *
 * @author Numan
 */
public abstract class AbstractFactory {
    
    public abstract Error getAlert(String errorType);
    public abstract Information getInformation(String infoType);
    
}
