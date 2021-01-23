/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fon.p1.abstractFactory;

import com.fon.p1.abstractFactory.informationFactory.InformationFactory;
import com.fon.p1.abstractFactory.errorFactory.ErrorFactory;

/**
 *
 * @author Numan
 */
public class FactoryProducer {
    public static AbstractFactory getFactory(String choice) {
        if(choice.equalsIgnoreCase("information")){
            return new InformationFactory();
        }
        else if(choice.equalsIgnoreCase("error")){
            return new ErrorFactory();
        }else{
            return null;
        }
   }
    
}
