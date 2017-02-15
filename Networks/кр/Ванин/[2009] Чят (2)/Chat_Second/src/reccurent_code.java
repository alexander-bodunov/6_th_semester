/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author 4ekist_5-45
 */
import java.util.*;
import java.lang.*;

public class reccurent_code
{
     public static Byte[] coding(Byte vector)
    {
        BitSet bits_vector = new BitSet(8);
        bits_vector = fromByte(vector);

        Byte[] out_arr_vector = new Byte[2];
        Byte first_byte = 0;
        Byte second_byte = 0;
        BitSet bits_first_vec = new BitSet(8);
        BitSet bits_sec_vec = new BitSet(8);
        for(int i = 0; i <4; i++)
        {
            if (bits_vector.get(i))
                bits_first_vec.set(i);

        }


         for(int i = 4; i < 8; i++)
        {
            if (bits_vector.get(i))
                bits_sec_vec.set(i-4);

        }

        BitToString("Первый битсет перед кодированием", bits_first_vec);
        BitToString("Второй битсет перед кодированием", bits_sec_vec);

        first_byte = toByte(bits_first_vec);
        second_byte = toByte(bits_sec_vec);


        first_byte = (byte)(first_byte << 3);
        second_byte = (byte)(second_byte << 3);
        Byte code_vector = 0;
        BitSet bits_current_vector = fromByte(first_byte);
        BitSet tmp_cur = new BitSet(8);

           for (int i = 0; i < 8; i++)
            {
                if (bits_current_vector.get(i))
                    tmp_cur.set(i);
            }
        BitSet bits_polynome = new BitSet(4);
        BitSet bits_Excess = new BitSet(3);
        /*-----------------------------------------------*/
        bits_polynome.set(3); bits_polynome.set(1); bits_polynome.set(0);
        /*------------------------------------------------*/
        BitToString("Первый битсет перед кодированием",bits_current_vector);

         do
            {
                DevideByTail(tmp_cur,bits_polynome);
            }
        while (CheckTail(tmp_cur));
            for (int i = 0; i < 3; i++)
            {
                if (tmp_cur.get(i))
                    bits_Excess.set(i);
            }
         for (int i = 0; i < 3; i++)
            {
                if (tmp_cur.get(i))
                    bits_current_vector.set(i);
            }
       BitToString("Первый битсет после кодирования",bits_current_vector);
       out_arr_vector[0] = toByte(bits_current_vector);


       /*---------------------------------------------------------------------*/
       //╨Æ╤é╨╛╤Ç╨╛╨╣ ╨┐╨╛╨╗╤â╨▒╨░╨╣╤é
         code_vector = 0;
         bits_current_vector = fromByte(second_byte);
         tmp_cur = new BitSet(8);

           for (int i = 0; i < 8; i++)
            {
                if (bits_current_vector.get(i))
                    tmp_cur.set(i);
            }
         bits_polynome = new BitSet(4);
         bits_Excess = new BitSet(3);
        /*-----------------------------------------------*/
        bits_polynome.set(3); bits_polynome.set(1); bits_polynome.set(0);
        /*------------------------------------------------*/
        BitToString("Второй битсет перед кодированием",bits_current_vector);

         do
            {
                DevideByTail(tmp_cur,bits_polynome);
            }
        while (CheckTail(tmp_cur));
            for (int i = 0; i < 3; i++)
            {
                if (tmp_cur.get(i))
                    bits_Excess.set(i);
            }
         for (int i = 0; i < 3; i++)
            {
                if (tmp_cur.get(i))
                    bits_current_vector.set(i);
            }
       BitToString("Второй битсет после кодирования",bits_current_vector);
       out_arr_vector[1] = toByte(bits_current_vector);

       return out_arr_vector;
    }

    public static Byte decoding(Byte vector)
    {
        BitSet  bits_rvector = fromByte(vector);
        BitToString("Битсет для раскодирования",bits_rvector);
        BitSet bits_temp = new BitSet(8);
        BitSet bits_polynome = new BitSet(4);
        Byte send_vector = 0;
        boolean isValid = true;
        /*-----------------------------------------------*/
        bits_polynome.set(3); bits_polynome.set(1); bits_polynome.set(0);


         for (int i = 0; i < 8; i++)
            {
                if (bits_rvector.get(i))
                    bits_temp.set(i);
            }

         do
            {
                DevideByTail(bits_temp,bits_polynome);
            }
        while (CheckTail(bits_temp));
            for (int i = 0; i < 3; i++)
            {
                if (bits_temp.get(i))
                    isValid = false;
            }

        if (isValid)
        {

            send_vector  = toByte(bits_rvector);
            send_vector = (byte)(send_vector >> 3);

        }

        return send_vector;

    }

     public static byte toByte(BitSet bits) {
		byte byt = 0;
		for (int i=0; i<bits.length(); i++) {
			if (bits.get(i)) {
				byt |= 1<<(i%8);
			}
		}
		return byt;
	}

      public static BitSet fromByte(byte byt) {
        BitSet bits = new BitSet(8);
        for (int i=0; i<8; i++) {
            if ((byt&(1<<(i%8))) > 0) {
                bits.set(i);
            }
        }
        return bits;
    }

      public static void BitToString(String text,BitSet bits)
      {
          System.out.println(text);
          for (int i = 7; i >=0; i--)
          {
              if (bits.get(i))
              {
                System.out.print(1);
              }
              else
              {
                 System.out.print(0);
              }
          }
          System.out.println();
      }


       public static boolean CheckTail(BitSet tmp)
        {
            boolean flag = false;
            for (int i = 6; i >= 3; i--)
            {
                if (tmp.get(i))
                {
                    flag = true;
                }
            }
            return flag;
        }
        public static void DevideByTail(BitSet tmp, BitSet pol)
        {
            int index = 6;
            while ((tmp.get(index) == false) & (index != 2))
            {
                index -= 1;
            }
            if (index > 2)
            {
                for (int i = index; i > index - 4; i--)
                {
                    if (tmp.get(i) ^ pol.get(3 - (index - i)))
                    {
                            tmp.set(i);
                    }
                    else
                    {
                        tmp.set(i,false);
                    }
                }
            }
        }
}
