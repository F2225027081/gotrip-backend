package com.ytzl.gotrip.rpc.service.impl;

import com.ytzl.gotrip.beans.Hotel;
import com.ytzl.gotrip.dao.BaseQuery;
import com.ytzl.gotrip.rpc.service.HotelSearchService;
import com.ytzl.gotrip.utils.common.EmptyUtils;
import com.ytzl.gotrip.utils.common.ErrorCode;
import com.ytzl.gotrip.utils.common.Page;
import com.ytzl.gotrip.utils.exception.GotripException;
import com.ytzl.gotrip.vo.hotel.SearchHotelVO;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service("hotelSearchService")
public class HotelSearchServiceImpl implements HotelSearchService {

    @Resource
    private BaseQuery<Hotel> hotleQuery;

    @Override
    public Page<Hotel> searchGotripHotelPage(SearchHotelVO searchHotelVO) throws GotripException, IOException, SolrServerException {

        if (EmptyUtils.isEmpty(searchHotelVO.getDestination())) {
            throw new GotripException("目的地不能为空！", ErrorCode.AUTH_PARAMETER_ERROR);

        }

        //创建查询条件
        SolrQuery solrQuery = new SolrQuery("*:*");
        //拼接Q查询条件
        StringBuffer query = new StringBuffer();
        //拼接目的地
        query.append(" destination:").append(searchHotelVO.getDestination());
        //如果关键字不为空，则拼接关键字
        if (EmptyUtils.isNotEmpty(searchHotelVO.getKeywords())) {
            query.append(" AND keyword:").append(searchHotelVO.getKeywords());
        }
        //将查询条件设置到q查询中
        solrQuery.setQuery(query.toString());


        //如果酒店级别不为空，匹配酒店级别

        if (EmptyUtils.isNotEmpty(searchHotelVO.getHotelLevel())) {
            solrQuery.addFilterQuery(" hotelLevel:"+searchHotelVO.getHotelLevel());
        }

        //特色
        if (EmptyUtils.isNotEmpty(searchHotelVO.getTradeAreaIds())) {
            String[] split = searchHotelVO.getTradeAreaIds().split(",");
            for (int i = 0; i < split.length; i++) {
                String areaId = split[i];
                if(i ==  0){
                    solrQuery.addFilterQuery(" tradingAreaIds:,"+areaId+",");
                }else{
                    solrQuery.addFilterQuery(" or tradingAreaIds:,"+areaId+",");
                }
            }
        }


        //商圈
        if (EmptyUtils.isNotEmpty(searchHotelVO.getFeatureIds())) {
            String[] split = searchHotelVO.getFeatureIds().split(",");
            for (int i = 0; i < split.length; i++) {
                String featureId = split[i];
                if(i ==  0){
                    solrQuery.addFilterQuery(" featureIds:,"+featureId+",");
                }else{
                    solrQuery.addFilterQuery(" or featureIds:,"+featureId+",");
                }
            }
        }



        //最高价钱
        if (EmptyUtils.isNotEmpty(searchHotelVO.getMaxPrice())) {
            //[0 to searchHotelVO.getMaxPrice()]
            solrQuery.addFilterQuery(" minPrice:[0 To "+searchHotelVO.getMaxPrice()+"]");
        }

        //最低价钱
        if (EmptyUtils.isNotEmpty(searchHotelVO.getMinPrice())) {
            //[0 to searchHotelVO.getMinPrice()]
            solrQuery.addFilterQuery(" maxPrice:[" + searchHotelVO.getMinPrice() + " To *]");
        }


        //排序
        if (EmptyUtils.isNotEmpty(searchHotelVO.getAscSort())) {
            solrQuery.setSort(searchHotelVO.getAscSort(), SolrQuery.ORDER.asc);
        }

        //降序
        if (EmptyUtils.isNotEmpty(searchHotelVO.getDescSort())) {
            solrQuery.setSort(searchHotelVO.getDescSort(), SolrQuery.ORDER.desc);
        }
        //调用查询工具类 返回page 直接return
        return hotleQuery.queryPage(solrQuery,
                searchHotelVO.getPageNo(),
                searchHotelVO.getPageSize(), Hotel.class);
    }
}
