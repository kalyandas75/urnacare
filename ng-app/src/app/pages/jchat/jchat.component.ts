import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AccountService } from 'src/app/shared/service/account.service.js';
import * as JitsiMeetExternalAPI from '../../../assets/js/jitsi_api.js';
@Component({
  selector: 'app-jchat',
  templateUrl: './jchat.component.html',
  styleUrls: ['./jchat.component.scss']
})
export class JChatComponent implements OnInit {
  roomId;
  userIdentity;
  constructor(private route: ActivatedRoute, private accountService: AccountService) { }

  ngOnInit(): void {
    this.route.params.subscribe(p => {
      this.roomId = p['roomId'];
    });
  }

  ngAfterViewInit(): void {
     
    if(this.roomId) {
      this.accountService.identity()
      .then((user) => {
        this.userIdentity = user;
        console.log(this.userIdentity);
        const options = {
          roomName: "urnacare-" + this.roomId,
          width: '100%',
          height: 700,
          parentNode: document.querySelector('#chat'),
          userInfo: {
            email: this.userIdentity.email,
            displayName: ('ROLE_DOCTOR' === this.userIdentity.role ? 'Dr. ': '' ) + this.userIdentity.firstName + ' ' + this.userIdentity.lastName
          },
          configOverwrite: { 
            startWithVideoMuted: true,
            enableWelcomePage: false,
            prejoinPageEnabled: false,
            liveStreamingEnabled: false
          },
          interfaceConfigOverwrite: {
            APP_NAME: 'Urnacare',
            SHOW_CHROME_EXTENSION_BANNER: false,
            NATIVE_APP_NAME: 'Urnacare',
            SHOW_JITSI_WATERMARK: false,
            HIDE_DEEP_LINKING_LOGO: true,
            JITSI_WATERMARK_LINK: 'www.urnacare.com',
            DEFAULT_LOGO_URL: null,
            TOOLBAR_BUTTONS: [
              'microphone', 'camera', 'fullscreen',
              'fodeviceselection', 'hangup', 'profile', 'chat', 'etherpad', 
              'settings', 'videoquality', 'filmstrip', 'invite', 'shortcuts',
              'tileview', 'videobackgroundblur', 'mute-everyone', 'security'
          ],
          DEFAULT_WELCOME_PAGE_LOGO_URL: null
          }
        } 
        const api = new JitsiMeetExternalAPI('meet.jit.si', options);
        api.executeCommand('toggleChat');
      });

    }

  }

}
