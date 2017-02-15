object Form6: TForm6
  Left = 410
  Top = 244
  BorderIcons = []
  BorderStyle = bsSingle
  Caption = #1042#1099#1073#1086#1088' '#1087#1086#1088#1090#1072
  ClientHeight = 98
  ClientWidth = 240
  Color = clBtnFace
  Font.Charset = DEFAULT_CHARSET
  Font.Color = clWindowText
  Font.Height = -11
  Font.Name = 'MS Sans Serif'
  Font.Style = []
  OldCreateOrder = False
  PixelsPerInch = 96
  TextHeight = 13
  object Label1: TLabel
    Left = 8
    Top = 24
    Width = 113
    Height = 15
    Caption = #1048#1089#1087#1086#1083#1100#1079#1091#1077#1084#1099#1081' '#1087#1086#1088#1090
    Font.Charset = RUSSIAN_CHARSET
    Font.Color = clWindowText
    Font.Height = -13
    Font.Name = 'Times New Roman'
    Font.Style = []
    ParentFont = False
  end
  object Button1: TButton
    Left = 80
    Top = 64
    Width = 75
    Height = 25
    Caption = 'OK'
    TabOrder = 0
    OnClick = Button1Click
  end
  object ComboBox1: TComboBox
    Left = 144
    Top = 24
    Width = 89
    Height = 21
    ItemHeight = 13
    ItemIndex = 0
    TabOrder = 1
    Text = 'COM1'
    Items.Strings = (
      'COM1'
      'COM2')
  end
end
