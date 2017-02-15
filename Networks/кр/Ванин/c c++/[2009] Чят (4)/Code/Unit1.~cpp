//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "Unit1.h"
#include "Unit2.h"
#include "Unit3.h"
#include "Unit4.h"
#include "Unit5.h"
#include "Unit6.h"
#include "Unit7.h"

//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma resource "*.dfm"
TForm1 *Form1;

//---------------------------------------------------------------------------
__fastcall TForm1::TForm1(TComponent* Owner)
        : TForm(Owner)
{

}
//---------------------------------------------------------------------------

HANDLE hCom, hInputThread, hMsgThread;
DCB dcb;
DWORD InputThreadID, MsgThreadID;
OVERLAPPED OL;
COMMTIMEOUTS  ct;
int SENDCONNECT=0;
int ERROR1, NUMBER_OF_REPEATS=0;
char * BUFFER;
int LEN, INFOBITS=3;
//---------------------------------------------------------------------------
void __fastcall Init(int i)
{
if (i)
 hCom = CreateFile("COM2", GENERIC_READ | GENERIC_WRITE,
       /*FILE_SHARE_READ | FILE_SHARE_WRITE*/0, NULL,
       OPEN_EXISTING, FILE_FLAG_OVERLAPPED, 0);
 else
   hCom = CreateFile("COM1", GENERIC_READ | GENERIC_WRITE,
       /*FILE_SHARE_READ | FILE_SHARE_WRITE*/0, NULL,
       OPEN_EXISTING, FILE_FLAG_OVERLAPPED, 0);

if( hCom == INVALID_HANDLE_VALUE )
  {ShowMessage("Не удалось открыть порт!");
   Application->Terminate();}
else
{
BOOL retcode=PurgeComm(hCom,PURGE_TXABORT | PURGE_RXABORT |
                           PURGE_TXCLEAR | PURGE_RXCLEAR);
if (!retcode) ShowMessage("Очистка не удалась");

retcode = ClearCommBreak(hCom);
if (!retcode) ShowMessage("Восстановление порта не удалось");

retcode = GetCommState(hCom,&dcb);
if (!retcode) ShowMessage("Невозможно получить параметры порта");

dcb.BaudRate = CBR_9600;
dcb.ByteSize = 8;
dcb.fParity=FALSE;
dcb.Parity = EVENPARITY;
dcb.StopBits = ONESTOPBIT;
dcb.fErrorChar = FALSE;
//dcb.ErrorChar=255;
dcb.fNull=FALSE;

ct.ReadIntervalTimeout=10;
ct.ReadTotalTimeoutMultiplier=ct.ReadTotalTimeoutConstant=0;
ct.WriteTotalTimeoutMultiplier=ct.WriteTotalTimeoutConstant=0;

int er = SetCommState(hCom,&dcb);
//if (!er) {strcpy(Message,"Ошибка утановки параметров порта");
//          NeedInMsg=1;}
//if (!retcode) ShowMessage("SetCommState failed");

retcode = SetCommTimeouts(hCom,&ct);
if (!retcode) ShowMessage("Невозможно установить параметры порта");
}

}
//---------------------------------------------------------------------------
char __fastcall ostatok(char a)
{
 char k=11;      //полином
 char z=0;       // остаток
 char x=64;      // множитель
 k=k<<3;
 for(int i=0;i<4;i++)
 {
  if ((a & x) > 0)
   {
    a=a ^ k;
    k=k>>1;
    x=x/2;
   }
  else
    {
     k=k>>1;
     x=x/2;
    }
 }
 return(a);
}
//---------------------------------------------------------------------------
void __fastcall Code(unsigned char * buf,unsigned char * buffer
, int num)
{
// unsigned char *buf = new unsigned char[2*num+1];
 //buf[2*num+1]='\0';
 int i,j=0;
 for(i=0;i<num;i++)
  {
   unsigned char a=buffer[i];
   a=a>>4;
   a=a<<3;
   a=a+ostatok(a);
   buf[j]=a;
   j++;
   a=buffer[i];
   a=a<<4;
   a=a>>1;
   a=a+ostatok(a);
   buf[j]=a;
   j++;
  }
// return(buf);
}
//---------------------------------------------------------------------------
void __fastcall Decode(unsigned char * buf,unsigned char * buffer, int num)
{
 int i,j=0;
// unsigned char * buf = (char*)HeapAlloc(GetProcessHeap(), HEAP_ZERO_MEMORY,num/2+1);
// new unsigned char[num/2+1];
 //buf[num/2]='\0';
 for(i=0; i<num;i++)
  {
    if (ostatok(buffer[i])==0)
     {
      buf[j]= buf[j] | buffer[i]>>3;
     }
      else ERROR1=1;
    if (i%2==0)
      buf[j]=buf[j]<<4;
     else j++;
  }
//return(buf);  
}
//---------------------------------------------------------------------------
/*void __fastcall AddToMemo(TMemo * pMemo, char * str)
{
  int len;
  len = pMemo->GetTextLen();
  char * buf1 = (char*)HeapAlloc(GetProcessHeap(),HEAP_ZERO_MEMORY, len+1);
  if (!buf1)
   {
    ShowMessage("AddToMemo: buf1=NULL");
    Application->Terminate();
   }
  pMemo->GetTextBuf(buf1, len+1);
  char * buf2 = (char*)HeapAlloc(GetProcessHeap(),HEAP_ZERO_MEMORY, len+strlen(str)+1);
  if (!buf2)
   {
    ShowMessage("AddToMemo: buf2=NULL");
    Application->Terminate();
   }
  strcpy(buf2,str);
  strcpy(buf2+strlen(str),buf1);
  pMemo->SetTextBuf(buf2);
  HeapFree(GetProcessHeap(),0,buf1);
  HeapFree(GetProcessHeap(),0,buf2);
} */
void __fastcall AddToMemo(TMemo * pMemo, char * str)
{
  int len;
  len = pMemo->GetTextLen();
  char * buf1 = (char*)HeapAlloc(GetProcessHeap(),HEAP_ZERO_MEMORY, len+1);
  if (!buf1)
   {ShowMessage("Невозможно выделить память");
    Application->Terminate();}
  pMemo->GetTextBuf(buf1, len+1);
  char * buf2 = (char*)HeapAlloc(GetProcessHeap(),HEAP_ZERO_MEMORY, len+strlen(str)+2);
  if (!buf2)
   {ShowMessage("Невозможно выделить память");
    Application->Terminate();}
  strcpy(buf2,buf1);
  strcpy(buf2+len,str);
  pMemo->SetTextBuf(buf2);
  HeapFree(GetProcessHeap(),0,buf1);
  HeapFree(GetProcessHeap(),0,buf2);
}
//---------------------------------------------------------------------------
void __fastcall PrintMessage(char * m)
{
 TMsgDlgButtons buttons;
 buttons<<mbOK;
 MessageDlg(m, mtError, buttons,0);
}
//---------------------------------------------------------------------------
DWORD WINAPI ReadThread(LPVOID p)
{
//ShowMessage(s);
DWORD CommEventMask = EV_BREAK | EV_CTS | EV_DSR | EV_ERR | EV_RING |
                      EV_RLSD | EV_RXCHAR | EV_RXFLAG | EV_TXEMPTY;

BOOL retcode = SetCommMask(hCom,CommEventMask);
if( !retcode ) ShowMessage("Невозможно установить маску отслеживаемых событий");
OL.hEvent=CreateEvent(NULL, FALSE, FALSE, NULL);
OL.Offset=0;
OL.OffsetHigh=0;
DWORD EventMask = 0;
int cadr=0;
do
{
retcode = WaitCommEvent(hCom, &EventMask, &OL);
if ( ( !retcode ) && (GetLastError()==ERROR_IO_PENDING) )
  WaitForSingleObject(OL.hEvent, INFINITE);
if (EventMask & EV_RXCHAR)
  {
   DWORD ErrorMask = 0;
   COMSTAT CStat;
   ClearCommError(hCom, &ErrorMask, &CStat);
   DWORD quelen = CStat.cbInQue;
   char *lpInBuffer = (char*)HeapAlloc(GetProcessHeap(),HEAP_ZERO_MEMORY,quelen+1);
   if (!lpInBuffer)
   {
    ShowMessage("Невозможно выделить память");
    Application->Terminate();
   }
//   memset(&lpInBuffer, '\0', (int)quelen);
   DWORD dwReaded = 0;
   retcode = ReadFile(hCom, lpInBuffer, quelen, &dwReaded, &OL);
   if( dwReaded == 0 && GetLastError() == ERROR_IO_PENDING )
     retcode = GetOverlappedResult(hCom, &OL, &dwReaded, FALSE) ;
    else
      if (dwReaded>0)
       {int num;
        char  * buffer;
//        int cadr=0;
        short int length;        
        if (lpInBuffer[0] == 0x01) //новый кадр
          {
            switch (lpInBuffer[1])
             {
              case 1:       // информационный кадр
               {
              //  TDateTime DateTime=Time();
              //  AnsiString str = TimeToStr(DateTime);

                memcpy(&length,lpInBuffer+2,2);

                buffer =  (char*)HeapAlloc(GetProcessHeap(),HEAP_ZERO_MEMORY,length+1);
                if (!buffer)
                  {
                   ShowMessage("Невозможно выделить память");
                   Application->Terminate();
                  }

                if (length > 3) memcpy(buffer, lpInBuffer+4,4);
                //HeapFree(GetProcessHeap(),0,time);
                break;
               }
              case 2:     // установка DCB
               {
                int baud_rate = lpInBuffer[2]-1;
//                int fPar = (lpInBuffer[3]==1)?1:0;
                int parity = lpInBuffer[4]-1;
                INFOBITS = lpInBuffer[5]-1;
                int stop = lpInBuffer[6]-1;
                GetCommState(hCom, &dcb);
                switch (baud_rate)
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
                //if (fPar) dcb.fParity = TRUE;
                dcb.fParity = FALSE;
                switch (parity)
                 {
                  case 0: dcb.Parity=EVENPARITY;break;
                  case 1: dcb.Parity=MARKPARITY;break;
                  case 2: dcb.Parity=NOPARITY;break;
                  case 3: dcb.Parity=ODDPARITY;break;
                  case 4: dcb.Parity=SPACEPARITY;break;
                 }

                switch (stop)
                 {
                  case 0: dcb.StopBits=ONESTOPBIT;break;
                  case 1: dcb.StopBits=ONE5STOPBITS;break;
                  case 2: dcb.StopBits=TWOSTOPBITS;break;
                 }
                int er = SetCommState(hCom,&dcb);
                ShowMessage("Ошибка утановки параметров порта");
                break;
               }
              case 3:     // заполнение канала для контроля соединения
               {
                char s[3]={1,3,2};
                Form1->Timer2->Enabled=FALSE;
                Form1->Timer2->Interval=5000;
                 int retcode=WriteFile(hCom, s, 3, (unsigned long*)&num, &OL);
                Form1->Timer2->Enabled=TRUE;
                break;
               }
              case 4: //установка соединения
               {
                char s[3]={1,4,2};
                Form1->Button1->Enabled=TRUE;
                if (!SENDCONNECT)
                 int retcode=WriteFile(hCom, s, 3, (unsigned long*)&num, &OL);
                Form1->Timer2->Enabled=FALSE;
                Form1->Button2->Enabled=FALSE;
                Form1->Timer2->Interval=5000;
//              SENDCONNECT=0;
                break;
               }
              case 5:              //разрыв соединения
               {Form1->Button1->Enabled=FALSE;
                Form1->Timer2->Enabled=FALSE;
                Form1->Timer2->Interval=5000;
                TMsgDlgButtons buttons;
                buttons << mbOK << mbCancel;
                ShowMessage("Сеанс связи завершен");
                        if (BUFFER!=NULL)
                         HeapFree(GetProcessHeap(),0,BUFFER);
                         Form1->Button2->Enabled=TRUE;
                         Form1->Button1->Enabled=FALSE;
                continue;
                break;
                }
              case 6:            //квитанция
                Form1->Timer3->Enabled=FALSE; //успешно принято
                Form1->Timer3->Interval=10000;
                Form1->Button1->Enabled=TRUE;//SUCCESS = 1;
                HeapFree(GetProcessHeap(),0,BUFFER);
                break;
              case 7:                        //повторить передачу

                Form1->Timer3->Enabled=FALSE;
                Form1->Timer3->Interval=10000;
                WriteFile(hCom, BUFFER, LEN, (unsigned long*)&num, &OL);
                Form1->Timer3->Enabled=TRUE;
                //REPEAT = 1;
                break;
             }
          }
         else              // продолжение информационного кадра
          {
           if (!memchr(lpInBuffer, 2, dwReaded))
            {memcpy(buffer+4+cadr*8, lpInBuffer, dwReaded);
             cadr++;}
            else                            //конец кадра
             {
             memcpy(buffer+4+cadr*8, lpInBuffer, dwReaded-1);
             char *tmp=buffer+4+cadr*8;
             char * b = (char*)HeapAlloc(GetProcessHeap(), HEAP_ZERO_MEMORY,length/2+1);
             if (!b)
              {
               ShowMessage("Невозможно выделить память");
               Application->Terminate();
              }
             Decode(b, buffer,length);
             if (ERROR1)
               {
                HeapFree(GetProcessHeap(),0,buffer);
                HeapFree(GetProcessHeap(),0,lpInBuffer);
                unsigned long n;
                WriteFile(hCom, "\x01\x07\x02",3, &n, &OL );
                PurgeComm(hCom, /*PURGE_TXCLEAR |*/ PURGE_RXCLEAR);
                EventMask=0;
                ResetEvent(OL.hEvent);
                ERROR1=0;
                HeapFree(GetProcessHeap(),0,b);
                continue;
               }
               else
                 {unsigned long n;
                  WriteFile(hCom, "\x01\x06\x02",3, &n, &OL );
                  Form2->Memo1->Clear();
                  AddToMemo(Form2->Memo1,(char*)b);
                  AddToMemo(Form7->Memo1," <IN> ");
                  AddToMemo(Form7->Memo1,(char*)b);
                  }

             HeapFree(GetProcessHeap(),0,buffer);
             HeapFree(GetProcessHeap(),0,b);
             cadr=0;
             }
           }
     }
   HeapFree(GetProcessHeap(),0,lpInBuffer);
  } //ev_rxchar
PurgeComm(hCom, /*PURGE_TXCLEAR |*/ PURGE_RXCLEAR);
EventMask=0;
ResetEvent(OL.hEvent);
}
while(1);
}

