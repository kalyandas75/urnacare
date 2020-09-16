import { Injectable } from '@angular/core';
import * as RTCMultiConnection from 'rtcmulticonnection';

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  connection = new RTCMultiConnection();
  constructor() {
    this.connection.socketURL = 'https://rtcmulticonnection.herokuapp.com:443/';
    // this.connection.socketURL = 'https://webrtcweb.com:9002/';
    this.connection.iceServers = [];

    // second step, set STUN url
    this.connection.iceServers.push({
        urls: [
          'stun:stun.l.google.com:19302',
          'stun:stun1.l.google.com:19302',
          'stun:stun2.l.google.com:19302',
          'stun:stun.l.google.com:19302?transport=udp'
        ]
    });

    this.connection.session = {
      audio: false,
      video: false,
      data: false
    };
  }

  getConnectionObject() {
    return this.connection;
  }

  getNewConnectionObject() {
    const newConnection = new RTCMultiConnection();
    newConnection.socketURL = 'https://rtcmulticonnection.herokuapp.com:443/';
    return newConnection;
  }
}
