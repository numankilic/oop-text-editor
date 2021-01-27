package com.fon.p2.strategy;

import com.fon.p2.abstractFactory.AbstractFactory;
import com.fon.p2.abstractFactory.FactoryProducer;
import java.io.FileWriter;

public class SaveNormal extends SaveAs implements Strategy {

    public AbstractFactory errorFactory = FactoryProducer.getFactory("error");
    public SaveAs saveAs = new SaveAs();

    @Override
    public String save(String filePath, String text) {

        try {
            FileWriter writer = new FileWriter(filePath);
            writer.write(text);
            writer.close();

        } catch (Exception e) {
            errorFactory.getAlert("filesaveerror").error();
        }
        return filePath;

    }

}
