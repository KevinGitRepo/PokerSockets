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

    localSocket.onmessage = (event) => {
      console.log(event.data);
      if ( event.data.includes( "Your Turn" ) ) {
        setPlayersTurnBool(true);
      }
      else if ( event.data.includes( "Dealer Cards" ) ) {
        setDealersCards( event.data.substring(event.data.indexOf('[') + 1, event.data.indexOf(']') ) );
      }
      else if ( event.data.includes( "Your Cards" ) ) {
        setPlayersCards( event.data.substring(event.data.indexOf('[') + 1, event.data.indexOf(']') ) );
      }
      setMessages(event.data );
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
      // socket.send(JSON.stringify({action: "create", data: playerData}));
      handleSocketSending("create", playerData);
      setPlayerCreatedBool(true);
  };

  const handleSocketSending = (action, message) => {
    socket.send(JSON.stringify({action: action, data: message}));
  }

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

  const handleCheck = () => {
    setPlayersTurnBool(false);
    handleSocketSending("check", "");
  }

  const handleFold = () => {
    setPlayersTurnBool(false);
    handleSocketSending("fold", "");
  }

  const handleBet = () => {
    setPlayersTurnBool(false);
    handleSocketSending("bet", 20);
  }

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
          {(playerCreatedBool && !playerTurnBool) &&
              <div>
                <p>Waiting for turn!</p>
              </div>
          }

          {(playerCreatedBool && playerTurnBool) &&
              <div>
                  <h1>Welcome to Poker</h1>
                  <h3>Dealer's Cards: {dealersCards}</h3>
                  <h4>Your Cards: {playersCards}</h4>
                  {playerTurnBool &&
                      <div>
                          <button onClick={handleBet}>Bet</button>
                          <button onClick={handleCheck}>Check</button>
                          <button onClick={handleFold}>Fold</button>
                      </div>
                  }
                  <p>Current Poker Hand: </p>
                  <button onClick={changePlayerTurn}>Change Player Turn Bool</button>

              </div>
          }
          <p>Log: {messages}</p>
      </div>
  );
}

export default App;