package es.codeurjc.nexusapp.utilities.queriers;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import es.codeurjc.nexusapp.model.Tweet;
import es.codeurjc.nexusapp.model.User;
import es.codeurjc.nexusapp.service.TweetService;

public class FollowedUsersTweetsQuerier extends CollectionQueriable<User, Tweet, TweetService>
{
    public FollowedUsersTweetsQuerier(TweetService service)
    {
        super(service);
    }

    @Override
    public Collection<Tweet> doQuery(Optional<User> subject, Pageable pageable) 
    {
        return getService().getFollowedUsersTweets(subject, pageable).getContent();
    }
}
