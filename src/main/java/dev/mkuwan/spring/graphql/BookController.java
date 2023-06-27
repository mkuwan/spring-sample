package dev.mkuwan.spring.graphql;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import javax.swing.plaf.PanelUI;

/**
 * GraphQl Controller
 * https://spring.pleiades.io/guides/gs/graphql-server/
 */
@Controller
public class BookController {
    /**
     * @QuerMapping でアノテーションが付けられた bookById という名前のメソッドを定義することにより、
     * このコントローラーは Query 型で定義されているように Book を取得する方法を宣言します。
     * クエリフィールドはメソッド名から決定されますが、アノテーション自体で宣言することもできます。
     * @param id
     * @return Book
     */
    @QueryMapping
    public Book bookById(@Argument String id){
        return Book.getById(id);
    }

    /**
     * @SchemaMapping アノテーションは、ハンドラー メソッドを GraphQL スキーマのフィールドにマップし、
     * それがそのフィールドの DataFetcher であることを宣言します。フィールド名はデフォルトでメソッド名になり、
     * 型名はデフォルトでメソッドに注入されたソース / 親オブジェクトの単純なクラス名になります。
     * この例では、フィールドはデフォルトで author に設定され、型はデフォルトで Book に設定されています。
     * @param book
     * @return Author
     */
    @SchemaMapping
    public Author author(Book book){
        return Author.getById(book.authorId());
    }
}
