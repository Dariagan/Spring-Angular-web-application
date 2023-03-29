package es.codeurjc.backend.controller;

import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RestController;


import es.codeurjc.backend.model.Tweet;
import es.codeurjc.backend.model.User;

import es.codeurjc.backend.service.UserService;
import es.codeurjc.backend.service.TweetService;

@RestController
public class DataBaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private TweetService tweetService;
    @Autowired
	private PasswordEncoder passwordEncoder;
   
    @PostConstruct
    public void init() {

        User.Builder builder = new User.Builder();
        Tweet.Builder tweetBuilder = new Tweet.Builder();

        // Building users
        builder
            .setUsername("a")
            .setEmail("a@a.com")
            .setEncodedPassword(passwordEncoder.encode("a"))
            .setAdmin();
        

        User userA = builder.build();
        User userB = builder.setUsername("b").setEmail("b@b.com").build();
        User userC = builder.setUsername("c").setEmail("c@c.com").build();
        
        userService.save(userA).save(userB).save(userC);

        userA.switchFollow(userB);
        
        // Building tweets
        tweetBuilder
            .setAuthor(userA)
            .setText("This is my first tweet!")
            .addTag("cars").addTag("sports").addTag("pets");
        
        Tweet tweet1 = tweetBuilder.build();

        tweet1.switchLike(userB, tweetService);
        //-------------------------------------
        tweetBuilder.setAuthor(userB).setText("I replied to userA's tweet!");
        
        Tweet tweet2 = tweetBuilder.build();

        tweet1.reply(tweet2, tweetService);
        

        tweetBuilder.setAuthor(userB);


        for (int i = 0; i < 20; i++){

            tweetService.save(tweetBuilder.setText("tweet " + i).build());
            if (i == 6)
                tweetBuilder.clearTags().addTag("sports").setAuthor(userC);
            if (i == 10)
                tweetBuilder.clearTags().addTag("pets").setAuthor(userA);
            if (i == 12)
                tweetBuilder.clearTags().addTag("cars").addTag("pets").setAuthor(userB);
            if (i == 16)
                tweetBuilder.clearTags().addTag("cars").addTag("sports").setAuthor(userB);
        }


    }
}
