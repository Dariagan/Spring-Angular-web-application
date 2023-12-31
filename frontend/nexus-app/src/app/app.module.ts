import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { LoginComponent } from './components/login/login.component';
import { NexuschiComponent } from './components/nexuschi.component';
import { NavButtonComponent } from './components/nav-button/nav-button.component';
import { NavComponent } from './components/nav.component';
import { FeedComponent } from './components/feed/feed.component';
import { ProfileComponent } from './components/profile/profile.component';
import { SignUpComponent } from './components/sign-up/sign-up.component';
import { TweetComponent } from './components/tweet/tweet.component';
import { ThreadComponent } from './components/thread/thread.component';
import { UsernameHyperlinkComponent } from './components/username-hyperlink.component';
import { BlockButtonComponent } from './components/block-button.component';
import { BanButtonComponent } from './components/ban-button.component';
import { UserImageComponent } from './components/user-image.component';
import { RedactableTextComponent } from './components/redactable-text.component';
import { UploadImagePromptComponent } from './components/upload-image-prompt.component';
import { WriteTweetComponent } from './components/write-tweet.component';
import { FollowButtonComponent } from './components/follow-button.component';
import { ChartComponent } from './components/chart/chart.component';
import { SearchbarComponent } from './components/searchbar.component';
import { ErrorComponent } from './components/error.component';
import { LogoutComponent } from './components/logout.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NexuschiComponent,
    NavButtonComponent,
    NavComponent,
    FeedComponent,
    ProfileComponent,
    SignUpComponent,
    TweetComponent,
    ThreadComponent,
    UsernameHyperlinkComponent,
    BlockButtonComponent,
    BanButtonComponent,
    UserImageComponent,
    RedactableTextComponent,
    UploadImagePromptComponent,
    WriteTweetComponent,
    FollowButtonComponent,
    ChartComponent,
    SearchbarComponent,
    ErrorComponent,
    LogoutComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
