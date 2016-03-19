package by.it.dao;

import by.it.model.UserProfile;

public interface UserProfileDao extends IBaseDao<Long, UserProfile> {

    UserProfile findByType(String profName);

}
