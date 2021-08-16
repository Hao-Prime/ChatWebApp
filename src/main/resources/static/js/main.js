/**
 * 
 */
'use strict';
 
 
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('#connecting');
 
var stompClient = null;
var username = null;
  
 
function connect() {
    username = document.querySelector('#username').innerText.trim();
      
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
 
    stompClient.connect({}, onConnected, onError);
}
 
// Connect to WebSocket Server.
connect();
 
function onConnected() {
    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/publicChatRoom', onMessageReceived);
 
    stompClient.send("/app/chat.addUser",
        {},
        JSON.stringify({username: username, type: 'JOIN'})
    )
    var data1 = new FormData();
    var d = new Date();
    data1.append('username', username);
    data1.append('noidung',"");
    data1.append('type', "JOIN");
    data1.append('date',d.getHours()+":"+d.getMinutes()+" "+d.getDate()+"/"+d.getMonth()+"/"+d.getFullYear());
    data1.append('brower', "web");
    var xhr1 = new XMLHttpRequest();
    xhr1.open("POST", 'http://localhost:8080/api/save', true);
    xhr1.send(data1);
    // Tell your username to the server

    connectingElement.classList.add('hidden');
}
 
 
function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}
 
 
function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    var d = new Date();
    var data = new FormData();
    data.append('username', username);
    data.append('noidung', messageInput.value);
    data.append('type', "CHAT");
    data.append('date',d.getHours()+":"+d.getMinutes()+" "+d.getDate()+"/"+d.getMonth()+"/"+d.getFullYear());
    data.append('brower', "web");

    var xhr = new XMLHttpRequest();
    xhr.open("POST", 'http://localhost:8080/api/save', true);
    xhr.send(data);
    
    if(messageContent && stompClient) {
        var d = new Date();
        var tinNhan = {
            id:0,
            username: username,
            noidung: messageInput.value,
            type: 'CHAT',
            date: d.getHours()+":"+d.getMinutes()+" "+d.getDate()+"/"+d.getMonth()+"/"+d.getFullYear(),
            brower: 'web'
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(tinNhan));
        messageInput.value = '';
    }
    event.preventDefault();


}
 
 
function onMessageReceived(payload) {
    var tinNhan = JSON.parse(payload.body);
 
    var messageElement = document.createElement('li');
 
    if(tinNhan.type === 'JOIN') {
        messageElement.classList.add('event-message');
        tinNhan.noidung = tinNhan.username + ' đã tham gia!';
    } else if (tinNhan.type === 'LEFT') {
        messageElement.classList.add('event-message');
        tinNhan.noidung = tinNhan.username + ' đã rời khỏi!';
    } else {
        messageElement.classList.add('chat-message');   
        var usernameElement = document.createElement('strong');
        usernameElement.classList.add('nickname');
        
        var usernameText = document.createTextNode(tinNhan.username+": ");
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
    }



    var textElement = document.createElement('span');
    var messageText = document.createTextNode(tinNhan.noidung);
    textElement.appendChild(messageText);
 
    messageElement.appendChild(textElement);
 
    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}
  
  
messageForm.addEventListener('submit', sendMessage, true);