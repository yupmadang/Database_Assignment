package com.example.demo.src.item;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.item.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import static org.springframework.transaction.annotation.Isolation.READ_COMMITTED;

@RestController
@RequestMapping("")
public class ItemController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ItemProvider itemProvider;

    public ItemController(ItemProvider itemProvider) {
        this.itemProvider = itemProvider;
    }

    /**
     * [GET] /item/total
     *  상품조회 API
     */
    //Query String
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @GetMapping("/item/total") // (GET) 127.0.0.1:9000/item/total
    public BaseResponse<List<GetItemRes>> getItem() {
        try{
            List<GetItemRes> getBoardRes = itemProvider.getItem();
            return new BaseResponse<>(getBoardRes);
        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * [GET] /item/total
     *  상품조회 API
     */
    //Query String
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @GetMapping("/item/{item_idx}") // (GET) 127.0.0.1:9000/item/total
    public BaseResponse<List<GetItemRes>> getItemOne(@PathVariable("item_idx") int item_idx) {
        try{
            List<GetItemRes> getBoardRes = itemProvider.getOneItem(item_idx);
            return new BaseResponse<>(getBoardRes);
        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 상품등록
     * [POST] /item/add
     * @return BaseResponse<PostItemRes>
     */
    // Body
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @PostMapping("/item/add/{user_idx}")
    public BaseResponse<List<PostItemRes>> postItem(@PathVariable("user_idx")int userIdx,
                                                     @RequestBody PostItemReq postItemReq) {
        try {
              if (userIdx == 0) {
                return new BaseResponse<>(BaseResponseStatus.USERS_EMPTY_USER_IDX);
            } else if (postItemReq.getItemName() == null || postItemReq.getItemName() == "") {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_ITEM_NAME);
            } else if (postItemReq.getItemName().length() > 200){
                return new BaseResponse<>(BaseResponseStatus.POST_ITEM_TITLE_INVALID);
            } else if (postItemReq.getItemDescription().length() > 1000) {
                return new BaseResponse<>(BaseResponseStatus.POST_ITEM_CONTENT_INVALID);
            }
            List<PostItemRes> postBoardRes = itemProvider.postItem(postItemReq);
            return new BaseResponse<>(postBoardRes);

        } catch (BaseException exception) {
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //게시글 수정
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @PatchMapping("/item/modify/{item_idx}")
    public BaseResponse<String> patchItem(@PathVariable("item_idx") int item_idx,
                                           @RequestBody PatchItemReq patchItemReq) {
        try{
            if(item_idx != patchItemReq.getItemIdx()){
                return new BaseResponse<>(BaseResponseStatus.INVALID_ITEM_IDX);
            } else if(item_idx== 0){
                return new BaseResponse<>(BaseResponseStatus.EMPTY_ITEM_IDX);
            } else if (patchItemReq.getItemName() == "") {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_ITEM_NAME);
            } else if (patchItemReq.getCategoryIdx() == 0) {
                return new BaseResponse<>(BaseResponseStatus.EMPTY_CATEGORY_IDX);
            } else if (patchItemReq.getItemName().length() > 200){
                return new BaseResponse<>(BaseResponseStatus.POST_ITEM_TITLE_INVALID);
            } else if (patchItemReq.getItemDescription().length() > 1000) {
                return new BaseResponse<>(BaseResponseStatus.POST_ITEM_CONTENT_INVALID);
            }
            itemProvider.patchItem(patchItemReq);
            String result = "상품정보가 수정되었습니다";
            return new BaseResponse<>(result);

        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    //게시물 삭제
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @DeleteMapping("/item/delete/{item_idx}")
    public BaseResponse<String> deleteItem(@PathVariable("item_idx") int item_idx){
        try{
            if(item_idx == 0){
                return new BaseResponse<>(BaseResponseStatus.EMPTY_ITEM_IDX);
            }
            itemProvider.deleteItem(item_idx);



            String result = "삭제에 성공하였습니다.";
            return new BaseResponse<>(result);

        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //게시글 상태수정
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED, isolation = READ_COMMITTED , rollbackFor = Exception.class)
    @PatchMapping("/item/modify/status/{item_idx}")
    public BaseResponse<String> patchStatus(@PathVariable("item_idx") int item_idx,
                                          @RequestBody PatchStatus patchStatus) {
        try{
            if(item_idx != patchStatus.getItemIdx()){
                return new BaseResponse<>(BaseResponseStatus.INVALID_ITEM_IDX);
            } else if(item_idx== 0 || patchStatus.getItemIdx() == 0){
                return new BaseResponse<>(BaseResponseStatus.EMPTY_ITEM_IDX);
            } else if (patchStatus.getStatus() != 0 && patchStatus.getStatus() != 1){
                return new BaseResponse<>(BaseResponseStatus.INVALID_STATUS_TYPE);
            }
            itemProvider.modifyStatus(patchStatus);
            String result = "판매상태정보가 수정되었습니다";
            return new BaseResponse<>(result);

        } catch(BaseException exception){
            System.out.println(exception);
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}

