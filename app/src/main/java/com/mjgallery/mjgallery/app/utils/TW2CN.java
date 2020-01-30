package com.mjgallery.mjgallery.app.utils;

import android.content.Context;
import android.content.res.Resources;

import com.mjgallery.mjgallery.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Description:工具类 -- 繁简转换
 * @author 鲁班
 */
public class TW2CN {

    static private Map<Character, Character> ts;//繁转简的集合，繁为键，简为值
    static private Map<Character, Character> st;//简转繁的集合，简为键，繁为值
    static private TW2CN instance;
    static private boolean isCN;

    /**
     * 传入繁体文字，如果当前app是简体就转成简体，
     * 如果APP是繁体，则不处理
     * @param str
     * @return
     */
    public String toLocalStringT2S(String str) {
        Locale locale = Locale.getDefault();
        isCN = locale.getCountry().equals("CN");
        if (isCN)
            return t2s(str);
        else
            return str;
    }

    /**
     * 传入简体文字，如果当前app是繁体就转成繁体，
     * 如果APP是简体，则不处理
     * @param str
     * @return
     */
    public String toLocalStringS2T(String str) {
        Locale locale = Locale.getDefault();
        isCN = locale.getCountry().equals("CN");
        if (isCN)
            return str;
        else
            return s2t(str);
    }


    /**
     * 初始化字典集合
     * @param context
     * @return
     */
    public static TW2CN getInstance(Context context) {
        if (instance == null) {
            try {
                instance = new TW2CN();
                ts = new HashMap<Character, Character>();
                st = new HashMap<Character, Character>();
                Resources resources = context.getResources();
                InputStream is = resources.openRawResource(R.raw.ts);
                StringBuffer sBuffer = new StringBuffer();
                InputStreamReader inputreader = new InputStreamReader(is);
                BufferedReader buffreader = new BufferedReader(inputreader);
                String line = null;
                while ((line = buffreader.readLine()) != null) {
                    if (line.isEmpty()) {
                        continue;
                    }
                    char[] chararry = line.toCharArray();
                    if (chararry.length != 2) {
                        continue;
                    }
                    if (chararry[0] != chararry[1]) {
                        ts.put(chararry[0], chararry[1]);
                        st.put(chararry[1], chararry[0]);
                    }
                }
                buffreader.close();
                inputreader.close();
                is.close();
            } catch (Exception e) {
                throw new IllegalStateException("Can not create new instance of JChineseConvertor : " + e.getMessage(), e);
            }
        }
        return instance;
    }

    /**
     * 繁转简
     * @param str
     * @return
     */
    public String t2s(String str) {
        char[] result = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            char tchar = str.charAt(i);
            Character schar = ts.get(tchar);
            if (schar != null) {
                result[i] = schar;
            } else {
                result[i] = tchar;
            }
        }
        return new String(result);
    }

    /**
     * 简转繁
     * @param str
     * @return
     */
    public String s2t(String str) {
        char[] result = new char[str.length()];
        for (int i = 0; i < str.length(); i++) {
            char tchar = str.charAt(i);
            Character schar = st.get(tchar);
            if (schar != null) {
                result[i] = schar;
            } else {
                result[i] = tchar;
            }
        }
        return new String(result);
    }

}
