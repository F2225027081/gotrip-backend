package com.ytzl.gotrip.mapper;
import com.ytzl.gotrip.model.GotripUserLinkUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface GotripUserLinkUserMapper {

	public GotripUserLinkUser getGotripUserLinkUserById(@Param(value = "id") Long id)throws Exception;

	public List<GotripUserLinkUser>	getGotripUserLinkUserListByMap(Map<String, Object> param)throws Exception;

	public Integer getGotripUserLinkUserCountByMap(Map<String, Object> param)throws Exception;

	public Integer insertGotripUserLinkUser(GotripUserLinkUser gotripUserLinkUser)throws Exception;

	public Integer updateGotripUserLinkUser(GotripUserLinkUser gotripUserLinkUser)throws Exception;

	public Integer deleteGotripUserLinkUserById(@Param(value = "id") long id)throws Exception;





	/**
	 * 根据常用联系人名称查找用户
	 * @param linkUserName
	 * @return
	 */
	public Integer findUserLinkUserByName(@Param(value = "linkUserName") String linkUserName)throws Exception;


}
