package com.example.demo.src.item;

import com.example.demo.src.item.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;

@Repository

public class ItemDao {
    // *********************** 동작에 있어 필요한 요소들을 불러옵니다. *************************

    private JdbcTemplate jdbcTemplate;

    @Autowired //readme 참고
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    // ******************************************************************************

    /**
     * DAO관련 함수코드의 전반부는 크게 String ~~~Query와 Object[] ~~~~Params, jdbcTemplate함수로 구성되어 있습니다.(보통은 동적 쿼리문이지만, 동적쿼리가 아닐 경우, Params부분은 없어도 됩니다.)
     * Query부분은 DB에 SQL요청을 할 쿼리문을 의미하는데, 대부분의 경우 동적 쿼리(실행할 때 값이 주입되어야 하는 쿼리) 형태입니다.
     * 그래서 Query의 동적 쿼리에 입력되어야 할 값들이 필요한데 그것이 Params부분입니다.
     * Params부분은 클라이언트의 요청에서 제공하는 정보(~~~~Req.java에 있는 정보)로 부터 getXXX를 통해 값을 가져옵니다. ex) getEmail -> email값을 가져옵니다.
     *      Notice! get과 get의 대상은 카멜케이스로 작성됩니다. ex) item -> getItem, password -> getPassword, email -> getEmail, userIdx -> getUserIdx
     * 그 다음 GET, POST, PATCH 메소드에 따라 jabcTemplate의 적절한 함수(queryForObject, query, update)를 실행시킵니다(DB요청이 일어납니다.).
     *      Notice!
     *      POST, PATCH의 경우 jdbcTemplate.update
     *      GET은 대상이 하나일 경우 jdbcTemplate.queryForObject, 대상이 복수일 경우, jdbcTemplate.query 함수를 사용합니다.
     * jdbcTeplate이 실행시킬 때 Query 부분과 Params 부분은 대응(값을 주입)시켜서 DB에 요청합니다.
     * <p>
     * 정리하자면 < 동적 쿼리문 설정(Query) -> 주입될 값 설정(Params) -> jdbcTemplate함수(Query, Params)를 통해 Query, Params를 대응시켜 DB에 요청 > 입니다.
     * <p>
     * <p>
     * DAO관련 함수코드의 후반부는 전반부 코드를 실행시킨 후 어떤 결과값을 반환(return)할 것인지를 결정합니다.
     * 어떠한 값을 반환할 것인지 정의한 후, return문에 전달하면 됩니다.
     * ex) return this.jdbcTemplate.query( ~~~~ ) -> ~~~~쿼리문을 통해 얻은 결과를 반환합니다.
     */

    /**
     * 참고 링크
     * https://jaehoney.tistory.com/34 -> JdbcTemplate 관련 함수에 대한 설명
     * https://velog.io/@seculoper235/RowMapper%EC%97%90-%EB%8C%80%ED%95%B4 -> RowMapper에 대한 설명
     */

    //상품조회
    public List<GetItemRes> getItem(){
        String getItemQuery = "SELECT itemIdx, U.userIdx, C.categoryIdx, itemName, price, itemDescription," +
                "       likeCount, viewed, chat, tradeType, Item.initDate, Item.editDate, Item.status" +
                "        FROM Item" +
                "        left join User U on U.userIdx = Item.userIdx" +
                "        left join Category C on C.categoryIdx = Item.categoryIdx";

        return this.jdbcTemplate.query(getItemQuery, (rs, rowNum) -> new GetItemRes(
                        rs.getInt("itemIdx"),
                        rs.getInt("userIdx"),
                        rs.getInt("categoryIdx"),
                        rs.getString("itemName"),
                        rs.getInt("price"),
                        rs.getString("itemDescription"),
                        rs.getInt("likeCount"),
                        rs.getInt("viewed"),
                        rs.getInt("chat"),
                        rs.getInt("tradeType"),
                        rs.getString("initDate"),
                        rs.getString("editDate"),
                        rs.getInt("status")
                ));
    }

