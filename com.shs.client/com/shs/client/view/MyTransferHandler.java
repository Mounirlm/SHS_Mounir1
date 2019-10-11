package com.shs.client.view;

import java.awt.Component;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.TransferHandler;

import com.shs.commons.model.Sensor;

public class MyTransferHandler extends TransferHandler 
{
	
	 @Override
    public boolean canImport(TransferSupport support) 
	 {
        return (support.getComponent() instanceof JLabel) && support.isDataFlavorSupported(Sensor.SENSOR_DATA_FLAVOR);
    }

    @Override
    public boolean importData(TransferSupport support) 
    {
        boolean accept = false;
        if (canImport(support)) 
        {
            try 
            {
                Transferable t = support.getTransferable();
                Object value = t.getTransferData(Sensor.SENSOR_DATA_FLAVOR);
                if (value instanceof Sensor) 
                {
                    Component component = support.getComponent();
                    accept = true;
                    
                }
            }
            catch (Exception exp) 
            {
                exp.printStackTrace();
            }
        }
        return accept;
    }

    @Override
    public int getSourceActions(JComponent c) 
    {
        return DnDConstants.ACTION_COPY_OR_MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent c) 
    {
        Transferable t = null;
        if (c instanceof JList) 
        {
            @SuppressWarnings("unchecked")
            JList<Sensor> list = (JList<Sensor>) c;
            Object value = list.getSelectedValue();
            if (value instanceof Sensor) 
            {
            	Sensor se = (Sensor) value;
            	return  se;
            }
        }
        return null;
        
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        System.out.println("ExportDone");
        // Here you need to decide how to handle the completion of the transfer,
        // should you remove the item from the list or not...
    }

}
