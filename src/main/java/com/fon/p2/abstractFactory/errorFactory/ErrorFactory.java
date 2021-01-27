package com.fon.p2.abstractFactory.errorFactory;

import com.fon.p2.abstractFactory.AbstractFactory;
import com.fon.p2.abstractFactory.informationFactory.Information;


public class ErrorFactory extends AbstractFactory{

    public Error getAlert(String alertType) {
        if (alertType == null) {
            return null;
        }
        if (alertType.equalsIgnoreCase("FileNotFound")) {
            return new FileNotFoundError();

        } else if (alertType.equalsIgnoreCase("FileReadError")) {
            return new FileReadError();

        } else if (alertType.equalsIgnoreCase("FileSaveError")) {
            return new FileSaveError();

        }

        return null;
    }

    @Override
    public Information getInformation(String infoType) {
        return null;
    }
}
