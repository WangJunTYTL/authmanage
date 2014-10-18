package com.peaceful.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * An internal utility class.
 *
 * @author Wang Jun
 */
public class StringUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }


    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);

    public static final String ALL_INTERFACES = "0.0.0.0";
    public static final String CRLF = "\015\012";
    public static final String __LINE_SEPARATOR =
            System.getProperty("line.separator", "\n");

    public static final String __ISO_8859_1 = "ISO-8859-1";
    public final static String __UTF8 = "UTF-8";
    public final static String __UTF8Alt = "UTF8";
    public final static String __UTF16 = "UTF-16";

    public final static Charset __UTF8_CHARSET;
    public final static Charset __ISO_8859_1_CHARSET;

    static {
        __UTF8_CHARSET = Charset.forName(__UTF8);
        __ISO_8859_1_CHARSET = Charset.forName(__ISO_8859_1);
    }

    private static char[] lowercases = {
            '\000', '\001', '\002', '\003', '\004', '\005', '\006', '\007',
            '\010', '\011', '\012', '\013', '\014', '\015', '\016', '\017',
            '\020', '\021', '\022', '\023', '\024', '\025', '\026', '\027',
            '\030', '\031', '\032', '\033', '\034', '\035', '\036', '\037',
            '\040', '\041', '\042', '\043', '\044', '\045', '\046', '\047',
            '\050', '\051', '\052', '\053', '\054', '\055', '\056', '\057',
            '\060', '\061', '\062', '\063', '\064', '\065', '\066', '\067',
            '\070', '\071', '\072', '\073', '\074', '\075', '\076', '\077',
            '\100', '\141', '\142', '\143', '\144', '\145', '\146', '\147',
            '\150', '\151', '\152', '\153', '\154', '\155', '\156', '\157',
            '\160', '\161', '\162', '\163', '\164', '\165', '\166', '\167',
            '\170', '\171', '\172', '\133', '\134', '\135', '\136', '\137',
            '\140', '\141', '\142', '\143', '\144', '\145', '\146', '\147',
            '\150', '\151', '\152', '\153', '\154', '\155', '\156', '\157',
            '\160', '\161', '\162', '\163', '\164', '\165', '\166', '\167',
            '\170', '\171', '\172', '\173', '\174', '\175', '\176', '\177'};

    /* ------------------------------------------------------------ */

    /**
     * fast lower case conversion. Only works on ascii (not unicode)
     *
     * @param s the string to convert
     * @return a lower case version of s
     */
    public static String asciiToLowerCase(String s) {
        char[] c = null;
        int i = s.length();

        // look for first conversion
        while (i-- > 0) {
            char c1 = s.charAt(i);
            if (c1 <= 127) {
                char c2 = lowercases[c1];
                if (c1 != c2) {
                    c = s.toCharArray();
                    c[i] = c2;
                    break;
                }
            }
        }

        while (i-- > 0) {
            if (c[i] <= 127)
                c[i] = lowercases[c[i]];
        }

        return c == null ? s : new String(c);
    }


    /* ------------------------------------------------------------ */
    public static boolean startsWithIgnoreCase(String s, String w) {
        if (w == null)
            return true;

        if (s == null || s.length() < w.length())
            return false;

        for (int i = 0; i < w.length(); i++) {
            char c1 = s.charAt(i);
            char c2 = w.charAt(i);
            if (c1 != c2) {
                if (c1 <= 127)
                    c1 = lowercases[c1];
                if (c2 <= 127)
                    c2 = lowercases[c2];
                if (c1 != c2)
                    return false;
            }
        }
        return true;
    }

    /* ------------------------------------------------------------ */
    public static boolean endsWithIgnoreCase(String s, String w) {
        if (w == null)
            return true;

        if (s == null)
            return false;

        int sl = s.length();
        int wl = w.length();

        if (sl < wl)
            return false;

        for (int i = wl; i-- > 0; ) {
            char c1 = s.charAt(--sl);
            char c2 = w.charAt(i);
            if (c1 != c2) {
                if (c1 <= 127)
                    c1 = lowercases[c1];
                if (c2 <= 127)
                    c2 = lowercases[c2];
                if (c1 != c2)
                    return false;
            }
        }
        return true;
    }

    /* ------------------------------------------------------------ */

    /**
     * returns the next index of a character from the chars string
     */
    public static int indexFrom(String s, String chars) {
        for (int i = 0; i < s.length(); i++)
            if (chars.indexOf(s.charAt(i)) >= 0)
                return i;
        return -1;
    }

    /**
     * 判断字符是否相等
     * @param s1
     * @param s2
     * @return
     */
    public static boolean isEquals(String s1,String s2){
        if (s1 == null && s2 == null)
            return true;
        if (s1 == null || s2 == null)
            return false;
        if (s1.equals(s2))
            return true;
        return false;
    }

    public static boolean isNotEquals(String s1,String s2){

        return !isEquals(s1,s2);
    }


    public static boolean contains(String s1,String s2){
        if (s1 == null || s2 == null)
            return false;
        if (s1.indexOf(s2) == -1)
            return false;
        else
            return true;
    }

    /* ------------------------------------------------------------ */

    /**
     * replace substrings within string.
     */
    public static String replace(String s, String sub, String with) {
        int c = 0;
        int i = s.indexOf(sub, c);
        if (i == -1)
            return s;

        StringBuilder buf = new StringBuilder(s.length() + with.length());

        do {
            buf.append(s.substring(c, i));
            buf.append(with);
            c = i + sub.length();
        } while ((i = s.indexOf(sub, c)) != -1);

        if (c < s.length())
            buf.append(s.substring(c, s.length()));

        return buf.toString();

    }




    /* ------------------------------------------------------------ */

    /**
     * Append substring to StringBuilder
     *
     * @param buf    StringBuilder to append to
     * @param s      String to append from
     * @param offset The offset of the substring
     * @param length The length of the substring
     */
    public static void append(StringBuilder buf,
                              String s,
                              int offset,
                              int length) {
        synchronized (buf) {
            int end = offset + length;
            for (int i = offset; i < end; i++) {
                if (i >= s.length())
                    break;
                buf.append(s.charAt(i));
            }
        }
    }


    /* ------------------------------------------------------------ */

    /**
     * append hex digit
     */
    public static void append(StringBuilder buf, byte b, int base) {
        int bi = 0xff & b;
        int c = '0' + (bi / base) % base;
        if (c > '9')
            c = 'a' + (c - '0' - 10);
        buf.append((char) c);
        c = '0' + bi % base;
        if (c > '9')
            c = 'a' + (c - '0' - 10);
        buf.append((char) c);
    }

    /* ------------------------------------------------------------ */
    public static void append2digits(StringBuffer buf, int i) {
        if (i < 100) {
            buf.append((char) (i / 10 + '0'));
            buf.append((char) (i % 10 + '0'));
        }
    }

    /* ------------------------------------------------------------ */
    public static void append2digits(StringBuilder buf, int i) {
        if (i < 100) {
            buf.append((char) (i / 10 + '0'));
            buf.append((char) (i % 10 + '0'));
        }
    }

    /* ------------------------------------------------------------ */

    /**
     * Return a non null string.
     *
     * @param s String
     * @return The string passed in or empty string if it is null.
     */
    public static String nonNull(String s) {
        if (s == null)
            return "";
        return s;
    }

    /* ------------------------------------------------------------ */
    public static boolean equals(String s, char[] buf, int offset, int length) {
        if (s.length() != length)
            return false;
        for (int i = 0; i < length; i++)
            if (buf[offset + i] != s.charAt(i))
                return false;
        return true;
    }

    /* ------------------------------------------------------------ */
    public static String toUTF8String(byte[] b, int offset, int length) {
        try {
            return new String(b, offset, length, __UTF8);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /* ------------------------------------------------------------ */
    public static String toString(byte[] b, int offset, int length, String charset) {
        try {
            return new String(b, offset, length, charset);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException(e);
        }
    }


    /* ------------------------------------------------------------ */
    public static boolean isUTF8(String charset) {
        return __UTF8.equalsIgnoreCase(charset) || __UTF8Alt.equalsIgnoreCase(charset);
    }


    /* ------------------------------------------------------------ */
    public static String printable(String name) {
        if (name == null)
            return null;
        StringBuilder buf = new StringBuilder(name.length());
        for (int i = 0; i < name.length(); i++) {
            char c = name.charAt(i);
            if (!Character.isISOControl(c))
                buf.append(c);
        }
        return buf.toString();
    }


    public static byte[] getBytes(String s) {
        try {
            return s.getBytes(__ISO_8859_1);
        } catch (Exception e) {
            LOGGER.warn("getBytes:{}", e);
            return s.getBytes();
        }
    }

    public static byte[] getBytes(String s, String charset) {
        try {
            return s.getBytes(charset);
        } catch (Exception e) {
            LOGGER.warn("getBytes:{}", e);
            return s.getBytes();
        }
    }


    /**
     * Converts a binary SID to a string SID
     * <p/>
     * http://en.wikipedia.org/wiki/Security_Identifier
     * <p/>
     * S-1-IdentifierAuthority-SubAuthority1-SubAuthority2-...-SubAuthorityn
     */
    public static String sidBytesToString(byte[] sidBytes) {
        StringBuilder sidString = new StringBuilder();

        // Identify this as a SID
        sidString.append("S-");

        // Add SID revision level (expect 1 but may change someday)
        sidString.append(Byte.toString(sidBytes[0])).append('-');

        StringBuilder tmpBuilder = new StringBuilder();

        // crunch the six bytes of issuing authority value
        for (int i = 2; i <= 7; ++i) {
            tmpBuilder.append(Integer.toHexString(sidBytes[i] & 0xFF));
        }

        sidString.append(Long.parseLong(tmpBuilder.toString(), 16)); // '-' is in the subauth loop

        // the number of subAuthorities we need to attach
        int subAuthorityCount = sidBytes[1];

        // attach each of the subAuthorities
        for (int i = 0; i < subAuthorityCount; ++i) {
            int offset = i * 4;
            tmpBuilder.setLength(0);
            // these need to be zero padded hex and little endian
            tmpBuilder.append(String.format("%02X%02X%02X%02X",
                    (sidBytes[11 + offset] & 0xFF),
                    (sidBytes[10 + offset] & 0xFF),
                    (sidBytes[9 + offset] & 0xFF),
                    (sidBytes[8 + offset] & 0xFF)));
            sidString.append('-').append(Long.parseLong(tmpBuilder.toString(), 16));
        }

        return sidString.toString();
    }

    /**
     * Converts a string SID to a binary SID
     */
    public static byte[] sidStringToBytes(String sidString) {
        String[] sidTokens = sidString.split("-");

        int subAuthorityCount = sidTokens.length - 3; // S-Rev-IdAuth-

        int byteCount = 0;
        byte[] sidBytes = new byte[1 + 1 + 6 + (4 * subAuthorityCount)];

        // the revision byte
        sidBytes[byteCount++] = (byte) Integer.parseInt(sidTokens[1]);

        // the # of sub authorities byte
        sidBytes[byteCount++] = (byte) subAuthorityCount;

        // the certAuthority
        String hexStr = Long.toHexString(Long.parseLong(sidTokens[2]));

        while (hexStr.length() < 12) // pad to 12 characters
        {
            hexStr = "0" + hexStr;
        }

        // place the certAuthority 6 bytes
        for (int i = 0; i < hexStr.length(); i = i + 2) {
            sidBytes[byteCount++] = (byte) Integer.parseInt(hexStr.substring(i, i + 2), 16);
        }


        for (int i = 3; i < sidTokens.length; ++i) {
            hexStr = Long.toHexString(Long.parseLong(sidTokens[i]));

            while (hexStr.length() < 8) // pad to 8 characters
            {
                hexStr = "0" + hexStr;
            }

            // place the inverted sub authorities, 4 bytes each
            for (int j = hexStr.length(); j > 0; j = j - 2) {
                sidBytes[byteCount++] = (byte) Integer.parseInt(hexStr.substring(j - 2, j), 16);
            }
        }

        return sidBytes;
    }


    public static int toInteger(String str,int num){
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return num;
        }
    }



    /**
     * 转换成long
     * @param str
     * @param num 默认
     * @return
     */
    public static long toLong(String str,long num){
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return num;
        }
    }



    public static String getStringFromArray (Object objects[],String join) {
        if (objects == null) return null;
        StringBuffer sb = new StringBuffer();
        for (Object obj : objects) {
            sb.append(obj.toString()).append(join);
        }
        if (!isEmpty(join) && sb.length() > join.length())
            return sb.substring(0,sb.length() - join.length());
        return sb.toString();
    }

    public static Object[] getArrayByComma (String str) {
        if (isEmpty(str)) {
            return new Object[]{};
        }
        return str.split(",");
    }

    public static String replaceArrayByEnter (String str) {
        if (isEmpty(str)) {
            return null;
        }
        return str.replaceAll("\r\n","</br>");

    }
    //o7yAXNFZRkgdcrdWT79g/g==
    public static final String EncoderPwdByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // 确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        Base64Utils base64en = new Base64Utils();
        // 加密后的字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("US-ASCII")));
        return newstr;
    }
}