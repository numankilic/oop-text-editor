/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fon.p1.abstractFactory.informationFactory;

import com.fon.p1.abstractFactory.AbstractFactory;
import com.fon.p1.abstractFactory.errorFactory.Error;

/**
 *
 * @author Numan
 */
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
