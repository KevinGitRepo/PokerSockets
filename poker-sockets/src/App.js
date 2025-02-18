import React, { useEffect, useState } from 'react';
import {CreatePlayer} from "./CreatePlayer";
import {Game} from "./Game";

function App() {
  const [messages, setMessages] = useState("");
  const [socket, setSocket] = useState(null);
  const [dealersCards, setDealersCards] = useState([]);
  const [playersCards, setPlayersCards] = useState([]);
  const [playerTurnBool, setPlayersTurnBool] = useState(false);
  const [playerCreatedBool, setPlayerCreatedBool] = useState(false);
  const [pot, setPot] = useState(0);
  const [roundBet, setRoundBet] = useState(0);

  const [gameOverBool, setGameOverBool] = useState(false);

  const [pName, setPName] = useState("");
  const [moneyLimit, setMoneyLimit] = useState(0);

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
      else if ( event.data.includes( "Bet Amount" ) ) {
        setMoneyLimit(
            parseInt(event.data.substring(event.data.indexOf('(') + 1, event.data.indexOf(')') ) ) );
      }
      else if ( event.data.includes("Pot") ) {
        setPot(
            parseInt(event.data.substring(event.data.indexOf('(') + 1, event.data.indexOf(')') ) ) );
      }
      else if ( event.data.includes( "won" ) ) {
        setGameOverBool(true);
      }
      else if ( event.data.includes( "started" ) ) {
        setGameOverBool(false);
      }
      else if ( event.data.includes( "Current Bet" ) ) {
        setRoundBet(event.data.substring(event.data.indexOf('(') + 1, event.data.indexOf(')') ) );
      }
      setMessages(event.data );
    };

    setSocket(localSocket);

    localSocket.onopen = () => {
      console.log("Connected to WebSocket");
    };

    localSocket.onclose = () => {
      console.log("Websocket closed");
      setPlayerCreatedBool(false);
    };

    return () => localSocket.close();
  }, []);

  const handleReady = () => {
    socket.send(JSON.stringify({action:"ready", data:""}));
  }

  return (
      <div>
          <CreatePlayer isPlayerCreated={playerCreatedBool}
                        playerName={pName}
                        playerMoney={moneyLimit}
                        setPlayerName={setPName}
                        setPlayerMoney={setMoneyLimit}
                        setIsPlayerCreated={setPlayerCreatedBool}
                        socket={socket}/>
          {(playerCreatedBool && !playerTurnBool) &&
              <div>
                <p>Waiting for turn!</p>
              </div>
          }

          <Game socket={socket}
                isPlayerCreated={playerCreatedBool}
                isPlayerTurn={playerTurnBool}
                setPlayersTurn={setPlayersTurnBool}
                dealersCards={dealersCards}
                playersCards={playersCards}
                moneyLimit={moneyLimit}
                setMoneyLimit={setMoneyLimit}
                setMessages={setMessages}
                setPot={setPot}
                roundBet={roundBet}/>
          <p>Log: {messages}</p>
          <p>Pot: ${pot}</p>

        {(gameOverBool && playerCreatedBool) &&
          <div>
            <button onClick={handleReady}>Ready up!</button>
          </div>
        }
      </div>
  );
}

export default App;
