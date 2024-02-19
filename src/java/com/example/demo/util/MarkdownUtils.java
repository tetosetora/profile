package com.example.demo.util;

import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.PolicyFactory;

// controllerで使用するパサークラス
public class MarkdownUtils {
    public static String markdownToHtmlAndSanitize(String markdown) {
    	
        // マークダウンをパース
        Parser parser = Parser.builder().build();
        org.commonmark.node.Node document = parser.parse(markdown);

        // HTMLにレンダリング
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        String html = renderer.render(document);

        // サニタイズポリシーを定義
        PolicyFactory policy = new HtmlPolicyBuilder()
                .allowElements(
                        "h1", "h2", "h3", "h4", "h5",
                        "code", "pre",
                        "ul", "ol", "li",
                        "p", "strong", "b", "em", "i",
                        "blockquote", "hr", "br"
                )
                .allowAttributes("class").onElements("code") // codeタグのclass属性を許可
                .toFactory();
        
        // サニタイズ実施
        String newHtml = policy.sanitize(html);
        
    	// データベース上の改行(￥n)をbrに変換する
    	String updatedHtml = newHtml.replaceAll("\n", "<br>");
    	
        return updatedHtml;
    }
}
