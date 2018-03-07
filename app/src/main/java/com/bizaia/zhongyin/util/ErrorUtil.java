package com.bizaia.zhongyin.util;

import android.util.SparseArray;

/**
 * Created by Administrator
 * Created on 2017/7/17 11:12
 */

public class ErrorUtil {

    private static final String DEFAULT_MSG = "サーバエラーです。しばらくお待ちいただき、再度お試しください";

    private static SparseArray<String> array;

    public static String getMsg(String errorCodeStr) {
        try {
            int errorCode = Integer.valueOf(errorCodeStr);
            return getMsg(errorCode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DEFAULT_MSG;
    }

    public static String getMsg(int errorCode) {
        init();
        return array.get(errorCode, DEFAULT_MSG);
    }

    private static void init() {
        array = new SparseArray<>();
        array.put(456, "携帯電話番号の誤り");
        array.put(457, "携帯電話番号の誤り");
        array.put(466, "検証コードエラー");
        array.put(468, "検証コードエラー");
        array.put(603, "携帯電話番号の誤り");
        array.put(3003, "ユーザーは既に登録されました");
        array.put(3007, "アカウントを新規する権限がありません");
        array.put(3015, "改めてログインしてください");
        array.put(3016, "ログインIDが存在しません");
        array.put(3018, "ユーザー名あるいはパースワードが間違います");
        array.put(8002, "ユーザーアカウントが禁止されました");
        array.put(8003, "認証番号が間違います");
        array.put(8007, "アカウントが空欄です");
        array.put(8008, "パスワードが空欄です");
        array.put(8017, "残高不足");
        array.put(8018, "アカウントが既に存在します");
        array.put(8023, "ニックネームが空欄です");
        array.put(9001, "アカウントが空欄です");
        array.put(9002, "パスワードが空欄です");
        array.put(9004, "新パスワードが空欄です");
        array.put(9005, "旧パスワードが空欄です");
        array.put(9006, "データレートが空欄です");
        array.put(9007, "データレートが空欄です");
        array.put(9008, "タイトルが空欄です");
        array.put(9009, "画像が空欄です");
        array.put(9010, "カテゴリが空欄です");
        array.put(9011, "地域が空欄です");
        array.put(9012, "開催日時が空欄です");
        array.put(9013, "申込締切が空欄です");
        array.put(9014, "会場が空欄です");
        array.put(9015, "定員が空欄です");
        array.put(9016, "参加費が空欄です");
        array.put(9017, "講師が空欄です");
        array.put(9018, "概要が空欄です");
        array.put(9019, "講師名が空欄です");
        array.put(9020, "社名が空欄です");
        array.put(9021, "役職が空欄です");
        array.put(9022, "画像が空欄です");
        array.put(9023, "視聴料が空欄です");
        array.put(9024, "講師IDが空欄です");
        array.put(9025, "テキスト資料が空欄です");
        array.put(9026, "テキスト画像が空欄です");
        array.put(9027, "携帯番号が空欄です");
        array.put(9028, "国番号が空欄です");
        array.put(9029, "メールアドレスが空欄です");
        array.put(9030, "名前が空欄です");
        array.put(9031, "カタカナが空欄です");
        array.put(9032, "生年月日が空欄です");
        array.put(9033, "性別が空欄です");
        array.put(9034, "職業が空欄です");
        array.put(9035, "業界が空欄です");
        array.put(9036, "役職が空欄です");
        array.put(9037, "国/地域が空欄です");
    }
}
