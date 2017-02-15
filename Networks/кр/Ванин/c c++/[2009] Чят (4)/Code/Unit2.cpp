//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "Unit1.h"
#include "Unit2.h"
#include "Unit7.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TForm2 *Form2;
HANDLE  extern hCom;
//---------------------------------------------------------------------------
__fastcall TForm2::TForm2(TComponent* Owner)
        : TForm(Owner)
{

}

void __fastcall extern Init(int);
int __fastcall extern SelectPort();
HANDLE extern hInputThread;
DWORD extern InputThreadID;
DWORD WINAPI extern ReadThread(LPVOID);
//---------------------------------------------------------------------------

void __fastcall TForm2::FormCreate(TObject *Sender)
{

  Init(SelectPort()-1);
  if (hCom==INVALID_HANDLE_VALUE) Application->Terminate();
  else
  {hInputThread = CreateThread(NULL,65535,&ReadThread,NULL,0,&InputThreadID);
  if (!hInputThread)
    {ShowMessage("Невозможно создать поток");
     Application->Terminate();}
  Form2->Visible=TRUE;
  Form2->Memo1->ReadOnly=TRUE;
  Form2->Memo1->Clear();}
}



//---------------------------------------------------------------------------
void __fastcall TForm2::FormClose(TObject *Sender, TCloseAction &Action)
{
 Application->Terminate();        
}
//---------------------------------------------------------------------------

void __fastcall TForm2::Button1Click(TObject *Sender)
{
Form1->Memo2->Clear();
Form1->Show();
}
//---------------------------------------------------------------------------

void __fastcall TForm2::Button3Click(TObject *Sender)
{
Form7->Show();
}
//---------------------------------------------------------------------------

