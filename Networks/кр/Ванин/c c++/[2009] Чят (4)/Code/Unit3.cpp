//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "Unit3.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TForm3 *Form3;
extern HANDLE hCom;
extern DCB dcb;
extern OVERLAPPED OL;
extern int INFOBITS;
//---------------------------------------------------------------------------
__fastcall TForm3::TForm3(TComponent* Owner)
        : TForm(Owner)
{
}
//---------------------------------------------------------------------------



void __fastcall TForm3::Button1Click(TObject *Sender)
{
  TMsgDlgButtons buttons;
  buttons << mbOK;
  unsigned long num;
  if ( (ComboBox2->ItemIndex == 0 && ComboBox3->ItemIndex == 2)
                                  ||
      ((ComboBox2->ItemIndex == 1 || ComboBox2->ItemIndex == 2 || ComboBox2->ItemIndex == 3)
                                  &&
        ComboBox3->ItemIndex == 1))
    {MessageDlg("ÍÅÂÅÐÍÛÅ ÏÀÐÀÌÅÒÐÛ!",mtError,buttons,0);
     return;}
  char * buffer = (char*)HeapAlloc(GetProcessHeap(),HEAP_ZERO_MEMORY, 8);
  buffer[0]='\x01';
  buffer[1]='\x02';
  buffer[2]=(char)ComboBox1->ItemIndex+1;
//  if (CheckBox1->State == cbChecked) buffer[3]='\x01';
  buffer[3]='\xFF';
  buffer[4]=(char)ComboBox4->ItemIndex+1;
  buffer[5]=(char)ComboBox2->ItemIndex+1;
  buffer[6]=(char)ComboBox3->ItemIndex+1;
  buffer[7]='\x02';
  WriteFile(hCom,buffer,8,&num,&OL);
  for(unsigned long i=0;i<4000000;i++)
   for(unsigned long j=0;j<300;j++);
  HeapFree(GetProcessHeap(),0,buffer);
  GetCommState(hCom,&dcb);
  switch (ComboBox1->ItemIndex)
  {
   case 0: dcb.BaudRate=CBR_110;break;
   case 1: dcb.BaudRate=CBR_300;break;
   case 2: dcb.BaudRate=CBR_600;break;
   case 3: dcb.BaudRate=CBR_1200;break;
   case 4: dcb.BaudRate=CBR_2400;break;
   case 5: dcb.BaudRate=CBR_4800;break;
   case 6: dcb.BaudRate=CBR_9600;break;
   case 7: dcb.BaudRate=CBR_14400;break;
   case 8: dcb.BaudRate=CBR_19200;break;
   case 9: dcb.BaudRate=CBR_38400;break;
   case 10: dcb.BaudRate=CBR_56000;break;
   case 11: dcb.BaudRate=CBR_57600;break;
   case 12: dcb.BaudRate=CBR_115200;break;
   case 13: dcb.BaudRate=CBR_128000;break;
   case 14: dcb.BaudRate=CBR_256000;break;
  }
  //if (CheckBox1->State == cbChecked)

  dcb.fParity=FALSE;
    switch (ComboBox4->ItemIndex)
    {
     case 0: dcb.Parity=EVENPARITY;break;
     case 1: dcb.Parity=MARKPARITY;break;
     case 2: dcb.Parity=NOPARITY;break;
     case 3: dcb.Parity=ODDPARITY;break;
     case 4: dcb.Parity=SPACEPARITY;break;
    }
  INFOBITS=ComboBox2->ItemIndex;
  switch (ComboBox3->ItemIndex)
  {
   case 0: dcb.StopBits=ONESTOPBIT;break;
   case 1: dcb.StopBits=ONE5STOPBITS;break;
   case 2: dcb.StopBits=TWOSTOPBITS;break;
  }
  int er = SetCommState(hCom,&dcb);
/*  if (!er) {strcpy(Message,"Îøèáêà óòàíîâêè ïàðàìåòðîâ ïîðòà");
            NeedInMsg=1;}*/
  Form3->ModalResult=1;
}
//---------------------------------------------------------------------------


void __fastcall TForm3::FormCreate(TObject *Sender)
{
  GetCommState(hCom,&dcb);
  if (dcb.BaudRate == CBR_110) ComboBox1->ItemIndex=0;
  if (dcb.BaudRate == CBR_300) ComboBox1->ItemIndex=1;
  if (dcb.BaudRate == CBR_600) ComboBox1->ItemIndex=2;
  if (dcb.BaudRate == CBR_1200) ComboBox1->ItemIndex=3;
  if (dcb.BaudRate == CBR_2400) ComboBox1->ItemIndex=4;
  if (dcb.BaudRate == CBR_4800) ComboBox1->ItemIndex=5;
  if (dcb.BaudRate == CBR_9600) ComboBox1->ItemIndex=6;
  if (dcb.BaudRate == CBR_14400) ComboBox1->ItemIndex=7;
  if (dcb.BaudRate == CBR_19200) ComboBox1->ItemIndex=8;
  if (dcb.BaudRate == CBR_38400) ComboBox1->ItemIndex=9;
  if (dcb.BaudRate == CBR_56000) ComboBox1->ItemIndex=10;
  if (dcb.BaudRate == CBR_57600) ComboBox1->ItemIndex=11;
  if (dcb.BaudRate == CBR_115200) ComboBox1->ItemIndex=12;
  if (dcb.BaudRate == CBR_128000) ComboBox1->ItemIndex=13;
  if (dcb.BaudRate == CBR_256000) ComboBox1->ItemIndex=14;
//  if (dcb.fParity == TRUE)
//    CheckBox1->State=cbChecked;
//  RadioGroup1->Enabled = TRUE;
  if (dcb.Parity == EVENPARITY) ComboBox4->ItemIndex=0;
  if (dcb.Parity == MARKPARITY) ComboBox4->ItemIndex=1;
  if (dcb.Parity == NOPARITY) ComboBox4->ItemIndex=2;
  if (dcb.Parity == ODDPARITY) ComboBox4->ItemIndex=3;
  if (dcb.Parity == SPACEPARITY) ComboBox4->ItemIndex=4;

//    else CheckBox1->State=cbUnchecked;
  ComboBox2->ItemIndex=INFOBITS;


  if (dcb.StopBits == ONESTOPBIT) ComboBox3->ItemIndex=0;
  if (dcb.StopBits == ONE5STOPBITS) ComboBox3->ItemIndex=1;
  if (dcb.StopBits == TWOSTOPBITS) ComboBox3->ItemIndex=2;

}
//---------------------------------------------------------------------------


//---------------------------------------------------------------------------



void __fastcall TForm3::Button2Click(TObject *Sender)
{
  ModalResult=2;        
}
//---------------------------------------------------------------------------


