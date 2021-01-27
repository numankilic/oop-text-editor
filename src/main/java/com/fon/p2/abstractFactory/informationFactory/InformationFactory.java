package com.fon.p2.abstractFactory.informationFactory;

import com.fon.p2.abstractFactory.AbstractFactory;
import com.fon.p2.abstractFactory.errorFactory.Error;

public class InformationFactory extends AbstractFactory{

    @Override
    public Error getAlert(String alertType) {
        return null;
    }

    @Override
    public Information getInformation(String infoType) {
        if (infoType == null) {
            return null;
        }
        if (infoType.equalsIgnoreCase("TextNotFound")) {
            return new TextNotFoundInfo();

        } else if (infoType.equalsIgnoreCase("NothingToUndo")) {
            return new NothingToUndoInfo();

        } else if (infoType.equalsIgnoreCase("NothingToRedo")) {
            return new NothingToRedoInfo();

        } 

        return null;
    }
    
}
