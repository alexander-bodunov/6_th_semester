//---------------------------------------------------------------------------

#ifndef Unit1H
#define Unit1H
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <Menus.hpp>
#include <ComCtrls.hpp>
#include <ExtCtrls.hpp>
//---------------------------------------------------------------------------

class TForm1 : public TForm
{
__published:	// IDE-managed Components
        TButton *Button1;
        TMemo *Memo2;
        TButton *Button2;
        TMainMenu *MainMenu1;
        TMenuItem *Exit1;
        TMenuItem *Options1;
        TMenuItem *File1;
        TMenuItem *About1;
        TMenuItem *N1;
        TButton *Button3;
        TTimer *Timer2;
        TTimer *Timer3;
        void __fastcall FormCreate(TObject *Sender);
        void __fastcall Button1Click(TObject *Sender);
        void __fastcall FormDestroy(TObject *Sender);
        void __fastcall Button2Click(TObject *Sender);
        void __fastcall Exit1Click(TObject *Sender);
        void __fastcall Options1Click(TObject *Sender);
        void __fastcall TimeOuts1Click(TObject *Sender);
        void __fastcall About1Click(TObject *Sender);
        void __fastcall HotKey1Enter(TObject *Sender);
        void __fastcall Button3Click(TObject *Sender);
        void __fastcall Timer2Timer(TObject *Sender);
        void __fastcall Timer3Timer(TObject *Sender);
//        void __fastcall FormShow(TObject *Sender);
//        void __fastcall FormResize(TObject *Sender);
//        void __fastcall Help1Click(TObject *Sender);
private:	// User declarations
public:		// User declarations
        __fastcall TForm1(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TForm1 *Form1;
//---------------------------------------------------------------------------
#endif
