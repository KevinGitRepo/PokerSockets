import React, { useEffect, useState } from 'react';
import io from 'socket.io-client';

function App() {
  const [message, setMessage] = useState('');
  const [socket, setSocket] = useState(null);

  useEffect(() => {
    const localSocket = io('http://localhost:5000');

    localSocket.on('connect', () => {
      console.log('Connect to WebSocket');
    });

    localSocket.on('message', (data) => {
      setMessage(data);
    });

    setSocket(localSocket);

    return () => {
      localSocket.disconnect();
    };
  }, []);

  const sendMessage = () => {
    socket.emit('message', 'Hello from React!');
  };

  return (
    <div>
      <h1>Socket.IO Communication</h1>
      <button onClick={sendMessage}>Send Message to Server</button>
      <p>Message from server: {message}</p>
    </div>
  );
}

export default App;