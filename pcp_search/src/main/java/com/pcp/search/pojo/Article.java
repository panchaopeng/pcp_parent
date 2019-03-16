package com.pcp.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * @depict: ElasticSearch的文档
 * @author: PCP
 * @create: 2019-02-27 16:58
 */
//indexName 索引库，type 索引库中的类型或者说表
@Document(indexName = "pcp_article",type = "article")
public class Article {
    @Id
    private String id;
    /**
     * index代表的含义
     * 1.是否索引，即该字段是否能被搜索
     * 2.是否分词，即搜索时是整体匹配还是单词匹配
     * 3.是否存储，即是否在页面上显示(能在页面上显示当然被存储了)
     */
    //两种分词类型:ik_max_word ik_smart
    //analyzer 分词类型，searchAnalyzer 搜索时按分词类型来搜索。二者必须一致
    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String title;

    @Field(index = true,analyzer = "ik_max_word",searchAnalyzer = "ik_max_word")
    private String content;

    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
