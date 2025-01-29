import React, { useEffect, useState } from 'react';

function App() {
  const [messages, setMessages] = useState([]);
  const [socket, setSocket] = useState(null);
  const [dealersCards, setDealersCards] = useState([]);
  const [playersCards, setPlayersCards] = useState([]);
  const [pokerHand, setPokerHand] = useState([]);
  const [playerTurnBool, setPlayersTurnBool] = useState(false);

  useEffect(() => {
    const localSocket = new WebSocket("ws://localhost:5000/game")
    // const localSocket = io('http://localhost:5000');

    localSocket.onmessage = (event) => {
      setMessages((prevMessages) => [...prevMessages, event.data]);
    };

    setSocket(localSocket);

    localSocket.onopen = () => {
      setMessages((prevMessages) => [...prevMessages, event.data]);
    };

    localSocket.onclose = () => {
      console.log("Websocket closed");
    };
  }, []);

  const sendMessage = () => {
    socket.emit('message', 'Hello from React!');
  };

  return (
    <div>
      <h1>Welcome to Poker</h1>
      <h3>Dealer's Cards: {dealersCards}</h3>
      <h4>Your Cards: {playersCards}</h4>
      {playerTurnBool &&
          <div>
            <button>Bet</button>
            <button>Check</button>
            <button>Fold</button>
          </div>
      }
      <p>Current Poker Hand: </p>
      <button onClick={() => setPlayersTurnBool(!playerTurnBool)}>Change Player Turn Bool</button>
      <p>Log: {messages}</p>
    </div>
  );
}

export default App;