package es.codeurjc.backend.utilities.queriers;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import es.codeurjc.backend.model.User;
import es.codeurjc.backend.service.UserService;

public class FollowingQuerier extends CollectionQueriable<User, User, UserService>
{
    public FollowingQuerier(UserService service)
    {
        super(service);
    }

    @Override
    public Collection<User> doQuery(Optional<User> subject, Pageable pageable) 
    {
        return getService().getFollowedBy(subject, pageable);
    }
}
