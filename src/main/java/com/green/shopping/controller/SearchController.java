package com.green.shopping.controller;

import com.green.shopping.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController

public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("searchview")
    public List<HashMap<String, Object>> searchview(@RequestParam HashMap<String, Object> map) {
        HashMap<String, Object> SearchPut = null;
        List<HashMap<String, Object>> SearchValue = new ArrayList<>();
        String searchcont;
        if (map.get("searchcont").toString().indexOf(" ") == -1) {
            searchcont = String.valueOf(map.get("searchcont").toString().replace(" ",""));
        } else {
            searchcont = String.valueOf(map.get("searchcont").toString());
        }

        if( map.get("name").equals("전체")){
            List<HashMap<String, Object>> Allcategorysearch = searchService.Allcategorysearch(searchcont);
            System.out.println(Allcategorysearch);
            for(int i=0; i< Allcategorysearch.size(); i++){
                HashMap<String, Object> c = searchService.searchview1(Allcategorysearch.get(i));
                HashMap<String, Object> f = searchService.searchview2(Allcategorysearch.get(i));
                SearchPut = new HashMap<>();
                HashMap<String,Object> productImage = searchService.getProductImgByProductId(Allcategorysearch.get(i).get("ID").toString());
                 if(productImage != null){
                     HashMap<String,Object> fileMap = searchService.getFile(productImage.get("FILE_NAME").toString());
                     SearchPut.put("FILE_NAME",fileMap.get("NAME") + "." + fileMap.get("FILE_TYPE"));
                 }

                SearchPut.put("product_price", c.get("PRODUCT_PRICE"));
                SearchPut.put("product_discount", c.get("PRODUCT_DISCOUNT"));
                SearchPut.put("star", f.get("STAR"));
                SearchPut.put("title", Allcategorysearch.get(i).get("TITLE"));
                SearchPut.put("id", Allcategorysearch.get(i).get("ID"));
                SearchPut.put("mainimage", Allcategorysearch.get(i).get("MAINIMAGE"));
                SearchPut.put("starcount", f.get("STARCOUNT"));

                SearchValue.add(SearchPut);


            }

        } else {
            List<HashMap<String, Object>> Categorysearch = new ArrayList<>();
            HashMap<String, Object> mapbasket = new HashMap<>();
            if(map.get("category").toString().length() != 4){
                mapbasket.put("categorynum", map.get("category").toString());
                mapbasket.put("searchcont", searchcont);
                Categorysearch = searchService.Categorysearch(mapbasket);
            } else{
                String categorynum = searchService.categorynum(map.get("name").toString());
                mapbasket.put("categorynum", categorynum);
                mapbasket.put("searchcont", searchcont);
               Categorysearch = searchService.Categorysearch(mapbasket);
            }
            for (int i = 0; i < Categorysearch.size(); i++) {
                SearchPut = new HashMap<>();
                HashMap<String, Object> c = searchService.searchview1(Categorysearch.get(i));
                HashMap<String, Object> f = searchService.searchview2(Categorysearch.get(i));
                HashMap<String,Object> productImage = searchService.getProductImgByProductId(Categorysearch.get(i).get("ID").toString());
                if(productImage != null){
                    HashMap<String,Object> fileMap = searchService.getFile(productImage.get("FILE_NAME").toString());
                    SearchPut.put("FILE_NAME",fileMap.get("NAME") + "." + fileMap.get("FILE_TYPE"));
                }

                SearchPut.put("product_price", c.get("PRODUCT_PRICE"));
                SearchPut.put("product_discount", c.get("PRODUCT_DISCOUNT"));
                SearchPut.put("star", f.get("STAR"));
                SearchPut.put("title", Categorysearch.get(i).get("TITLE"));
                SearchPut.put("id", Categorysearch.get(i).get("ID"));
                SearchPut.put("mainimage", Categorysearch.get(i).get("MAINIMAGE"));
                SearchPut.put("starcount", f.get("STARCOUNT"));

                SearchValue.add(SearchPut);

            }
        }

    return SearchValue;





        }
}