int __fastcall SelectPort()
{
int i;
Application->CreateForm(__classid(TForm6), &Form6);
if (i = (Form6->ShowModal()))
    {Form6->Close();
     return(i);}
Form6->Visible=TRUE;
}
//---------------------------------------------------------------------------
void __fastcall TForm1::FormCreate(TObject *Sender)
{
//Message = new char[100];
Form1->Memo2->Clear();
Form1->Button1->Enabled=FALSE;
//char str[10];

}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button1Click(TObject *Sender)
{
if (!Memo2->Text.Length()) return;

char * outbuffer;
//char * buffer;
unsigned long num;

/*TDateTime DateTime=Time();
AnsiString str = TimeToStr(DateTime);
char * time = (char*)HeapAlloc(GetProcessHeap(),HEAP_ZERO_MEMORY,str.Length()+1);
if (!time)
 {
   ShowMessage("при отправке time=NULL");
   Application->Terminate();
 }
memcpy(time,str.c_str(),str.Length());
time[str.Length()]='\0';              */

int len = Form1->Memo2->GetTextLen();
outbuffer = (char*)HeapAlloc(GetProcessHeap(),HEAP_ZERO_MEMORY,len+3);
if (!outbuffer)
 {
   ShowMessage("при отправке outbuffer=NULL");
   Application->Terminate();
 }
Form1->Memo2->GetTextBuf(outbuffer,len+1);
outbuffer[len]='\r';
outbuffer[len+1]='\n';

unsigned char *b = (char*)HeapAlloc(GetProcessHeap(),HEAP_ZERO_MEMORY,2*(len+2)+1);
if (!b)
 {
   ShowMessage("Невозможно выделить память");
   Application->Terminate();
 }
Code(b,outbuffer,len+2);

BUFFER = (char*)HeapAlloc(GetProcessHeap(),HEAP_ZERO_MEMORY, (len+2)*2+6);
if (!BUFFER)
 {
   ShowMessage("при отправке BUFFER=NULL");
   Application->Terminate();
 }
BUFFER[0] = 0x01; //заголовок кадра
BUFFER[1] = 0x01; //признак информационного кадра
int g=((len+2)*2);
char * s = (char*)&g;
BUFFER[2] = s[0];
BUFFER[3] = s[1];
//*((int*)(buffer+2)) = (int)(-strlen(outbuffer)); //длина информационного кадра
memcpy(BUFFER+4, b, (len+2)*2); //информационное поле
BUFFER[(len+2)*2+4] = 0x02; //окончание кадра
//short int  f;
//memcpy(&f,BUFFER+2,2);
//f=-f;
LEN = (len+2)*2+5;

int retcode=WriteFile(hCom, BUFFER, LEN, &num, &OL);
Form1->Timer3->Enabled=TRUE;
//Button1->Enabled=FALSE;

AddToMemo(Form7->Memo1, " <OUT> ");
AddToMemo(Form7->Memo1, outbuffer);

//AddToMemo(Form7->Memo1, time);
HeapFree(GetProcessHeap(),0,outbuffer);
HeapFree(GetProcessHeap(),0,b);
//HeapFree(GetProcessHeap(),0,time);
//delete b;

Form1->Memo2->Clear();
Form1->Memo2->SetFocus();

}
//---------------------------------------------------------------------------

