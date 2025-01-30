import React, { useEffect, useState } from 'react';

function App() {
  const [messages, setMessages] = useState([]);
  const [socket, setSocket] = useState(null);
  const [dealersCards, setDealersCards] = useState([]);
  const [playersCards, setPlayersCards] = useState([]);
  const [pokerHand, setPokerHand] = useState([]);
  const [playerTurnBool, setPlayersTurnBool] = useState(false);
  const [playerCreatedBool, setPlayerCreatedBool] = useState(false);

  const [pName, setPName] = useState("");
  const [moneyLimit, setMoneyLimit] = useState("");

  useEffect(() => {
    const localSocket = new WebSocket("ws://localhost:8080/game");
    // const localSocket = io('http://localhost:5000');

    localSocket.onmessage = (event) => {
      setMessages((prevMessages) => [...prevMessages, event.data]);
    };

    setSocket(localSocket);

    localSocket.onopen = () => {
      console.log("Connected to WebSocket");
      // localSocket.send("Hello from client!");
    };

    localSocket.onclose = () => {
      console.log("Websocket closed");
      setPlayerCreatedBool(false);
    };

    return () => localSocket.close();
  }, []);

  const createPlayer = () => {
      const playerData = { name: pName, moneyLimit: moneyLimit};
      socket.send(JSON.stringify({action: "create", data: playerData}));
      setPlayerCreatedBool(true);
  };

  const changePlayerTurn = () => {
      setPlayersTurnBool(!playerTurnBool);
      // socket.send("Hello");
  };

  const handleNameChange = (event) => {
      setPName(event.target.value);
  };

  const handleMoneyLimitChange = (event) => {
      setMoneyLimit(event.target.value);
  };

  return (
      <div>
          {!playerCreatedBool &&
              <div>
                  <h1>Welcome!</h1>
                  <h2>Please enter your name and money limit:</h2>
                  <input type="text" value={pName} onChange={handleNameChange} placeholder="Enter Player Name"/>
                  <input type="text" value={moneyLimit} onChange={handleMoneyLimitChange} placeholder="Enter Your Money Limit"/>
                  <button onClick={createPlayer}>Submit</button>
              </div>
          }
          {playerCreatedBool &&
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
                  <button onClick={changePlayerTurn}>Change Player Turn Bool</button>
                  <p>Log: {messages}</p>
              </div>
          }
      </div>
  );
}

export default App;