package com.mjgallery.mjgallery.app.utils;

import android.content.Context;

import com.mjgallery.mjgallery.R;
import com.mjgallery.mjgallery.app.BaseApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 简繁体转换
 */
public class JChineseConvertorUtil {

    private static JChineseConvertorUtil convertor;
    private Map<Character, Character> st = new HashMap();
    private Map<Character, Character> ts = new HashMap();
    Context context;

    public static JChineseConvertorUtil getInstance() throws IOException {
        if (convertor == null) {
            convertor = new JChineseConvertorUtil();
        }
        return convertor;
    }

    /**
     * 繁体转成简体
     * @param s
     * @return
     */
    public String t2s(String s) {
        char[] cs = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            cs[i] = t2s(s.charAt(i)).charValue();
        }
        return new String(cs);
    }

    /**
     * 简体转成繁体
     * @param s
     * @return
     */
    public String s2t(String s) {

        char[] cs = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            cs[i] = s2t(s.charAt(i)).charValue();
        }
        return new String(cs);
    }

    public Character t2s(char c) {
        if (this.ts.get(Character.valueOf(c)) == null) {
            return Character.valueOf(c);
        }
        return (Character) this.ts.get(Character.valueOf(c));
    }


    public Character s2t(char c) {
        if (this.st.get(Character.valueOf(c)) == null) {
            return Character.valueOf(c);
        }
        return (Character) this.st.get(Character.valueOf(c));
    }

    private List<Character> loadTable() throws IOException {
        List<Character> cs = loadChar("/cfg/ts.tab", "UTF-8");
        if (cs.size() % 2 == 0) {
            return cs;
        }
        throw new RuntimeException("The conversion table may be damaged or not exists");
    }

    private JChineseConvertorUtil() throws IOException {
        List<Character> cs = loadTable();
        for (int i = 0; i < cs.size(); i += 2) {
            this.ts.put((Character) cs.get(i), (Character) cs.get(i + 1));
            this.st.put((Character) cs.get(i + 1), (Character) cs.get(i));
        }
    }

    private List<Character> loadChar(String file, String charset) throws IOException {
        List<Character> content = new ArrayList<>();
        //BufferedReader in = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(file), charset));
        InputStream in=(BaseApplication.getInstance()).getResources().openRawResource(R.raw.ts);
        while (true) {
            int c = in.read();
            if (c == -1) {
                in.close();
                return content;
            }
            content.add(Character.valueOf((char) c));
        }
    }

}
