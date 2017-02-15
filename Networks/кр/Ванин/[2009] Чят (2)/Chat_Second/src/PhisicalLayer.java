


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.comm.*;
import java.util.*;
import java.io.*;
import java.lang.*;
import java.util.concurrent.atomic.*;
import javax.swing.*;
import javax.swing.Timer;

public  class PhisicalLayer implements SerialPortEventListener {

	/**
	 * @param args
	 */
	ChatForm mainForm;
	DataLinkLayer DlLayer;

	public boolean isReady;
        public boolean isInterrupted = false;
        public boolean isPortOpened;
	 
	
	public PhisicalLayer()
	{}
	public PhisicalLayer (DataLinkLayer iDlLayer)
	{
		this.DlLayer = iDlLayer;
	}
	
	CommPortIdentifier portId, currentPortId;
	SerialPort serialPort;
	InputStream inputStream;
	OutputStream outStream;
	Thread readThread;
	int sum = 0;
	byte[] globalBufer = new byte[100];
	int glBufIndex = 0; 
	
	
	private boolean isConnected;

	public void SearchPorts(String name) {
		System.setSecurityManager(null);
		
		Enumeration ports;
		ports = CommPortIdentifier.getPortIdentifiers();

		if (ports == null) {
			System.out.println("No comm ports found!");

			return;
		} else {
			while (ports.hasMoreElements()) {
				currentPortId = (CommPortIdentifier) ports.nextElement();
				if (currentPortId.getName().equals(name)) {
					this.portId = currentPortId;
					break;
				}

			}
		}
	}

	 public void SetPortParams(int rate) {
		
		try {
			serialPort.setSerialPortParams(rate, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
		} catch (UnsupportedCommOperationException e) {
		}
	}

	public void PortOpen() {
		try {
			serialPort = (SerialPort) this.portId.open("COM1", 2000);
                        isPortOpened = true;
                        JOptionPane.showMessageDialog(mainForm, "COM порт определен");
                        this.serialPort.setDTR(true);

		} catch (PortInUseException e) {
			System.out.println(portId.getName() + " in use by "
					+ e.currentOwner);

		}
	}

	public void serialEvent(SerialPortEvent event) {
		switch (event.getEventType()) {
		case SerialPortEvent.BI:
		case SerialPortEvent.OE:
		case SerialPortEvent.FE:
		case SerialPortEvent.PE:
		case SerialPortEvent.CD:
		case SerialPortEvent.CTS:
		case SerialPortEvent.DSR:
                {
                    boolean flag = this.serialPort.isDSR();
                            if (!this.serialPort.isDSR())
                            {
                                   isConnected = false;
                                   DlLayer.isConnect = false;
                                   JOptionPane.showMessageDialog(mainForm, "Соединение DSR потеряно");
                                   this.DlLayer.mainForm.ClearAll();
                                   this.DlLayer.mainForm.ResetAllButtonsToDefault();
                                   this.DlLayer.UserIDVector.clear();
                                   if (isPortOpened)
                                   {
                                    serialPort.close();
                                   }
                            }
                            else
                            {
                                this.Start("COM1", 115600);
                            }
                }
                break;
		case SerialPortEvent.RI:
		case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
			break;
		case SerialPortEvent.DATA_AVAILABLE:
		{
			
			this.GetDataFromInputStream();
			
		}
			break;
		}
	}

	public void Start(String portName, int rate) {

		SearchPorts(portName);
		PortOpen();
		SetPortParams(rate);

		try {
			this.outStream = serialPort.getOutputStream();
			this.inputStream = serialPort.getInputStream();
		} catch (IOException e) {
		}
		try 
		{
			serialPort.addEventListener(this);
		} 
		catch (TooManyListenersException e) 
		{
		}
		serialPort.notifyOnDataAvailable(true);
                serialPort.notifyOnBreakInterrupt(true);
                serialPort.notifyOnDSR(true);
                serialPort.notifyOnCarrierDetect(true);

	}
	
	public void ReceiveMessage(byte[] data)
	{
		try
		{
			this.isReady = false;
                        this.DlLayer.isReady = false;
			this.outStream.write(data);

			
		}
		catch (IOException e)
		{}
	}

	

	public void ClosePort() {
		serialPort.close();
                isPortOpened = false;
                JOptionPane.showMessageDialog(mainForm, "Пользователь вышел из сети");
                this.isConnected = false;
                this.DlLayer.isConnect = false;
	}
	

	
public synchronized void GetDataFromInputStream()
	{
		byte[] readBuffer = new byte[21];
		try 
		{
			int numBytes = 0;
			while (this.inputStream.available() > 0) 
				{

					numBytes = this.inputStream.read(readBuffer);
					if ((numBytes ==1) &&(readBuffer[0] == 1))
					{
						this.isReady = true;
                                                this.DlLayer.isReady = true;
                                                this.inputStream.reset();
					}
					this.sum += numBytes;
					for (int i = 0; i < numBytes; i++)
					{
						globalBufer[this.glBufIndex] = readBuffer[i];
						this.glBufIndex++;
					}
				}
				
				int index = 0;
				while (this.glBufIndex - 21 >= 0)
				{
					int frameIndex;
					byte[] frame = new byte[21];
					for (frameIndex = 0; frameIndex < 21; frameIndex++)
					{
						frame[frameIndex] = this.globalBufer[index];
						index++;
					}
				
				this.sum = 0;
				this.glBufIndex -= 21;
				String temp = new String(frame,0,21);
				this.outStream.write(1);
				//this.isReady = true;
				this.DlLayer.ReceiveDataFromPhysicalLayer(frame,21);
				
				}
			
		} 
		catch (IOException e) 
				{
				}
		
	}
	
}