    //상품 1개조회
    public List<GetItemRes> getOneItem(int itemIdx){
        String getItemQuery = "SELECT itemIdx, U.userIdx, C.categoryIdx, itemName, price, itemDescription," +
                "       likeCount, viewed, chat, tradeType, Item.initDate, Item.editDate, Item.status" +
                "       FROM Item " +
                "       left join User U on U.userIdx = Item.userIdx" +
                "       left join Category C on C.categoryIdx = Item.categoryIdx" +
                "       WHERE itemIdx = ?";

        return this.jdbcTemplate.query(getItemQuery, (rs, rowNum) -> new GetItemRes(
                rs.getInt("itemIdx"),
                rs.getInt("userIdx"),
                rs.getInt("categoryIdx"),
                rs.getString("itemName"),
                rs.getInt("price"),
                rs.getString("itemDescription"),
                rs.getInt("likeCount"),
                rs.getInt("viewed"),
                rs.getInt("chat"),
                rs.getInt("tradeType"),
                rs.getString("initDate"),
                rs.getString("editDate"),
                rs.getInt("status")
        ),itemIdx);
    }

    // 상품등록
    public List<PostItemRes> postItem(PostItemReq postItemReq) {
        String postItemQuery = "INSERT INTO Item (itemIdx, userIdx, categoryIdx, itemName, price, itemDescription, likeCount, viewed, chat, tradeType, initDate, editDate, status)" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,?);";
        Object[] postItemParam = new Object[]{postItemReq.getItemIdx(), postItemReq.getUserIdx(), postItemReq.getCategoryIdx(),postItemReq.getItemName(),
                postItemReq.getPrice(), postItemReq.getItemDescription(), postItemReq.getLikeCount(), postItemReq.getViewed(), postItemReq.getChat(), postItemReq.getTradeType(),
                postItemReq.getStatus()};

        this.jdbcTemplate.update(postItemQuery, postItemParam);

        String lastInsertIdQuery = "select * from Item";

        return this.jdbcTemplate.query(lastInsertIdQuery,(rs, rowNum) -> new PostItemRes(
                rs.getInt("itemIdx"),
                rs.getInt("userIdx"),
                rs.getInt("categoryIdx"),
                rs.getString("itemName"),
                rs.getInt("price"),
                rs.getString("itemDescription"),
                rs.getInt("likeCount"),
                rs.getInt("viewed"),
                rs.getInt("chat"),
                rs.getInt("tradeType"),
                rs.getString("initDate"),
                rs.getString("editDate"),
                rs.getInt("status")
        ));
    }
    // 상품수정
    public GetItemRes patchItem(PatchItemReq patchItemReq){
        String updateQuery = "UPDATE Item set itemName = ? ,price = ? ,itemDescription = ?  " +
                    ",tradeType = ?, editDate = CURRENT_TIMESTAMP, categoryIdx = ?  where itemIdx = ?" ;

        Object[] updateParams = new Object[]{
                patchItemReq.getItemName(), patchItemReq.getPrice(), patchItemReq.getItemDescription(),
                patchItemReq.getTradeType(),patchItemReq.getCategoryIdx(), patchItemReq.getItemIdx()
        };

        this.jdbcTemplate.update(updateQuery, updateParams);
        String updateResultQuery = "select * from Item where itemIdx = ?" ;

        Object[] resultParams = new Object[]{
                patchItemReq.getItemIdx()
        };

        return this.jdbcTemplate.queryForObject(updateResultQuery, (rs, rowNum) -> new GetItemRes(
                rs.getInt("itemIdx"),
                rs.getInt("userIdx"),
                rs.getInt("categoryIdx"),
                rs.getString("itemName"),
                rs.getInt("price"),
                rs.getString("itemDescription"),
                rs.getInt("likeCount"),
                rs.getInt("viewed"),
                rs.getInt("chat"),
                rs.getInt("tradeType"),
                rs.getString("initDate"),
                rs.getString("editDate"),
                rs.getInt("status")
        ),resultParams);
    }

    // 상품상태 변경
    public int modifyStatus(PatchStatus patchStatus) {
        String modifyStatusQuery = "update Item set status = ? where itemIdx = ? ";
        Object[] modifyStatusParams = new Object[]{patchStatus.getStatus(),patchStatus.getItemIdx()};

        return this.jdbcTemplate.update(modifyStatusQuery, modifyStatusParams);
    }

    //상품 삭제
    public List<GetItemRes> deleteItem(int item_idx){
        String deleteBoardQuery = "DELETE from Item where itemIdx = ?" ;

        this.jdbcTemplate.update(deleteBoardQuery, item_idx);
        return null;
    }

}
