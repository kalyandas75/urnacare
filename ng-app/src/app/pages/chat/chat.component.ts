import { Component, OnInit, ViewChild, ElementRef, EventEmitter, ChangeDetectorRef } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ChatService } from 'src/app/shared/service/chat.service';

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent implements OnInit {
  @ViewChild('mainVideo') mainDivVideo: ElementRef;
  @ViewChild('otherVideos') otherVideos: ElementRef;
  @ViewChild('msgInput') msgInput: ElementRef;
  @ViewChild('mainAudio') mainAudio: ElementRef;

  roomId: string;
  partner: string;
  isDoctor = false;

  partnerOnline = false;

  connection;

  partnerCalling = false;

  msgs = [];

  audioOn = false;


  constructor(public activeModal: NgbActiveModal, private chatservice: ChatService,
    private changeDetectorRef: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.connection = this.chatservice.getConnectionObject();
    this.connection.session.data = true;
    this.connection.openOrJoin(this.roomId);

    this.connection.onstream = (event) => {
      console.log('onstream', event);
      this.audioOn = true;
      this.mainAudio.nativeElement.setAttribute('data-streamid', event.streamid);
      this.mainAudio.nativeElement.setAttribute('id', event.userid);
      this.mainAudio.nativeElement.srcObject = event.stream;
      /*setTimeout(function() {
        this.mainAudio.media.play();
      }, 5000);*/
    };

    this.connection.onstreamended = function(event) {
      this.audioOn = false;
    };

    this.connection.onopen = (event) => {
      //this.connection.send('hello everyone');
      console.log('on open', event);
    };
    
    this.connection.onmessage = (event) => {
      console.log('on message', event);
      if(event.data.enableAudio) {
        console.log('enabling audio ....');
        this.partnerCalling = true;
        this.changeDetectorRef.detectChanges();
      } else {
        this.msgs.push({
          msg: event.data.msg,
          at: new Date(),
          type: 'in'
        });
      }

    };

    this.connection.socket.on('disconnect', () => {
      //   location.reload();
      console.log('socket on disconnect');
      // this.connection.connectSocket(() => {
      //     console.log('Socket Reconnected');
      // });
      // alert('Socket Disconnected Please Refresh the Page');

    });

    this.connection.onSocketDisconnect = (event) => {
      console.log('onSocketDisconnect', event);
      // this.connection.connectSocket(() => {
      //   console.log('Socket Reconnected');
      // });
    };

    this.connection.onUserStatusChanged = (event) => {
        console.info('remote user status is', event.userid, event.status, event.status == 'online');
        if('online' == event.status) {
          this.partnerOnline = true;
        } else {
          this.partnerOnline = false;
        }
    };
  }

  sendMessage(msg: string) {
    console.log(this.connection);
    this.connection.send({
      msg: msg
    });
    if('enableAudio' !== msg) {

      this.msgs.push({
        msg: msg,
        at: new Date(),
        type: 'out'
      });
      this.msgInput.nativeElement.value = '';
    }

  }

  trackChatMessage(index: number, chatMessage: any) {
    return chatMessage.at;
  }

  onClose(reason) {
    this.connection.getAllParticipants().forEach(function(pid) {
      console.log(pid);
      //this.connection.disconnectWith(pid);
    });

    // stop all local cameras
    this.connection.attachStreams.forEach(function(localStream) {
        localStream.stop();
    });

    // close socket.io connection
    this.connection.closeSocket();
    this.activeModal.dismiss(reason);
  }

  onCall() {
    if(!this.partnerCalling) {
      this.connection.send({
        enableAudio: true
      });
    }
    this.call();
  }

  call() {
    this.connection.disconnect();
    this.connection.session.audio = true;
    this.connection.sdpConstraints.mandatory = {
      OfferToReceiveAudio: true,
      OfferToReceiveVideo: false
    };
   this.connection.join(this.roomId);
   this.partnerCalling = false;
  }

  onAcceptCall() {
    console.log('on accept call');
    this.call();
  }

  onCallEnd() {
    this.connection.streams.stop();
  }



}
