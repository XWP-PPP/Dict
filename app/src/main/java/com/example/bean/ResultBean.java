package com.example.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultBean {
    @Override
    public String toString() {
        return  "搜索词='" + query + '\'' +
                "\n\n " + "基本解释:  " + basic +
                "\n\n " + "翻译=" + translation +
                "\n\n " + "网络解释=" + web;
    }

    /**
     * translation : ["词"]
     * basic : {"us-phonetic":"wɜːrd","phonetic":"wɜːd","uk-phonetic":"wɜːd","explains":["n. [语] 单词；话语；消息；诺言；命令","vt. 用言辞表达","n. (Word)人名；(英)沃德"]}
     * query : word
     * errorCode : 0
     * web : [{"value":["字","单词","及答案"],"key":"Word"},{"value":["混成词","紧缩词","行囊词"],"key":"portmanteau word"},{"value":["构词法","词性转换","单词构成"],"key":"Word Formation"}]
     */

    private BasicBean basic;
    private String query;
    private int errorCode;
    private List<String> translation;
    private List<WebBean> web;

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public List<String> getTranslation() {
        return translation;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public List<WebBean> getWeb() {
        return web;
    }

    public void setWeb(List<WebBean> web) {
        this.web = web;
    }

    public static class BasicBean {
        @Override
        public String toString() {
            return  "解释=" + explains +
                    '}';
        }

        /**
         * us-phonetic : wɜːrd
         * phonetic : wɜːd
         * uk-phonetic : wɜːd
         * explains : ["n. [语] 单词；话语；消息；诺言；命令","vt. 用言辞表达","n. (Word)人名；(英)沃德"]
         */

        @SerializedName("us-phonetic")
        private String usphonetic;
        private String phonetic;
        @SerializedName("uk-phonetic")
        private String ukphonetic;
        private List<String> explains;

        public String getUsphonetic() {
            return usphonetic;
        }

        public void setUsphonetic(String usphonetic) {
            this.usphonetic = usphonetic;
        }

        public String getPhonetic() {
            return phonetic;
        }

        public void setPhonetic(String phonetic) {
            this.phonetic = phonetic;
        }

        public String getUkphonetic() {
            return ukphonetic;
        }

        public void setUkphonetic(String ukphonetic) {
            this.ukphonetic = ukphonetic;
        }

        public List<String> getExplains() {
            return explains;
        }

        public void setExplains(List<String> explains) {
            this.explains = explains;
        }
    }

    public static class WebBean {
        @Override
        public String toString() {
            return "\n" +
                    "组词='" + key + '\'' +
                    ", 意义=" + value +
                    '}';
        }

        /**
         * value : ["字","单词","及答案"]
         * key : Word
         */

        private String key;
        private List<String> value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public List<String> getValue() {
            return value;
        }

        public void setValue(List<String> value) {
            this.value = value;
        }
    }
}