void __fastcall TForm1::FormDestroy(TObject *Sender)
{
unsigned long num;
if (hCom!=INVALID_HANDLE_VALUE)WriteFile(hCom, "\x01\x05\x02", 3, &num, &OL);
CloseHandle(hCom);
CloseHandle(OL.hEvent);
}
//---------------------------------------------------------------------------


void __fastcall TForm1::Button2Click(TObject *Sender)
{

unsigned long num;
SENDCONNECT=1;
char buf[3] = {1,4,2};
int retcode=WriteFile(hCom, buf, 3, &num, &OL);
//Form1->Timer2->Enabled=TRUE;
buf[1] = 3;
retcode=WriteFile(hCom, buf, 3, &num, &OL);
Form1->Timer2->Enabled=TRUE;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Exit1Click(TObject *Sender)
{
 Application->Terminate();

}
//---------------------------------------------------------------------------


void __fastcall TForm1::Options1Click(TObject *Sender)
{
  Application->CreateForm(__classid(TForm3), &Form3);
  if (Form3->ShowModal())
    {Form3->Close();
     return;}
  Form3->Visible=TRUE;
}
//---------------------------------------------------------------------------



void __fastcall TForm1::TimeOuts1Click(TObject *Sender)
{
  Application->CreateForm(__classid(TForm4), &Form4);
  if (Form4->ShowModal())
    {Form4->Close();
     return;}
  Form4->Visible=TRUE;
}
//---------------------------------------------------------------------------

void __fastcall TForm1::About1Click(TObject *Sender)
{
  Application->CreateForm(__classid(TForm5), &Form5);
  if (Form5->ShowModal())
    {Form5->Close();
     return;}
  Form5->Visible=TRUE;
}
//---------------------------------------------------------------------------


//---------------------------------------------------------------------------


void __fastcall TForm1::HotKey1Enter(TObject *Sender)
{
  TForm1::Button1Click(Sender);
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Button3Click(TObject *Sender)
{
Form7->Show();
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Timer2Timer(TObject *Sender)
{
  TMsgDlgButtons buttons;
  Form1->Timer2->Enabled=FALSE;
  Form1->Timer2->Interval=5000;
  Form1->Button2->Enabled=TRUE;
  buttons << mbOK << mbCancel;
  int button = MessageDlg("Невозможно установить соединение со вторым компьютером или недоступна среда передачи. Закрыть приложение?",mtError,buttons,0);
  if (button==mrOk)
    Application->Terminate();
   else
     {if (BUFFER!=NULL)
       HeapFree(GetProcessHeap(),0,BUFFER);
      Button2->Enabled=TRUE;
      Button1->Enabled=FALSE;}
}
//---------------------------------------------------------------------------

void __fastcall TForm1::Timer3Timer(TObject *Sender)
{
  Form1->Timer3->Enabled=FALSE;
  Form1->Timer3->Interval=10000;
  ShowMessage("Сообщение не доставлено");

}
//---------------------------------------------------------------------------


