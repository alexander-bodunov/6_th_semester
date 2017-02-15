//---------------------------------------------------------------------------

#ifndef Unit3H
#define Unit3H
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include <ExtCtrls.hpp>
//---------------------------------------------------------------------------
class TForm3 : public TForm
{
__published:	// IDE-managed Components
        TButton *Button1;
        TButton *Button2;
        TStaticText *StaticText1;
        TComboBox *ComboBox1;
        TStaticText *StaticText3;
        TComboBox *ComboBox3;
        TStaticText *StaticText2;
        TComboBox *ComboBox2;
        TStaticText *StaticText4;
        TComboBox *ComboBox4;
        void __fastcall Button1Click(TObject *Sender);
        void __fastcall FormCreate(TObject *Sender);

        void __fastcall Button2Click(TObject *Sender);
//        void __fastcall StaticText2Click(TObject *Sender);
//        void __fastcall ComboBox1Change(TObject *Sender);
//        void __fastcall ListBox1Click(TObject *Sender);
private:	// User declarations
public:		// User declarations
        __fastcall TForm3(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TForm3 *Form3;
//---------------------------------------------------------------------------
#endif
