

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.comm.*;
import java.util.*;
import javax.swing.JOptionPane;

public class DataLinkLayer {
	private SerialPort serialPort;
	public boolean isConnect;
	private PhisicalLayer PhLayer;
	public ChatForm mainForm;
	private byte SourseAddress = (byte)0xC1;
	
	public String networkName;
	
	private boolean dataFlag;

        public boolean isReady;
	
	public class UserId
	{
		public byte userNA;
		public String usernick;

                @Override
                public String toString()
                {
                    return this.usernick;
                }
	}
	
         Vector<UserId> UserIDVector  = new Vector<UserId>();
		
	

	

	
	

	public DataLinkLayer (ChatForm tmpForm)
	{
		this.mainForm = tmpForm;
		this.dataFlag = true;
                this.isReady =true;

	}


	//������� ���������� ������ ����������� �������� ����������
	public void CheckConnection() 
	{
		byte[] snap = new byte[21];
		byte[] checkSnap = new byte[16];
		byte[] temp = this.networkName.getBytes();
		checkSnap[0] = this.SourseAddress;
		for (int index = 1; index <= this.networkName.length(); index++)
		{
			checkSnap[index] = temp[index-1];
		}
		snap = this.CreateCheckSnap(checkSnap);
               this.PhLayer.ReceiveMessage(snap);
            
	}


	public void ReadyToTransmit() 
	{
		if (this.isConnect) {

		}
	}
	

	
	public void ConnectToCOMPort(String name, String comPort, int rate)
	{
		if (!isConnect)
		{
			PhLayer = new PhisicalLayer(this);
			this.PhLayer.isReady = true;
			this.PhLayer.Start(comPort,rate);
			this.isConnect = true;
                        this.networkName = name;
		}
	}
	
//��������� ������ �� ����������� ������
	public void ReceiveDataFromAppLayer(String text)
	{
		int bufSize = text.length();
		byte[] receiveBuffer = new byte[bufSize];
		receiveBuffer = text.getBytes();
		byte[] snap = new byte[21];
                Byte[] code_bytes = new Byte[2];
		
		int number = 0;
		int currentBufSize = bufSize;
		int snapCount = 0;
		//-15
		while(currentBufSize-7 >= 0)
		{
			byte[] sendBuffer = new byte[16];
                        byte[] code_send_buffer = new byte[16];
			for (int index = 1; index<8;index++)
			{
				sendBuffer[0] = (byte)snapCount;
				sendBuffer[index] = receiveBuffer[index-1 + snapCount*7];
				currentBufSize -= 1;
			}
                //формируем закодированный кадр с данными
                        code_send_buffer[0] = sendBuffer[0];
                        int code_index = 1;
                        for (int index = 1; index<8;index++)
			{
				code_bytes =  reccurent_code.coding(sendBuffer[index]);
                                code_send_buffer[code_index] = code_bytes[0];
                                code_send_buffer[code_index + 1] = code_bytes[1];
                                code_index += 2;
			}

			if (currentBufSize != 0)
			{
				code_send_buffer[15] = (byte)0xE0;
			}
			else
			{
				code_send_buffer[15] = (byte)0xE1;
			}
			snap = this.CreateInformSnap(code_send_buffer);

			

			while (this.dataFlag)
			{
				if (this.PhLayer.isReady)
				{
					this.dataFlag = false;
					this.PhLayer.ReceiveMessage(snap);
					snapCount += 1;
				}
			}
			this.dataFlag = true;
		}
		
		if (currentBufSize != 0)
		{

                         byte[] sendBuffer = new byte[16];
                        byte[] code_send_buffer = new byte[16];
			for (int index = 1; index<currentBufSize+1;index++)
			{
				sendBuffer[0] = (byte)snapCount;
				sendBuffer[index] = receiveBuffer[index-1 + snapCount*7];
			}
                //формируем закодированный кадр с данными
                        code_send_buffer[0] = sendBuffer[0];
                        int code_index = 1;
                        for (int index = 1; index<currentBufSize+1;index++)
			{
				code_bytes =  reccurent_code.coding(sendBuffer[index]);
                                code_send_buffer[code_index] = code_bytes[0];
                                code_send_buffer[code_index + 1] = code_bytes[1];
                                code_index += 2;
			}

				code_send_buffer[15] = (byte)0xE1;

			snap = this.CreateInformSnap(code_send_buffer);

			while (this.dataFlag)
			{
				if (this.PhLayer.isReady)
				{
					this.dataFlag = false;
					this.PhLayer.ReceiveMessage(snap);
					snapCount += 1;
				}
			}
			this.dataFlag = true;
		}
		
	}
	
	
	
