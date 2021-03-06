package uk.co.jamware.rsource.connections.types.support;

public class Text {
	
    public static String decryptUsername(long l)
    {
        try
        {
            if(l <= 0L || l >= 0x5b5b57f8a98a5dd1L)
                return "invalid_name";
            if(l % 37L == 0L)
                return "invalid_name";
            int j = 0;
            char ac[] = new char[12];
            while(l != 0L) 
            {
                long l1 = l;
                l /= 37L;
                ac[11 - j++] = nameTable[(int)(l1 - l * 37L)];
            }
            return new String(ac, 12 - j, j);
        }
        catch(RuntimeException runtimeexception)
        {
            runtimeexception.printStackTrace();
        }
        throw new RuntimeException();
    }
    
    public static char nameTable[] = {
        '_', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 
        'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 
        't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', 
        '3', '4', '5', '6', '7', '8', '9'
    };
}
