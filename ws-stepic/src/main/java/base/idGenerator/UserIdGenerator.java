package base.idGenerator;

import base.UserProfile;

public class UserIdGenerator <T>{

    private static long id = -1;
    public LongId<UserProfile> getAndIncrement(){
        return new LongId(++id);
    };
}