	public void ReceiveDataFromPhysicalLayer(byte[] receivebuffer, int numBytes)
	{
            Byte[] decode_vector = new Byte[2];
            int uncode_index = 1;
            int data_index = 1;
		if (receivebuffer[3] == this.SourseAddress)
		{
			UserId tempUser = new UserId();
			
			byte[] dataBuf = new byte[8];
			String text = "";
			
			byte[] data = new byte[16];
			data = this.TranslateSnap(receivebuffer);
			
                        dataBuf[0] = data[0];

			while (uncode_index < 15 )
			{
                                decode_vector[0] = (byte)reccurent_code.decoding(data[uncode_index]);
                                decode_vector[1] = (byte)reccurent_code.decoding(data[uncode_index+1]);

                                uncode_index += 2;
                                decode_vector[1] = (byte)(decode_vector[1] << 4);
                                decode_vector[1] = (byte)(decode_vector[1] & 240);
                                decode_vector[0] = (byte)(decode_vector[0] & 15);

                                byte UNCODE = (byte)(decode_vector[0]);
                                UNCODE = (byte)(UNCODE | decode_vector[1]);
				dataBuf[data_index] = UNCODE;
                                data_index += 1;
			}
				
			if (dataBuf[0] == 0)
			{
                                UserId tmp = (UserId) this.UserIDVector.get(0);
				this.mainForm.AppendTextAreaChatText(tmp.usernick + ">> ");
			}
			if (data[15] == (byte)0xE0)
			{
				text = new String((dataBuf), 1, 7 );
				this.mainForm.AppendTextAreaChatText(text);
	
			}
			else if (data[15] == (byte)0xE1)
			{
				text = new String(dataBuf, 1, 7 );
				this.mainForm.AppendTextAreaChatText(text);
				this.mainForm.AppendTextAreaChatText(" " + '\n');
	
			}
			
		}
		else if ((receivebuffer[3] == (byte)0xFF) && (receivebuffer[1] == (byte)0xDD))
		{

                     if (receivebuffer[2] != this.SourseAddress)
                    {
                        this.PhLayer.ReceiveMessage(receivebuffer);
                    }
                    this.mainForm.ClearAll();
                    this.mainForm.ResetAllButtonsToDefault();
                    this.UserIDVector.clear();
                    this.PhLayer.ClosePort();

		}

                else if (receivebuffer[3] == (byte)0xFF)
                {
                   byte[] b_UserName = new byte[10];
			byte userNA = receivebuffer[2];
			int index = 0;


			while (receivebuffer[index+5] != 0)
			{
				b_UserName[index] = receivebuffer[index+5];
				index++;
			}

			String s_UserName = new String(b_UserName,0,index);

			UserId tmpUser = new UserId();
			tmpUser.userNA = userNA;
			tmpUser.usernick = s_UserName;

                        boolean flag= true;
                        for (int i = 0; i < this.UserIDVector.size(); i++)
                        {
                            UserId tmp = (UserId)this.UserIDVector.get(i);
                            if (tmpUser.userNA == tmp.userNA)
                            {
                                flag = false;

                            }
                        }

                        if(flag)
                        {
                            this.UserIDVector.add(tmpUser);
                           this.mainForm.InitList(UserIDVector);
                             //this.mainForm.AddListItem(tmpUser.toString() + '\n');
                            JOptionPane.showMessageDialog(mainForm, "Соединение установлено");
                        }
                }

	}
	
	public byte[] CreateInformSnap(byte[] data)
	{
		byte[] snap = new byte[21];
		byte startByte,stopByte,DA,SA,Type,DATA;

	
		snap[0] = (byte)0xFF;
		snap[1] = (byte)0x11;
		snap[2] = (byte)this.SourseAddress;
		snap[3] = (byte)0xD1;
		for (int index = 4; index <20; index++)
		{
			snap[index] = data[index-4];
		}
		snap[20] = (byte)0xFF;
		
		return snap;
		
	}
	
	public byte[] CreateCheckSnap(byte[] data)
	{
		byte[] snap = new byte[21];
		byte startByte,stopByte,DA,SA,Type,DATA;

	
		snap[0] = (byte)0xFF;
		snap[1] = (byte)0x11;
		snap[2] = (byte)this.SourseAddress;
		snap[3] = (byte)0xFF;
		for (int index = 4; index <20; index++)
		{
			snap[index] = data[index-4];
		}
		snap[20] = (byte)0xFF;
		
		return snap;
		
	}

        public byte[] CreateDisconnectSnap()
        {

		byte[] snap = new byte[21];


		snap[0] = (byte)0xFF;
		snap[1] = (byte)0xDD;
		snap[2] = (byte)this.SourseAddress;
		snap[3] = (byte)0xFF;
		return snap;

        }
	
	public byte[] TranslateSnap(byte[] buffer)
	{
		byte[] data = new byte[16];
		for (int index = 4; index <20; index++)
		{
			data[index - 4] = buffer[index];
		}
		
		return data;
	}
        public void Disconnect()
        {
            byte[] disconnect = this.CreateDisconnectSnap();
            this.PhLayer.ReceiveMessage(disconnect);
        }
}
