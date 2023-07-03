import { Component, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Tweet } from 'app/models/tweet.model';
import { User } from 'app/models/user';
import { LoginService } from 'app/services/login.service';
import { TweetService } from 'app/services/tweet.service';
import { UserService } from 'app/services/user.service';
import { Observable } from 'rxjs';
import { ThreadComponent } from '../thread/thread.component';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css', './profile.boxes.css', './profile.style.css']
})
export class ProfileComponent {

  user?: User;
  viewingUser?: User;
  ownProfile?: boolean;
  blockedByViewer?: boolean;
  banned: boolean = false;
  getTweetsMethod!: (page: number, size: number) => Observable<Tweet[]>;
  @ViewChild(ThreadComponent) threadComponent!: ThreadComponent;

  ngOnInit(): void {
    this.activatedRoute.params.subscribe(params => {
      const username = params['username'];
      this.getTweetsMethod = (page: number) => this.tweetService.getUserTweets(username, page, 10);
      this.userService.getUser(username).subscribe(
        foundUser => {
          this.user = foundUser
          this.banned = this.user?.role == "BANNED";
          this.viewingUser = this.loginService.getUser();
          this.ownProfile = this.viewingUser && this.viewingUser.username == this.user?.username;
          this.blockedByViewer = !this.ownProfile && this.viewingUser && this.user && this.viewingUser.blocked.includes(this.user?.username);
        },
        error => error//TODO redirigir a 404 page si hay error
      )
    })
  }

  onBan(ban:boolean){
    this.threadComponent.refreshTweetsBanned(ban)
  }

  loadInLastTweet(){
    this.threadComponent.loadInLastTweet()
  }

  refreshOnBlock(block: boolean){
    this.blockedByViewer = block;
    this.threadComponent.refreshTweetsBlocked(block)
  }

  refreshTweets(){
    this.threadComponent.refreshTweets()
  }

  refreshTweetsViewingUser(){
    this.threadComponent.refreshUsers()
  }

  moderateButtonClicked(){
    
  }

  constructor(private loginService: LoginService, private userService: UserService, private tweetService: TweetService,private activatedRoute: ActivatedRoute){
    
  }
}
 