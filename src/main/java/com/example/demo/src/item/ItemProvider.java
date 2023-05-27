package com.example.demo.src.item;

import com.example.demo.config.BaseException;
import com.example.demo.src.item.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@Repository
public class ItemProvider {
    private final ItemDao itemDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ItemProvider(ItemDao itemDao) {
        this.itemDao = itemDao;
    }
    //상품 조회
    public List<GetItemRes> getItem() throws BaseException {
        try{
            List<GetItemRes> getItemRes = itemDao.getItem();
            return getItemRes;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //상품 1개조회
    public List<GetItemRes> getOneItem(int itemIdx) throws BaseException{
        try{
            List<GetItemRes> getItemRes = itemDao.getOneItem(itemIdx);
            return getItemRes;
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //상품등록
    public List<PostItemRes> postItem(PostItemReq postItemReq) throws BaseException{
        try{
            List<PostItemRes> postBoardRes = itemDao.postItem(postItemReq);
            return postBoardRes;
        }
        catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //상품수정
    public GetItemRes patchItem(PatchItemReq patchItemReq) throws BaseException {
        try{
            GetItemRes patchItemRes = itemDao.patchItem(patchItemReq);
            return patchItemRes;
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //상품삭제
    public List<GetItemRes> deleteItem(int item_idx, int user_idx) throws BaseException{
        try{
            List<GetItemRes> deleteItemRes = itemDao.deleteItem(item_idx, user_idx);
            return deleteItemRes;
        }catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int modifyStatus(PatchStatus patchStatus) throws BaseException {
        try{
            int patchItemRes = itemDao.modifyStatus(patchStatus);
            return patchItemRes;
        }catch (Exception exception){
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
