import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { FeedComponent } from './components/feed/feed.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ChartComponent } from './components/chart/chart.component';

const routes: Routes = [
  { path: '', component: LoginComponent },
  { path: 'feed', component: FeedComponent},
  { path: 'chart', component: ChartComponent},
  {
    path: 'u/:username',
    component: ProfileComponent
  },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }