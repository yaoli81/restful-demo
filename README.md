# RESTful Demo

這是一個簡單的 RESTful API 範例，提供簡單的「書籍管理」API，包含新增/更新/刪除/查詢書籍資料。

## 使用技術

| 類別               | 技術                 |
| ----------------- |:-------------------- |
| 後端               | Java (Spring Boot)   |
| 資料庫             | H2 Database (使用 JPA) |

(P.S. 使用的是記憶體資料庫，所以資料不會永久保存。)

## Demo 網址

此 RESTful Demo 有架設到 Google 雲端平台 (GCP) - Google App Engine 上，可直接進入使用。

(P.S. 剛開始進入可能需要等待 10 餘秒雲端 Tomcat 的啟動)

-> [RESTful Demo 網址](https://restful-demo-dot-gcp-various-demo.uc.r.appspot.com)

此外，本範例有使用 Swagger，可到 Swagger 查看 API 資訊。

-> [Swagger 網址](https://restful-demo-dot-gcp-various-demo.uc.r.appspot.com/swagger-ui/index.html)

![](https://i.imgur.com/dhR2GPW.png)

由於架到雲端 Server 上，透過 Swagger 直接執行 API (Try it out) 可能會遇到 CORS 的問題。

![](https://i.imgur.com/24ASa2F.png)

若遇到此問題，請透過別的方式呼叫此 API 即可。

## API 說明與範例

###	API 返回的 JSON 格式說明

- 返回格式
```json=
{
    "success": 執行是否成功 (Y/N)，Y 表示成功、N 表示失敗 
    "message": 若執行失敗，會回傳失敗原因
    "data": 若執行成功，會回傳相關資訊
}
```

###	新增一本書籍

- 請求方法：POST
- API 路徑：/api/books
- Http Request Body 參數說明：

| 名稱               | 類型               | 必填              | 說明               |
| ----------------- | ----------------- | ----------------- | ----------------- |
| name              | String            | 是                | 書名 |
| author            | String            | 是                | 作者 |
| translator        | String            | 否                | 譯者 |
| isbn              | String            | 是                | ISBN，格式 ISBN 10 或 ISBN 13 都可，可帶分隔符號 (-) 或純數字。<br>( e.g. 978-986-7794-52-9、986-7794-52-4、9867794524) |
| publisher         | String            | 是                | 出版商 |
| publication_date  | String            | 是                | 出版日期，日期格式為 YYYY-MM-DD。<br>(e.g. 2022-05-01) |
| price             | String            | 是                | 定價，支援到小數後二位。 |

- Http Request Body 參數範例：
```json=
{
    "isbn" : "978-986-7794-52-9",
    "name" : "Head First Design Patterns: 深入淺出設計模式",
    "author" : "Eric Freeman, Elisabeth Robson",
    "translator" : "蔡學鏞",
    "publisher" : "美商歐萊禮股份有限公司台灣分公司",
    "publication_date" : "2005-09-21",
    "price" : "880"
}
```

- 返回範例 - 成功
```json=
{
    "success": "Y",
    "data": {
        "name": "Head First Design Patterns: 深入淺出設計模式",
        "author": "Eric Freeman, Elisabeth Robson",
        "translator": "蔡學鏞",
        "publisher": "美商歐萊禮股份有限公司台灣分公司",
        "price": "880",
        "isbn": "9789867794529",
        "publication_date": "2005-09-21"
    }
}
```

- 返回範例 - 失敗
```json=
{
    "success": "N",
    "message": "新增失敗，此筆書籍資料已存在 (ISBN 重複)。"
}
```
```json=
{
    "success": "N",
    "message": "無效的 ISBN。"
}
```

###	更新一本書籍資料

- 請求方法：PUT
- API 路徑：/api/books
- Http Request Body 參數說明：

| 名稱               | 類型               | 必填              | 說明               |
| ----------------- | ----------------- | ----------------- | ----------------- |
| name              | String            | 是                | 書名 |
| author            | String            | 是                | 作者 |
| translator        | String            | 否                | 譯者 |
| isbn              | String            | 是                | ISBN，格式 ISBN 10 或 ISBN 13 都可，可帶分隔符號 (-) 或純數字。<br>( e.g. 978-986-7794-52-9、986-7794-52-4、9867794524) |
| publisher         | String            | 是                | 出版商 |
| publication_date  | String            | 是                | 出版日期，日期格式為 YYYY-MM-DD。<br>(e.g. 2022-05-01) |
| price             | String            | 是                | 定價，支援到小數後二位。 |

- Http Request Body 參數範例：
```json=
{
    "isbn" : "978-986-7794-52-9",
    "name" : "Head First Design Patterns: 深入淺出設計模式",
    "author" : "Eric Freeman, Elisabeth Robson",
    "translator" : "蔡學鏞",
    "publisher" : "歐萊禮",
    "publication_date" : "2005-09-21",
    "price" : "792"
}
```

- 返回範例 - 成功
```json=
{
    "success": "Y",
    "data": {
        "name": "Head First Design Patterns: 深入淺出設計模式",
        "author": "Eric Freeman, Elisabeth Robson",
        "translator": "蔡學鏞",
        "publisher": "歐萊禮",
        "price": "792",
        "isbn": "9789867794529",
        "publication_date": "2005-09-21"
    }
}
```

- 返回範例 - 失敗
```json=
{
    "success": "N",
    "message": "更新失敗，查無此筆資料 (查無此筆 ISBN)。"
}
```
```json=
{
    "success": "N",
    "message": "無效的 ISBN。"
}
```

###	刪除一本書籍

- 請求方法：DELETE
- API 路徑：/api/books/{isbn}
- Http Request 參數說明：

| 名稱               | 類型               | 必填              | 說明               |
| ----------------- | ----------------- | ----------------- | ----------------- |
| isbn              | String            | 是                | ISBN，格式 ISBN 10 或 ISBN 13 都可，可帶分隔符號 (-) 或純數字。<br>( e.g. 978-986-7794-52-9、986-7794-52-4、9867794524) |

- Http Request 範例：
```
/api/books/978-986-7794-52-9
```

- 返回範例 - 成功
```json=
{
    "success": "Y",
    "data": {
        "name": "Head First Design Patterns: 深入淺出設計模式",
        "author": "Eric Freeman, Elisabeth Robson",
        "translator": "蔡學鏞",
        "publisher": "歐萊禮",
        "price": "792.00",
        "isbn": "9789867794529",
        "publication_date": "2005-09-21"
    }
}
```

- 返回範例 - 失敗
```json=
{
    "success": "N",
    "message": "刪除失敗，查無此筆書籍資料 (查無此筆 ISBN)。"
}
```
```json=
{
    "success": "N",
    "message": "無效的 ISBN。"
}
```

###	列出所有書籍

- 請求方法：GET
- API 路徑：/api/books/
- 返回範例 - 成功 (有資料)

```json=
{
    "success": "Y",
    "data": [
        {
            "name": "Head First Design Patterns: 深入淺出設計模式",
            "author": "Eric Freeman, Elisabeth Robson",
            "translator": "蔡學鏞",
            "publisher": "歐萊禮股份有限公司台灣分公司",
            "price": "880.00",
            "isbn": "9789867794529",
            "publication_date": "2005-09-21"
        },
        {
            "name": "富爸爸，窮爸爸（20週年紀念版）",
            "author": "羅勃特‧T‧清崎",
            "translator": "MTS 翻譯團隊",
            "publisher": "高寶",
            "price": "380.00",
            "isbn": "9789863615460",
            "publication_date": "2018-07-04"
        }
    ]
}
```
- 返回範例 - 成功 (無資料)
```json=
{
    "success": "Y",
    "data": []
}
```
