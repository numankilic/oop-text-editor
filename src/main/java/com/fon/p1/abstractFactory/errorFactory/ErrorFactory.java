/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fon.p1.abstractFactory.errorFactory;

import com.fon.p1.abstractFactory.AbstractFactory;
import com.fon.p1.abstractFactory.informationFactory.Information;
import com.fon.p1.abstractFactory.errorFactory.Error;

/**
 *
 * @author pepper
 */
public class ErrorFactory extends AbstractFactory{

    public Error getAlert(String alertType) {
        if (alertType == null) {
            return null;
        }
        if (alertType.equalsIgnoreCase("FileNotFound")) {
            return new FileNotFoundError();

        } else if (alertType.equalsIgnoreCase("FileReadError")) {
            return new FileReadErrorError();

        } else if (alertType.equalsIgnoreCase("FileSaveError")) {
            return new FileSaveErrorError();

        }

        return null;
    }

    @Override
    public Information getInformation(String infoType) {
        return null;
    }
}
