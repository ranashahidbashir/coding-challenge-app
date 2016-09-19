package com.name.list.util;


import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Rana Shahid Bashir on 9/18/2016.
 */
public class StringsUtil {

    public static ArrayList<String> nameList;


    public static ArrayList<String> getNameList() {

        if (nameList == null) {
            nameList = new ArrayList<String>();
            nameList.add("Simon, Ryan");
            nameList.add("Sophia,William");
            nameList.add("Elizabeth,Aiden");
            nameList.add("Natalie,Matthew");
            nameList.add("Anthony,Joshua");
            nameList.add("Emily,Alexander");
            nameList.add("David,Christopher");
        }
        return nameList;


    }

    public static String toTitleCase(String in) {
        if (in == null || in.isEmpty())
            return in;
        StringBuilder sb = new StringBuilder(in.length());
        String[] words = in.split("\\s+");
        for (String w : words) {
            if (sb.length() > 0)
                sb.append(" ");
            if (w.length() > 0) {
                sb.append(w.substring(0, 1).toUpperCase(Locale.getDefault()));
                sb.append(w.substring(1, w.length()));
            } else {
                sb.append(w);
            }
        }
        String txMin = sb.toString();
        StringBuilder sbMinus = new StringBuilder(txMin.length());
        String[] sMinus = txMin.toString().split("\\,");
        for (String w : sMinus) {
            if (sbMinus.length() > 0)
                sbMinus.append(",");
            if (w.length() > 0) {
                sbMinus.append(w.substring(0, 1).toUpperCase(Locale.getDefault()));
                sbMinus.append(w.substring(1, w.length()));
            } else {
                sbMinus.append(w);
            }
        }

        String txDot = sbMinus.toString();
        StringBuilder sbDot = new StringBuilder(txDot.length());
        String[] sDot = txDot.toString().split("\\.");
        for (String w : sDot) {
            if (sbDot.length() > 0)
                sbDot.append(".");

            if (w.length() > 0) {
                sbDot.append(w.substring(0, 1).toUpperCase(Locale.getDefault()));
                sbDot.append(w.substring(1, w.length()));
            } else {
                sbDot.append(w);
            }
        }
        String txInvComma = sbDot.toString();
        StringBuilder sbInv = new StringBuilder(txInvComma.length());
        String[] sInv = txInvComma.toString().split("\\'");


        for (String w : sInv) {
            if (sbInv.length() > 0)
                sbInv.append("'");

            if (w.length() > 0) {
                sbInv.append(w.substring(0, 1).toUpperCase(Locale.getDefault()));
                sbInv.append(w.substring(1, w.length()));
            } else {
                sbInv.append(w);
            }

        }

        String res = sbInv.toString();
        if (txInvComma.startsWith("'"))
            res = "'" + res;

        return res;


    }
}

