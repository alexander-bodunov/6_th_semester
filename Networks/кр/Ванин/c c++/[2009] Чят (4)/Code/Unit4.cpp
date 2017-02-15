//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "Unit4.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TForm4 *Form4;
extern COMMTIMEOUTS  ct;
extern HANDLE hCom;
extern OVERLAPPED OL;
//---------------------------------------------------------------------------
__fastcall TForm4::TForm4(TComponent* Owner)
        : TForm(Owner)
{
}
//---------------------------------------------------------------------------

void __fastcall TForm4::FormCreate(TObject *Sender)
{
/*  COMMPROP prop;
  GetCommProperties(hCom,&prop);
  AnsiString  str(prop.dwMaxTxQueue);
  AnsiString str1(prop.dwMaxRxQueue);
  ShowMessage(str);
  ShowMessage(str1);*/
  GetCommTimeouts(hCom,&ct);
  char * buf = new char [10];
  Edit1->SetTextBuf(itoa(ct.ReadIntervalTimeout,buf,10));
  Edit2->SetTextBuf(itoa(ct.ReadTotalTimeoutMultiplier,buf,10));
  Edit3->SetTextBuf(itoa(ct.ReadTotalTimeoutConstant,buf,10));
  Edit4->SetTextBuf(itoa(ct.WriteTotalTimeoutMultiplier,buf,10));
  Edit5->SetTextBuf(itoa(ct.WriteTotalTimeoutConstant,buf,10));
  delete buf;
}
//---------------------------------------------------------------------------

void __fastcall TForm4::Button1Click(TObject *Sender)
{
  TMsgDlgButtons buttons;
  buttons << mbOK;
  if (Edit1->Text.ToIntDef(0)>255 ||
      Edit2->Text.ToIntDef(0)>255 ||
      Edit3->Text.ToIntDef(0)>255 ||
      Edit4->Text.ToIntDef(0)>255 ||
      Edit5->Text.ToIntDef(0)>255 )
      {MessageDlg("Значения не должны превышать 255",mtError,buttons,0);
       return;}

  unsigned long num;
  char * buffer = (char*)HeapAlloc(GetProcessHeap(),HEAP_ZERO_MEMORY, 8);
  buffer[0] =1;
  buffer[1] =3;
  buffer[2] = (char)Edit1->Text.ToIntDef(0);
  buffer[3] = (char)Edit2->Text.ToIntDef(0);
  buffer[4] = (char)Edit3->Text.ToIntDef(0);
  buffer[5] = (char)Edit4->Text.ToIntDef(0);
  buffer[6] = (char)Edit5->Text.ToIntDef(0);
  buffer[7] = 2;
  WriteFile(hCom,buffer,8,&num,&OL);
//  ShowMessage("1");
  for(unsigned long i=0;i<4000000;i++)
   for(unsigned long j=0;j<300;j++);
//  ShowMessage("2");
  HeapFree(GetProcessHeap(),0,buffer);

  ct.ReadIntervalTimeout = Edit1->Text.ToIntDef(0);
  ct.ReadTotalTimeoutMultiplier = Edit2->Text.ToIntDef(0);
  ct.ReadTotalTimeoutConstant = Edit3->Text.ToIntDef(0);
  ct.WriteTotalTimeoutMultiplier = Edit4->Text.ToIntDef(0);
  ct.WriteTotalTimeoutConstant = Edit5->Text.ToIntDef(0);
  SetCommTimeouts(hCom,&ct);
  ModalResult=1;
}
//---------------------------------------------------------------------------
void __fastcall TForm4::Button2Click(TObject *Sender)
{
  ModalResult=2;        
}
//---------------------------------------------------------------------------
