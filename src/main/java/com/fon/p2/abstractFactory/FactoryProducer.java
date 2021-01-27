package com.fon.p2.abstractFactory;

import com.fon.p2.abstractFactory.informationFactory.InformationFactory;
import com.fon.p2.abstractFactory.errorFactory.ErrorFactory;


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
